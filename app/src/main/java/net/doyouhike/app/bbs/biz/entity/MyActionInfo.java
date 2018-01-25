/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: MyActionInfo.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-11-03
 *
 * Changes (from 2015-11-04)
 * -----------------------------------------------------------------
 * 2015-11-04 创建MyActionInfo.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity;

import java.util.List;

public class MyActionInfo {

	private String node_id;
	private String title;
	private String image;

	private long begin_date; // "1445817600"
	private long end_date; // 同上
	private int days; // 活动持续多久

	private String from;
	private List<String> to;

	private int status; // 1/2/3 招集中/已满员/已结束

	public String getNode_id() {
		return node_id;
	}

	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}

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

	public int getDays() {
		return days;
	}

	public void setDays(int days) {
		this.days = days;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public List<String> getTo() {
		return to;
	}

	public void setTo(List<String> to) {
		this.to = to;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
