package net.doyouhike.app.bbs.biz.openapi.request.nodes;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodesLikesCountResp;

/**
 * 作者：luochangdong on 16/9/13
 * 描述：
 */
public class NodesLikesCountGet extends BaseGetRequest {
    private String node_id;

    @Override
    protected void setMapValue() {

    }

    public NodesLikesCountGet(String node_id) {
        this.node_id = node_id;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.NODES + node_id + "/likes";
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<NodesLikesCountResp>(NodesLikesCountResp.class);
    }
}
