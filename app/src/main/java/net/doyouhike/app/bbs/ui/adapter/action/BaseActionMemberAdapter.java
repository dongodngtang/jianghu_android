/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-10-19
 *
 */
package net.doyouhike.app.bbs.ui.adapter.action;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.DetailMembers;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventMembersResp;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.util.List;

/**
 * 活动成员列表
 */
public class BaseActionMemberAdapter extends CommonAdapter<EventMembersResp.ItemsBean> {


    private int mCount;


    private static final int VIEW_TYPE_HEAD = 0;
    private static final int VIEW_TYPE_CONTENT = 1;


    private String format = "未确认%d人";

    private String currentUserId;

    public BaseActionMemberAdapter(Context context, List<EventMembersResp.ItemsBean> datas) {
        super(context, datas, R.layout.item_action_member);
        currentUserId = UserInfoUtil.getInstance().getUserId();
    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEW_TYPE_HEAD : VIEW_TYPE_CONTENT;
    }

    @Override
    public int getCount() {
        if (mDatas != null) {
            return mDatas.size() + 1;
        }
        return 1;
    }

    @Override
    public EventMembersResp.ItemsBean getItem(int position) {

        if (position == 0) {
            return null;
        }

        if (mDatas == null || mDatas.isEmpty()) {
            return null;
        }
        return mDatas.get(position - 1);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (getItemViewType(position) == VIEW_TYPE_HEAD) {// 设置参加人数标题栏
            convertView = View.inflate(mContext,
                    R.layout.item_action_listview_head, null);
            TextView tv = (TextView) convertView.findViewById(R.id.tv_number);
            tv.setText(String.format(format, mCount));
            return convertView;
        } else {
            return super.getView(position, convertView, parent);
        }
    }


    @Override
    public void convert(ViewHolder holder, final EventMembersResp.ItemsBean members) {

        //设置头像
        ImageView ivAvatar = holder.getView(R.id.riv_action_manager_head);
        String avatarUrl = Constant.PHOTO_DOMAIN_PATH + members.getUser().getAvatar();
        GlideHelper.displayHeader(mContext, ivAvatar, avatarUrl);

        UserHeadNickClickHelper.getInstance().setClickListener(ivAvatar,
                members.getUser().getNick_name(),
                members.getUser().getUser_id(),
                members.getUser().getUser_id(),
                members.getUser().getAvatar());
        //整栏可点
        View parentView = holder.getView(R.id.ll_item_action_member);
        if (currentUserId.equals(members.getUser().getUser_id())) {
            parentView.setOnClickListener(null);
            parentView.setBackgroundColor(Color.WHITE);
        } else {
            parentView.setBackgroundResource(R.drawable.selector_list_item_bg);
            parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityRouter.openOtherPageActivity(v.getContext(), members.getUser().getUser_id());
                }
            });
        }


        holder.setText(R.id.tv_action_manager_nickname, members.getUser().getNick_name());

        ImageView sex = holder.getView(R.id.iv_action_manager_sex);

        if (Constant.MALE.equals(members.getUser().getSex())) {
            sex.setImageResource(R.drawable.ic_me_male_3x);
        } else if (Constant.FEMALE.equals(members.getUser().getSex())) {
            sex.setImageResource(R.drawable.ic_me_female_3x);
        } else {
            sex.setVisibility(View.GONE);
        }
    }

    /**
     * @param count 设置未确认人员数量
     */
    public void setCount(int count) {
        mCount = count;
    }

    /**
     * @param isAdd 设置未确认数量 是否加减
     */
    public void resetUnConfirmCount(boolean isAdd) {

        if (isAdd) {
            mCount++;
        } else if (mCount > 0) {
            mCount--;
        }
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
