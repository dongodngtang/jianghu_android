package net.doyouhike.app.bbs.biz.newnetwork.model.request.post.road;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.road.RoadListResp;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

/**
 * 查看线路列表
 * Created by terry on 5/8/16.
 */
@RequestUrlAnnotation(Content.REQ_POST_ROAD_LIST_EVENT)
public class RoadListReq extends BaseListGet {

    @Override
    protected void setMapValue() {
        super.setMapValue();

        putValue("keyword",keyword);
        putValue("city_slug",city_slug);
        putValue("route_type_id",route_type_id);
        putValue("tag_id",tag_id);

    }


    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<RoadListResp>(RoadListResp.class);
    }

    private String keyword;

    private String city_slug;

    private  String route_type_id;

    private String tag_id;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getRoute_type_id() {
        return route_type_id;
    }

    public void setRoute_type_id(String route_type_id) {
        this.route_type_id = route_type_id;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }


    public String getCity_slug() {
        return city_slug;
    }

    public void setCity_slug(String city_slug) {
        this.city_slug = city_slug;
    }


}
