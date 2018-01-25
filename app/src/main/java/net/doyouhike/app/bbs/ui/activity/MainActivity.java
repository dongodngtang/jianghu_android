/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: MainActivity.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-9-25
 *
 * Changes (from 2015-9-25)
 * -----------------------------------------------------------------
 * 2015-9-25 创建MainActivity.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 * from 2015-10-4 添加changeMeOrOtherFragment方法，
 * 				  用于更换“个人页面”的Fragment(ChenWeiZhen);
 * -----------------------------------------------------------------
 * from 2015-10-4 把跳到主页的fragment改为我自己定义的LiveFragment类(wu-yoline);
 * -----------------------------------------------------------------
 * from 2015-10-19 实现父类onDestroy()方法(wu-yoline);
 * -----------------------------------------------------------------
 * from 2015-11-04 打开发布按钮进入入口;(wu-yoline)
 * -----------------------------------------------------------------
 */

package net.doyouhike.app.bbs.ui.activity;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.alibaba.sdk.android.feedback.util.IWxCallback;
import com.flyco.dialog.listener.OnBtnClickL;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;
import com.hyphenate.EMMessageListener;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;
import com.newrelic.agent.android.NewRelic;

import net.doyouhike.app.bbs.BuildConfig;
import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.db.dao.UserInfoDbUtil;
import net.doyouhike.app.bbs.biz.entity.JPushNotificationInfo;
import net.doyouhike.app.bbs.biz.entity.im.GroupMsgDetail;
import net.doyouhike.app.bbs.biz.event.CheckoutEvent;
import net.doyouhike.app.bbs.biz.event.GoToLiveListEvent;
import net.doyouhike.app.bbs.biz.event.LogoutEvent;
import net.doyouhike.app.bbs.biz.event.SendLive;
import net.doyouhike.app.bbs.biz.event.im.ChatMsgCountEvent;
import net.doyouhike.app.bbs.biz.event.im.GetMyImIdRequestEvent;
import net.doyouhike.app.bbs.biz.event.im.SendGroupMsgEvent;
import net.doyouhike.app.bbs.biz.event.live.CheckoutMainPageEvent;
import net.doyouhike.app.bbs.biz.event.live.TotalUnreadCountEvent;
import net.doyouhike.app.bbs.biz.event.login.LoginEvent;
import net.doyouhike.app.bbs.biz.event.open.UserSettingEvent;
import net.doyouhike.app.bbs.biz.helper.im.ImLoginHelper;
import net.doyouhike.app.bbs.biz.helper.jiguang.JPushUtil;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.MyImInfo;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.SendingTimeline;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.newnetwork.service.BackgroundService;
import net.doyouhike.app.bbs.biz.openapi.presenter.UserSettingHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.request.users.ims.UsersSelfImGet;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserSettingResp;
import net.doyouhike.app.bbs.chat.chatui.ConversationListFragment;
import net.doyouhike.app.bbs.chat.helper.EMHelper;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.activity.me.SettingBindPhoneActivity;
import net.doyouhike.app.bbs.ui.adapter.live.PageFragmentAdapter;
import net.doyouhike.app.bbs.ui.fragment.ActionFragment;
import net.doyouhike.app.bbs.ui.fragment.MeFragment;
import net.doyouhike.app.bbs.ui.fragment.RoadListFragment;
import net.doyouhike.app.bbs.ui.home.HomeFragment;
import net.doyouhike.app.bbs.ui.home.popupmenu.fixedtag.TagFactory;
import net.doyouhike.app.bbs.ui.home.popupmenu.popuprelease.PopupReleaseMenu;
import net.doyouhike.app.bbs.ui.home.topic.TimelineRequestType;
import net.doyouhike.app.bbs.ui.release.NewEditLiveActivity;
import net.doyouhike.app.bbs.ui.release.yueban.EditEventActivity;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.widget.action.ForbidSlideViewPage;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.ShareUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;
import net.doyouhike.app.library.ui.eventbus.EventCenter;
import net.doyouhike.app.library.ui.netstatus.NetUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.jpush.android.api.JPushInterface;
import de.greenrobot.event.EventBus;
import me.leolin.shortcutbadger.ShortcutBadger;

/**
 *
 */
public class MainActivity extends net.doyouhike.app.bbs.base.activity.BaseActivity implements OnClickListener {

    public static final int REQUEST_CODE_TO_RELEASE_LIVE = 2135;
    private static final int HANDLE_MSG_WHAT_REQUEST_IM_INFO = 2001;

    public static final String INTENT_EXTRA_NAME_IS_LOOK_AROUND = "isLookAround";


    /**
     * 页码：主页
     */
    public static final int PAGE_PIECES = 0;
    /**
     * 页码：活动
     */
    private static final int PAGE_ACTION = 1;

    /**
     * 页码：线路
     */
    private static final int PAGE_ROAD = 2;
    /**
     * 页码：消息
     */
    public static final int PAGE_MESSAGE = 3;
    /**
     * 页码：个人
     */
    public static final int PAGE_ME = 4;


    /**
     * 当前页码
     */
    private int nowPageType = PAGE_PIECES;


    /**
     * 主要内容块
     */
    private ForbidSlideViewPage viewPager;
    /**
     * mViewPager的适配器
     */
    private PageFragmentAdapter fragmentAdapter;
    /**
     * mFragmentAdapter的数据源
     */
    private List<Fragment> listFragment = new ArrayList<Fragment>();

    /**
     * 直播页图标
     */
    private ImageView piecesIv;
    /**
     * 直播页文字
     */
    private TextView piecesTv;

    /**
     * 活动页图标
     */
    private ImageView actionIv;
    /**
     * 活动页文字
     */
    private TextView actionTv;

    /**
     * 消息页图标
     */
    private ImageView messageIv;
    /**
     * 消息页文字
     */
    private TextView messageTv;

    /**
     * 消息页图标右上角,未读消息总数
     */
    private TextView mTvUnreadMsgCount;
    /**
     * 未读消息指示,消息标签红小圆点
     */
    private ImageView mIvUnreadMsgIndicate;

    /**
     * 我页图标
     */
    private ImageView meIv;
    /**
     * 我页文字
     */
    private TextView meTv;

    /**
     * 直播页按钮
     */
    private RelativeLayout piecesRlyt;
    /**
     * 活动页按钮
     */
    private RelativeLayout actionRlyt;
    /**
     * 线路按钮
     */
    private RelativeLayout rlyt_road;

    /**
     * 线路图标
     */
    private ImageView roadIv;
    /**
     * 线路文字
     */
    private TextView roadTv;

    /**
     * 我有未读消息
     */
    private ImageView iv_me_msg_not_read;
    /**
     * 目的地页按钮
     */
    private RelativeLayout messageRlyt;
    /**
     * 我页按钮
     */
    private RelativeLayout meRlyt;

    /**
     * 直播的Fragment
     */

    private HomeFragment liveFragment;
    /**
     * 活动的Fragment
     */
    private ActionFragment actionFragment;
    /**
     * 消息的Fragment
     */
    private ConversationListFragment mseeageFragment;

    /**
     * 线路的Fragment
     */
    private RoadListFragment roadFragment;


    /**
     * 我页"自己"的Fragment
     */
    private MeFragment meFragment;


    private boolean isLookAround;
    /**
     * 直播约伴
     */
    public PopupReleaseMenu popupReleaseMenu;
    /**
     * 退出应用弹窗
     */
    ActionSheetDialog quitAppDialog;
    WeakReference<MainActivity> weakInstance;
    /**
     * 发送timeline的handler
     * 防止在service没有注册完成,收不到EventBus消息
     */
    private Handler mPostTimeLineHandler = new Handler() {


        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String strData = (String) msg.obj;
            switch (msg.what) {

                case HANDLE_MSG_WHAT_REQUEST_IM_INFO:
                    EventBus.getDefault().post(new GetMyImIdRequestEvent(strData));      //请求环信用户信息
                    break;
            }
        }
    };


    private void initData() {


        initLoginData();


        Intent intent = this.getIntent();
        if (intent != null) {
            isLookAround = intent.getBooleanExtra(
                    INTENT_EXTRA_NAME_IS_LOOK_AROUND, false);
        }


    }

    private void initLoginData() {
        if (UserInfoUtil.getInstance().isLogin()) {
            // 发送获取是否接收推送信息的请求
            UsersHelper.getSingleTon().getUserSetting(mContext, UserInfoUtil.getInstance().getUserId(), settingListener);
            // 用户数据相关（me模块数据源相关）,避免MainActivity打开时进行大量请求
            SharedPreferencesManager.setUserId(UserInfoUtil.getInstance().getUserId());
            // 初始化极光推送
            JPushUtil.getInstance().init(MyApplication.getInstance());
            //阿里百川 反馈
            FeedbackAPI.initAnnoy(MyApplication.getInstance(), BuildConfig.FEED_BACK_KEY);
            FeedbackAPI.getFeedbackUnreadCount(MyApplication.getInstance(), null, new IWxCallback() {
                @Override
                public void onSuccess(Object... objects) {
                    int feedbackNum = (int) objects[0];
                    SharedPreferencesManager.setFeedBackNum(feedbackNum);
                }

                @Override
                public void onError(int i, String s) {

                }

                @Override
                public void onProgress(int i) {

                }
            });

            //环信登录
            MyImInfo info = SharedPreferencesManager.getImUserInfo();

            if (null != info) {
                new ImLoginHelper().doImLogin(info);
                //请求环信登陆信息,只是刷新网络数据,不需要执行回调执行,一般用户注册好了,这些信息都不会变的
                ApiReq.doGet(new UsersSelfImGet(UserInfoUtil.getInstance().getUUID()));
            } else {
                //请求环信用户信息
                mPostTimeLineHandler.obtainMessage(HANDLE_MSG_WHAT_REQUEST_IM_INFO, UserInfoUtil.getInstance().getUUID()).sendToTarget();
            }

        }
    }

    IOnResponseListener<Response<UserSettingResp>> settingListener = new IOnResponseListener<Response<UserSettingResp>>() {
        @Override
        public void onSuccess(Response<UserSettingResp> response) {
            UserSettingHelper.getInstance().setUserSettingBean(response.getData().getUser_setting());
        }

        @Override
        public void onError(Response response) {

        }
    };

    public void onEvent(UserSettingEvent event) {
        if (event.getCode() == 0) {
            UserSettingHelper.getInstance().updateSettingBean();
        }
    }

    /**
     * 绑定控件
     */
    private void bindControl() {
        weakInstance = new WeakReference<>(this);

        viewPager = (ForbidSlideViewPage) findViewById(R.id.vp_main);
        viewPager.setOffscreenPageLimit(5);
        viewPager.setCanScroll(false);

        piecesIv = (ImageView) findViewById(R.id.iv_pieces);
        piecesIv.setSelected(true);
        piecesTv = (TextView) findViewById(R.id.tv_pieces);
        piecesTv.setTextColor(getResources().getColor(R.color.clickable));

        actionIv = (ImageView) findViewById(R.id.iv_action);
        actionIv.setSelected(false);
        actionTv = (TextView) findViewById(R.id.tv_action);

        messageIv = (ImageView) findViewById(R.id.iv_message);
        messageIv.setSelected(false);
        messageTv = (TextView) findViewById(R.id.tv_message);

        meIv = (ImageView) findViewById(R.id.iv_me);
        meIv.setSelected(false);
        meTv = (TextView) findViewById(R.id.tv_me);

        mTvUnreadMsgCount = (TextView) findViewById(R.id.tv_item_msg_count);
        mIvUnreadMsgIndicate = (ImageView) findViewById(R.id.iv_item_msg_has_unread);

        piecesRlyt = (RelativeLayout) findViewById(R.id.rlyt_pieces);
        actionRlyt = (RelativeLayout) findViewById(R.id.rlyt_activity);

        rlyt_road = (RelativeLayout) findViewById(R.id.rlyt_road);
        messageRlyt = (RelativeLayout) findViewById(R.id.rlyt_message);
        meRlyt = (RelativeLayout) findViewById(R.id.rlyt_me);
        iv_me_msg_not_read = (ImageView) findViewById(R.id.iv_me_no_read);

        roadIv = (ImageView) findViewById(R.id.iv_road);
        roadTv = (TextView) findViewById(R.id.tv_raod);
    }

    private void setAdapterToViewPage() {


        liveFragment = new HomeFragment();
//        meFragment = new MeFragment(this);
        meFragment = new MeFragment();
        mseeageFragment = new ConversationListFragment();
        actionFragment = new ActionFragment();
        if (null == roadFragment)
            roadFragment = new RoadListFragment();
        listFragment.add(liveFragment); // 首页Fragment
        listFragment.add(actionFragment); // 活动Fragment
        listFragment.add(roadFragment);
        listFragment.add(mseeageFragment); // 消息Fragment
        listFragment.add(meFragment); // 我的Fragment


        fragmentAdapter = new PageFragmentAdapter(getSupportFragmentManager(),
                listFragment);
        viewPager.setAdapter(fragmentAdapter);

        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {

                piecesIv.setSelected(false);
                piecesTv.setTextColor(getResources().getColor(
                        R.color.main_bottom_bar_word));
                actionIv.setSelected(false);
                actionTv.setTextColor(getResources().getColor(
                        R.color.main_bottom_bar_word));
                messageIv.setSelected(false);
                messageTv.setTextColor(getResources().getColor(
                        R.color.main_bottom_bar_word));
                meIv.setSelected(false);
                meTv.setTextColor(getResources().getColor(
                        R.color.main_bottom_bar_word));
                roadIv.setSelected(false);
                roadTv.setTextColor(getResources().getColor(
                        R.color.main_bottom_bar_word));

                viewPager.setCurrentItem(nowPageType);

                switch (nowPageType) {
                    case PAGE_PIECES:
                        piecesIv.setSelected(true);
                        piecesTv.setTextColor(getResources().getColor(
                                R.color.clickable));
                        break;
                    case PAGE_ACTION:
                        actionIv.setSelected(true);
                        actionTv.setTextColor(getResources().getColor(
                                R.color.clickable));
                        break;
                    case PAGE_MESSAGE:
                        if (UserInfoUtil.getInstance().isLogin()) {
                            messageIv.setSelected(true);
                            messageTv.setTextColor(getResources().getColor(
                                    R.color.clickable));
                        } else {
                            openLoginActivity();
                        }
                        break;
                    case PAGE_ME:
                        if (UserInfoUtil.getInstance().isLogin()) {
                            meIv.setSelected(true);
                            meTv.setTextColor(getResources().getColor(
                                    R.color.clickable));
                        } else {
                            openLoginActivity();
                        }
                        break;
                    case PAGE_ROAD:
                        roadIv.setSelected(true);
                        roadTv.setTextColor(getResources().getColor(
                                R.color.clickable));
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrolled(int position, float offset, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int position) {
            }
        });
    }

    /**
     * 特定的时机，未登录时调用
     */
    private void openLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    /**
     * 为控件添加监听者
     */
    private void setListener() {
        piecesRlyt.setOnClickListener(this);
        actionRlyt.setOnClickListener(this);
        rlyt_road.setOnClickListener(this);
        messageRlyt.setOnClickListener(this);
        meRlyt.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        viewPager.setCanScroll(true);

        switch (v.getId()) {
            case R.id.rlyt_pieces:
                nowPageType = PAGE_PIECES;
                viewPager.setCurrentItem(PAGE_PIECES, false);
                break;
            case R.id.rlyt_activity:
                nowPageType = PAGE_ACTION;
                viewPager.setCurrentItem(PAGE_ACTION, false);
                break;
            case R.id.rlyt_road:
                nowPageType = PAGE_ROAD;
                viewPager.setCurrentItem(PAGE_ROAD, false);
                break;
            case R.id.rlyt_message:
                if (UserInfoUtil.getInstance().isLogin()) {
                    nowPageType = PAGE_MESSAGE;
                    viewPager.setCurrentItem(PAGE_MESSAGE, false);
                    // 清除通知栏消息
                    JPushInterface.clearAllNotifications(getApplicationContext());

                } else {
                    openLoginActivity();
                }
                break;
            case R.id.rlyt_me:
                if (UserInfoUtil.getInstance().isLogin()) {
                    nowPageType = PAGE_ME;
                    iv_me_msg_not_read.setVisibility(View.GONE);
                    viewPager.setCurrentItem(PAGE_ME, false);
                } else {
                    openLoginActivity();
                }
                break;

            default:
                break;
        }

        viewPager.setCanScroll(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (nowPageType == PAGE_MESSAGE) {
            // 清除通知栏消息
            JPushInterface.clearAllNotifications(getApplicationContext());
        }
        JPushUtil.getInstance().setAlias();
        EMClient.getInstance().chatManager().addMessageListener(messageListener);
        //发送草稿箱里的消息,当有群聊消息发送失败时,重试发送
        sendGroupDraftMsg();
    }

    @Override
    protected void onStop() {
        EMClient.getInstance().chatManager().removeMessageListener(messageListener);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unbindService(connection);

        if (null != mPostTimeLineHandler) {
            mPostTimeLineHandler.removeCallbacksAndMessages(null);
            mPostTimeLineHandler = null;
        }
        super.onDestroy();
    }

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }

    @Override
    protected void initViewsAndEvents() {
        //开启发送服务
        bindSendService();

        GlideHelper.setBuidler(this);

        initData();

        bindControl();

        setAdapterToViewPage();

        setListener();

        //initTimeMachineDialog();
        initButtomDialog();

        popupReleaseMenu = new PopupReleaseMenu(this);
        NewRelic.withApplicationToken(BuildConfig.NEW_RELIC_KEY).start(this);

    }

    /**
     * 退出底部弹窗
     */
    private void initButtomDialog() {
        // 提示：询问是否删除直播
        String[] itemStrList = {getResources().getString(R.string.confirm_quit)};

        quitAppDialog = new ActionSheetDialog(mContext, itemStrList, null);

        quitAppDialog.isTitleShow(false);
        quitAppDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                quitAppDialog.superDismiss();
                MyApplication.getInstance().finishActivies();
            }
        });
    }

    public void goSendEvent() {
        if (!UserInfoUtil.getInstance().isLogin()) {
            openLoginActivity();
            return;
        }
        if (StringUtil.isEmpty(UserInfoUtil.getInstance().getUserMobile())) {


            TipUtil.alert(MainActivity.this, "请先绑定手机号"
                    , "发布约伴需要绑定手机完成实名认证"
                    , new String[]{"取消", "立即绑定"}
                    , new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                        }
                    }
                    ,
                    new OnBtnClickL() {
                        @Override
                        public void onBtnClick() {
                            readyGo(SettingBindPhoneActivity.class);
                        }
                    }
            );


        } else {
            onSendEvent();
        }
    }

    /**
     * 绑定发送服务
     */
    private void bindSendService() {
        bindService(new Intent(this, BackgroundService.class), connection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    /**
     * 点击直播
     */
    public void onSendLive() {

        if (!UserInfoUtil.getInstance().isLogin()) {
            openLoginActivity();
            return;
        }

        SendingTimeline timeline = SharedPreferencesManager.getPostLive();
        if (null == timeline) {
            startActivityForResult(new Intent(mContext, NewEditLiveActivity.class),
                    REQUEST_CODE_TO_RELEASE_LIVE);
        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                piecesRlyt.callOnClick();
            }
            String liveJson = new Gson().toJson(timeline);
            EventBus.getDefault().post(new SendLive(liveJson));
            Toast.makeText(MainActivity.this,
                    R.string.sending_last_please_to_wait,
                    Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 点击约伴
     */
    private void onSendEvent() {


        SendingTimeline timeline = SharedPreferencesManager.getPostEvent();
        SendingTimeline timeline1 = SharedPreferencesManager.getPostEventEdit();


        if (timeline == null && timeline1 == null) {
            readyGo(EditEventActivity.class);

        } else {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
                piecesRlyt.callOnClick();
            }
            String liveJson = new Gson().toJson(timeline);
            EventBus.getDefault().post(new SendLive(liveJson));
            Toast.makeText(MainActivity.this,
                    R.string.sending_last_please_to_wait,
                    Toast.LENGTH_LONG).show();
        }
    }


    /**
     * 发送草稿箱里的消息,当有群聊消息发送失败时,重试发送
     */
    private void sendGroupDraftMsg() {

        //获取发送失败的群聊消息列表
        List<GroupMsgDetail> details = UserInfoDbUtil.getInstance().getDrafts();

        for (GroupMsgDetail detail : details) {
            //群发消息
            SendGroupMsgEvent event = new SendGroupMsgEvent(detail);
            EventBus.getDefault().post(event);
        }
    }


    /**
     * 环信 消息接收监听
     */
    EMMessageListener messageListener = new EMMessageListener() {

        @Override
        public void onMessageReceived(List<EMMessage> messages) {
            // notify new message
            for (EMMessage message : messages) {
                EMHelper.getInstance().getNotifier().onNewMsg(message);
            }
            refreshUIWithMessage();
        }

        @Override
        public void onCmdMessageReceived(List<EMMessage> messages) {
            //red packet code : 处理红包回执透传消息

            //end of red packet code
            refreshUIWithMessage();
        }

        @Override
        public void onMessageReadAckReceived(List<EMMessage> messages) {
        }

        @Override
        public void onMessageDeliveryAckReceived(List<EMMessage> message) {
        }

        @Override
        public void onMessageChanged(EMMessage message, Object change) {
        }
    };

    private void refreshUIWithMessage() {
        runOnUiThread(new Runnable() {
            public void run() {
                // refresh unread count

                // refresh conversation list
                if (mseeageFragment != null) {
                    mseeageFragment.refresh();
                }
            }
        });
    }

    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }

    /**
     * 登出回调
     * <p/>
     * response
     */
    public void onEventMainThread(LogoutEvent logOutEvent) {
        //登出切换至热门
        EventBus.getDefault().post(new CheckoutEvent(TagFactory.createTag(TimelineRequestType.HOT)));
    }

    /**
     * 由消息页面发出GoToHotEvent，这里选定为首页
     */
    @SuppressLint("NewApi")
    public void onEventMainThread(GoToLiveListEvent event) {
        piecesRlyt.callOnClick();
    }


    /**
     * 用户未读信息总数更新
     */
    public void onEventMainThread(TotalUnreadCountEvent response) {

        UIUtils.showView(mTvUnreadMsgCount, response.getTotalCount() > 0);

        int unreadCount = response.getTotalCount() > 99 ? 99 : response.getTotalCount();
        mTvUnreadMsgCount.setText(String.valueOf(unreadCount));

        ShortcutBadger.applyCount(mContext, unreadCount);
    }

    // ----------------------网络响应回调-----------------------------//


    /**
     * 服务器推送消息时
     */
    public void onEventMainThread(JPushNotificationInfo info) {
        LogUtil.d(TAG_LOG, info.getAlert() + " extras:" + info.getExtras());

        if (nowPageType != PAGE_ME && info.getExtras().equals("fans")) {
            iv_me_msg_not_read.setVisibility(View.VISIBLE);
        }
        if (info.getExtras().equals("fans")) {
            EventBus.getDefault().post(new EventCenter<>(Content.NEW_FANS_TAG));
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        ShareUtil.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 登录回调
     *
     * @param loginEvent 登录事件
     */
    public void onEventMainThread(LoginEvent loginEvent) {
        //随便看看标识重置
        isLookAround = false;
        //初始化登陆信息
        initLoginData();
    }

    /**
     * title：
     * 已发送
     * content：
     * 可前往消息查看发送状态
     * button：
     * 关闭/查看
     * <p/>
     * 点击查看，跳转消息主页。
     *
     * @param response 切换到消息模块,从群发消息调用
     */
    public void onEventMainThread(CheckoutMainPageEvent response) {


        //除了主页,其他都关闭
        MyApplication.getInstance().finishActiviesExcept(MainActivity.class);

        if (response.getPage() == PAGE_ME) {
            //切换我的页面
            meRlyt.callOnClick();
        } else if (response.getPage() == PAGE_MESSAGE) {
            //切换消息页面
            messageRlyt.callOnClick();
        }

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && !isLookAround) {
            showSureBottom();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    protected void showSureBottom() {

        quitAppDialog.show();
    }


}
