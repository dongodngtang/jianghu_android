package net.doyouhike.app.bbs.biz.newnetwork.model.response;

import java.util.List;

/**
 * Created by zaitu on 15-12-1.
 */
public class SearchUserSucRepo {

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

    private List<LoginSucRepo> users;

    public void setUsers(List<LoginSucRepo> users) {
        this.users = users;
    }

    public List<LoginSucRepo> getUsers() {
        return users;
    }

}
