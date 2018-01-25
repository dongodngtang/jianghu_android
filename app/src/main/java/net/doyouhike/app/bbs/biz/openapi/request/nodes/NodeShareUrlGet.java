package net.doyouhike.app.bbs.biz.openapi.request.nodes;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeShareUrlResp;

/**
 * 作者：luochangdong on 16/10/9
 * 描述：
 */
public class NodeShareUrlGet extends BaseGetRequest {
    private String node_id;

    @Override
    protected void setMapValue() {

    }

    public NodeShareUrlGet(String node_id) {
        this.node_id = node_id;
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<NodeShareUrlResp>(NodeShareUrlResp.class);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.NODES + node_id + "/share_url";
    }
}
