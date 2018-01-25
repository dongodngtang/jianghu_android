package net.doyouhike.app.bbs.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.BuildConfig;
import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.biz.presenter.action.ActionDetailPresenter;
import net.doyouhike.app.bbs.chat.chatui.ChatActivity;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.activity.AddCommentActivity;
import net.doyouhike.app.bbs.ui.activity.ReportActivity;
import net.doyouhike.app.bbs.ui.activity.action.ActionDetailActivity;
import net.doyouhike.app.bbs.ui.activity.action.ActionManageActivity;
import net.doyouhike.app.bbs.ui.activity.live.DesAndRoadListActivity;
import net.doyouhike.app.bbs.ui.activity.live.CommentListActivity;
import net.doyouhike.app.bbs.ui.activity.live.LookPicsForChooseActivity;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.activity.me.FollowFansActivity;
import net.doyouhike.app.bbs.ui.activity.road.RoadDetailActivity;
import net.doyouhike.app.bbs.ui.activity.start.NewGuideActivity;
import net.doyouhike.app.bbs.ui.user.other.OtherActivity;
import net.doyouhike.app.bbs.ui.widget.common.webview.BaseWebViewActivity;

import java.util.List;

/**
 * 功能：
 *
 * @author：曾江 日期：16-5-5.
 */
public class ActivityRouter {


    public static void openFollowFansActivity(Context context, String user_id, String follow_fans) {
        Intent intent = new Intent(context, FollowFansActivity.class);
        intent.putExtra("user_id", user_id);
        intent.putExtra("follow_fans", follow_fans);
        context.startActivity(intent);
    }


    /**
     * 跳转活动管理
     *
     * @param context
     * @param userId          用户id
     * @param actionDetailId  活动id
     * @param role            角色
     * @param isManager
     * @param showConfirmView
     * @param statuts         状态,活动结束等状态
     */
    public static void openActionManagerActivity(Context context, String userId,
                                                 String actionDetailId, int role,
                                                 boolean isManager, boolean showConfirmView,
                                                 String statuts) {

        Intent managerIntent = new Intent(context, ActionManageActivity.class);
        managerIntent.putExtra(ActionDetailPresenter.MYUSERID, userId);
        managerIntent.putExtra(ActionManageActivity.I_NODE_ID, actionDetailId);
        managerIntent.putExtra(ActionManageActivity.I_IS_MANAGER, isManager);//管理活动
        managerIntent.putExtra(ActionManageActivity.I_CURRENT_ROLE, role + "");//活动角色
        managerIntent.putExtra(ActionManageActivity.I_CHECKOUT_CONFIRMED, showConfirmView);//活动角色
        managerIntent.putExtra(ActionManageActivity.I_STATUS, statuts);
        context.startActivity(managerIntent);
    }


    /**
     * 跳转活动详情
     *
     * @param context
     * @param actionId
     */
    public static void openActionDetailActivity(Context context, String actionId) {
        Intent intent = new Intent(context, ActionDetailActivity.class);
        intent.putExtra("nodeId", actionId);
        context.startActivity(intent);
    }

    /**
     * 跳转网页
     *
     * @param context
     * @param url     帖子
     */
    public static void openWebActivity(Context context, String url) {
        LoginUser loginUser = SharedPreferencesManager.getUserInfo(context);
        String disscution = url;
        if (loginUser != null &&
                loginUser.getUser().getSign_wap_topic_token() != null) {
            disscution = url + "?jhsign=" + loginUser.getUser().getSign_wap_topic_token()
                    + "&uid=" + loginUser.getUser().getInternal_id();
        }

        //链接类型
        Intent webIntent = new Intent(context, BaseWebViewActivity.class);
        webIntent.putExtra(BaseWebViewActivity.I_URL, disscution);
        context.startActivity(webIntent);
    }

    /**
     * 打开浏览器
     *
     * @param url 链接
     */
    public static void openBrowser(Context context, String url) {

        if (TextUtils.isEmpty(url)) {
            return;
        }

        try {
            Uri uri = Uri.parse(url);
            Intent it = new Intent(Intent.ACTION_VIEW, uri);
            context.startActivity(it);
        } catch (Exception e) {
            StringUtil.showSnack(context, "帖子地址异常:" + url);
        }

    }

    public static void openLoginActivity(Context context) {
        Intent intent = new Intent();
        intent.setClass(context, LoginActivity.class);
        context.startActivity(intent);
    }


    /**
     * 跳转评论页
     *
     * @param context
     * @param nodeId
     * @param canDel
     */
    public static void openLiveCommentActivity(Context context, String nodeId, boolean canDel) {
        Intent intent = new Intent(context, CommentListActivity.class);
        intent.putExtra(CommentListActivity.I_NODE_ID, nodeId);
        intent.putExtra(CommentListActivity.I_CAN_DELETE, canDel);
        context.startActivity(intent);
    }

    /**
     * 打开他人主页
     *
     * @param context
     * @param userId
     */
    public static void openOtherPageActivity(Context context, String userId) {


        if (UserInfoUtil.getInstance().isCurrentUser(userId)) {
            //自己打不开自己的主页
            return;
        }


        Intent intent = new Intent(context, OtherActivity.class);
        intent.putExtra(OtherActivity.OTHER_USER_ID, userId);
        context.startActivity(intent);
    }

    /**
     * 回复评论
     *
     * @param context
     * @param commentId       被回复的评论ID,如果对评论进行评论，commentId值为被评论的commentId,如果不是，commentId为0
     * @param commentNickName 引用评论昵称
     * @param userId          被回复评论的userId,不是对评论回复，不用传此值
     * @param nodeId          主题id
     * @param avator
     */
    public static void openReplyCommentActivity(Context context,
                                                String commentId,
                                                String commentNickName,
                                                String userId,
                                                String nodeId,
                                                String avator) {

        Intent intent = new Intent(context, AddCommentActivity.class);
        intent.putExtra(AddCommentActivity.INTENT_EXTRA_NAME_COMMENT_ID, commentId);
        intent.putExtra(AddCommentActivity.INTENT_EXTRA_NAME_COMMENT_NICKNAME, commentNickName);
        intent.putExtra(AddCommentActivity.INTENT_EXTRA_NAME_USER_ID, userId);
        intent.putExtra(AddCommentActivity.INTENT_EXTRA_NAME_LIVE_ID, nodeId);

        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, AddCommentActivity.RESULT_CODE_TO_COMMENT_FINISH);
        } else {
            context.startActivity(intent);
        }


    }

    /**
     * 添加评论
     *
     * @param context
     * @param nodeId  主题id
     */
    public static void openAddCommentActivity(Context context,
                                              String nodeId) {


        Intent intent = new Intent(context,
                AddCommentActivity.class);
        intent.putExtra(AddCommentActivity.INTENT_EXTRA_NAME_LIVE_ID,
                nodeId);

        if (context instanceof Activity) {
            ((Activity) context).startActivityForResult(intent, AddCommentActivity.RESULT_CODE_TO_COMMENT_FINISH);
        } else {
            context.startActivity(intent);
        }


    }

    /**
     * 线路详情列表
     *
     * @param context
     * @param slug
     */
    public static void openDesAndRoadListActivity(Context context, String slug) {
        Intent intent = new Intent(context, DesAndRoadListActivity.class);
        intent.putExtra("node_slug", slug);
        context.startActivity(intent);
    }

    /**
     * 线路详情
     *
     * @param context
     * @param road_sulg
     */
    public static void openRoadDetailActivity(Context context, String road_sulg) {
        Intent intent = new Intent(context, RoadDetailActivity.class);
        intent.putExtra("road_slug", road_sulg);
        context.startActivity(intent);
    }

    public static void openGalleryActivity(Context context, String[] url, int index) {
        if (url != null) {
            Intent intent = new Intent(context,
                    LookPicsForChooseActivity.class);
            intent.putExtra(
                    LookPicsForChooseActivity.INTENT_EXTRA_NAME_PICS_STR_ARRAY,
                    url);
            intent.putExtra(
                    LookPicsForChooseActivity.INTENT_EXTRA_NAME_AIM_INT,
                    LookPicsForChooseActivity.OPENED_FOR_LOOK);
            intent.putExtra(
                    LookPicsForChooseActivity.INTENT_EXTRA_NAME_SHOW_INDEX_INT,
                    index);
            context.startActivity(intent);
        }
    }

    /**
     * 举报页
     *
     * @param context
     * @param nodeId
     * @param isEvent
     */
    public static void openReportActivity(Context context, String nodeId, boolean isEvent) {
        LoginUser user = SharedPreferencesManager.getUserInfo(context);
        if (user == null) {
            openLoginActivity(context);
            return;
        }
        Intent intent = new Intent(context, ReportActivity.class);
        intent.putExtra(ReportActivity.NODEID, nodeId);
        intent.putExtra(ReportActivity.MYUSERID, user.getUser().getUser_id());
        intent.putExtra(ReportActivity.ISEVENT, isEvent);
        context.startActivity(intent);
    }

    /**
     * 聊天界面
     *
     * @param context
     * @param toChatUserId
     */
    public static void openChatActvity(Context context, String toChatUserHxId, String toChatUserId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(ChatActivity.USER_ID, toChatUserId);
        intent.putExtra(ChatActivity.IM_USER_ID, toChatUserHxId);
        context.startActivity(intent);
    }

    /**
     * 密码更改,直接跳转登陆界面
     * 跳转登陆界面
     */
    public static synchronized void jumpToLogin() {


        List<Activity> activities = MyApplication.getInstance().getActivities();

        if (null != activities && !activities.isEmpty()) {
            //获取顶部ACTIVITY
            Activity topActivity = activities.get(activities.size() - 1);

            if (null != topActivity && !(topActivity instanceof LoginActivity)) {
                openLoginActivity(topActivity);
            }

        }


    }

    /**
     * token失效,调整登陆
     */
    public static synchronized void tokenExpiry() {


        List<Activity> activities = MyApplication.getInstance().getActivities();

        if (null != activities && !activities.isEmpty()) {
            //获取顶部ACTIVITY
            Activity topActivity = activities.get(activities.size() - 1);


            if (null != topActivity && !(topActivity instanceof LoginActivity)) {

                topActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MyApplication.getInstance().getApplicationContext(),
                                "账号权限过期，请重新登陆！",
                                Toast.LENGTH_SHORT).show();
                    }
                });

                openLoginActivity(topActivity);

                MyApplication.getInstance().finishActiviesExcept(LoginActivity.class);
            }

        }


    }


    public static void openGuideActivity(Context mContext) {
        Intent intent = new Intent(mContext, NewGuideActivity.class);
        mContext.startActivity(intent);
    }
}
