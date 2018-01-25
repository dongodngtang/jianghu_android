package net.doyouhike.app.bbs.biz.db.green.entities;

import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 聊天的用户信息
 * Created by zengjiang on 16/7/21.
 */
@Entity
public class ChatUserInfo  {

    @Id
    @NotNull
    @Unique
    private String user_id; // UserID:
    private String sex; // Gender: "male"
    private String avatar; // Face:"http://xxx/xxx/7d9662225.jpg"
    private String mobile; // Mobile: "13800138000"
    private String city_name; // CityName: "深圳"

    private String im_id;//用于获取环信ID
    private String internal_id;

    private String user_desc;
    private String nick_name;

    private boolean im_enabled = true;

    private long lastMsgTime=0;//最后发送悄悄话时间,时间间隔超过24小时,发送IM消息同事需重新发送悄悄话

    @Generated(hash = 826896406)
    public ChatUserInfo(@NotNull String user_id, String sex, String avatar,
            String mobile, String city_name, String im_id, String internal_id,
            String user_desc, String nick_name, boolean im_enabled, long lastMsgTime) {
        this.user_id = user_id;
        this.sex = sex;
        this.avatar = avatar;
        this.mobile = mobile;
        this.city_name = city_name;
        this.im_id = im_id;
        this.internal_id = internal_id;
        this.user_desc = user_desc;
        this.nick_name = nick_name;
        this.im_enabled = im_enabled;
        this.lastMsgTime = lastMsgTime;
    }

    @Generated(hash = 1735600647)
    public ChatUserInfo() {
    }

    public ChatUserInfo(CurrentUserDetails details) {
        if (null == details) {
            return;
        }
        setUser_id(details.getUser_id());
        setNick_name(details.getNick_name());
        setSex(details.getSex());
        setAvatar(details.getAvatar());
        setMobile(details.getMobile());
        setIm_id(details.getUser_id());
        setCity_name((details.getCity_name()));

    }

    public String getUser_id() {
        return this.user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getMobile() {
        return this.mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity_name() {
        return this.city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getIm_id() {
        return this.im_id;
    }

    public void setIm_id(String im_id) {
        this.im_id = im_id;
    }

    public String getUser_desc() {
        return this.user_desc;
    }

    public void setUser_desc(String user_desc) {
        this.user_desc = user_desc;
    }

    public String getNick_name() {
        return this.nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public boolean getIm_enabled() {
        return this.im_enabled;
    }

    public void setIm_enabled(boolean im_enabled) {
        this.im_enabled = im_enabled;
    }

    public long getLastMsgTime() {
        return this.lastMsgTime;
    }

    public void setLastMsgTime(long lastMsgTime) {
        this.lastMsgTime = lastMsgTime;
    }

    public String getInternal_id() {
        return this.internal_id;
    }

    public void setInternal_id(String internal_id) {
        this.internal_id = internal_id;
    }


}
