/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: PhoneContactsListAdapter.java
 * Author: Zhangkai
 * Version: 1.0
 * Create: 2015-11-03
 *
 */

package net.doyouhike.app.bbs.ui.adapter.action;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.PhoneContactInfo;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.ui.util.FollowButtonHelper;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.BitmapUtil;
import net.doyouhike.app.bbs.util.ImageUtil;
import net.doyouhike.app.bbs.util.StrUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import java.io.InputStream;
import java.util.List;

public class PhoneContactsListAdapter extends BaseAdapter {
    private Context context;
    private List<PhoneContactInfo> contactList;
    private String myUserID;

    public PhoneContactsListAdapter(Context context,
                                    List<PhoneContactInfo> contactList) {
        this.context = context;
        this.contactList = contactList;
        this.myUserID = UserInfoUtil.getInstance().getUserId();
    }

    @Override
    public int getCount() {
        if (contactList != null) {
            return contactList.size();
        }

        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (contactList != null && position < contactList.size()) {
            return contactList.get(position);
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
                    R.layout.item_recommend_user_info, parent, false);
            vh.ivAvatar = (ImageView) convertView
                    .findViewById(R.id.iv_avatar);
            vh.tvConatctName = (TextView) convertView
                    .findViewById(R.id.tv_nick_name);
            vh.tvContactNum = (TextView) convertView
                    .findViewById(R.id.tv_user_signature);
            vh.vBottomSide = convertView.findViewById(R.id.v_bottom_side);
            vh.parentView = convertView.findViewById(R.id.llyt_user_info);
            vh.imgBtn = (TextView) convertView.findViewById(R.id.iv_attend_btn);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }


        PhoneContactInfo info = contactList.get(position);

        if (vh != null && info != null) {
            //是否磨房用户
            if (info.isMFUser) {
                // 设置头像
                setAvatar(vh.ivAvatar, info);

                //整栏目可点,打开他人主页
                setParentView(vh.parentView,info.mfUserId);
                // 设置关注按钮
                FollowButtonHelper.setTextState(vh.imgBtn,info.attendState,info.mfUserId);


            } else {
                // 设置头像
                setAvatar(vh.ivAvatar, info.photoId, info.contactId);
                vh.parentView.setOnClickListener(null);
                vh.parentView.setBackgroundColor(Color.WHITE);
                // 设置邀请按钮
                FollowButtonHelper.setTextState(vh.imgBtn,info.attendState,info.phoneNum);
            }

            // 设置通讯录名称
            setUsrName(vh.tvConatctName, info.name);

            // 设置号码或者磨坊昵称
            setPhoneOrSignature(vh.tvContactNum, info);

            // 设置边线
            setSideLine(vh.vBottomSide, position);

        }

        return convertView;
    }

    private void setSideLine( View vBottomSide, int position) {
        // 最后一个
        if (position == contactList.size() - 1) {
            vBottomSide.setVisibility(View.GONE);
        }else{
            vBottomSide.setVisibility(View.VISIBLE);
        }

    }


    private void setParentView(View vParent, final String userID) {

        if (UserInfoUtil.getInstance().isSameUser(myUserID,userID)) {
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


    private void setPhoneOrSignature(TextView tvUserSignature,
                                     PhoneContactInfo info) {
        if (info.isMFUser && info.mfNickName != null) {
            String text = StrUtils.getResourcesStr(context, R.string.mo_fang_);
            tvUserSignature.setText(text + info.mfNickName);
        } else {
            tvUserSignature.setText(info.phoneNum);
        }
    }

    private void setUsrName(TextView tvUserName, String userName) {
        if (tvUserName == null) {
            return;
        }

        if (userName != null) {
            tvUserName.setText(userName);
        } else {
            tvUserName.setText("");
        }
    }

    private void setAvatar(ImageView ivAvatar, long photoid, long contactid) {

        ivAvatar.setOnClickListener(null);

        if (ivAvatar == null) {
            return;
        }

        if (photoid > 0) {
            Uri uri = ContentUris.withAppendedId(
                    ContactsContract.Contacts.CONTENT_URI, contactid);
            InputStream input = ContactsContract.Contacts
                    .openContactPhotoInputStream(context.getContentResolver(),
                            uri);
            ivAvatar.setImageBitmap(ImageUtil.toRoundCorner(BitmapFactory.decodeStream(input),ivAvatar.getWidth()/2));
        } else {
            ivAvatar.setImageResource(R.drawable.address_book_placeholder_3x);
        }

    }

    private void setAvatar(ImageView ivAvatar,  PhoneContactInfo info) {
        if (ivAvatar == null) {
            return;
        }
        if (info.mfAvatarUrl != null) {
            GlideHelper.displayHeader(context, ivAvatar, info.mfAvatarUrl);
        } else {
            ivAvatar.setImageResource(R.drawable.none_header); // TODO
        }

        //设置头像打开他人主页
        UserHeadNickClickHelper.getInstance().setClickListener(
                ivAvatar,
                info.mfNickName,
                info.mfUserId,
                info.uuid,
                info.mfAvatarUrl);
    }

    private class ViewHolder {
        private View vBottomSide;
        private ImageView ivAvatar;
        private TextView tvConatctName;
        private TextView tvContactNum;
        private TextView imgBtn;
        private View parentView;
    }

    public List<PhoneContactInfo> getContactList() {
        return contactList;
    }

    public void setContactList(List<PhoneContactInfo> contactList) {
        this.contactList = contactList;
    }

}
