/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: CurrentUserDetails.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-10-24
 *
 * Changes (from 2015-10-24)
 * -----------------------------------------------------------------
 * 2015-10-24 创建CurrentUserDetails.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity;

/**
 * 当前用户详细信息
 */
public class CurrentUserDetails {


	/**
	 * user_id : 4417b1d0efa10f1372c456d009a42df6
	 * nick_name : cofsd
	 * sex : female
	 * user_desc : CD fun GC
	 * signature :
	 * avatar : files/faces/b/b/bbb8e9633.jpg
	 * authed : true
	 * mobile : 13424311559
	 * email : ellen_xia@163.net
	 * email_alt : null
	 * home_page :
	 * qq : 15251303
	 * msn :
	 * city_id : 110000
	 * city_name : 深圳
	 * city_slug : shenzhen
	 * hide_mail : true
	 * karma : 30
	 * karma_max : 30
	 * blog_name :
	 * weibo :
	 * font_size : 14px
	 * admin : false
	 * reg_date : 2001-09-26
	 * user_level : 21
	 * super_mod : false
	 * photo_domain_path : http://192.168.1.231:8002/
	 * user_counter : {"comment_msg_num":0,"like_msg_num":0,"event_msg_num":0,"fan_msg_num":1,"minilog_num":54,"favorite_num":11,"topic_num":236,"event_num":118,"live_num":184,"event_join_num":275,"event_coor_num":24,"follow_num":28,"fan_num":15}
	 * user_extra : {"real_name":"jjjjj","cred_num":"441522199506185970","id_type":"C","birthday":"1995-06-18T00:00:00.000+08:00","mobile":"13424311559","address":"福田","temp_address":"","med":false,"med_detail":"","intro":"","insurance":"","insurance_name":"hhhfjfj","insurance_number":"","contact_method":null,"contact_name1":"","contact_tel1":"","contact_name2":"","contact_tel2":""}
	 */

	private String user_id;
	private String nick_name;
	private String sex;
	private String user_desc;
	private String signature;
	private String avatar;
	private boolean authed;
	private String mobile;
	private String email;
	private Object email_alt;
	private String home_page;
	private String qq;
	private String msn;
	private int city_id;
	private String city_name;
	private String city_slug;
	private boolean hide_mail;
	private int karma;
	private int karma_max;
	private String blog_name;
	private String weibo;
	private String font_size;
	private boolean admin;
	private String reg_date;
	private int user_level;
	private boolean super_mod;
	private String photo_domain_path;
	/**
	 * comment_msg_num : 0
	 * like_msg_num : 0
	 * event_msg_num : 0
	 * fan_msg_num : 1
	 * minilog_num : 54
	 * favorite_num : 11
	 * topic_num : 236
	 * event_num : 118
	 * live_num : 184
	 * event_join_num : 275
	 * event_coor_num : 24
	 * follow_num : 28
	 * fan_num : 15
	 */

	private UserCounterBean user_counter;
	/**
	 * real_name : jjjjj
	 * cred_num : 441522199506185970
	 * id_type : C
	 * birthday : 1995-06-18T00:00:00.000+08:00
	 * mobile : 13424311559
	 * address : 福田
	 * temp_address :
	 * med : false
	 * med_detail :
	 * intro :
	 * insurance :
	 * insurance_name : hhhfjfj
	 * insurance_number :
	 * contact_method : null
	 * contact_name1 :
	 * contact_tel1 :
	 * contact_name2 :
	 * contact_tel2 :
	 */

	private UserExtraBean user_extra;

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

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUser_desc() {
		return user_desc;
	}

	public void setUser_desc(String user_desc) {
		this.user_desc = user_desc;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public boolean isAuthed() {
		return authed;
	}

	public void setAuthed(boolean authed) {
		this.authed = authed;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Object getEmail_alt() {
		return email_alt;
	}

	public void setEmail_alt(Object email_alt) {
		this.email_alt = email_alt;
	}

	public String getHome_page() {
		return home_page;
	}

	public void setHome_page(String home_page) {
		this.home_page = home_page;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getMsn() {
		return msn;
	}

	public void setMsn(String msn) {
		this.msn = msn;
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

	public String getCity_slug() {
		return city_slug;
	}

	public void setCity_slug(String city_slug) {
		this.city_slug = city_slug;
	}

	public boolean isHide_mail() {
		return hide_mail;
	}

	public void setHide_mail(boolean hide_mail) {
		this.hide_mail = hide_mail;
	}

	public int getKarma() {
		return karma;
	}

	public void setKarma(int karma) {
		this.karma = karma;
	}

	public int getKarma_max() {
		return karma_max;
	}

	public void setKarma_max(int karma_max) {
		this.karma_max = karma_max;
	}

	public String getBlog_name() {
		return blog_name;
	}

	public void setBlog_name(String blog_name) {
		this.blog_name = blog_name;
	}

	public String getWeibo() {
		return weibo;
	}

	public void setWeibo(String weibo) {
		this.weibo = weibo;
	}

	public String getFont_size() {
		return font_size;
	}

	public void setFont_size(String font_size) {
		this.font_size = font_size;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public String getReg_date() {
		return reg_date;
	}

	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}

	public int getUser_level() {
		return user_level;
	}

	public void setUser_level(int user_level) {
		this.user_level = user_level;
	}

	public boolean isSuper_mod() {
		return super_mod;
	}

	public void setSuper_mod(boolean super_mod) {
		this.super_mod = super_mod;
	}

	public String getPhoto_domain_path() {
		return photo_domain_path;
	}

	public void setPhoto_domain_path(String photo_domain_path) {
		this.photo_domain_path = photo_domain_path;
	}

	public UserCounterBean getUser_counter() {
		return user_counter;
	}

	public void setUser_counter(UserCounterBean user_counter) {
		this.user_counter = user_counter;
	}

	public UserExtraBean getUser_extra() {
		return user_extra;
	}

	public void setUser_extra(UserExtraBean user_extra) {
		this.user_extra = user_extra;
	}

	public static class UserCounterBean {
		private int comment_msg_num;
		private int like_msg_num;
		private int event_msg_num;
		private int fan_msg_num;
		private int minilog_num;
		private int favorite_num;
		private int topic_num;
		private int event_num;
		private int live_num;
		private int event_join_num;
		private int event_coor_num;
		private int follow_num;
		private int fan_num;

		public int getComment_msg_num() {
			return comment_msg_num;
		}

		public void setComment_msg_num(int comment_msg_num) {
			this.comment_msg_num = comment_msg_num;
		}

		public int getLike_msg_num() {
			return like_msg_num;
		}

		public void setLike_msg_num(int like_msg_num) {
			this.like_msg_num = like_msg_num;
		}

		public int getEvent_msg_num() {
			return event_msg_num;
		}

		public void setEvent_msg_num(int event_msg_num) {
			this.event_msg_num = event_msg_num;
		}

		public int getFan_msg_num() {
			return fan_msg_num;
		}

		public void setFan_msg_num(int fan_msg_num) {
			this.fan_msg_num = fan_msg_num;
		}

		public int getMinilog_num() {
			return minilog_num;
		}

		public void setMinilog_num(int minilog_num) {
			this.minilog_num = minilog_num;
		}

		public int getFavorite_num() {
			return favorite_num;
		}

		public void setFavorite_num(int favorite_num) {
			this.favorite_num = favorite_num;
		}

		public int getTopic_num() {
			return topic_num;
		}

		public void setTopic_num(int topic_num) {
			this.topic_num = topic_num;
		}

		public int getEvent_num() {
			return event_num;
		}

		public void setEvent_num(int event_num) {
			this.event_num = event_num;
		}

		public int getLive_num() {
			return live_num;
		}

		public void setLive_num(int live_num) {
			this.live_num = live_num;
		}

		public int getEvent_join_num() {
			return event_join_num;
		}

		public void setEvent_join_num(int event_join_num) {
			this.event_join_num = event_join_num;
		}

		public int getEvent_coor_num() {
			return event_coor_num;
		}

		public void setEvent_coor_num(int event_coor_num) {
			this.event_coor_num = event_coor_num;
		}

		public int getFollow_num() {
			return follow_num;
		}

		public void setFollow_num(int follow_num) {
			this.follow_num = follow_num;
		}

		public int getFan_num() {
			return fan_num;
		}

		public void setFan_num(int fan_num) {
			this.fan_num = fan_num;
		}
	}

	public static class UserExtraBean {
		private String real_name;
		private String cred_num;
		private String id_type;
		private String birthday;
		private String mobile;
		private String address;
		private String temp_address;
		private boolean med;
		private String med_detail;
		private String intro;
		private String insurance;
		private String insurance_name;
		private String insurance_number;
		private Object contact_method;
		private String contact_name1;
		private String contact_tel1;
		private String contact_name2;
		private String contact_tel2;

		public String getReal_name() {
			return real_name;
		}

		public void setReal_name(String real_name) {
			this.real_name = real_name;
		}

		public String getCred_num() {
			return cred_num;
		}

		public void setCred_num(String cred_num) {
			this.cred_num = cred_num;
		}

		public String getId_type() {
			return id_type;
		}

		public void setId_type(String id_type) {
			this.id_type = id_type;
		}

		public String getBirthday() {
			return birthday;
		}

		public void setBirthday(String birthday) {
			this.birthday = birthday;
		}

		public String getMobile() {
			return mobile;
		}

		public void setMobile(String mobile) {
			this.mobile = mobile;
		}

		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

		public String getTemp_address() {
			return temp_address;
		}

		public void setTemp_address(String temp_address) {
			this.temp_address = temp_address;
		}

		public boolean isMed() {
			return med;
		}

		public void setMed(boolean med) {
			this.med = med;
		}

		public String getMed_detail() {
			return med_detail;
		}

		public void setMed_detail(String med_detail) {
			this.med_detail = med_detail;
		}

		public String getIntro() {
			return intro;
		}

		public void setIntro(String intro) {
			this.intro = intro;
		}

		public String getInsurance() {
			return insurance;
		}

		public void setInsurance(String insurance) {
			this.insurance = insurance;
		}

		public String getInsurance_name() {
			return insurance_name;
		}

		public void setInsurance_name(String insurance_name) {
			this.insurance_name = insurance_name;
		}

		public String getInsurance_number() {
			return insurance_number;
		}

		public void setInsurance_number(String insurance_number) {
			this.insurance_number = insurance_number;
		}

		public Object getContact_method() {
			return contact_method;
		}

		public void setContact_method(Object contact_method) {
			this.contact_method = contact_method;
		}

		public String getContact_name1() {
			return contact_name1;
		}

		public void setContact_name1(String contact_name1) {
			this.contact_name1 = contact_name1;
		}

		public String getContact_tel1() {
			return contact_tel1;
		}

		public void setContact_tel1(String contact_tel1) {
			this.contact_tel1 = contact_tel1;
		}

		public String getContact_name2() {
			return contact_name2;
		}

		public void setContact_name2(String contact_name2) {
			this.contact_name2 = contact_name2;
		}

		public String getContact_tel2() {
			return contact_tel2;
		}

		public void setContact_tel2(String contact_tel2) {
			this.contact_tel2 = contact_tel2;
		}
	}
}
