package net.doyouhike.app.bbs.biz.entity;

public class ToCommentInfo {
	private String liveId;
	private String content = "";
	private String commentId;
	private String reply_to;
	private String reply_to_user_name;
	private String reply_to_nick_name;
	private boolean isForwarding;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isForwarding() {
		return isForwarding;
	}

	public void setForwarding(boolean isForwarding) {
		this.isForwarding = isForwarding;
	}

	public String getLiveId() {
		return liveId;
	}

	public void setLiveId(String liveId) {
		this.liveId = liveId;
	}

	public String getCommentId() {
		return commentId;
	}

	public void setCommentId(String commentId) {
		this.commentId = commentId;
	}

	public String getReply_to() {
		return reply_to;
	}

	public void setReply_to(String reply_to) {
		this.reply_to = reply_to;
	}

	public String getReply_to_user_name() {
		return reply_to_user_name;
	}

	public void setReply_to_user_name(String reply_to_user_name) {
		this.reply_to_user_name = reply_to_user_name;
	}

	public String getReply_to_nick_name() {
		return reply_to_nick_name;
	}

	public void setReply_to_nick_name(String reply_to_nick_name) {
		this.reply_to_nick_name = reply_to_nick_name;
	}
}
