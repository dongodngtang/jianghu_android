package net.doyouhike.app.bbs.biz.openapi.request.events;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/10/18
 * 描述：
 */
public class EventCancelPost extends BasePostRequest {
    private String node_id;

    public EventCancelPost(String node_id) {
        this.node_id = node_id;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.EVENTS + "/" + node_id + "/cancel";
    }
}
