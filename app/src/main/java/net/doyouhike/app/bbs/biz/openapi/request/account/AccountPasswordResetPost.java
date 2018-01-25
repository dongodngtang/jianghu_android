package net.doyouhike.app.bbs.biz.openapi.request.account;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.event.login.AccountPasswordResetEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.newnetwork.net.ResponseEventAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;

/**
 * 作者：luochangdong on 16/9/8
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.ACCOUNT_PASSWORD_RESET)
@ResponseEventAnnotation(AccountPasswordResetEvent.class)
public class  AccountPasswordResetPost extends BasePostRequest{

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<LoginUser>(LoginUser.class);
    }

    /**
     * email/mobile  重置密码方式
     */
    @Expose
    private String type;
    /**
     *  liumoumou@163.com/13478392098  邮箱或手机号
     */
    @Expose
    private String uid;
    /**
     * 新的用户密码， 为MD5码
     */
    @Expose
    private String password;
    /**
     * 验证码(如果是通过验证码的方式则必填)
     */
    @Expose
    private String code;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
