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

import java.util.List;

/**
 * 活动详情
 */
public class ActionDetailInfo {

	private String minilog_type;
	private String node_id;
	private String time;
	private ActionEvent event;
	private UserInfo user_info;
	private List<UserInfo> members;

    private String like_num;
	private String hit_num;
	private String post_num;
	private String favorite_num;
	private String is_follow;
	private String is_like;
	private String is_favorite;
	private List<Tags> tags;
	private List<CommentLastListData> comment_last_list;

	private List<LikeMembersData> like_members;


	public String getMinilog_type() {
		return minilog_type;
	}

	public void setMinilog_type(String minilog_type) {
		this.minilog_type = minilog_type;
	}

	public String getNode_id() {
		return node_id;
	}

	public void setNode_id(String node_id) {
		this.node_id = node_id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public ActionEvent getEvent() {
		return event;
	}

	public void setEvent(ActionEvent event) {
		this.event = event;
	}

	public UserInfo getUser_info() {
		return user_info;
	}

	public void setUser_info(UserInfo user_info) {
		this.user_info = user_info;
	}

	public List<UserInfo> getMembers() {
		return members;
	}

	public void setMembers(List<UserInfo> members) {
		this.members = members;
	}

	public String getLike_num() {
		return like_num;
	}

	public void setLike_num(String like_num) {
		this.like_num = like_num;
	}

	public String getHit_num() {
		return hit_num;
	}

	public void setHit_num(String hit_num) {
		this.hit_num = hit_num;
	}

	public String getPost_num() {
		return post_num;
	}

	public void setPost_num(String post_num) {
		this.post_num = post_num;
	}

	public String getFavorite_num() {
		return favorite_num;
	}

	public void setFavorite_num(String favorite_num) {
		this.favorite_num = favorite_num;
	}

	public String getIs_follow() {
		return is_follow;
	}

	public void setIs_follow(String is_follow) {
		this.is_follow = is_follow;
	}

	public String getIs_like() {
		return is_like;
	}

	public void setIs_like(String is_like) {
		this.is_like = is_like;
	}

	public String getIs_favorite() {
		return is_favorite;
	}

	public void setIs_favorite(String is_favorite) {
		this.is_favorite = is_favorite;
	}

	public List<Tags> getTags() {
		return tags;
	}

	public void setTags(List<Tags> tags) {
		this.tags = tags;
	}

	public List<CommentLastListData> getComment_last_list() {
		return comment_last_list;
	}

	public void setComment_last_list(List<CommentLastListData> comment_last_list) {
		this.comment_last_list = comment_last_list;
	}

	public List<LikeMembersData> getLike_members() {
		return like_members;
	}

	public void setLike_members(List<LikeMembersData> like_members) {
		this.like_members = like_members;
	}


	/**
	 * 位置
	 */
	public  class Location {

		private  String latitude;
		private String longitude;
		private String altiude;
		private String city_id;
		private String city;
		private String location;



		public String getLatitude() {
			return latitude;
		}

		public void setLatitude(String latitude) {
			this.latitude = latitude;
		}

		public String getLongitude() {
			return longitude;
		}

		public void setLongitude(String longitude) {
			this.longitude = longitude;
		}

		public String getAltiude() {
			return altiude;
		}

		public void setAltiude(String altiude) {
			this.altiude = altiude;
		}

		public String getCity_id() {
			return city_id;
		}

		public void setCity_id(String city_id) {
			this.city_id = city_id;
		}

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getLocation() {
			return location;
		}

		public void setLocation(String location) {
			this.location = location;
		}

	}

	/**
	 * 活动详情
	 */
	public class ActionEvent {
		private String title;
		private String begin_date;
		private String end_date;
		private String days;
		private String fee_type;
		private String from;
		private List<Destination> to;
		private String status;
		private String joined;
		private String limitation;
		private List<RichTextContent> content;
		private String me_role;
		private List<ActionMember> members;
		private String share_content;
		private String image;
		private String gather_date;

		public String getGather_date() {
			return gather_date;
		}

		public void setGather_date(String gather_date) {
			this.gather_date = gather_date;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}


		public String getDays() {
			return days;
		}

		public void setDays(String days) {
			this.days = days;
		}

		public String getFee_type() {
			return fee_type;
		}

		public void setFee_type(String fee_type) {
			this.fee_type = fee_type;
		}

		public String getFrom() {
			return from;
		}

		public void setFrom(String from) {
			this.from = from;
		}


		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getJoined() {
			return joined;
		}

		public void setJoined(String joined) {
			this.joined = joined;
		}

		public String getLimitation() {
			return limitation;
		}

		public void setLimitation(String limitation) {
			this.limitation = limitation;
		}


		public String getMe_role() {
			return me_role;
		}

		public void setMe_role(String me_role) {
			this.me_role = me_role;
		}


		public String getShare_content() {
			return share_content;
		}

		public void setShare_content(String share_content) {
			this.share_content = share_content;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}


		public String getBegin_date() {
			return begin_date;
		}

		public void setBegin_date(String begin_date) {
			this.begin_date = begin_date;
		}

		public String getEnd_date() {
			return end_date;
		}

		public void setEnd_date(String end_date) {
			this.end_date = end_date;
		}

		public List<Destination> getTo() {
			return to;
		}

		public void setTo(List<Destination> to) {
			this.to = to;
		}



		public List<ActionMember> getMembers() {
			return members;
		}

		public void setMembers(List<ActionMember> members) {
			this.members = members;
		}


		public List<RichTextContent> getContent() {
			return content;
		}

		public void setContent(List<RichTextContent> content) {
			this.content = content;
		}
	}


	public static class ActionMember {
		private String avatar;
		private String user_id;
		private String nick_name;
		private String uuid;


		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
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

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			ActionMember that = (ActionMember) o;

			return user_id != null ? user_id.equals(that.user_id) : that.user_id == null;

		}

		@Override
		public int hashCode() {
			return user_id != null ? user_id.hashCode() : 0;
		}

	}

	public class UserInfo {
		private String user_name;
		private String nick_name;
		private String user_id;
		private String avatar;
		//1 unknown 2男 3女
		private String gender;
		private String user_desc;
		private String is_follow;
		private String event_num;
		private String uuid;


		public String getNick_name() {
			return nick_name;
		}

		public void setNick_name(String nick_name) {
			this.nick_name = nick_name;
		}

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getAvatar() {
			return avatar;
		}

		public void setAvatar(String avatar) {
			this.avatar = avatar;
		}

		public String getIs_follow() {
			return is_follow;
		}

		public void setIs_follow(String is_follow) {
			this.is_follow = is_follow;
		}

		public String getUser_name() {
			return user_name;
		}

		public void setUser_name(String user_name) {
			this.user_name = user_name;
		}

		public String getGender() {
			return gender;
		}

		public void setGender(String gender) {
			this.gender = gender;
		}

		public String getUser_desc() {
			return user_desc;
		}

		public void setUser_desc(String user_desc) {
			this.user_desc = user_desc;
		}

		public String getEvent_num() {
			return event_num;
		}

		public void setEvent_num(String event_num) {
			this.event_num = event_num;
		}

		public String getUuid() {
			return uuid;
		}

		public void setUuid(String uuid) {
			this.uuid = uuid;
		}
	}


	public class Tags {
		private String tag_id;
		private String desc;



		public String getDesc() {
			return desc;
		}

		public void setDesc(String desc) {
			this.desc = desc;
		}

		public String getTag_id() {
			return tag_id;
		}

		public void setTag_id(String tag_id) {
			this.tag_id = tag_id;
		}
	}

	public class Destination {

		/**
		 * dest_id : 2008
		 * dest_name : 深圳
		 * dest_cat : city
		 */

		private String dest_id;
		private String dest_name;
		private String dest_cat;

		private String node_slug;



		public String getNode_slug() {
			return node_slug;
		}

		public void setNode_slug(String node_slug) {
			this.node_slug = node_slug;
		}





		public String getDest_cat() {
			return dest_cat;
		}

		public void setDest_cat(String dest_cat) {
			this.dest_cat = dest_cat;
		}

		public String getDest_id() {
			return dest_id;
		}

		public void setDest_id(String dest_id) {
			this.dest_id = dest_id;
		}

		public String getDest_name() {
			return dest_name;
		}

		public void setDest_name(String dest_name) {
			this.dest_name = dest_name;
		}


	}
}