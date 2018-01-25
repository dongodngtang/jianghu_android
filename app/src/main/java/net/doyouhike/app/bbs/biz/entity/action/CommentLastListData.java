/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ActionDetailInfo.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-11-8
 *
 */
package net.doyouhike.app.bbs.biz.entity.action;

import net.doyouhike.app.bbs.biz.entity.common.RichTextContent;
import net.doyouhike.app.bbs.util.StringUtil;

import java.util.List;

public class CommentLastListData {
	private String avatar;
	private String comment_id;
	private List<RichTextContent> content;
	private long created;
	private String user_id;
	private String user_name;
	private String nick_name;
	private String reply_to_user_name;//回复谁谁谁用户名
	private String reply_to_nick_name;//回复谁谁谁昵称
	private String reply_to;//回复谁谁谁用户id
	private String uuid;

	public String getNick_name() {
		return nick_name;
	}

	public void setNick_name(String nick_name) {
		this.nick_name = nick_name;
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

	public long getCreated() {
		return created;
	}

	public void setCreated(long created) {
		this.created = created;
	}

	public List<RichTextContent> getContent() {
		return content;
	}


	public String contentToString(){

		if (null==content){
			return "";
		}

		StringBuilder stringBuilder=new StringBuilder();

		for (RichTextContent richTextContent :content){
			if (richTextContent.isTextType()){
				stringBuilder.append(richTextContent.getContent()).append("\n");
			}
		}

		String strComment= StringUtil.removeEnd(stringBuilder.toString(),"\n");
		return strComment;
	}

	public void setContent(List<RichTextContent> content) {
		this.content = content;
	}

	public String getComment_id() {
		return comment_id;
	}

	public void setComment_id(String comment_id) {
		this.comment_id = comment_id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getReply_to_user_name() {
		return reply_to_user_name;
	}

	public void setReply_to_user_name(String reply_to_user_name) {
		this.reply_to_user_name = reply_to_user_name;
	}

	public String getReply_to_nick_name() {
		return reply_to_nick_name;
	}

	public void setReply_to_nick_name(String reply_to_nick_name) {
		this.reply_to_nick_name = reply_to_nick_name;
	}

	public String getReply_to() {
		return reply_to;
	}

	public void setReply_to(String reply_to) {
		this.reply_to = reply_to;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
