package net.doyouhike.app.bbs.biz.openapi.request.users;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserNodeStateResp;

/**
 * 作者：luochangdong on 16/10/10
 * 描述：
 */
public class UsersNodesStateGet extends BaseGetRequest {
    @Override
    protected void setMapValue() {

    }

    private String user_id;
    private String nodes;

    public UsersNodesStateGet(String user_id, String nodes) {
        this.user_id = user_id;
        this.nodes = nodes;
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<UserNodeStateResp>(UserNodeStateResp.class);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS + user_id + "/nodes_status/" + nodes;
    }
}
