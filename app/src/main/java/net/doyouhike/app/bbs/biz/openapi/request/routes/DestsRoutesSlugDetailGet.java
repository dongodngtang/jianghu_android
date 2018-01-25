package net.doyouhike.app.bbs.biz.openapi.request.routes;

import net.doyouhike.app.bbs.biz.entity.road.RoadDetailInfo;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/9/27
 * 描述：
 */
public class DestsRoutesSlugDetailGet extends BaseGetRequest {

    private String route_slug;

    @Override
    protected void setMapValue() {

    }

    public DestsRoutesSlugDetailGet(String route_slug) {
        this.route_slug = route_slug;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.DESTS_ROUTES + "/" + route_slug;
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<RoadDetailInfo>(RoadDetailInfo.class);
    }
}
