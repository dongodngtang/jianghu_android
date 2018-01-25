/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: UserInfoAdapter.java
 * Author: 伍建鹏(wu-yoline)
 * Version: 1.0
 * Create: 2015-11-03
 *
 * Changes (from 2015-11-03)
 * -----------------------------------------------------------------
 * 2015-11-03 : 创建本类，完成本类功能;(wu-yoline)
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.adapter.live;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.RecommendUser;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.util.FollowButtonHelper;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.List;

/**
 * 搜索用户界面，推荐用户ListView的Adapter
 *
 * @author wu-yoline
 */
public class RecommendUserAdapter extends BaseAdapter {

    private Context context;
    private List<RecommendUser> users;
    private final String token;
    private String currentUserId;

    public RecommendUserAdapter(Context context, List<RecommendUser> users) {
        this.context = context;
        this.users = users;
        token = UserInfoUtil.getInstance().getToken();
        currentUserId = UserInfoUtil.getInstance().getUserId();
    }

    @Override
    public int getCount() {
        if (users != null) {
            return users.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (users != null) {
            return users.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_recommend_user_info, null);
            vh.ivAvatar = (ImageView) convertView
                    .findViewById(R.id.iv_avatar);
            vh.tvUserName = (TextView) convertView
                    .findViewById(R.id.tv_nick_name);
            vh.tvUserSignature = (TextView) convertView
                    .findViewById(R.id.tv_user_signature);
            vh.vBottomSide = convertView.findViewById(R.id.v_bottom_side);
            vh.vParent = convertView.findViewById(R.id.llyt_user_info);
            vh.ivAttend = (TextView) convertView
                    .findViewById(R.id.iv_attend_btn);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        RecommendUser user = null;
        if (users != null) {
            user = users.get(position);
        }
        if (vh != null && user != null) {

            // 设置头像
            setAvatar(vh.ivAvatar, user);
            //整栏可点
            setParentView(vh.vParent, user.getUser_id());

            //整栏可点


            // 设置昵称
            setUsrName(vh.tvUserName, user.getNick_name());

            // 设置签名
            setUserSignature(vh.tvUserSignature, user.getUser_desc());

            // 设置关注按钮

            int isFollowed = UsersHelper.getSingleTon().getFollowStateByUserId(user.getUser_id());

            FollowButtonHelper.setTextState(vh.ivAttend, isFollowed, user.getUser_id());

            // 设置边线
            setSideLine(vh.vBottomSide, position);
        }

        return convertView;
    }

    private void setParentView(View vParent, final String userID) {

        if (null == userID || (null != currentUserId && currentUserId.equals(userID))) {
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

    private void setSideLine(View vBottomSide, int position) {

        // 最后一个
        if (position == users.size() - 1) {
            vBottomSide.setVisibility(View.GONE);
        } else {
            vBottomSide.setVisibility(View.VISIBLE);
        }

    }

    private void setUserSignature(TextView tvUserSignature, String signature) {
        if (tvUserSignature != null && signature != null) {
            tvUserSignature.setText(signature);
        }
    }

    private void setUsrName(TextView tvUserName, String userName) {
        if (tvUserName != null && userName != null) {
            tvUserName.setText(userName);
        }
    }

    private void setAvatar(ImageView ivAvatar, RecommendUser user) {
        if (ivAvatar != null) {
            String avatar_url = Constant.PHOTO_DOMAIN_PATH + user.getAvatar();
            GlideHelper.displayHeader(context, ivAvatar,avatar_url);
            UserHeadNickClickHelper.getInstance().setClickListener(ivAvatar,
                    user.getNick_name(),
                    user.getUser_id(),
                    user.getUser_id(),
                    avatar_url);
        }
    }


    private class ViewHolder {
        private View vBottomSide;
        private View vParent;
        private ImageView ivAvatar;
        private TextView tvUserName;
        private TextView tvUserSignature;
        private TextView ivAttend;
    }

    public List<RecommendUser> getUsers() {
        return users;
    }

    public void setUsers(List<RecommendUser> users) {
        this.users = users;
    }

}
