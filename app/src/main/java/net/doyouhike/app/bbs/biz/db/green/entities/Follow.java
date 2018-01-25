package net.doyouhike.app.bbs.biz.db.green.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;

/**
 * 作者：luochangdong on 16/9/13
 * 描述：
 */
@Entity
public class Follow {

    /**
     * follow_each : true
     * user_id : cda884c5ce4d96933c0056d009f03b2e
     * nick_name : 江湖骗子啊;;kkj
     * sex : female
     * avatar : files/faces/3/9/39dfbfeb7.jpg
     */
    @Id
    @NotNull
    @Unique
    private String user_id;

    private boolean follow_each;
    private String nick_name;
    private String sex;
    private String avatar;
    private String user_desc;

    @Generated(hash = 1148484698)
    public Follow(@NotNull String user_id, boolean follow_each, String nick_name,
                  String sex, String avatar, String user_desc) {
        this.user_id = user_id;
        this.follow_each = follow_each;
        this.nick_name = nick_name;
        this.sex = sex;
        this.avatar = avatar;
        this.user_desc = user_desc;
    }

    @Generated(hash = 2125231264)
    public Follow() {
    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean getFollow_each() {
        return this.follow_each;
    }

    public void setFollow_each(boolean follow_each) {
        this.follow_each = follow_each;
    }

    public String getNick_name() {
        return this.nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getSex() {
        return this.sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAvatar() {
        return this.avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_desc() {
        return this.user_desc;
    }

    public void setUser_desc(String user_desc) {
        this.user_desc = user_desc;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null)
            return false;
        if (this == obj) {
            return true;
        }
        if (obj instanceof Follow) {
            Follow other = (Follow) obj;
            return (other.getUser_id()).equals(this.getUser_id());
        }
        return false;
    }
}
