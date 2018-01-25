package net.doyouhike.app.bbs.biz.openapi.request.routes;

import net.doyouhike.app.bbs.biz.event.road.RoadListEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.newnetwork.net.ResponseEventAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.openapi.response.routes.DestsRoutesResp;
import net.doyouhike.app.bbs.util.StringUtil;

/**
 * 作者：luochangdong on 16/9/8
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.DESTS_ROUTES)
@ResponseEventAnnotation(RoadListEvent.class)
public class DestsRoutesGet extends BaseListGet {
    @Override
    protected void setMapValue() {
        super.setMapValue();
        if (StringUtil.isNotEmpty(city_slug))
            map.put("city_slug", city_slug);
        if (StringUtil.isNotEmpty(tag_id))
            map.put("tag_id", tag_id);
        if (StringUtil.isNotEmpty(route_type_id))
            map.put("route_type_id", route_type_id);
        if (StringUtil.isNotEmpty(keyword))
            map.put("keyword", keyword);
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<DestsRoutesResp>(DestsRoutesResp.class);
    }

    /**
     * 城市的slug
     */
    private String city_slug;
    /**
     * 标签id
     */
    private String tag_id;
    /**
     * 线路类型id
     */
    private String route_type_id;
    /**
     * 搜索的关键字
     */
    private String keyword;


    public String getCity_slug() {
        return city_slug;
    }

    public void setCity_slug(String city_slug) {
        this.city_slug = city_slug;
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getRoute_type_id() {
        return route_type_id;
    }

    public void setRoute_type_id(String route_type_id) {
        this.route_type_id = route_type_id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
