/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: MsgMyActionInfo.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-11-03
 *
 * Changes (from 2015-11-04)
 * -----------------------------------------------------------------
 * 2015-11-04 创建MsgMyActionInfo.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.biz.entity;

public class MsgMyActionInfo {
	public static final String minilog_del="minilog_del";

	private String UserID; // "UserID":"1297852",
	private String UserName; // "UserName":"体验公司",
	private String NickName; // "NickName":"晏邪",
	private String Face; // "Face":"http://static.test.doyouhike.net/files/faces/e/0/e0dbb1339.jpg",
	private String NodeType;//minilog_del→活动删除
	private String NodeID; // "NodeID":"1470701",
	private String EventID; // "EventID":"954263",
	private long Created; // Created: "1446714388"
	private String EventTitle; // "EventTitle":"六月12日越南南北天双飞度假之旅",
	private String Memo; // "Memo":"",//留言
	private int Role; // "Role":"9",

	private int IsRead; // "IsRead":"0"
	private int IsOrganizer; // "IsRead":"0"
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

	public String getEventID() {
		return EventID;
	}

	public void setEventID(String eventID) {
		EventID = eventID;
	}

	public String getEventTitle() {
		return EventTitle;
	}

	public void setEventTitle(String eventTitle) {
		EventTitle = eventTitle;
	}

	public String getMemo() {
		return Memo;
	}

	public void setMemo(String memo) {
		Memo = memo;
	}

	public int getRole() {
		return Role;
	}

	public void setRole(int role) {
		Role = role;
	}

	public long getCreated() {
		return Created;
	}

	public void setCreated(long created) {
		Created = created;
	}

	public int getIsRead() {
		return IsRead;
	}

	public void setIsRead(int isRead) {
		IsRead = isRead;
	}

	public int getIsOrganizer() {
		return IsOrganizer;
	}

	public void setIsOrganizer(int isOrganizer) {
		IsOrganizer = isOrganizer;
	}

	public String getNodeType() {
		return NodeType;
	}

	public void setNodeType(String nodeType) {
		NodeType = nodeType;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
