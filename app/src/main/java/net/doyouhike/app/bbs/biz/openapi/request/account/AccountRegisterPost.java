package net.doyouhike.app.bbs.biz.openapi.request.account;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;

/**
 * 作者：luochangdong on 16/9/22
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.ACCOUNT_REGISTER)
public class AccountRegisterPost extends BasePostRequest {

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<LoginUser>(LoginUser.class);
    }

    /**
     * register_type : mobile或email   注册类型
     * vcode : 564481 验证码
     * nick_name : 小好时光 昵称
     * mobile : 13428725255
     * password : cc03e747a6afbbcbf8be7668acfebee5
     */



    @Expose
    private String register_type;
    @Expose
    private String vcode;
    @Expose
    private String nick_name;
    @Expose
    private String mobile;
    @Expose
    private String password;

    public String getRegister_type() {
        return register_type;
    }

    public void setRegister_type(String register_type) {
        this.register_type = register_type;
    }

    public String getVcode() {
        return vcode;
    }

    public void setVcode(String vcode) {
        this.vcode = vcode;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
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
}
