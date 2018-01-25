package net.doyouhike.app.bbs.biz.openapi.request;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.event.login.LoginResponseEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.newnetwork.net.ResponseEventAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;

/**
 * 作者：luochangdong on 16/9/7
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.ACCOUNT_LOGIN)
@ResponseEventAnnotation(LoginResponseEvent.class)
public class LoginPost extends BasePostRequest {


    @Expose
    private String mobile;
    @Expose
    private String password;
    @Expose
    private String login_type;
    @Expose
    private String vcode;
    @Expose
    private String uname;
    @Expose
    private String token;
    @Expose
    private String user_id;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogin_type() {
        return login_type;
    }

    public void setLogin_type(String login_type) {
        this.login_type = login_type;
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<LoginUser>(LoginUser.class);
    }
}
