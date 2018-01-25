package net.doyouhike.app.bbs.biz.openapi.request.nodes;

import com.google.gson.annotations.Expose;
import com.yolanda.nohttp.rest.CacheMode;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines.MinilogPost;

/**
 * 作者：luochangdong on 16/10/18
 * 描述：
 */
public class NodeForwardPost extends BasePostRequest {
    private String node_id;
    /**
     * content : test_title
     * location : {"city_id":40010,"latitude":"10.00","longitude":"10.00","altitude":"10.00","location_name":"深圳西丽湖环湖跑","dest_id":"1212"}
     */

    @Expose
    private String content;
    /**
     * city_id : 40010
     * latitude : 10.00
     * longitude : 10.00
     * altitude : 10.00
     * location_name : 深圳西丽湖环湖跑
     * dest_id : 1212
     */
    @Expose
    private MinilogPost.LocationBean location;

    public NodeForwardPost(String node_id) {
        this.node_id = node_id;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.NODES + node_id + "/forwards";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MinilogPost.LocationBean getLocation() {
        return location;
    }

    public void setLocation(MinilogPost.LocationBean location) {
        this.location = location;
    }


}
