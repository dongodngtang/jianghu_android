/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: MessageAdapter.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-6
 *
 * Changes (from 2015-10-7)
 * -----------------------------------------------------------------
 * 2015-10-7 创建MessageAdapter.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.adapter.message;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.listinfo.MsgMyActionListInfo;
import net.doyouhike.app.bbs.biz.entity.listinfo.MsgMyCommentListInfo;
import net.doyouhike.app.bbs.biz.entity.listinfo.MsgMyLikeListInfo;
import net.doyouhike.app.bbs.biz.entity.listinfo.MsgMyListInfo;
import net.doyouhike.app.bbs.biz.openapi.presenter.UserSettingHelper;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgCommentsResp;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgEventResp;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgLikeResp;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserSettingResp;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.utils.DateUtils;

import java.util.List;

public class MessageAdapter extends BaseAdapter {

    private Context context;

    private List<MsgMyListInfo<?>> msgMyListInfoList;

    // 默认参数
    private String[] messageStrArr = new String[3]; // 题目
    private int[] messageColorArr = new int[3]; // 图标背景颜色
    private int[] messageImageArr = new int[3]; // 图标

    public MessageAdapter(Context context,
                          List<MsgMyListInfo<?>> msgMyListInfoList) {
        this.context = context;
        this.msgMyListInfoList = msgMyListInfoList;

        setArrData();
    }

    private void setArrData() {
        Resources resources = context.getResources();

        // 图标、图标背景、题目
        messageStrArr[0] = resources.getString(R.string.like);
        messageStrArr[1] = resources.getString(R.string.comment);
        messageStrArr[2] = resources.getString(R.string.action);
//		messageStrArr[3] = resources.getString(R.string.follow);
        messageColorArr[0] = R.drawable.icon_new_like;
        messageColorArr[1] = R.drawable.icon_new_comment;
        messageColorArr[2] = R.drawable.icon_news_activity;
//		messageColorArr[3] = R.color.green_bg;
        messageImageArr[0] = R.drawable.icon_new_like;
        messageImageArr[1] = R.drawable.icon_new_comment;
        messageImageArr[2] = R.drawable.icon_news_activity;
//		messageImageArr[3] = R.drawable.news_attention;
    }

    @Override
    public int getCount() {
        return messageStrArr.length;
    }

    @Override
    public Object getItem(int position) {
        return messageStrArr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_msg_main, null);
        }
        RelativeLayout relativeLayout = (RelativeLayout) convertView
                .findViewById(R.id.rl_message_list_circle);
        ImageView messageIconIv = (ImageView) convertView
                .findViewById(R.id.iv_itemt_msg_icon);
        TextView messageTextTv = (TextView) convertView
                .findViewById(R.id.tv_itemt_msg_text);
        TextView messageContentTv = (TextView) convertView
                .findViewById(R.id.tv_itemt_msg_content);

        TextView messageCountTv = (TextView) convertView
                .findViewById(R.id.tv_itemt_msg_count);
        TextView messageNonbellTv = (TextView) convertView
                .findViewById(R.id.tv_itemt_msg_nobell);
        //未读消息小红点
        ImageView ivMsgUnreadIndicate = (ImageView) convertView
                .findViewById(R.id.iv_item_msg_has_unread);

        TextView messageTimeTv = (TextView) convertView
                .findViewById(R.id.tv_message_list_time);
        View divider = convertView.findViewById(R.id.v_item_msg_frg_divider);

        relativeLayout.setBackgroundResource(messageColorArr[position]);
//        messageIconIv.setImageDrawable(context.getResources().getDrawable(messageImageArr[position]));
        messageTextTv.setText(messageStrArr[position]);
        UIUtils.showView(divider, position != 2);

        String content = "";
        messageTimeTv.setText("");
        switch (position) {
            case 0:
                if (position < msgMyListInfoList.size()) {
                    // 赞
                    MsgMyLikeListInfo likeListInfo = (MsgMyLikeListInfo) msgMyListInfoList
                            .get(position);

                    if (likeListInfo != null
                            && likeListInfo.getMsgBean() != null
                            && likeListInfo.getMsgBean().size() > 0) {
                        UserMsgLikeResp.MsgsBean likeInfo = likeListInfo.getMsgBean().get(0);
                        content += likeInfo.getNick_name() + " 赞了你";
                        messageTimeTv.setText(DateUtils.getStrDate(context, likeInfo.getCreated_time() * 1000));
                    } else {
                        content = "这里可以看到被哪些人点赞";
                    }
                    messageContentTv.setText(content);
                }
                break;
            case 1:

                // 评论
                if (position < msgMyListInfoList.size()) {
                    MsgMyCommentListInfo commentListInfo = (MsgMyCommentListInfo) msgMyListInfoList
                            .get(position);

                    if (commentListInfo != null
                            && commentListInfo.getMsgBean() != null
                            && commentListInfo.getMsgBean().size() > 0) {
                        UserMsgCommentsResp.MsgsBean commentInfo = commentListInfo.getMsgBean().get(0);

                        List<UserMsgCommentsResp.MsgsBean.ContentBean> contents = commentInfo.getContent();

                        content = commentInfo.getNick_name() + " 评论 ：";
                        String strComment = "";
                        if (null != contents && !contents.isEmpty()) {

                            for (UserMsgCommentsResp.MsgsBean.ContentBean richTextContent : contents) {
                                if (null != richTextContent.getType()
                                        && richTextContent.getContent() != null
                                        && richTextContent.getType().equals("text")) {
                                    strComment = strComment + richTextContent.getContent();

                                }
                            }
                            content += strComment;

                        }

                        messageTimeTv.setText(DateUtils.getStrDate(context,
                                commentInfo.getCreated_time() * 1000));

                    } else {
                        content = "将展示你被评论、被回复的内容";
                    }
                    messageContentTv.setText(content);
                }
                break;
            case 2:
                if (position < msgMyListInfoList.size()) {
                    // 活动
                    MsgMyActionListInfo actionListInfo = (MsgMyActionListInfo) msgMyListInfoList
                            .get(position);

                    if (actionListInfo != null
                            && actionListInfo.getMsgBean() != null
                            && actionListInfo.getMsgBean().size() > 0) {
                        UserMsgEventResp.MsgsBean actionInfo = actionListInfo.getMsgBean().get(0);

                        String actionTitle = actionInfo.getEvent_title();

                        if (UserInfoUtil.getInstance().isCurrentUser(actionInfo.getUser_id())) {

                            content = "我 参加：" + actionTitle;

                            if (actionInfo.getRole() != 9) {
                                //"role": "1",  // 角色（1：召集人，2：协作，3：财务，4：普通成员，5：留守人员，9:未确认）
                                content += " 已被确认";
                            }
                        } else {

                            content = actionInfo.getNick_name() + " 参加："
                                    + actionTitle;
                        }

                        messageTimeTv.setText(DateUtils.getStrDate(context, actionInfo.getCreated_time() * 1000));
                    } else {
                        content = "你创建、参加的活动消息";
                    }
                    messageContentTv.setText(content);
                }
                break;

            default:
                break;
        }
        setCountAndState(position, messageCountTv, messageNonbellTv, ivMsgUnreadIndicate);

        return convertView;
    }

    /**
     * 设置未读消息数量,及可见性
     *
     * @param position            位置
     * @param messageCountTv      消息数量
     * @param messageNonbellTv    屏蔽消息标识
     * @param ivMsgUnreadIndicate 未读消息小圆点
     */
    private void setCountAndState(int position, TextView messageCountTv,
                                  TextView messageNonbellTv, ImageView ivMsgUnreadIndicate) {


        UserSettingResp.UserSettingBean setting = UserSettingHelper.getInstance().getUserSettingBean();
        boolean state = true;
        if (setting != null) {
            switch (position) {
                case 0:
                    state = setting.isPush_like_msg();
                    break;
                case 1:
                    state = setting.isPush_comment_msg();
                    break;
                case 2:
                    state = setting.isPush_event_msg();
                    break;
                case 3:
                    state = setting.isPush_fans_msg();
                    break;
                default:
                    break;
            }

            UIUtils.showView(messageNonbellTv, !state);
        }


        if (position < msgMyListInfoList.size()) {

            MsgMyListInfo<?> listInfo = msgMyListInfoList.get(position);

            if (listInfo != null
                    && listInfo.getMsgBean() != null
                    && listInfo.getMsgBean().size() > 0
                    && listInfo.getNumNoRead() > 0
                    ) {

                int unreadCount = listInfo.getNumNoRead();

                if (unreadCount <= 99) {
                    messageCountTv.setText(String.valueOf(unreadCount));
                } else {
                    messageCountTv.setText("99");
                }

                LogUtil.d("msgEvent", "listInfo :" + listInfo.getNumNoRead());

                UIUtils.showView(messageCountTv, state);
                UIUtils.showView(ivMsgUnreadIndicate, !state);


            } else {
                LogUtil.d("msgEvent", "getMsgBean().size()=0 :");
                messageCountTv.setText("0");
                UIUtils.showView(messageCountTv, false);
                UIUtils.showView(ivMsgUnreadIndicate, false);
            }

        }

    }

    /**
     * 更新数据源，顺便刷新界面
     */
    public void setMyListInfoList(List<MsgMyListInfo<?>> msgMyListInfoList) {
        this.msgMyListInfoList = msgMyListInfoList;

    }

}
