package net.doyouhike.app.bbs.biz.openapi.request.users;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.account.UsersFollowsResp;

/**
 * 作者：luochangdong on 16/9/12
 * 描述：
 */
public class UsersFollowsGet extends BaseListGet {

    private String user_id;
    @Override
    protected void setMapValue() {
         super.setMapValue();
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS +user_id+"/follows";
    }

    public UsersFollowsGet(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<Response<UsersFollowsResp>>(UsersFollowsResp.class);
    }
}
