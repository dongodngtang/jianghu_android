/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ContactInfo.java
 * Author: Zhangkai
 * Version: 1.0
 * Create: 2015-11-03
 *
 * -----------------------------------------------------------------
 * 2015-11-04 1、重写equals()方法;(wu-yoline)
 * 			  2、继承Comparable接口，实现按姓名排序;(wu-yoline)
 * -----------------------------------------------------------------
 */

package net.doyouhike.app.bbs.biz.entity;

import net.doyouhike.app.bbs.biz.openapi.presenter.AttendState;

import java.text.Collator;
import java.text.RuleBasedCollator;
import java.util.Locale;

/**
 * 通讯录联系人
 * 
 */
public class PhoneContactInfo implements Comparable<PhoneContactInfo> {
	// 联系人ID
	public long contactId;

	// 磨房用户id
	public String mfUserId;
	// 磨房用户id
	public String uuid;

	// 联系人姓名
	public String name;

	// 联系人号码
	public String phoneNum;

	// 用户签名
	public String mfSignature;

	// 联系人头像ID
	public long photoId;

	// 磨坊用户名
	public String mfUserName;
	
	//磨房昵称
	public String mfNickName;

	// 是不是磨坊用户
	public boolean isMFUser = false;

	// 磨房用户头像
	public String mfAvatarUrl;
	
	// 磨房用户个人说明
	public String mfUserDesc;

	// 关注状态
	public int attendState = AttendState.ATTENTION_TO_INVITE;

	@Override
	public boolean equals(Object o) {
		if (this.phoneNum != null) {
			return this.phoneNum.equals(((PhoneContactInfo) o).phoneNum);
		} else if (o == null) {
			return true;
		}
		return false;
	}

	@Override
	public int compareTo(PhoneContactInfo another) {
		RuleBasedCollator collator = (RuleBasedCollator) Collator
				.getInstance(Locale.CHINA);
		if (another != null) {
			if (this.isMFUser && another.isMFUser) {
				return collator.compare(this.mfUserName, another.mfUserName);
			} else if (!this.isMFUser && !another.isMFUser) {
				return collator.compare(this.name, another.name);
			} else if (this.isMFUser != another.isMFUser) {
				if (this.isMFUser) {
					return 1;
				} else {
					return -1;
				}
			}
		}
		return 0;
	}
}
