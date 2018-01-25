package net.doyouhike.app.bbs.biz.helper.message;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.LinearLayout;

import com.hyphenate.chat.EMClient;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.entity.JPushNotificationInfo;
import net.doyouhike.app.bbs.biz.entity.listinfo.MsgMyActionListInfo;
import net.doyouhike.app.bbs.biz.entity.listinfo.MsgMyCommentListInfo;
import net.doyouhike.app.bbs.biz.entity.listinfo.MsgMyLikeListInfo;
import net.doyouhike.app.bbs.biz.entity.listinfo.MsgMyListInfo;
import net.doyouhike.app.bbs.biz.event.im.ChatMsgCountEvent;
import net.doyouhike.app.bbs.biz.event.live.TotalUnreadCountEvent;
import net.doyouhike.app.bbs.biz.event.open.UserSettingEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.MyImInfo;
import net.doyouhike.app.bbs.biz.openapi.presenter.UserSettingHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgCommentsResp;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgCountResp;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgEventResp;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgLikeResp;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserSettingResp;
import net.doyouhike.app.bbs.ui.activity.message.MsgActionActivity;
import net.doyouhike.app.bbs.ui.activity.message.MsgCommentMeActivity;
import net.doyouhike.app.bbs.ui.activity.message.MsgLikedActivity;
import net.doyouhike.app.bbs.ui.adapter.message.MessageAdapter;
import net.doyouhike.app.bbs.util.DensityUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 首页 消息 模块
 * Created by zengjiang on 16/8/3.
 */

public class MessageHelper {

    private Context mContext;
    LinearLayout mContentView;

    private boolean isRefreshComment = true;
    private boolean isRefreshLike = true;
    private boolean isRefreshAction = true;

    private MsgMyCommentListInfo commentListInfo;
    private MsgMyLikeListInfo likeListInfo;
    private MsgMyActionListInfo actionListInfo;
    private MessageAdapter messageAdapter; // 信息选项列表的适配器
    private List<MsgMyListInfo<?>> msgMyListInfoList = new ArrayList<MsgMyListInfo<?>>();
    private String user_id;

    /**
     * 消息未读数
     */
    private UserMsgCountResp msgCountBean;


    public MessageHelper(Context context) {
        this.mContext = context;
        MyApplication.getInstance().registerEventBus(this);
        messageAdapter = new MessageAdapter(mContext, msgMyListInfoList);
        msgMyListInfoList.add(likeListInfo);
        msgMyListInfoList.add(commentListInfo);
        msgMyListInfoList.add(actionListInfo);
        user_id = UserInfoUtil.getInstance().getUserId();
    }

    public View getView() {

        mContentView = new LinearLayout(mContext);
        bindLinearLayout();
        refreshResource();
        getResource();
        return mContentView;
    }


    public void onDestroy() {
        MyApplication.getInstance().unregisterEventBus(this);
    }


    /**
     * 绑定布局
     */
    public void bindLinearLayout() {
        int count = messageAdapter.getCount();
        mContentView.removeAllViews();
        mContentView.setOrientation(LinearLayout.VERTICAL);
        int totalHeight = 0;
        for (int i = 0; i < count; i++) {
            final int position = i;
            View itemView = messageAdapter.getView(position, null, null);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    readMessageDetail(position);
                }
            });


            itemView.measure(0, 0);
            totalHeight += itemView.getMeasuredHeight();

            mContentView.addView(itemView, i);
        }

        int paddingTop = DensityUtil.dip2px(mContext, 10);
        int paddingBottom = DensityUtil.dip2px(mContext, 14);

        mContentView.setPadding(0, paddingTop, 0, paddingBottom);

        totalHeight += paddingTop;
        totalHeight += paddingBottom;

        AbsListView.LayoutParams params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, totalHeight);
        mContentView.setLayoutParams(params);

    }


    private void readMessageDetail(int position) {


        Intent intent = new Intent();
        switch (position) {
            case 0:
                intent.setClass(mContext, MsgLikedActivity.class);
                break;
            case 1:
                intent.setClass(mContext, MsgCommentMeActivity.class);
                break;
            case 2:
                intent.setClass(mContext, MsgActionActivity.class);
                break;

            default:
                break;
        }
        if (msgMyListInfoList.get(position) != null) {
            msgMyListInfoList.get(position).setNumNoRead(0);
            messageAdapter.notifyDataSetChanged();
        }
        mContext.startActivity(intent);

    }

    public void refreshCount() {
        updateMsgCount();
        updateContent();
    }


    /**
     * 设置控件原始显示数据
     */
    protected void refreshResource() {

        msgMyListInfoList.clear();
        msgMyListInfoList.add(likeListInfo);
        msgMyListInfoList.add(commentListInfo);
        msgMyListInfoList.add(actionListInfo);

        updateMsgCount();
        updateContent();
    }

    private void updateContent() {

        messageAdapter.setMyListInfoList(msgMyListInfoList);
//        bindLinearLayout();
        if (null != mContentView) {

            for (int i = 0; i < mContentView.getChildCount(); i++) {

                if (null == mContentView.getChildAt(i)) {
                    continue;
                }

                messageAdapter.getView(i, mContentView.getChildAt(i), null);
            }

        }


    }


    /**
     * 请求数据
     */
    public void getResource() {
        if (StringUtil.isNotEmpty(user_id)) {
            UsersHelper.getSingleTon().getMessageCount(mContext, user_id, countListener);
        }
    }

    IOnResponseListener<Response<UserMsgCountResp>> countListener = new IOnResponseListener<Response<UserMsgCountResp>>() {
        @Override
        public void onSuccess(Response<UserMsgCountResp> response) {
            msgCountBean = response.getData();
            if (user_id == null)
                return;
            if (isRefreshComment) {
                UsersHelper.getSingleTon().getMsgComment(mContext, user_id, commentListener);
            }
            if (isRefreshLike) {
                UsersHelper.getSingleTon().getMsgLike(mContext, user_id, likeListener);
            }
            if (isRefreshAction) {
                UsersHelper.getSingleTon().getMsgEvent(mContext, user_id, eventListener);
            }
        }

        @Override
        public void onError(Response response) {

        }
    };

    IOnResponseListener<Response<UserMsgEventResp>> eventListener = new IOnResponseListener<Response<UserMsgEventResp>>() {
        @Override
        public void onSuccess(Response<UserMsgEventResp> response) {
            if (response.getData() == null || response.getData().getMsgs() == null
                    || response.getData().getMsgs().size() <= 0)
                return;
            MsgMyActionListInfo info = new MsgMyActionListInfo();


            if (msgCountBean != null && msgCountBean.getMsg_data() != null)
                info.setNumNoRead(msgCountBean.getMsg_data().getEvent_msg_num());
            info.setMsgBean(response.getData().getMsgs());
            isRefreshAction = false;
            actionListInfo = info;

            refreshResource();
        }

        @Override
        public void onError(Response response) {

        }
    };
    IOnResponseListener<Response<UserMsgCommentsResp>> commentListener = new IOnResponseListener<Response<UserMsgCommentsResp>>() {
        @Override
        public void onSuccess(Response<UserMsgCommentsResp> response) {
            if (response.getData() == null || response.getData().getMsgs() == null
                    || response.getData().getMsgs().size() <= 0)
                return;
            MsgMyCommentListInfo info = new MsgMyCommentListInfo();

            if (msgCountBean != null && msgCountBean.getMsg_data() != null)
                info.setNumNoRead(msgCountBean.getMsg_data().getComment_msg_num());
            info.setMsgBean(response.getData().getMsgs());

            commentListInfo = info;
            isRefreshComment = false;

            refreshResource();
        }

        @Override
        public void onError(Response response) {

        }
    };

    IOnResponseListener<Response<UserMsgLikeResp>> likeListener = new IOnResponseListener<Response<UserMsgLikeResp>>() {
        @Override
        public void onSuccess(Response<UserMsgLikeResp> response) {
            if (response.getData() == null || response.getData().getMsgs() == null
                    || response.getData().getMsgs().size() <= 0)
                return;
            MsgMyLikeListInfo info = new MsgMyLikeListInfo();

            if (msgCountBean != null && msgCountBean.getMsg_data() != null)
                info.setNumNoRead(msgCountBean.getMsg_data().getLike_msg_num());

            info.setMsgBean(response.getData().getMsgs());
            isRefreshLike = false;

            likeListInfo = info;
            refreshResource();
        }

        @Override
        public void onError(Response response) {

        }
    };


    private void updateMsgCount() {


        if (!UserInfoUtil.getInstance().isLogin()) {
            return;
        }

        UserSettingResp.UserSettingBean setting = UserSettingHelper.getInstance().getUserSettingBean();
        //统计数量,统计设置接收消息数量;
        int totalNureadCount = 0;
        //是否有未读数量,用于显示小红点
        boolean hasUnreadMsg = false;

        if (null != likeListInfo && setting != null) {

            if (setting.isPush_like_msg()) {
                //点赞数量
                totalNureadCount += likeListInfo.getNumNoRead();
            }
            //是否有未读数量
            hasUnreadMsg = likeListInfo.getNumNoRead() > 0;

        }

        if (null != commentListInfo && setting != null) {

            if (setting.isPush_comment_msg()) {
                //统计评论数量
                totalNureadCount += commentListInfo.getNumNoRead();
            }

            if (!hasUnreadMsg) {
                hasUnreadMsg = commentListInfo.getNumNoRead() > 0;
            }

        }

        if (null != actionListInfo && setting != null) {

            if (setting.isPush_event_msg()) {
                //统计活动数量
                totalNureadCount += actionListInfo.getNumNoRead();
            }

            if (!hasUnreadMsg) {
                hasUnreadMsg = actionListInfo.getNumNoRead() > 0;
            }
        }


        //统计聊天未读消息数量
        MyImInfo imInfo = SharedPreferencesManager.getImUserInfo();
        if (null != imInfo) {

            int chatUnreadCount = EMClient.getInstance().chatManager().getUnreadMsgsCount();

            totalNureadCount += chatUnreadCount;
            if (!hasUnreadMsg) {
                hasUnreadMsg = chatUnreadCount > 0;
            }

        }


        EventBus.getDefault().post(new TotalUnreadCountEvent(totalNureadCount, hasUnreadMsg));
    }


    /**
     * "设置接收推送信息"接口的响应
     *
     * @param event
     */
    public void onEventMainThread(UserSettingEvent event) {
        if (event.getCode() == 0) {
            UserSettingHelper.getInstance().updateSettingBean();
            updateMsgCount();
            updateContent();
        }
    }

    public void onEventMainThread(ChatMsgCountEvent event) {
        updateMsgCount();
    }

    /**
     * 服务器推送消息时
     */
    public void onEventMainThread(JPushNotificationInfo info) {
        if (info.getExtras().equals("like"))
            isRefreshLike = true;
        else if (info.getExtras().equals("comment"))
            isRefreshComment = true;
        else if (info.getExtras().equals("event"))
            isRefreshAction = true;
        getResource();
    }


}
