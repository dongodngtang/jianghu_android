package net.doyouhike.app.bbs.biz.newnetwork.model.response;

import java.util.List;

/**
 * Created by luochangdong on 15-11-30.
 */
public class MyFollowResp {

    /**
     * UserID : 966208
     * UserName : jeff2383
     * Avatar : null
     * Signature : null
     * isFollowed : 1
     */

    private List<UsersEntity> users;

    public void setUsers(List<UsersEntity> users) {
        this.users = users;
    }

    public List<UsersEntity> getUsers() {
        return users;
    }

    public static class UsersEntity {
        private String UserID;
        private String UserName;
        private Object Avatar;
        private Object Signature;
        private String isFollowed;

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public void setAvatar(Object Avatar) {
            this.Avatar = Avatar;
        }

        public void setSignature(Object Signature) {
            this.Signature = Signature;
        }

        public void setIsFollowed(String isFollowed) {
            this.isFollowed = isFollowed;
        }

        public String getUserID() {
            return UserID;
        }

        public String getUserName() {
            return UserName;
        }

        public Object getAvatar() {
            return Avatar;
        }

        public Object getSignature() {
            return Signature;
        }

        public String getIsFollowed() {
            return isFollowed;
        }
    }
}
