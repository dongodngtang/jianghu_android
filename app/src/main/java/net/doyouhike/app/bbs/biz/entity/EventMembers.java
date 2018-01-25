/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: EventMembers.java
 * Author: ZouWenJie
 * Version: 1.0
 * Create: 2015-11-13
 *
 */
package net.doyouhike.app.bbs.biz.entity;

import java.util.List;

/**
 * @author ZouWenJie
 * 活动参与人员实体类
 */
public class EventMembers {
	private String confirmedCount;
	private String memberCount;
	private String unconfirmCount;
	private List<DetailMembers> members;
	public String getConfirmedCount() {
		return confirmedCount;
	}
	public void setConfirmedCount(String confirmedCount) {
		this.confirmedCount = confirmedCount;
	}
	public String getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(String memberCount) {
		this.memberCount = memberCount;
	}
	public String getUnconfirmCount() {
		return unconfirmCount;
	}
	public void setUnconfirmCount(String unconfirmCount) {
		this.unconfirmCount = unconfirmCount;
	}
	public List<DetailMembers> getMembers() {
		return members;
	}
	public void setMembers(List<DetailMembers> members) {
		this.members = members;
	}
	
}
