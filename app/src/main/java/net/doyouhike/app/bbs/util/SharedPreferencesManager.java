/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: SharedPreferencesManager.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-10
 *
 * Changes (from 2015-10-10)
 * -----------------------------------------------------------------
 * 2015-10-10 创建SharedPreferencesManager.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 * 2015-10-19 添加userId字段(wu-yoline);
 * -----------------------------------------------------------------
 * 2015-10-19 添加clearUserInfo()方法和 clearTimeLine()方法(wu-yoline);
 * -----------------------------------------------------------------
 * 2015-10-27 添加对直播评论的相关缓存方法和属性;(wu-yoline);
 * -----------------------------------------------------------------
 * 2015-11-04 添加保存LiveInfo相关的sp;(wu-yoline);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.doyouhike.app.bbs.BuildConfig;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.entity.RecommendUser;
import net.doyouhike.app.bbs.biz.entity.listinfo.MyActionListInfo;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.MyImInfo;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.SendingTimeline;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.login.GetServerResponse;
import net.doyouhike.app.bbs.biz.openapi.response.AppVersionResp;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventDetailResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserSettingResp;
import net.doyouhike.app.bbs.chat.helper.Constant;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesManager {

    public static Context appContext;

    /**
     * 区别分开不同用户的信息
     */
    private static String userId = "-1";

    // SharedPreferences name
    private static final String SP_SERVER_URL = "serverUrl";
    private static final String KEY_EDIT_SERVER_URL = "KEY_EDIT_SERVER_URL";
    private static final String SERVER_ENVIRMENT = "SERVER_ENVIRMENT";
    private static final String SP_USERINFO = "userinfo" + userId;
    private static final String SP_BASEINFO = "baseinfo" + userId;
    private static final String SP_TIMELINE = "timeline";
    private static final String SP_ACTION_MY = "action_my";
    // private static final String SP_TO_COMMENT = "comment" + userId;
    private static final String SP_LIVEINFO = "release";
    private static final String SP_EVENTINFO = "eventinfo";
    private static final String SP_CONTACTS_USERS = "contacts_users";

    // lock
    private static final Object LOCK_USERINFO = new Object();
    private static final Object LOCK_TIMELINE = new Object();
    private static final Object LOCK_ACTION_MY = new Object();
    private static final Object LOCK_TO_COMMENT = new Object();
    private static final Object LOCK_LIVEINFO = new Object();
    private static final Object LOCK_CONTACTS = new Object();

    // SharedPreferences key
    private static final String KEY_SERVER_URL = "serverUrl";

    // user key
    private static final String KEY_USERINFO = "userinfo";
    private static final String KEY_USER_DETAILS_INFO = "userin_details_info";
    private static final String KEY_USER_IM_INFO = "userin_im_info";

    // timeline的key
    public static final String KEY_TIMELINE_FOLLOW = "timelinefollow" + userId;
    public static final String KEY_TIMELINE_HOT = "timelinehot";
    public static final String KEY_TIMELINE_MY = "timeline_my" + userId;

    // 个人页活动的key
    public static final String KEY_ACTION_MY_ALL = "action_my_all" + userId;
    public static final String KEY_ACTION_MY_SPONSOR = "action_my_sponsor"
            + userId;
    public static final String KEY_ACTION_MY_JOIN = "action_my_join" + userId;

    // 收藏的key
    public static final String KEY_FAVORITE_MY = "favorite_my" + userId;

    // // SP_COMMENT的key
    // public static final String KEY_TO_COMMENT_HEAD = "commentcontent";

    // 基本设置开关的状态
    private static final String USER_SETTING = "user_setting";
    private static final String STATE_MSG_LIKE = "msg_like";
    private static final String STATE_MSG_ACTION = "msg_action";
    private static final String STATE_MSG_FOLLOWER = "msg_follower";
    private static final String STATE_JPUSH_ALIAS = "state_jpush_alias";
    private static final String STATE_PRIVACY_PHONE = "state_privacy_phone";
    private static final String STATE_PRIVACY_NEAR = "state_privacy_near";

    // 发布时要保存的LiveInfo
    private static final String KEY_TO_RELEASE_LIVEINFO = "releaseliveinfo";

    private static final String KEY_TO_RELEASE_EVENT = "releaseevent";

    // 联系人注册情况
    private static final String KEY_CONTACTS_USERID = "contacts_userId"; // 区分不同用户，当前用户的userId
    private static final String KEY_CONTACTS_USERS = "contacts_users";
    //记录已经查看过时光机的用户ID
    private static final String KEY_TIME_MACHINE_USERID = "KEY_TIME_MACHINE_USERID";
    /**
     * 发送中的直播
     */
    private static final String KEY_POST_TIME_LINE_LIVE = "KEY_POST_TIME_LINE" + userId;
    /**
     * 发送中的约伴活动
     */
    private static final String KEY_POST_TIME_LINE_EVENT = "KEY_POST_TIME_LINE_EVENT" + userId;

    private static final String KEY_POST_TIME_LINE_EVENT_EDIT = "KEY_POST_TIME_LINE_EVENT_EDIT" + userId;
    /**
     * 存储相关APP更新信息
     */
    private static final String APP_UPDATE_INFO = "app_update_info";

    /**
     * 记忆首页
     */
    private static final String MAIN_TOPIC_PAGE = "main_topic_page";

    /**
     * 是否显示引导页
     */
    private static final String SHOW_GUIDE_PAGE = "show_guide_page";

    /**
     * 当前登录的用户名
     */
    private static final String LOGIN_USERNAME = "login_username";

    /**
     * 图片服务地址
     */
    private static final String PHOTO_DOMAIN_PATH = "photo_domain_path";

    /**
     * 反馈回复数量
     */
    private static final String FEEDBACK_NUM = "FEEDBACK_NUM";

    /**
     * 获取对应的SharedPreferences对象
     *
     * @param context
     * @param name    SharedPreferences name
     * @return
     */
    private static SharedPreferences getSPByName(Context context, String name) {
        if (context == null) {
            return null;
        }
        return context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }

    /**
     * 设置服务器地址
     */
    public static void setServerUrl(Context context, String serverUrl) {
        SharedPreferences serverUrlSp = getSPByName(context, SP_SERVER_URL);
        if (serverUrlSp != null) {
            serverUrlSp.edit().putString(KEY_SERVER_URL, serverUrl).commit();
        }
    }

    /**
     * CTO 要求可配服务器地址 写入
     *
     * @param devServerUrl
     */
    public static void setDevServerUrl(String devServerUrl) {
        SharedPreferences serverUrlSp = getSPByName(MyApplication.getInstance(), SP_SERVER_URL);
        if (serverUrlSp != null) {
            serverUrlSp.edit().putString(KEY_EDIT_SERVER_URL, devServerUrl).commit();
        }
    }

    /**
     * 设置手机端 环境
     *
     * @param devServerUrl
     */
    public static void setServerEnvironment(String devServerUrl) {
        SharedPreferences serverUrlSp = getSPByName(MyApplication.getInstance(), SERVER_ENVIRMENT);
        if (serverUrlSp != null) {
            serverUrlSp.edit().putString(SERVER_ENVIRMENT, devServerUrl).commit();
        }
    }


    /**
     * 获取服务器 环境
     */
    public static String getServerEnvirment() {
        SharedPreferences serverUrlSp = getSPByName(appContext, SERVER_ENVIRMENT);
        String serverEnvirment = "";
        if (serverUrlSp != null)
            serverEnvirment = serverUrlSp.getString(SERVER_ENVIRMENT, BuildConfig.ENV_TYPE);
        if (StringUtil.isEmpty(serverEnvirment))
            serverEnvirment = BuildConfig.ENV_TYPE;
        return serverEnvirment;
    }


    /**
     * 设置服务器地址
     */
    public static String getServerUrl() {
        SharedPreferences serverUrlSp = getSPByName(appContext, SP_SERVER_URL);
        String serverUrl = MyApplication.serverUrl;
        String devServerUrl;

        if (serverUrlSp != null) {
            devServerUrl = serverUrlSp.getString(KEY_EDIT_SERVER_URL, null);
            if (StringUtil.isEmpty(devServerUrl)) {
                serverUrl = serverUrlSp.getString(KEY_SERVER_URL, serverUrl);//默认服务器
            } else {
                serverUrl = devServerUrl;//自定义服务器 CTO 要求
            }

        }
        return serverUrl;
    }

    // ----------------------------------用户信息---------------------------//

    /**
     * 设置用户信息
     */
    public static void setUserInfo(Context context, LoginUser currentUser) {
        synchronized (LOCK_USERINFO) {

            // System.out.println("setUserInfo, token = " +
            // currentUser.getToken());

            userId = currentUser.getUser().getUser_id();

            SharedPreferences userInfoSp = getSPByName(context, SP_USERINFO);
            if (userInfoSp != null) {

                try {
                    String dataJson = new Gson().toJson(currentUser);

                    userInfoSp
                            .edit()
                            .putString(KEY_USERINFO,
                                    EnCoder.encryptMD5(dataJson)).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取用户信息
     */
    public static LoginUser getUserInfo(Context context) {

        synchronized (LOCK_USERINFO) {
            SharedPreferences userInfoSp = getSPByName(context, SP_USERINFO);
            if (userInfoSp != null) {
                String objStr = userInfoSp.getString(KEY_USERINFO, null);
                if (objStr != null) {
                    objStr = EnCoder.encryptMD5(objStr);

                    try {
                        LoginUser currentUser = new Gson().fromJson(objStr,
                                LoginUser.class);

                        // System.out.println("getUserInfo, token = " +
                        // currentUser
                        // .getToken());

                        return currentUser;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }


    /**
     * 获取IM信息
     *
     * @return IM信息
     */
    public static MyImInfo getImUserInfo() {

        synchronized (LOCK_USERINFO) {
            SharedPreferences userInfoSp = getSPByName(MyApplication.getInstance().getApplicationContext(), SP_USERINFO);
            if (null != userInfoSp) {
                String objStr = userInfoSp.getString(KEY_USER_IM_INFO, null);
                if (objStr != null) {
                    objStr = EnCoder.encryptMD5(objStr);

                    return new Gson().fromJson(objStr, MyImInfo.class);
                }
            }

            return null;
        }
    }

    /**
     * 保存IM信息
     *
     * @param myImInfo IM信息
     */
    public static void saveImUserInfo(MyImInfo myImInfo) {

        synchronized (LOCK_USERINFO) {
            SharedPreferences userInfoSp = getSPByName(MyApplication.getInstance().getApplicationContext(), SP_USERINFO);


            if (userInfoSp != null) {

                try {
                    String dataJson = "";

                    if (null != myImInfo) {
                        dataJson = new Gson().toJson(myImInfo);
                    }


                    userInfoSp
                            .edit()
                            .putString(KEY_USER_IM_INFO,
                                    EnCoder.encryptMD5(dataJson)).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }
    }


    /**
     * 清除用户信息(包括详细信息)
     *
     * @param context
     */
    public static void clearUserInfo(Context context) {
        synchronized (LOCK_USERINFO) {
            System.out.println("清空啦！！！！！！！！！！！");
            SharedPreferences userInfoSp = getSPByName(context, SP_USERINFO);
            if (userInfoSp != null) {
                userInfoSp.edit().clear().commit();
            }
            userId = "-1";
        }
    }

    // -------------------------------用户详细信息-------------------------------------//

    /**
     * 设置用户详细信息
     */
    public static void setUserDetailsInfo(Context context,
                                          CurrentUserDetails currentUserDetails) {
        synchronized (LOCK_USERINFO) {
            SharedPreferences userInfoSp = getSPByName(context, SP_USERINFO);
            if (userInfoSp != null) {

                try {
                    String dataJson = new Gson().toJson(currentUserDetails);

                    userInfoSp
                            .edit()
                            .putString(KEY_USER_DETAILS_INFO,
                                    EnCoder.encryptMD5(dataJson)).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 获取用户详细信息
     *
     * @param context
     * @return Json 字符串
     */
    public static CurrentUserDetails getUserDetailsInfo(Context context) {
        synchronized (LOCK_USERINFO) {
            SharedPreferences userInfoSp = getSPByName(context, SP_USERINFO);
            if (userInfoSp != null) {
                String objStr = userInfoSp.getString(KEY_USER_DETAILS_INFO,
                        null);
                if (objStr != null) {
                    objStr = EnCoder.encryptMD5(objStr);
                    try {
                        return new Gson().fromJson(objStr,
                                CurrentUserDetails.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

    // -----------------------------"消息"的推送开关--------------------------------------//

    public static void setPrivacyPhone(Context context, String state) {
        SharedPreferences baseInfoSp = getSPByName(context, SP_BASEINFO);
        if (baseInfoSp != null) {
            baseInfoSp.edit().putString(STATE_PRIVACY_PHONE, state)
                    .commit();
        }
    }

    public static String getPrivacyPhone(Context context) {
        SharedPreferences spBaseInfo = getSPByName(context, SP_BASEINFO);
        if (spBaseInfo != null) {
            return spBaseInfo.getString(STATE_PRIVACY_PHONE, "");
        } else {
            return "";
        }

    }

    public static void setPrivacyNear(Context context, String state) {
        SharedPreferences baseInfoSp = getSPByName(context, SP_BASEINFO);
        if (baseInfoSp != null) {
            baseInfoSp.edit().putString(STATE_PRIVACY_NEAR, state)
                    .commit();
        }
    }

    public static String getPrivacyNear(Context context) {
        SharedPreferences spBaseInfo = getSPByName(context, SP_BASEINFO);
        if (spBaseInfo != null) {
            return spBaseInfo.getString(STATE_PRIVACY_NEAR, "");
        } else {
            return "";
        }
    }

    public static void setUserSetting(Context context, UserSettingResp.UserSettingBean userSettingBean) {
        SharedPreferences base = getSPByName(context, SP_BASEINFO);
        String json = new Gson().toJson(userSettingBean);
        base.edit().putString(USER_SETTING, json).commit();
    }

    public static UserSettingResp.UserSettingBean getUserSetting(Context context) {
        SharedPreferences spBaseInfo = getSPByName(context, SP_BASEINFO);
        String json = spBaseInfo.getString(USER_SETTING, "");
        UserSettingResp.UserSettingBean userSettingBean = new Gson().fromJson(json, UserSettingResp.UserSettingBean.class);
        return userSettingBean;
    }

    /**
     * 设置“消息_评论”的推送开关状态
     *
     * @param context
     * @param commentState
     */
    public static void setMsgCommentState(Context context, boolean commentState) {
        SharedPreferences baseInfoSp = getSPByName(context, SP_BASEINFO);

    }

    /**
     * 获取“消息_评论”的推送开关状态
     *
     * @param context
     * @return
     */
    public static boolean getMsgCommentState(Context context) {

        return true;

    }

    /**
     * 设置“消息_被赞”的推送开关状态
     *
     * @param context
     * @param likeState
     */
    public static void setMsgLikeState(Context context, boolean likeState) {
        SharedPreferences baseInfoSp = getSPByName(context, SP_BASEINFO);
        if (baseInfoSp != null) {
            baseInfoSp.edit().putBoolean(STATE_MSG_LIKE, likeState).commit();
        }
    }

    /**
     * 获取“消息_被赞”推送开关
     *
     * @param context
     * @return
     */
    public static boolean getMsgLikeState(Context context) {
        SharedPreferences spBaseInfo = getSPByName(context, SP_BASEINFO);
        if (spBaseInfo != null) {
            return spBaseInfo.getBoolean(STATE_MSG_LIKE, true);
        } else {
            return true;
        }
    }

    /**
     * 设置“消息_活动”的推送开关状态
     *
     * @param context
     * @param actionState
     */
    public static void setMsgActionState(Context context, boolean actionState) {
        SharedPreferences baseInfoSp = getSPByName(context, SP_BASEINFO);
        if (baseInfoSp != null) {
            baseInfoSp.edit().putBoolean(STATE_MSG_ACTION, actionState)
                    .commit();
        }
    }

    /**
     * 获取“消息_活动”推送开关
     *
     * @param context
     * @return
     */
    public static boolean getMsgActionState(Context context) {
        SharedPreferences spBaseInfo = getSPByName(context, SP_BASEINFO);
        if (spBaseInfo != null) {
            return spBaseInfo.getBoolean(STATE_MSG_ACTION, true);
        } else {
            return true;
        }
    }

    /**
     * 设置“消息_关注”的推送开关状态
     *
     * @param context
     * @param actionState
     */
    public static void setMsgFollowState(Context context, boolean actionState) {
        SharedPreferences baseInfoSp = getSPByName(context, SP_BASEINFO);
        if (baseInfoSp != null) {
            baseInfoSp.edit().putBoolean(STATE_MSG_FOLLOWER, actionState).apply();
        }
    }

    /**
     * 获取“消息_关注”推送开关
     *
     * @param context
     * @return
     */
    public static boolean getMsgFollowState(Context context) {
        SharedPreferences spBaseInfo = getSPByName(context, SP_BASEINFO);
        if (spBaseInfo != null) {
            return spBaseInfo.getBoolean(STATE_MSG_FOLLOWER, true);
        } else {
            return true;
        }
    }

    /**
     * 设置极光推送别名设置状态
     *
     * @param context
     * @param alias
     */
    public static void setJPushAliasState(Context context, String alias) {


        if (userId.equals("-1")) {
            return;
        }


        SharedPreferences baseInfoSp = getSPByName(context, SP_BASEINFO);
        if (baseInfoSp != null) {
            baseInfoSp.edit().putString(STATE_JPUSH_ALIAS, alias).apply();
        }
    }

    /**
     * 获取设置极光推送别名设置状态
     *
     * @param context
     * @return
     */
    public static String getJPushAliasState(Context context) {
        SharedPreferences spBaseInfo = getSPByName(context, SP_BASEINFO);
        if (spBaseInfo != null) {
            return spBaseInfo.getString(STATE_JPUSH_ALIAS, "");
        } else {
            return "";
        }
    }


    /**
     * @return 发送中的直播
     */
    public static SendingTimeline getPostLive() {

        synchronized (LOCK_LIVEINFO) {
            String strTimeline = SpTools.getString(MyApplication.getInstance().getApplicationContext(), KEY_POST_TIME_LINE_LIVE, "");

            return getPostTimeline(strTimeline);
        }
    }


    /**
     * @return 发送中的约伴活动
     */
    public static SendingTimeline getPostEvent() {

        synchronized (LOCK_LIVEINFO) {
            String strTimeline = SpTools.getString(MyApplication.getInstance().getApplicationContext(), KEY_POST_TIME_LINE_EVENT, "");

            return getPostTimeline(strTimeline);
        }
    }


    private static SendingTimeline getPostTimeline(String strTimeline) {

        if (TextUtils.isEmpty(strTimeline)) {
            return null;
        }

        SendingTimeline timeline = new Gson().fromJson(strTimeline,
                SendingTimeline.class);

        if (timeline.getEvent() != null &&
                null != timeline.getEvent().getEvent_contents()) {

            for (EventDetailResp.ContentBean imagesEntity : timeline.getEvent().getEvent_contents()) {
                if (imagesEntity.getStatus() == Timeline.ImagesEntity.UPLOADING) {
                    imagesEntity.setPhoto_id(null);
                    imagesEntity.setStatus(Timeline.ImagesEntity.UPLOAD_FAIL);
                }
            }
        }

        return timeline;
    }

    /**
     * @param timeline 保存发送中的直播
     */
    public static synchronized void setPostLive(SendingTimeline timeline) {

        synchronized (LOCK_LIVEINFO) {
            String strTimeline = getStrPostTimeline(timeline);
            SpTools.setString(MyApplication.getInstance().getApplicationContext(), KEY_POST_TIME_LINE_LIVE, strTimeline);

        }
    }

    /**
     * @param timeline 保存发送中的约伴活动
     */
    public static synchronized void setPostEvent(SendingTimeline timeline) {

        synchronized (LOCK_LIVEINFO) {
            String strTimeline = getStrPostTimeline(timeline);
            SpTools.setString(MyApplication.getInstance().getApplicationContext(), KEY_POST_TIME_LINE_EVENT, strTimeline);
        }

    }

    /**
     * @param timeline 保存发送中的约伴活动编辑
     */
    public static synchronized void setPostEventEdit(SendingTimeline timeline) {

        synchronized (LOCK_LIVEINFO) {
            String strTimeline = getStrPostTimeline(timeline);
            SpTools.setString(MyApplication.getInstance().getApplicationContext(), KEY_POST_TIME_LINE_EVENT_EDIT, strTimeline);
        }

    }

    /**
     * @return 发送中的约伴活动编辑
     */
    public static SendingTimeline getPostEventEdit() {

        synchronized (LOCK_LIVEINFO) {
            String strTimeline = SpTools.getString(MyApplication.getInstance().getApplicationContext(), KEY_POST_TIME_LINE_EVENT_EDIT, "");
            return getPostTimeline(strTimeline);
        }
    }


    private static String getStrPostTimeline(SendingTimeline timeline) {

        String strTimeline = "";

        if (null != timeline) {
            strTimeline = new Gson().toJson(timeline);
        }
        return strTimeline;
    }


    // ------------------------------个人页面的活动-----------------------------------//

    /**
     * 设置保存在sp中的myAction
     */
    public static void setMyActionList(Context context, MyActionListInfo info,
                                       String keyActionMyType) {
        synchronized (LOCK_ACTION_MY) {
            SharedPreferences userInfoSp = getSPByName(context, SP_ACTION_MY);
            if (userInfoSp != null) {

                try {
                    String dataJson = new Gson().toJson(info);

                    userInfoSp
                            .edit()
                            .putString(keyActionMyType,
                                    EnCoder.encryptMD5(dataJson)).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    /**
     * 获取保存在sp中的myAction
     *
     * @param context
     * @param keyActionMyType
     * @return
     */
    public static MyActionListInfo getMyActionList(Context context,
                                                   String keyActionMyType) {
        synchronized (LOCK_ACTION_MY) {
            SharedPreferences actionMySp = getSPByName(context, SP_ACTION_MY);
            if (actionMySp != null) {
                String objStr = actionMySp.getString(keyActionMyType, null);
                if (objStr != null) {
                    objStr = EnCoder.encryptMD5(objStr);
                    try {
                        return new Gson().fromJson(objStr,
                                MyActionListInfo.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return null;
        }
    }

    // ------------------------------收藏-----------------------------------//

    /**
     * 设置保存在sp中的timeline接口的json
     */
    public static void setFavoriteList(Context context, String json) {
        if (json != null) {
            synchronized (LOCK_TIMELINE) {
                SharedPreferences spTimeline = getSPByName(context, SP_TIMELINE);
                if (spTimeline != null) {
                    spTimeline
                            .edit()
                            .putString(KEY_FAVORITE_MY,
                                    EnCoder.encryptMD5(json)).commit();
                }
            }
        }
    }


    public static void setToReleaseLiveInfo(Context context, String json) {
        try {

            LogUtil.d(json);
            synchronized (LOCK_LIVEINFO) {
                SharedPreferences spLiveInfo = getSPByName(context, SP_LIVEINFO + userId);
                if (spLiveInfo != null) {
                    spLiveInfo.edit().putString(KEY_TO_RELEASE_LIVEINFO, json)
                            .commit();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void setToEventLiveInfo(Context context, String jsonEvent) {
        synchronized (LOCK_LIVEINFO) {
            SharedPreferences spLiveInfo = getSPByName(context, SP_EVENTINFO + userId);
            if (spLiveInfo != null) {
                spLiveInfo.edit().putString(KEY_TO_RELEASE_EVENT, jsonEvent)
                        .commit();
            }

        }
    }

    /**
     * 清理约伴活动历史记录
     *
     * @param context
     */
    public static void clearToEventLiveInfo(Context context) {
        synchronized (LOCK_LIVEINFO) {
            SharedPreferences spLiveInfo = getSPByName(context, SP_EVENTINFO + userId);
            if (spLiveInfo != null) {
                spLiveInfo.edit().remove(KEY_TO_RELEASE_EVENT).commit();
            }

        }
    }

    public static NodeTimeline.ItemsBean.NodeBean getToReleaseEvent(Context context) {
        synchronized (LOCK_LIVEINFO) {
            SharedPreferences spLiveInfo = getSPByName(context, SP_EVENTINFO + userId);

            if (spLiveInfo != null) {
                String json = spLiveInfo.getString(KEY_TO_RELEASE_EVENT, "");
                if (StrUtils.hasContent(json)) {
                    try {
                        return new Gson().fromJson(json, NodeTimeline.ItemsBean.NodeBean.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    public static NodeTimeline.ItemsBean.NodeBean getToReleaseLiveInfo(Context context) {
        synchronized (LOCK_LIVEINFO) {
            SharedPreferences spLiveInfo = getSPByName(context, SP_LIVEINFO + userId);

            if (spLiveInfo != null) {
                String json = spLiveInfo.getString(KEY_TO_RELEASE_LIVEINFO, "");
                if (StrUtils.hasContent(json)) {
                    try {
                        return new Gson().fromJson(json, NodeTimeline.ItemsBean.NodeBean.class);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    public static void clearToReleaseLiveInfo(Context context) {
        synchronized (LOCK_LIVEINFO) {
            SharedPreferences spLiveInfo = getSPByName(context, SP_LIVEINFO + userId);
            if (spLiveInfo != null) {
                spLiveInfo.edit().remove(KEY_TO_RELEASE_LIVEINFO).commit();
            }

        }
    }

    /**
     * 保存联系人注册情况
     *
     * @param context
     * @param usersJson
     */
    public static void saveContactsUsers(Context context, String usersJson) {
        synchronized (LOCK_CONTACTS) {
            LoginUser user = getUserInfo(context);
            if (user.getUser() != null && user.getUser().getUser_id() != null) {
                SharedPreferences spContacts = getSPByName(context,
                        SP_CONTACTS_USERS + user.getUser().getUser_id());
                if (spContacts != null) {
                    LogUtil.d("保存联系人注册情况：currUserId = " + user.getUser().getUser_id()
                            + ",usersJson = " + usersJson);
                    spContacts.edit().putString(KEY_CONTACTS_USERS, usersJson)
                            .putString(KEY_CONTACTS_USERID, user.getUser().getUser_id())
                            .commit();
                }
            }
        }
    }

    public static void saveContactsUsers(Context context, List<RecommendUser> users) {
        String json = new Gson().toJson(users,
                new TypeToken<List<RecommendUser>>() {
                }.getType());
        saveContactsUsers(context, json);
    }

    /**
     * 获取联系人注册情况
     *
     * @param context
     * @param context
     * @return
     */
    public static List<RecommendUser> getContactsUsers(Context context) {
        synchronized (LOCK_CONTACTS) {
            LoginUser user = getUserInfo(context);
            if (user != null && user.getUser().getUser_id() != null) {
                SharedPreferences spContacts = getSPByName(context,
                        SP_CONTACTS_USERS + user.getUser().getUser_id());
                String savedUserId = spContacts.getString(KEY_CONTACTS_USERID,
                        null);
                String users = spContacts.getString(KEY_CONTACTS_USERS, null);
                CommonUtil.testLog("获取联系人注册情况：userId = " + user.getUser().getUser_id()
                        + ",saveUserId = " + savedUserId);
                if (savedUserId != null && users != null
                        && savedUserId.equals(user.getUser().getUser_id())) {
                    return new Gson().fromJson(users,
                            new TypeToken<List<RecommendUser>>() {
                            }.getType());
                }
            }
            return null;
        }
    }

    // userId 的get And set
    public static void setUserId(String nowUserId) {
        userId = nowUserId;
    }

    public static String getUserId() {
        return userId;
    }


    /**
     * @param context
     * @return 获取已读时光机用户列表
     */
    public static List<String> getReadedTimelineMachine(Context context) {

        List<String> userId = new ArrayList<>();

        String strUserId = SpTools.getString(context, KEY_TIME_MACHINE_USERID, "");


        if (!TextUtils.isEmpty(strUserId)) {
            Gson gson = new Gson();

            userId = gson.fromJson(strUserId, new TypeToken<List<String>>() {
            }.getType());
        }

        return userId;
    }

    public static void setReadedTimelineMachine(Context context, String strUserId) {


        String strUserIds = SpTools.getString(context, KEY_TIME_MACHINE_USERID, "");


        Gson gson = new Gson();
        List<String> userId = gson.fromJson(strUserIds, new TypeToken<List<String>>() {
        }.getType());

        if (null == userId) {
            userId = new ArrayList<>();
        }

        if (!userId.contains(strUserId))
            userId.add(strUserId);

        String newStrUserIds = gson.toJson(userId);

        SpTools.setString(context, KEY_TIME_MACHINE_USERID, newStrUserIds);

    }

    public static AppVersionResp getAppUpdateInfo() {


        String strInfo = SpTools.getString(MyApplication.getInstance(), APP_UPDATE_INFO, "");

        if (!TextUtils.isEmpty(strInfo)) {

            return new Gson().fromJson(strInfo, AppVersionResp.class);
        }

        return null;
    }


    public static void saveAppUpdateInfo(AppVersionResp info) {
        if (null != info) {
            String strInfo = new Gson().toJson(info);
            SpTools.setString(MyApplication.getInstance(), APP_UPDATE_INFO, strInfo);
        }
    }

    public static BaseTag getMainTopicPage() {
        String strInfo = SpTools.getString(MyApplication.getInstance(), MAIN_TOPIC_PAGE, "");

        if (!TextUtils.isEmpty(strInfo)) {
            return new Gson().fromJson(strInfo, BaseTag.class);
        }

        return null;
    }

    public static void setMainTopicPage(BaseTag page) {


        if (!UserInfoUtil.getInstance().isLogin()) {
            //首页标签纪录,防止历史纪录,退出登录时,切换到其他标签
            page = null;
        }


        if (null != page) {
            String strInfo = new Gson().toJson(page);
            SpTools.setString(MyApplication.getInstance(), MAIN_TOPIC_PAGE, strInfo);
        } else {
            SpTools.clear(MyApplication.getInstance(), MAIN_TOPIC_PAGE);
        }
    }

    public static void setShowGuidePage(boolean isShow) {
        SpTools.setBoolean(MyApplication.getInstance(), SHOW_GUIDE_PAGE + CommonUtil.getAppVersionName(MyApplication.getInstance()), isShow);
    }

    public static boolean getShowGuidePage() {
        return SpTools.getBoolean(MyApplication.getInstance(), SHOW_GUIDE_PAGE + CommonUtil.getAppVersionName(MyApplication.getInstance()), true);
    }

    public static void setLoginUsername(String username) {
        SpTools.setString(MyApplication.getInstance(), LOGIN_USERNAME, username);
    }

    public static String getLoginUsername() {
        return SpTools.getString(MyApplication.getInstance(), LOGIN_USERNAME, "");
    }

    public static void setPhotoDomainPath(String domainPath) {
        Constant.PHOTO_DOMAIN_PATH = domainPath;
        SpTools.setString(MyApplication.getInstance(), PHOTO_DOMAIN_PATH, domainPath);
    }

    public static String getPhotoDomainPath() {
        return SpTools.getString(MyApplication.getInstance(), PHOTO_DOMAIN_PATH, "");
    }

    public static void setFeedBackNum(int feedBackNum) {
        SpTools.setInt(MyApplication.getInstance(), FEEDBACK_NUM, feedBackNum);
    }

    public static int getFeedBackNum() {
        return SpTools.getInt(MyApplication.getInstance(), FEEDBACK_NUM, 0);
    }

}
