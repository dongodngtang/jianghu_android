package net.doyouhike.app.bbs.biz.db.green.entities;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：luochangdong on 16/9/13
 * 描述：用户粉丝
 */
@Entity
public class UserFans {


    /**
     * follow_each : true
     * user_id : 4417b1d0efa10f1372c456d009a42df6
     * nick_name : coffee
     * sex : female
     * avatar : files/faces/b/b/bbb8e9633.jpg
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
    @Generated(hash = 435433664)
    public UserFans(@NotNull String user_id, boolean follow_each, String nick_name,
            String sex, String avatar, String user_desc) {
        this.user_id = user_id;
        this.follow_each = follow_each;
        this.nick_name = nick_name;
        this.sex = sex;
        this.avatar = avatar;
        this.user_desc = user_desc;
    }
    @Generated(hash = 104059512)
    public UserFans() {
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



}
