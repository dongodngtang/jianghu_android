package net.doyouhike.app.bbs.biz.entity.listinfo;

import net.doyouhike.app.bbs.biz.entity.FollowedUser;

import java.util.ArrayList;
import java.util.List;

/**
 * “关注/粉丝”列表
 */
public class FollowedUserListInfo {

	private List<FollowedUser> users = new ArrayList<FollowedUser>();

	public List<FollowedUser> getUsers() {
		return users;
	}

	public void setUsers(List<FollowedUser> users) {
		this.users = users;
	}

}
