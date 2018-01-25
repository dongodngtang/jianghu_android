package net.doyouhike.app.bbs.biz.openapi.request.events;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/9/20
 * 描述：
 */
public class EventQuitPost extends BasePostRequest {

    @Expose
    private String node_id;

    public EventQuitPost(String node_id) {
        this.node_id = node_id;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.EVENTS + "/" + node_id + "/quit";
    }
}
