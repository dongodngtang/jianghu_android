package net.doyouhike.app.bbs.biz.openapi.request.users;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.account.UsersFansResp;

/**
 * 作者：luochangdong on 16/9/13
 * 描述：
 */
public class UsersFansGet extends BaseListGet {



    @Override
    protected void setMapValue() {
        super.setMapValue();
    }

    private String user_id;

    public UsersFansGet(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS + user_id +"/fans";
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<UsersFansResp>(UsersFansResp.class);
    }
}
