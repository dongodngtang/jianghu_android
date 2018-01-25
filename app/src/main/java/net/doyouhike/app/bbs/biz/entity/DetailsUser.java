/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: DetailsUser.java
 * Author: 伍建鹏(wu-yoline)
 * Version: 1.0
 * Create: 2015-10-21
 *
 * Changes (from 2015-10-21)
 * -----------------------------------------------------------------
 * 2015-10-21 : 1、创建本类，添加avatar、user_name、user_id三个属性;(wu-yoline)
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity;

/** 贴子详情中的作者和评论者的实体类，主要是对应http接口返回的json而创建
 * @author wu-yoline
 *
 */
public class DetailsUser {
	private String avatar;
	private String user_name;
	private String user_id;
	private String nick_name;

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
	}
}
