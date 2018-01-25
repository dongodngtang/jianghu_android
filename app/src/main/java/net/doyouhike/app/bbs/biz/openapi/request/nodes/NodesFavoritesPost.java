package net.doyouhike.app.bbs.biz.openapi.request.nodes;

import com.yolanda.nohttp.rest.CacheMode;

import net.doyouhike.app.bbs.biz.event.open.NodesFavoriteEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.ResponseEventAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/9/26
 * 描述：
 */
@ResponseEventAnnotation(NodesFavoriteEvent.class)
public class NodesFavoritesPost extends BasePostRequest {
    private String node_id;

    public NodesFavoritesPost(String node_id) {
        this.node_id = node_id;
        setExtraInfo(this);
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.NODES + node_id + "/favorites";
    }
}
