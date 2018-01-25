package net.doyouhike.app.bbs.biz.newnetwork.model.bean;

/**
 * 基本的环信用户信息
 * Created by zengjiang on 16/8/5.
 */

public class HxUserInfo {
    private String user_uuid;
    private String im_id;
    private String internal_id;
    private boolean im_enabled;
    private String nickName;

    public HxUserInfo() {
    }

    public HxUserInfo(String userId, String user_uuid, String nickName) {
        this.user_uuid = user_uuid;
    }

    public HxUserInfo(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getIm_id() {
        return im_id;
    }

    public void setIm_id(String im_id) {
        this.im_id = im_id;
    }

    public boolean isIm_enabled() {
        return im_enabled;
    }

    public void setIm_enabled(boolean im_enabled) {
        this.im_enabled = im_enabled;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HxUserInfo that = (HxUserInfo) o;

        return user_uuid != null ? user_uuid.equals(that.user_uuid) : that.user_uuid == null;

    }

    @Override
    public int hashCode() {
        return user_uuid != null ? user_uuid.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "HxUserInfo{" +
                "user_uuid='" + user_uuid + '\'' +
                ", im_id='" + im_id + '\'' +
                ", im_enabled=" + im_enabled +
                '}';
    }

}
