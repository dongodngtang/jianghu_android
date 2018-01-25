package net.doyouhike.app.bbs.biz.openapi.request.account;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/9/26
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.ACCOUNT_MOBILE_AUTH_VCODES)
public class AccountMobileAuthVcodePost extends BasePostRequest{
    @Expose
    private String mobile;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
