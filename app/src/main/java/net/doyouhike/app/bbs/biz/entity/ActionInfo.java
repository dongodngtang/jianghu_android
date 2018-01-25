/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: ActionInfo.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-10-15
 *
 */
package net.doyouhike.app.bbs.biz.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author ZouWenJie
 * 活动页ListView的item项的数据封装类
 */
public class ActionInfo {

	/**
	 * title : 2016年4月7日端午新西兰自驾游约伴
	 * begin_date : 1460067600
	 * end_date : 1461363600
	 * from : 重庆
	 * to : [{"dest_id":"20165","dest_name":"新西兰","dest_cat":null},{"dest_id":0,"dest_name":"基督城","dest_cat":null}]
	 * days : 15
	 * fee_type : null
	 * status : 1
	 * org_event_state : plan
	 * joined : 1
	 * limitation : 4
	 * user_info : {"user_name":"馒头稀饭","nick_name":"馒头稀饭","user_id":"545202","avatar":"http://dev.static.doyouhike.com/files/faces/6/6/669ca5e09.jpg","user_desc":""}
	 * image :
	 * node_id : 2626270
	 */

	private String title;
	private String begin_date;
	private String end_date;
	private String from;
	private String days;
	private Object fee_type;
	private int status;
	private String org_event_state;
	private String joined;
	private String limitation;
	/**
	 * user_name : 馒头稀饭
	 * nick_name : 馒头稀饭
	 * user_id : 545202
	 * avatar : http://dev.static.doyouhike.com/files/faces/6/6/669ca5e09.jpg
	 * user_desc :
	 */

	private UserInfoEntity user_info;
	private String image;
	private String node_id;
	/**
	 * dest_id : 20165
	 * dest_name : 新西兰
	 * dest_cat : null
	 */

	@SerializedName("otherTo")
	private List<ToEntity> to;

	public void setTitle(String title) {
		this.title = title;
	}

	public void setBegin_date(String begin_date) {
		this.begin_date = begin_date;
	}

	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public void setFee_type(Object fee_type) {
		this.fee_type = fee_type;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setOrg_event_state(String org_event_state) {
		this.org_event_state = org_event_state;
	}

	public void setJoined(String joined) {
		this.joined = joined;
	}

	public void setLimitation(String limitation) {
		this.limitation = limitation;
	}

	public void setUser_info(UserInfoEntity user_info) {
		this.user_info = user_info;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}

	public void setTo(List<ToEntity> to) {
		this.to = to;
	}

	public String getTitle() {
		return title;
	}

	public String getBegin_date() {
		return begin_date;
	}

	public String getEnd_date() {
		return end_date;
	}

	public String getFrom() {
		return from;
	}

	public String getDays() {
		return days;
	}

	public Object getFee_type() {
		return fee_type;
	}

	public int getStatus() {
		return status;
	}

	public String getOrg_event_state() {
		return org_event_state;
	}

	public String getJoined() {
		return joined;
	}

	public String getLimitation() {
		return limitation;
	}

	public UserInfoEntity getUser_info() {
		return user_info;
	}

	public String getImage() {
		return image;
	}

	public String getNode_id() {
		return node_id;
	}

	public List<ToEntity> getTo() {
		return to;
	}

	public static class UserInfoEntity {
		private String user_name;
		private String nick_name;
		private String user_id;
		private String avatar;
		private String user_desc;

		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}

		public void setNick_name(String nick_name) {
			this.nick_name = nick_name;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public void setUser_desc(String user_desc) {
			this.user_desc = user_desc;
		}

		public String getUser_name() {
			return user_name;
		}

		public String getNick_name() {
			return nick_name;
		}

		public String getUser_id() {
			return user_id;
		}

		public String getAvatar() {
			return avatar;
		}

		public String getUser_desc() {
			return user_desc;
		}
	}

	public static class ToEntity {
		private String dest_id;
		private String dest_name;
		private Object dest_cat;

		public void setDest_id(String dest_id) {
			this.dest_id = dest_id;
		}

		public void setDest_name(String dest_name) {
			this.dest_name = dest_name;
		}

		public void setDest_cat(Object dest_cat) {
			this.dest_cat = dest_cat;
		}

		public String getDest_id() {
			return dest_id;
		}

		public String getDest_name() {
			return dest_name;
		}

		public Object getDest_cat() {
			return dest_cat;
		}
	}
}
