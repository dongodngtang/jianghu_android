/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: RecommendUser.java
 * Author: wu-yoline(伍建鹏)
 * Version: 1.0
 * Create: 2015-11-03
 *
 * Changes (from 2015-11-04)
 * -----------------------------------------------------------------
 * 2015-11-04 : 1、添加changeToPhoneContactInfo()方法; (wu-yoline)
 * 				2、继承Comparable接口，实现按姓名排序;(wu-yoline)
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity;

import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.util.StrUtils;

import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Locale;

/**
 * 针对搜索用户界面的请求接口"/system/getRecommendUser"返回的用户信息的实体类，对"/system/checkContact"
 * 接口同样适用
 *
 * @author wu-yoline
 */
public class RecommendUser implements Comparable<RecommendUser> {
    private String user_id;
    private int internal_id;
    private String user_name;
    private String nick_name;
    private String sex;
    private String avatar;
    private String user_desc;
    private String mobile;

    private String follow;


    /**
     * 转变为PhoneContactInfo类的对象
     *
     * @return
     */
    public PhoneContactInfo changeToPhoneContactInfo() {
        PhoneContactInfo info = new PhoneContactInfo();
        info.mfUserId = user_id;
        info.isMFUser = true;
        info.mfUserName = user_name;
        info.mfNickName = nick_name;
        info.mfAvatarUrl = Constant.PHOTO_DOMAIN_PATH + avatar;
        info.mfUserDesc = user_desc;
        info.phoneNum = mobile;
        info.attendState = UsersHelper.getSingleTon().getFollowStateBySocial(follow);
        return info;
    }


    @Override
    public int compareTo(RecommendUser another) {
        RuleBasedCollator collator = (RuleBasedCollator) Collator
                .getInstance(Locale.CHINA);
        if (another != null && StrUtils.hasContent(another.user_id)
                && StrUtils.hasContent(this.user_id)) {
            return collator.compare(another.user_id, this.user_id);
        }
        return 0;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getInternal_id() {
        return internal_id;
    }

    public void setInternal_id(int internal_id) {
        this.internal_id = internal_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_desc() {
        return user_desc;
    }

    public void setUser_desc(String user_desc) {
        this.user_desc = user_desc;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }
}
