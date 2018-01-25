/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: MsgActionAdapter.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-21
 *
 * Changes (from 2015-10-21)
 * -----------------------------------------------------------------
 * 2015-10-21 创建MsgActionAdapter.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.adapter.message;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.MsgMyActionInfo;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.ui.widget.BottomDialogWindow;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.library.ui.utils.DateUtils;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.List;

public class MsgActionAdapter extends BaseAdapter {

    private Context context;

    BottomDialogWindow cancelMemberDialog;

    /**
     * 当前登录用户ID
     */
    private String myselfID;

    // 数据源
    private List<MsgMyActionInfo> actionList;


    public MsgActionAdapter(Context context, List<MsgMyActionInfo> actionList) {
        this.context = context;
        this.actionList = actionList;
        myselfID=UserInfoUtil.getInstance().getUserId();

    }

    @Override
    public int getCount() {
        return actionList.size();
    }

    @Override
    public MsgMyActionInfo getItem(int position) {
        if (actionList!=null&&!actionList.isEmpty()){
            return actionList.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_msg_action, parent, false);
        }

        // 绑定
        //View headV = convertView.findViewById(R.id.item_msg_action_head);

        ImageView actionPortraitIv = (ImageView) convertView
                .findViewById(R.id.iv_itemt_action_portrait);
        TextView actionNameTv = (TextView) convertView
                .findViewById(R.id.tv_itemt_action_name);
        TextView actionJoinTv = (TextView) convertView
                .findViewById(R.id.tv_itemt_action_join);

        TextView actionElapsedTimeTv = (TextView) convertView
                .findViewById(R.id.tv_itemt_action_elapsed_time);
        TextView actionTitleTv = (TextView) convertView
                .findViewById(R.id.tv_itemt_action_title);
        final TextView actionConfirmTv = (TextView) convertView
                .findViewById(R.id.tv_itemt_action_confirm);
        TextView actionVerificationtTv = (TextView) convertView
                .findViewById(R.id.tv_itemt_action_verification_text);
        RelativeLayout rl_item_msg_action  =  (RelativeLayout) convertView
                .findViewById(R.id.rl_item_msg_action);
        /*ImageView actionIsReadIv = (ImageView) convertView
                .findViewById(R.id.iv_itemt_action_isread);*/

        View v_item_divider = convertView.findViewById(R.id.v_item_msg_action_divider);
        UIUtils.showView(v_item_divider, position == 0);

        final MsgMyActionInfo aInfo = getItem(position);

        if (null==aInfo){
            return convertView;
        }

        // 显示
        GlideHelper.displayHeader(context, actionPortraitIv, aInfo.getFace());// 头像
        // 点击头像,打开其他人的个人页面
        UserHeadNickClickHelper.getInstance().setClickListener(actionPortraitIv,
                aInfo.getNickName(),
                aInfo.getUserID(),
                aInfo.getUuid(),
                aInfo.getFace());

        // 点击昵称，他人主页
        actionNameTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 打开其他人的个人页面
                ActivityRouter.openOtherPageActivity(context,aInfo.getUserID());
            }
        });

        actionNameTv.setText(aInfo.getNickName()); // 参加者名
        if (UserInfoUtil.getInstance().isSameUser(myselfID,aInfo.getUserID())){
            actionNameTv.setText("我"); // 参加者名
        }

        actionJoinTv.setText(context.getResources().getString(
                R.string.join_the_action));

        // TODO 申请时间
        String creatTimeStr = DateUtils.getStrDate(context,
                aInfo.getCreated() * 1000);
        actionElapsedTimeTv.setText(creatTimeStr);

        actionTitleTv.setText(aInfo.getEventTitle()); // 活动标题


        // 留言
        if (aInfo.getMemo() != null && !aInfo.getMemo().equals("")) {
            actionVerificationtTv.setVisibility(View.VISIBLE);
            // 修改actionVerificationtTv的内容
            actionVerificationtTv.setText(aInfo.getMemo());

        } else {
            actionVerificationtTv.setVisibility(View.GONE);
        }

       /* if (position != 0) {
            headV.setVisibility(View.GONE);
        } else {
            headV.setVisibility(View.VISIBLE);
        }*/

        // 是否已读
        if (aInfo.getIsRead() == 2) {
            rl_item_msg_action.setBackgroundColor(context.getResources().getColor(R.color.white));
        } else {
            rl_item_msg_action.setBackgroundColor(context.getResources().getColor(R.color.bg_message_itme_unread));
        }

        // 最后项填充列表处理
        /*if (position < actionList.size() - 1) {
            gapV.setVisibility(View.GONE);
        } else {
            int gapHeight = 0;
            if (parent != null) {
                gapHeight = getGapHeight();
            }
            LayoutParams layoutParams = new LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, gapHeight);
            gapV.setLayoutParams(layoutParams);
            gapV.setVisibility(View.VISIBLE);
        }*/


        // actionConfirmTv.setSelected(false); // 是否确认(按钮屏蔽)
        if (aInfo.getRole() != 9 && aInfo.getIsOrganizer() == 0) {
            actionConfirmTv.setVisibility(View.VISIBLE);
            actionConfirmTv.setText(context.getResources().getString(
                    R.string.action_confirmed));
        } else {
            actionConfirmTv.setVisibility(View.INVISIBLE);
        }

        if (getItem(position).getNodeType().equals(MsgMyActionInfo.minilog_del)){
        //活动取消
            actionTitleTv.setOnClickListener(null);
            actionConfirmTv.setText(context.getResources().getString(
                    R.string.action_cancel));
        }else {

            //点击标题进入活动详情
            actionTitleTv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityRouter.openActionDetailActivity(v.getContext(),aInfo.getNodeID());
                }
            });
        }


        return convertView;
    }

    /**
     * 获取ListView高度
     *
     * @param maxHeight
     */
    private int getTotalHeightofListView(int maxHeight) {
        int totalHeight = 0;

        for (int i = 0; i < getCount(); i++) {
            View itemView = getView(i, null, null);
            itemView.measure(
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
                    MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED));
            totalHeight += itemView.getMeasuredHeight();
            if (maxHeight > 0 && totalHeight > maxHeight) {
                return maxHeight;
            }
        }
        return totalHeight;
    }

    /**
     * 重设新数据源，顺便刷新界面
     */
    public void setActionList(List<MsgMyActionInfo> actionList) {
        this.actionList = actionList;

        notifyDataSetChanged();
    }
}
