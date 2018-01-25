package net.doyouhike.app.bbs.ui.release.sendutil;

import android.text.TextUtils;

import net.doyouhike.app.bbs.biz.event.PostLiveEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.SendingTimeline;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventDetailResp;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventPostResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.adapter.NodeTimelineAdapter;
import net.doyouhike.app.bbs.util.LogUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import de.greenrobot.event.EventBus;

/**
 * 功能：上传直播对象类
 *
 * @author：曾江 日期：16-2-29.
 */
public abstract class BaseSendEntity {

    private String TAG = SendUtil.class.getSimpleName();

    public abstract BasePostRequest getSendLiveParam();

    protected abstract void updateLocalState();

    protected abstract void sendSuccess();


    private SendingTimeline timeline;
    private AtomicBoolean hasCancel = new AtomicBoolean(false);
    private AtomicBoolean hasSend = new AtomicBoolean(false);
    private ArrayBlockingQueue<EventDetailResp.ContentBean> waitUploadImgQueue;

    public BaseSendEntity() {
    }

    public void initialize(SendingTimeline timeline) {
        this.timeline = timeline;
        initUploadImgs();
    }


    public synchronized Boolean isReadySendLive() {

        if (hasCancel.get()) {
            return false;
        }

        if (hasSend.get()) {
            return false;
        }

        if (!isImgAllUpload()) {
            return false;
        }


        if (hasSend.getAndSet(true)) {
            return false;
        }

        return true;
    }


    public boolean isImgAllUpload() {

        if (!isEmptyImage()) {

            for (EventDetailResp.ContentBean item : getImgs()) {

                if (TextUtils.isEmpty(item.getWhole_photo_path())) {
                    continue;
                }

                if (item.getStatus() == EventDetailResp.ContentBean.UPLOAD_FAIL ||
                        item.getStatus() == EventDetailResp.ContentBean.UPLOADING) {
                    return false;
                }

            }

        }

        return true;
    }

    public void resetHasSend() {
        hasSend.set(false);
    }

    public void setHasSend(boolean hasSend) {
        this.hasSend.set(hasSend);
    }

    public boolean getHasCancel() {
        return hasCancel.get();
    }

    public boolean getHasSend() {
        return hasSend.get();
    }

    public void setHasCancel(boolean cancel) {
        hasCancel.set(cancel);
    }

    public EventDetailResp.ContentBean getWaitUploadImg() {
        return waitUploadImgQueue.poll();
    }


    public SendingTimeline getTimeline() {
        return timeline;
    }


    public void setTimeline(SendingTimeline timeline) {
        this.timeline = timeline;
    }


    /**
     * 初始化上传的图片队列,图片ID为空则为未上传图片
     */
    private void initUploadImgs() {

        initUploadQueue();

        if (!isEmptyImage()) {

            for (EventDetailResp.ContentBean image : getImgs()) {
                if (image.getType() != null
                        && image.getType().equals(Constant.IMAGE)
                        && TextUtils.isEmpty(image.getPhoto_id())
                        && !TextUtils.isEmpty(image.getWhole_photo_path())
                        && image.getStatus() == Timeline.ImagesEntity.UPLOAD_FAIL) {
                    waitUploadImgQueue.add(image);
                }
            }
        }
    }


    protected boolean isEmptyImage() {
        return null == getImgs() || getImgs().isEmpty();
    }

    private void initUploadQueue() {
        waitUploadImgQueue = new ArrayBlockingQueue<>(9);

    }

    protected List<EventDetailResp.ContentBean> getImgs() {
        return timeline.getEvent().getEvent_contents();
    }

    /**
     * 执行发送,图片上传完成后会执行此命令
     */
    public void postLive() {

        if (getTimeline().getReleaseStatus() != Content.SENDING) {
            return;
        }

        LogUtil.d(TAG, "开始发送直播数据");

        ApiReq.doPost(getSendLiveParam(), new IOnResponseListener<Response<EventPostResp>>() {
            @Override
            public void onSuccess(Response<EventPostResp> response) {
                //发送成功,去掉本地缓存
                sendSuccess();
                LogUtil.d(TAG, "postLive 发送直播成功！");
                if (response.getData() != null)
                    getTimeline().setNode_id(response.getData().getNode_id());
                getTimeline().setTime(System.currentTimeMillis());
                getTimeline().setReleaseStatus(Content.SEND_OK);
                setHasSend(true);
                setSendState(Content.SEND_OK, Content.RELEASE_LIVE_SUCCUSS);
            }

            @Override
            public void onError(Response response) {
                int liveFail = Content.RELEASE_LIVE_FAIL;
                int status = Content.SEND_FAIL;

                if (response.getCode() == Content.RELEASE_LIVE_FORBEDING) {
                    //被禁用户发送约伴
                    liveFail = Content.RELEASE_LIVE_FORBEDING;
                    status = Content.RELEASE_USER_LIMIT;
                } else if (response.getCode() == Content.RELEASE_LIVE_OVER_MORE) {
                    //发布次数超限制
                    liveFail = Content.RELEASE_LIVE_OVER_MORE;
                    status = Content.RELEASE_LIVE_OVER_COUNT;
                }
                LogUtil.d(TAG, "postLive 发送直播失败！" + response.getMsg());
                resetHasSend();
                setSendState(status, liveFail);
            }
        });


    }


    /**
     * 更新发送状态
     *
     * @param status
     * @param msgType
     */
    public void setSendState(int status, int msgType) {

        getTimeline().setReleaseStatus(status);

        if (getHasCancel()) {
            return;
        }

        if (!getHasSend()) {
            //为了避免在发送直播过程中(非发送图片过程)被取消,不改变本地缓存数据,否则会导致重发直播
            updateLocalState();
        }
        EventBus.getDefault().post(new PostLiveEvent(msgType, getTimeline()));
    }

    public void onUploadSuc() {

        if (isReadySendLive()) {
            LogUtil.d(TAG, "图片全部上传完成");
            postLive();
        } else {
            //更新已发送的图片信息到本地
            updateLocalState();
        }
    }

    public void onUploadFail() {

        setSendState(Content.SEND_FAIL, Content.RELEASE_LIVE_FAIL);
        //不要调换顺序,否则收不到eventbus 消息
        setHasCancel(true);
    }
}
