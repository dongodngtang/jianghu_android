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
package net.doyouhike.app.bbs.biz.entity;

import net.doyouhike.app.bbs.biz.entity.action.CommentLastListData;
import net.doyouhike.app.bbs.biz.entity.action.LikeMembersData;

import java.util.List;

public class ActionDetailInfo {
	private String favoriteNum;
	private String hitNum;
	private int isFavorite;
	private int isFollow;
	private int isLike;
	private String likeNum;
	private int minilogType;
	private String nodeId;
	private String postNum;
	private String time;
	private EventData event;
	public String getFavoriteNum() {
		return favoriteNum;
	}
	public void setFavoriteNum(String favoriteNum) {
		this.favoriteNum = favoriteNum;
	}
	public String getHitNum() {
		return hitNum;
	}
	public void setHitNum(String hitNum) {
		this.hitNum = hitNum;
	}
	public int getIsFavorite() {
		return isFavorite;
	}
	public void setIsFavorite(int isFavorite) {
		this.isFavorite = isFavorite;
	}
	public int getIsFollow() {
		return isFollow;
	}
	public void setIsFollow(int isFollow) {
		this.isFollow = isFollow;
	}
	public int getIsLike() {
		return isLike;
	}
	public void setIsLike(int isLike) {
		this.isLike = isLike;
	}
	public String getLikeNum() {
		return likeNum;
	}
	public void setLikeNum(String likeNum) {
		this.likeNum = likeNum;
	}
	public int getMinilogType() {
		return minilogType;
	}
	public void setMinilogType(int minilogType) {
		this.minilogType = minilogType;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getPostNum() {
		return postNum;
	}
	public void setPostNum(String postNum) {
		this.postNum = postNum;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public EventData getEvent() {
		return event;
	}
	public void setEvent(EventData event) {
		this.event = event;
	}
	public ActionUserInfo getUserInfo() {
		return userInfo;
	}
	public void setUserInfo(ActionUserInfo userInfo) {
		this.userInfo = userInfo;
	}
	public List<LikeMembersData> getLikeMembers() {
		return likeMembers;
	}
	public void setLikeMembers(List<LikeMembersData> likeMembers) {
		this.likeMembers = likeMembers;
	}
	public List<CommentLastListData> getCommentLastList() {
		return commentLastList;
	}
	public void setCommentLastList(List<CommentLastListData> commentLastList) {
		this.commentLastList = commentLastList;
	}
	private ActionUserInfo userInfo;

	private List<LikeMembersData> likeMembers;
	private List<CommentLastListData> commentLastList;

}