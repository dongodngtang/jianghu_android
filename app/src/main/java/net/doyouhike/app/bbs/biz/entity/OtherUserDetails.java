/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: OtherUserDetails.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-11-10
 *
 * Changes (from 2015-10-24)
 * -----------------------------------------------------------------
 * 2015-11-10 创建OtherUserDetails.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity;

/**
 * 其他用户详细信息
 */
public class OtherUserDetails {

	private String UserID; // UserID: "1287404"
	private String UserName; // UserName: "JTam"
	private String NickName; // "NickName":"晏邪",
	private String Gender; // Gender: "male"
	private String Signature; // Signature: "这是个人签名吖吖吖吖"
	private String UserDesc; // UserDesc: null "个人说明"
	private String Face; // Face:"http://xxx/xxx/7d9662225.jpg"
	private String Mobile; // Mobile: "13800138000"
	private String CityName; // CityName: "深圳"

	private int FollowCount; // FollowCount 关注数
	private int FollowMeCount; // FollowMeCount 粉丝数
	private int NodeCount; // NodeCount 帖子数
	private int ActivityCount; // ActivityCount 活动数
	private int BookMarkCount; // BookMarkCount 收藏数

	private String Authed; // Authed: "1" (不知道是什么)
	private String RegDate; // RegDate: "2015-08-24" (不知道是什么)
	private String EventDate; // EventDate: null (不知道是什么)
	private String PostsNum; // PostsNum: "0" (不知道是什么)
	// private List<String> Intro; // TODO Intro: null (不知道是什么: 个人详细，目前用不到)

	private String group_count; // group_count: "0" (不知道是什么)

	private int is_follow;

	public String getUserID() {
		return UserID;
	}

	public void setUserID(String userID) {
		UserID = userID;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getSignature() {
		return Signature;
	}

	public void setSignature(String signature) {
		Signature = signature;
	}

	public String getFace() {
		return Face;
	}

	public void setFace(String face) {
		Face = face;
	}

	public String getMobile() {
		return Mobile;
	}

	public void setMobile(String mobile) {
		Mobile = mobile;
	}

	public String getCityName() {
		return CityName;
	}

	public void setCityName(String cityName) {
		CityName = cityName;
	}

	public int getFollowCount() {
		return FollowCount;
	}

	public void setFollowCount(int followCount) {
		FollowCount = followCount;
	}

	public int getFollowMeCount() {
		return FollowMeCount;
	}

	public void setFollowMeCount(int followMeCount) {
		FollowMeCount = followMeCount;
	}

	public int getNodeCount() {
		return NodeCount;
	}

	public void setNodeCount(int nodeCount) {
		NodeCount = nodeCount;
	}

	public int getActivityCount() {
		return ActivityCount;
	}

	public void setActivityCount(int activityCount) {
		ActivityCount = activityCount;
	}

	public int getBookMarkCount() {
		return BookMarkCount;
	}

	public void setBookMarkCount(int bookMarkCount) {
		BookMarkCount = bookMarkCount;
	}

	public String getAuthed() {
		return Authed;
	}

	public void setAuthed(String authed) {
		Authed = authed;
	}

	public String getRegDate() {
		return RegDate;
	}

	public void setRegDate(String regDate) {
		RegDate = regDate;
	}

	public String getEventDate() {
		return EventDate;
	}

	public void setEventDate(String eventDate) {
		EventDate = eventDate;
	}

	public String getPostsNum() {
		return PostsNum;
	}

	public void setPostsNum(String postsNum) {
		PostsNum = postsNum;
	}

	// public List<String> getIntro() {
	// return Intro;
	// }
	//
	// public void setIntro(List<String> intro) {
	// Intro = intro;
	// }

	public String getUserDesc() {
		return UserDesc;
	}

	public void setUserDesc(String userDesc) {
		UserDesc = userDesc;
	}

	public String getGroup_count() {
		return group_count;
	}

	public void setGroup_count(String group_count) {
		this.group_count = group_count;
	}

	public int getIs_follow() {
		return is_follow;
	}

	public void setIs_follow(int is_follow) {
		this.is_follow = is_follow;
	}

}
