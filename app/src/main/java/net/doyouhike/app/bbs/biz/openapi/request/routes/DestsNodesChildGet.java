package net.doyouhike.app.bbs.biz.openapi.request.routes;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.openapi.response.routes.DestsRouteChildResp;

/**
 * 作者：luochangdong on 16/9/27
 * 描述：
 */
public class DestsNodesChildGet extends BaseListGet {
    private String node_slug;

    @Override
    protected void setMapValue() {
        super.setMapValue();
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<DestsRouteChildResp>(DestsRouteChildResp.class);
    }

    public DestsNodesChildGet(String node_slug) {
        this.node_slug = node_slug;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.DESTS_NODES + node_slug + "/child_dests";
    }
}
