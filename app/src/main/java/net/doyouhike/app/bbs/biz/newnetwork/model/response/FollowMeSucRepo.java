package net.doyouhike.app.bbs.biz.newnetwork.model.response;


import net.doyouhike.app.bbs.biz.entity.FollowedUser;

import java.util.List;

/**
 * Created by zaitu on 15-12-1.
 */
public class FollowMeSucRepo {

    /**
     * UserID : 535503
     * UserName : ramerame
     * NickName : ramerame
     * avatar : http://static.test.doyouhike.net/files/faces/none_header.gif
     * Gender : 3
     * Signature :
     * UserDesc :
     * isFollowed : 0
     */

    private List<FollowedUser> users;

    public void setUsers(List<FollowedUser> users) {
        this.users = users;
    }

    public List<FollowedUser> getUsers() {
        return users;
    }

}
