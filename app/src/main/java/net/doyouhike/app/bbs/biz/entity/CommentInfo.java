/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: CommentInfo.java
 * Author: 伍建鹏(wu-yoline)
 * Version: 1.0
 * Create: 2015-10-21
 *
 * Changes (from 2015-10-21)
 * -----------------------------------------------------------------
 * 2015-10-21 : 创建本类，添加属性及get、set方法;(wu-yoline);
 * -----------------------------------------------------------------
 * 2015-10-22 : 根据http接口返回的数据，修改user属性为数组类型;(wu-yoline);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity;


/**
 * 评论信息的实体类
 * 
 * @author wu-yoline
 *
 */
public class CommentInfo {

	private String comment_id;
	private long created;
	private String content;
	private int city_id;
	private String city_name;
	private int is_like;
	private int like_num;
	private String avatar;
	private String user_name;
	private String user_id;
	private String nick_name;

	public String getComment_id() {
		return comment_id;
	}

	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getCity_id() {
		return city_id;
	}

	public void setCity_id(int city_id) {
		this.city_id = city_id;
	}

	public String getCity_name() {
		return city_name;
	}

	public void setCity_name(String city_name) {
		this.city_name = city_name;
	}

	public int getIs_like() {
		return is_like;
	}

	public void setIs_like(int is_like) {
		this.is_like = is_like;
	}

	public int getLike_num() {
		return like_num;
	}

	public void setLike_num(int like_num) {
		this.like_num = like_num;
	}

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
