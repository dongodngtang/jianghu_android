package net.doyouhike.app.bbs.biz.entity;

import java.io.Serializable;

/**
 * Created by zengjiang on 16/8/9.
 */

public class TimeLineUserInfoEntity implements Serializable{
    private String user_id;
    private String user_name;
    private String avatar;
    private String nick_name;
    private String uuid;

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public String getNick_name() {
        return nick_name;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


    @Override
    public String toString() {
        return "TimeLineUserInfoEntity{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", nick_name='" + nick_name + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
