package net.doyouhike.app.bbs.biz.openapi.request.routes;

import net.doyouhike.app.bbs.biz.entity.dynamic.DesAndRoadInfo;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/9/27
 * 描述：
 */
public class DestsNodesDetailGet extends BaseGetRequest {
    private String node_slug;

    public DestsNodesDetailGet(String node_slug) {
        this.node_slug = node_slug;
    }

    @Override
    protected void setMapValue() {

    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.DESTS_NODES + node_slug;
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<DesAndRoadInfo>(DesAndRoadInfo.class);
    }
}
