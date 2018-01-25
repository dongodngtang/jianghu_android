package net.doyouhike.app.bbs.biz.openapi.request;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.AppVersionResp;

/**
 * 作者：luochangdong on 16/10/28
 * q q:2270333671
 * 描述：
 */
public class AppVersionGet extends BaseGetRequest {
    @Override
    protected void setMapValue() {

    }

    public AppVersionGet() {
        setTime_out(1500);
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<AppVersionResp>(AppVersionResp.class);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.APP_VERSION + "/" + app_type + "/" + app_vcode;
    }

    private int app_type = 2;
    private String app_vcode;

    public int getApp_type() {
        return app_type;
    }

    public void setApp_type(int app_type) {
        this.app_type = app_type;
    }

    public String getApp_vcode() {
        return app_vcode;
    }

    public void setApp_vcode(String app_vcode) {
        this.app_vcode = app_vcode;
    }
}
