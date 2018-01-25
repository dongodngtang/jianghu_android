package net.doyouhike.app.bbs.biz.newnetwork.model.request.post.road;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;

/**
 * 线路详情
 * Created by terry on 5/8/16.
 */
public class RoadDetailReq extends BasePostRequest {

    @Expose
    private String route_slug;


    public String getRoute_slug() {
        return route_slug;
    }

    public void setRoute_slug(String route_slug) {
        this.route_slug = route_slug;
    }

}
