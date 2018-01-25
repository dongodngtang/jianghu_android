/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ActivityInfo.java
 * Author: wu-yoline(伍建鹏)
 * Version: 1.0
 * Create: 2015-10-1
 *
 * Changes (from 2015-10-2)
 * -----------------------------------------------------------------
 * 2015-10-1 : 创建 本类 (wu-yoline)，添加主界面要显示内容的对应属性并添加getRemainingDay()方法;
 * -----------------------------------------------------------------
 * 2015-10-2 : 修改setmJoinedCount和setmTotalCount函数，但传入参数为负数时设置对应属性为0
 * -----------------------------------------------------------------
 * 2015-10-11 : 添加属性url
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity;

public class ActiveInfo {

	/**
	 * 标题
	 */
	private String title;

	/**
	 * 图标
	 */
	private String image;

	/**
	 * 开始时间
	 */
	private long begin_date;

	/**
	 * 结束时间
	 */
	private long end_date;

	/**
	 * 开始城市
	 */
	private String from;

	/**
	 * 会到达的城市
	 */
	private String[] to;

	/**
	 * 活动天数
	 */
	private int days;

	/**
	 * 活动状态
	 */
	private int status;

	/**
	 * 参加活动的人数
	 */
	private int joined;

	/**
	 * 参加活动的人数上限
	 */
	private int limitation;

	/**
	 * 活动内容
	 */
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public long getBegin_date() {
		return begin_date;
	}

	public void setBegin_date(long begin_date) {
		this.begin_date = begin_date;
	}

	public long getEnd_date() {
		return end_date;
	}

	public void setEnd_date(long end_date) {
		this.end_date = end_date;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String[] getTo() {
		return to;
	}

	public void setTo(String[] to) {
		this.to = to;
	}

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getJoined() {
		return joined;
	}

	public void setJoined(int joined) {
		this.joined = joined;
	}

	public int getLimitation() {
		return limitation;
	}

	public void setLimitation(int limitation) {
		this.limitation = limitation;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isJoinFull() {
		return limitation == joined;
	}
}
