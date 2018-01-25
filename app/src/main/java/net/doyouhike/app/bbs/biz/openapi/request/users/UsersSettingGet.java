package net.doyouhike.app.bbs.biz.openapi.request.users;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserSettingResp;

/**
 * 作者：luochangdong on 16/10/9
 * 描述：
 */
public class UsersSettingGet extends BaseGetRequest {
    @Override
    protected void setMapValue() {

    }

    private String user_id;

    public UsersSettingGet(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<UserSettingResp>(UserSettingResp.class);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS + user_id + "/user_setting";
    }
}
