package net.doyouhike.app.bbs.biz.newnetwork.model.response.chat;

/**
 * 作者：luochangdong on 16/8/16
 * 描述：
 */
public class GetUserInfoResp {

    /**
     * user_id : 10
     * uuid : 440f8685026ee1a22a2d56d0098f89f0
     * nick_name : Wowabc
     * avatar : http://c1.zdb.io/files/faces/8/f/8fe3e7d99.jpg
     */

    private String user_id;
    private String uuid;
    private String nick_name;
    private String avatar;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
