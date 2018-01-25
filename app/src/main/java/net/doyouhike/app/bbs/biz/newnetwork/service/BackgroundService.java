package net.doyouhike.app.bbs.biz.newnetwork.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.view.WindowManager;

import com.flyco.dialog.widget.MaterialDialog;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.event.CheckoutEvent;
import net.doyouhike.app.bbs.biz.event.GlobalDialogEvent;
import net.doyouhike.app.bbs.biz.event.LogoutEvent;
import net.doyouhike.app.bbs.biz.event.PostLiveEvent;
import net.doyouhike.app.bbs.biz.event.ResendLiveEvent;
import net.doyouhike.app.bbs.biz.event.SendEvent;
import net.doyouhike.app.bbs.biz.event.SendLive;
import net.doyouhike.app.bbs.biz.event.im.GetMyImIdRequestEvent;
import net.doyouhike.app.bbs.biz.event.im.GetOtherHxIdEvent;
import net.doyouhike.app.bbs.biz.event.im.GetUserInfoByHxIdEvent;
import net.doyouhike.app.bbs.biz.event.im.HxIdsRequestEvent;
import net.doyouhike.app.bbs.biz.event.im.SendGroupMsgEvent;
import net.doyouhike.app.bbs.biz.event.im.SendPrivateMsgEvent;
import net.doyouhike.app.bbs.biz.event.open.AccountFollowsListEvent;
import net.doyouhike.app.bbs.biz.helper.im.HxidDispatcher;
import net.doyouhike.app.bbs.biz.helper.im.ImLoginHelper;
import net.doyouhike.app.bbs.biz.helper.im.action.HxidActionCreater;
import net.doyouhike.app.bbs.biz.helper.im.privatemsg.PrivateMsgSender;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.MyImInfo;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.SendingTimeline;
import net.doyouhike.app.bbs.biz.nohttp.CallServer;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.request.users.ims.UsersSelfImGet;
import net.doyouhike.app.bbs.biz.openapi.response.account.AccountUserSelfImResp;
import net.doyouhike.app.bbs.ui.home.popupmenu.fixedtag.TagFactory;
import net.doyouhike.app.bbs.ui.home.topic.TimelineRequestType;
import net.doyouhike.app.bbs.ui.release.sendutil.SendUtil;
import net.doyouhike.app.bbs.ui.widget.common.dialog.PasswordChangeDialog;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.StringUtil;

import java.util.Timer;
import java.util.TimerTask;

import de.greenrobot.event.EventBus;

/**
 * 发布服务
 */
public class BackgroundService extends Service {


    SendUtil sendUtil;

    public BackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        EventBus.getDefault().register(this);
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        resetState();
        //注册事件放在后面防止在重置状态过程中,有发送请求,导致数据混乱

    }

    @Override
    public boolean onUnbind(Intent intent) {
        EventBus.getDefault().unregister(this);
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        onCancel();
        PasswordChangeDialog.getInstance().onDestroy();
        super.onDestroy();
    }


    /**
     * @param event 群发消息
     */
    public void onEventBackgroundThread(SendGroupMsgEvent event) {


        HxidDispatcher.getInstance().dispatch(HxidActionCreater.createGroupMsgAction(event.getMsgDetail()));


    }

    /**
     * 获取用户信息
     *
     * @param event
     */
    public void onEventBackgroundThread(GetUserInfoByHxIdEvent event) {

        HxidDispatcher.getInstance().dispatch(HxidActionCreater.createRequestUserAction(event.getHxId()));

    }

    /**
     * @param event 批量请求环信ID
     */
    public void onEventBackgroundThread(HxIdsRequestEvent event) {

        HxidDispatcher.getInstance().dispatch(HxidActionCreater.createListRequestAction(event.getUuIds()));


    }

    /**
     * 获取环信ID
     *
     * @param event
     */
    public void onEventBackgroundThread(GetOtherHxIdEvent event) {

        HxidDispatcher.getInstance().dispatch(HxidActionCreater.createRequestIdAction(event.getUserId()));

    }

    /**
     * @param event 重发直播
     */
    public void onEventBackgroundThread(ResendLiveEvent event) {
        if (event.getTimeline().getSendingId() == SendingTimeline.ID_SENDING_LIVE) {
            sendLive(event.getTimeline());
        } else if (event.getTimeline().getSendingId() == SendingTimeline.ID_SENDING_EVENT) {
            sendEvent(event.getTimeline());
        }
    }

    /**
     * @param event 发送直播事件
     */
    public void onEventBackgroundThread(SendLive event) {

        SendingTimeline timeline = event.getTimeline();

        if (timeline == null) {
            return;
        }
        SharedPreferencesManager.setPostLive(timeline);
        //切换到关注标签
        EventBus.getDefault().post(new CheckoutEvent(TagFactory.createTag(TimelineRequestType.ATTEND)));
        //在关注中显示
        EventBus.getDefault().post(new PostLiveEvent(Content.RELEASE_TAG, timeline));

        sendLive(timeline);

    }

    /**
     * @param event 发送约伴活动事件
     */
    public void onEventBackgroundThread(SendEvent event) {

        SendingTimeline timeline = event.getTimeline();
        if (timeline == null) {
            return;
        }
        if (StringUtil.isEmpty(timeline.getTempNoteId()))
            SharedPreferencesManager.setPostEvent(timeline);
        //切换到关注标签
        EventBus.getDefault().post(new CheckoutEvent(TagFactory.createTag(TimelineRequestType.ATTEND)));
        //在关注中显示
        EventBus.getDefault().post(new PostLiveEvent(Content.RELEASE_TAG, timeline));

        sendEvent(timeline);

    }


    /**
     * 发送约伴活动
     *
     * @param timeline
     */
    private void sendEvent(SendingTimeline timeline) {

        //发送直播
        if (null == sendUtil) {
            sendUtil = SendUtil.getInstance();
        }
        sendUtil.sendAction(MyApplication.getInstance().getApplicationContext(), timeline);
    }


    /**
     * 发送直播
     *
     * @param timeline
     */
    private void sendLive(SendingTimeline timeline) {

        //发送直播
        if (null == sendUtil) {
            sendUtil = SendUtil.getInstance();
        }
        sendUtil.sendLive(MyApplication.getInstance().getApplicationContext(), timeline);
    }

    public void onEventMainThread(LogoutEvent event) {
        onCancel();
        sendUtil = null;


    }

    private void onCancel() {
        if (null != sendUtil) {
            sendUtil.cancelLiveTask();
            sendUtil.cancelEventTask();
        }
    }

    /**
     * 重置本地缓存的发送timeline,如果是发送中的状态,则重置为失败状态
     * 因为有可能出现发送过程中状态没有改变,可能程序异常退出,或其他情况,导致发送状态没更新,属于不正常状况
     */
    private void resetState() {

        if (null == sendUtil) {

            SendingTimeline sendingLive = SharedPreferencesManager.getPostLive();
            //重置本地缓存的发送直播,如果是发送中的状态,则重置为失败状态
            if (null != sendingLive && sendingLive.getReleaseStatus() == Content.SENDING) {
                sendingLive.setReleaseStatus(Content.SEND_FAIL);
                SharedPreferencesManager.setPostLive(sendingLive);
            }

            SendingTimeline sendingEvent = SharedPreferencesManager.getPostEvent();
            //重置本地缓存的发送约伴,如果是发送中的状态,则重置为失败状态
            if (null != sendingEvent && sendingEvent.getReleaseStatus() == Content.SENDING) {
                sendingEvent.setReleaseStatus(Content.SEND_FAIL);
                SharedPreferencesManager.setPostEvent(sendingEvent);
            }
        }
    }

    /**
     * 获取IM信息失败,重新尝试
     */
    int retryCount = 0;

    /**
     * @param event 获取当前用户环信信息
     */
    public void onEventBackgroundThread(final GetMyImIdRequestEvent event) {

        LogUtil.d("BackgroundService", "GetMyImIdRequestEvent");

        ApiReq.doGet(new UsersSelfImGet(event.getUuid()), new IOnResponseListener<Response<AccountUserSelfImResp>>() {
            Timer timer = new Timer();

            @Override
            public void onSuccess(Response<AccountUserSelfImResp> response) {
                if (response.getData() != null && response.getData().getIm() != null && !response.getData().getIm().isEmpty()) {

                    MyImInfo myImInfo = response.getData().getIm().get(0);

                    new ImLoginHelper().doImLogin(myImInfo);

                } else {

                    retry();
                }
            }


            @Override
            public void onError(Response response) {
                retry();
            }

            private void retry() {
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        retryCount++;
                        if (retryCount < 5) {
                            onEventBackgroundThread(event);     //请求环信用户信息
                        } else {
                            retryCount = 0;
                        }
                    }
                }, 5000);

            }
        });

    }


    /**
     * @param event 全局对话框事件
     */
    public void onEventMainThread(GlobalDialogEvent event) {

        MaterialDialog dialog = event.getGlobalDialog(this);

        if (null != dialog.getWindow()) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            dialog.show();
        }


    }

    /**
     * @param event 发送悄悄话事件
     */
    public void onEventMainThread(SendPrivateMsgEvent event) {
        //执行发送悄悄话
        PrivateMsgSender.getInstance().sendMsg(event.getReceiverId());
    }

    /**
     * 获取关注列表
     *
     * @param event
     */
    public void onEventBackgroundThread(AccountFollowsListEvent event) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                retryCount++;
                if (retryCount < 5) {
                    UsersHelper.getSingleTon().getNetUserFollows();
                } else {
                    retryCount = 0;
                }
            }
        }, 1000);
    }


}
