/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: MsgMyLikeInfo.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-11-03
 *
 * Changes (from 2015-11-04)
 * -----------------------------------------------------------------
 * 2015-11-04 创建MsgMyLikeInfo.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity;

public class MsgMyLikeInfo {

	public static final String NODE_TYPE_MINILOG = "minilog";
	public static final String NODE_TYPE_ACTION = "event";
	public static final String NODE_TYPE_URL_SHARE = "discussion";
	public static final String NODE_TYPE_MINILOG_DEL = "minilog_del";

	public static final String MINILOG_TYPE_TEXT = "txt";
	public static final String MINILOG_TYPE_FORWARD = "forward";
	public static final String MINILOG_TYPE_TEXT_IMAGE = "txt-photo";

	private String UserID; // "UserID":"1297852",
	private String UserName; // "UserName":"体验公司",
	private String NickName; // "NickName":"晏邪",
	private String Face; // "Face":"http://static.test.doyouhike.net/files/faces/e/0/e0dbb1339.jpg",

	private String NodeID; // "NodeID":"1470701",
	private String Title; // "Title":"1",
	private long Created; // "Created":"1446455228", 创建时间
	private String refContent; // "refContent":"" 被点赞的主帖内容
	private String firstPhoto; // "firstPhoto" 被点赞的主贴第一张图片

	private String NodeType; // "NodeType": minilog、minilog_del
	private String MinilogType; // "MinilogType"://1-text, 2-text_image,
								// 3-url_share, 4-activity, 5-trace, 6-forward

	private int IsRead; // "IsRead":"0"
	private String uuid;

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

	public String getFace() {
		return Face;
	}

	public void setFace(String face) {
		Face = face;
	}

	public String getNodeID() {
		return NodeID;
	}

	public void setNodeID(String nodeID) {
		NodeID = nodeID;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public long getCreated() {
		return Created;
	}

	public void setCreated(long created) {
		Created = created;
	}

	public String getRefContent() {
		return refContent;
	}

	public void setRefContent(String refContent) {
		this.refContent = refContent;
	}

	public String getFirstPhoto() {
		return firstPhoto;
	}

	public void setFirstPhoto(String firstPhoto) {
		this.firstPhoto = firstPhoto;
	}

	public String getNodeType() {
		return NodeType;
	}

	public void setNodeType(String nodeType) {
		NodeType = nodeType;
	}

	public String getMinilogType() {
		return MinilogType;
	}

	public void setMinilogType(String minilogType) {
		MinilogType = minilogType;
	}

	public int getIsRead() {
		return IsRead;
	}

	public void setIsRead(int isRead) {
		IsRead = isRead;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
