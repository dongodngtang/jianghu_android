package net.doyouhike.app.bbs.biz.entity;

/**
 * “关注/粉丝”列表中的用户信息
 */
public class FollowedUser {

	private String UserID; // "UserID":"966208",
	private String UserName; // "UserName":"jeff2383"
	private String NickName; // "NickName":"晏邪",
	private String UserDesc; // "UserDesc":"-。-" // 个人说明
	private String Avatar; // "Avatar":null // 头像
	private String Signature; // "Signature":null // 签名
	private int isFollowed; // "isFollowed":"1"
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

	public String getUserDesc() {
		return UserDesc;
	}

	public void setUserDesc(String userDesc) {
		UserDesc = userDesc;
	}

	public String getAvatar() {
		return Avatar;
	}

	public void setAvatar(String avatar) {
		Avatar = avatar;
	}

	public String getSignature() {
		return Signature;
	}

	public void setSignature(String signature) {
		Signature = signature;
	}

	public int getIsFollowed() {
		return isFollowed;
	}

	public void setIsFollowed(int isFollowed) {
		this.isFollowed = isFollowed;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
}
