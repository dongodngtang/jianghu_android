package net.doyouhike.app.bbs.biz.openapi.request.account;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/10/19
 * 描述：
 */
public class AccountChangePwdPost extends BasePostRequest {
    private String user_id;

    @Expose
    private String new_pwd;
    @Expose
    private String old_pwd;

    @Override
    public String getSubUrl() {
        return OpenApiUrl.ACCOUNT_USERS + user_id + "/change_password";
    }

    public AccountChangePwdPost(String user_id) {
        this.user_id = user_id;
    }

    public String getNew_pwd() {
        return new_pwd;
    }

    public void setNew_pwd(String new_pwd) {
        this.new_pwd = new_pwd;
    }

    public String getOld_pwd() {
        return old_pwd;
    }

    public void setOld_pwd(String old_pwd) {
        this.old_pwd = old_pwd;
    }
}
