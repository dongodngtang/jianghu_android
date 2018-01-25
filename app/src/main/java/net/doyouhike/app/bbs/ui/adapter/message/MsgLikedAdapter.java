/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: MsgLikedAdapter.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-21
 *
 * Changes (from 2015-10-21)
 * -----------------------------------------------------------------
 * 2015-10-21 创建MsgLikedAdapter.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.adapter.message;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.MsgMyCommentInfo;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.message.GetLikeMeListResp;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgLikeResp;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.activity.action.ActionDetailActivity;
import net.doyouhike.app.bbs.ui.home.NewLiveAdapter;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.glide.GlideHelper;
import net.doyouhike.app.library.ui.utils.DateUtils;

import java.util.List;

public class MsgLikedAdapter extends CommonAdapter<UserMsgLikeResp.MsgsBean> {

    private Context context;


    public MsgLikedAdapter(Context context, List<UserMsgLikeResp.MsgsBean> likedList) {
        super(context, likedList, R.layout.item_msg_liked);
        this.context = context;

    }


    @Override
    public void convert(ViewHolder holder, final UserMsgLikeResp.MsgsBean lInfo) {

        int position = holder.getmPosition();


        View headV = holder.getView(R.id.item_msg_liked_head);
        //内容块,整个itemView
        RelativeLayout parentV = holder.getView(R.id.rl_item_msg_liked_parent);

        ImageView likedPortraitIv = holder.getView(R.id.iv_itemt_liked_portrait);
        TextView likedNameTv = holder.getView(R.id.tv_itemt_liked_name);
        TextView tvTime = holder.getView(R.id.tv_msg_like_time);

        RelativeLayout likedOriginalRlyt = holder.getView(R.id.rlyt_item_liked_original);
        TextView likedOriginalTextV = holder.getView(R.id.tv_itemt_liked_original);
        ImageView likeOriginalImageV = holder.getView(R.id.iv_itemt_liked_original);


        View gapV = holder.getView(R.id.v_gap);


        // 头像
        String faceUrl = Constant.PHOTO_DOMAIN_PATH + lInfo.getFace();

        GlideHelper.displayHeader(context, likedPortraitIv, faceUrl);
        // 点击头像，他人主页
        UserHeadNickClickHelper.getInstance().setClickListener(
                likedPortraitIv,
                lInfo.getNick_name(),
                lInfo.getUser_id(),
                lInfo.getUser_id(),
                lInfo.getFace());
        // 点击昵称，他人主页
        likedNameTv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // 打开其他人的个人页面
                ActivityRouter.openOtherPageActivity(context, lInfo.getUser_id());

            }
        });
        // 名字
        likedNameTv.setText(lInfo.getNick_name());

        //时间设置
        tvTime.setText(DateUtils.getStrDate(mContext, lInfo.getCreated_time() * 1000));


        // 默认不用点击事件,只有活动才进入详情
        likedOriginalRlyt.setOnClickListener(null);
        parentV.setOnClickListener(null);
        parentV.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.selector_msg_item_disable_press));

        // 右边内容块的设置
        if (lInfo.getNode_type().equals("minilog")) { // 图文

            UIUtils.showView(likedOriginalTextV, false);
            UIUtils.showView(likeOriginalImageV, true);
            if (lInfo.getFirst_photo() != null)
                GlideHelper.displayNetImage(context, likeOriginalImageV,
                        Constant.PHOTO_DOMAIN_PATH + lInfo.getFirst_photo(), R.drawable.home_big_pic_fail_white);
            else
                GlideHelper.displayNetImage(context, likeOriginalImageV, "", R.drawable.news_icon_text);

        } else if (lInfo.getNode_type().equals(
                MsgMyCommentInfo.NODE_TYPE_URL_SHARE)) {// 帖子

            UIUtils.showView(likedOriginalTextV, false);
            UIUtils.showView(likeOriginalImageV, true);

            likeOriginalImageV
                    .setImageResource(R.drawable.ic_home_repost_link_3x);


        } else if (lInfo.getNode_type()
                .equals(MsgMyCommentInfo.NODE_TYPE_ACTION)) {// 活动


            UIUtils.showView(likedOriginalTextV, true);
            UIUtils.showView(likeOriginalImageV, true);


            likedOriginalTextV.setBackgroundResource(R.color.alpha_50_black);
            likedOriginalTextV.setTextColor(mContext.getResources().getColor(R.color.txt_dark_content));
            likedOriginalTextV.setText(lInfo.getEvent_title());
            likeOriginalImageV.setImageResource(R.drawable.activity_pic);


            parentV.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.selector_msg_item_bg));


            // 点击打开活动详情
            setOnOriginalClickListener(parentV, likedOriginalRlyt, lInfo.getNode_id(), lInfo.getNode_type());

        } else {// 已经删除


            UIUtils.showView(likedOriginalTextV, true);
            UIUtils.showView(likeOriginalImageV, false);

            likedOriginalTextV.setText(context.getResources().getString(
                    R.string.content_is_delete));
            likedOriginalTextV.setTextColor(context.getResources().getColor(R.color.txt_live_tip));
            likedOriginalTextV.setBackgroundColor(0xFFF6F8F6);

            likeOriginalImageV.setVisibility(View.GONE);

        }

        // 顶部补白部分
        if (position == 0) {
            headV.setVisibility(View.VISIBLE);
        } else {
            headV.setVisibility(View.GONE);
        }

        // 是否已读
        parentV.setSelected(lInfo.getIs_read() != 2);


        // 最后项填充列表处理
        UIUtils.showView(gapV, holder.getmPosition() < getCount() - 1);
    }


    /**
     * 给原内容块添加点击监听的方法
     */
    private void setOnOriginalClickListener(RelativeLayout parentV, RelativeLayout likedOriginalRlyt,
                                            final String nodeID, final String liveTypeText) {
        parentV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 点击跳转到活动详情
                Intent intent = new Intent(context,
                        ActionDetailActivity.class);
                intent.putExtra("nodeId", nodeID);
                context.startActivity(intent);
            }
        });
        likedOriginalRlyt.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                if (liveTypeText.equals(MsgMyCommentInfo.NODE_TYPE_ACTION)) {
                    // 点击跳转到活动详情
                    Intent intent = new Intent(context,
                            ActionDetailActivity.class);
                    intent.putExtra("nodeId", nodeID);
                    context.startActivity(intent);
                } else {

//                    Intent intent = new Intent();
//                    intent.setClass(context, LiveDetailActivity.class);
//                    intent.putExtra(
//                            LiveDetailActivity.INTENT_EXTRA_NAME_LIVE_ID,
//                            nodeID);
//                    intent.putExtra(
//                            LiveDetailActivity.INTENT_EXTRA_NAME_LIVE_TYPE,
//                            liveTypeText);
//                    context.startActivity(intent);
                }
            }
        });
    }


}
