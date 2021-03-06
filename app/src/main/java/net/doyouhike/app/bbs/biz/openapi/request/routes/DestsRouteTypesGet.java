package net.doyouhike.app.bbs.biz.openapi.request.routes;

import com.google.gson.reflect.TypeToken;

import net.doyouhike.app.bbs.biz.entity.road.RoadListType;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

import java.util.List;

/**
 * 作者：luochangdong on 16/9/8
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.DESTS_ROUTE_TYPES)
public class DestsRouteTypesGet extends BaseGetRequest {
    @Override
    protected void setMapValue() {

    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<List<RoadListType>>(new TypeToken<List<RoadListType>>() {
        }.getType());
    }

}
