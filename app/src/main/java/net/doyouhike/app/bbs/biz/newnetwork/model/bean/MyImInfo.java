package net.doyouhike.app.bbs.biz.newnetwork.model.bean;

/**
 * 自己im信息
 * Created by zengjiang on 16/8/8.
 */

public class MyImInfo {
    private String im_id;
    private String im_password;
    private boolean im_enabled;

    public String getIm_id() {
        return im_id;
    }

    public void setIm_id(String im_id) {
        this.im_id = im_id;
    }

    public String getIm_password() {
        return im_password;
    }

    public void setIm_password(String im_password) {
        this.im_password = im_password;
    }

    public boolean isIm_enabled() {
        return im_enabled;
    }

    public void setIm_enabled(boolean im_enabled) {
        this.im_enabled = im_enabled;
    }
}
