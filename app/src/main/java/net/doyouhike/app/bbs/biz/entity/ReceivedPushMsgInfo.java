/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ReceivedPushMsgInfo.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-12-02
 *
 * Changes (from 2015-12-02)
 * -----------------------------------------------------------------
 * 2015-12-02 创建ReceivedPushMsgInfo.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity;

public class ReceivedPushMsgInfo {

	public static final int RECEIVED_YES = 1;
	public static final int RECEIVED_NO = 1;

	private int comment;
	private int like;
	private int event;

	public int getComment() {
		return comment;
	}

	public void setComment(int comment) {
		this.comment = comment;
	}

	public int getLike() {
		return like;
	}

	public void setLike(int like) {
		this.like = like;
	}

	public int getEvent() {
		return event;
	}

	public void setEvent(int event) {
		this.event = event;
	}

}
