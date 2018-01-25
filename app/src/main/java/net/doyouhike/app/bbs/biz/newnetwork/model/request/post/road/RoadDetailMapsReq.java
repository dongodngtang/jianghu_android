package net.doyouhike.app.bbs.biz.newnetwork.model.request.post.road;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.entity.road.RoadDetailMaps;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

/**
 * 线路详情 地图
 * Created by terry on 5/8/16.
 */
@RequestUrlAnnotation(Content.REQ_POST_ROAD_DETAIL_MAP)
public class RoadDetailMapsReq extends BaseGetRequest {


    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<RoadDetailMaps>(RoadDetailMaps.class);
    }

    @Override
    protected void setMapValue() {
        putValue("route_slug",route_slug);
    }

    @Expose
    private String  route_slug;

    public String getRoute_slug() {
        return route_slug;
    }

    public void setRoute_slug(String route_slug) {
        this.route_slug = route_slug;
    }
}
