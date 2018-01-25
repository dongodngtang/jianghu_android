package net.doyouhike.app.bbs.biz.openapi.request.account;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.account.AccountInternalVcodeResp;

/**
 * 作者：luochangdong on 16/9/7
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.ACCOUNT_INTERNAL_VCODE)
public class AccountInternalVcodePost extends BasePostRequest {


    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<AccountInternalVcodeResp>(AccountInternalVcodeResp.class);
    }

    /**
     * mobile : 18873688664
     * vcode_type : login
     */

    @Expose
    private String mobile;
    @Expose
    private String vcode_type;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getVcode_type() {
        return vcode_type;
    }

    public void setVcode_type(String vcode_type) {
        this.vcode_type = vcode_type;
    }
}
