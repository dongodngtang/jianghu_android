/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: FollowAndFollowerAdapter.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-6
 *
 * Changes (from 2015-10-7)
 * -----------------------------------------------------------------
 * 2015-10-7 创建FollowAndFollowerAdapter.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.adapter.me;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.FollowedUser;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.ui.util.FollowButtonHelper;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.List;

public class FollowAndFollowerAdapter extends BaseAdapter {

    private Context context;
    private int acitivtyType;
    private List<FollowedUser> followUserList;

    private ViewHolder mViewHolder;

    private int paddingTop;
    /**
     * 当前登录用户ID
     */
    private String currentUserId;

    public FollowAndFollowerAdapter(Context context, int acitivtyType,
                                    List<FollowedUser> followUserList, int paddingTop) {
        this.context = context;
        this.acitivtyType = acitivtyType;
        this.followUserList = followUserList;
        this.paddingTop = paddingTop;

        currentUserId = UserInfoUtil.getInstance().getUserId();
    }

    @Override
    public int getCount() {
        return followUserList.size();
    }

    @Override
    public Object getItem(int position) {
        return followUserList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_follow_and_follower, parent, false);

            mViewHolder = new ViewHolder(convertView);


            mViewHolder.portraitIv = (ImageView) convertView
                    .findViewById(R.id.iv_itemt_portrait);
            mViewHolder.nicknameTv = (TextView) convertView
                    .findViewById(R.id.tv_itemt_nickname);
            mViewHolder.autographTv = (TextView) convertView
                    .findViewById(R.id.tv_itemt_autograph);
            mViewHolder.followStateBtn = (TextView) convertView
                    .findViewById(R.id.btn_itemt_follow_state);
            mViewHolder.parentView = convertView
                    .findViewById(R.id.ll_item_follow_and_follower_parent);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        // 项数据显示
        final FollowedUser followedUser = followUserList.get(position);

        // 头像
        String faceUrl = followedUser.getAvatar();

        GlideHelper.displayHeader(context, mViewHolder.portraitIv, faceUrl);

        // 打开其他人的个人页面
        UserHeadNickClickHelper.getInstance().setClickListener(mViewHolder.portraitIv,
                followedUser.getNickName(),
                followedUser.getUserID(),
                followedUser.getUuid(),
                followedUser.getAvatar());

        //整栏可点
        setParentView(mViewHolder.parentView, followedUser.getUserID());

        mViewHolder.nicknameTv.setText(followedUser.getNickName());
        if (followedUser.getSignature() != null) {
            mViewHolder.autographTv.setText(followedUser.getUserDesc());
        } else {
            mViewHolder.autographTv.setText("");
        }

        //配置关注按钮
        FollowButtonHelper.setTextState(mViewHolder.followStateBtn, followedUser.getIsFollowed(), followedUser.getUserID());

        if (UserInfoUtil.getInstance().isSameUser(currentUserId, followedUser.getUserID())) {
            // 在别人的列表中，出现自己，则没有按钮
            mViewHolder.portraitIv.setOnClickListener(null);
        }


        return convertView;
    }

    @SuppressLint("NewApi")
    private int getGapHeight() {
        // 屏幕高度
        Point outSize = new Point();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getRealSize(outSize);

        int windowH = outSize.y;

        int listViewHeight = getTotalHeightofListView(windowH - paddingTop);
        int gapHeight = windowH - paddingTop - listViewHeight;
        return gapHeight;
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
     * 更新数据，顺便刷新显示
     */
    public void setFolloUserList(List<FollowedUser> followUserList) {
        this.followUserList = followUserList;

        notifyDataSetChanged();
    }


    private void setParentView(View vParent, final String userID) {

        if (UserInfoUtil.getInstance().isSameUser(currentUserId, userID)) {
            vParent.setOnClickListener(null);
            vParent.setBackgroundColor(Color.WHITE);
        } else {
            vParent.setBackgroundResource(R.drawable.selector_list_item_bg);
            vParent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityRouter.openOtherPageActivity(v.getContext(), userID);
                }
            });
        }
    }


    static class ViewHolder {

        ImageView portraitIv;
        TextView nicknameTv;
        TextView autographTv;
        TextView followStateBtn;
        View parentView;

        public ViewHolder(View convertView) {


            portraitIv = (ImageView) convertView
                    .findViewById(R.id.iv_itemt_portrait);
            nicknameTv = (TextView) convertView
                    .findViewById(R.id.tv_itemt_nickname);
            autographTv = (TextView) convertView
                    .findViewById(R.id.tv_itemt_autograph);
            followStateBtn = (TextView) convertView
                    .findViewById(R.id.btn_itemt_follow_state);
            parentView = convertView
                    .findViewById(R.id.ll_item_follow_and_follower_parent);
        }
    }

}
