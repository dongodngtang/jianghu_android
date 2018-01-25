package net.doyouhike.app.bbs.biz.openapi.request.nodes;

import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;

import net.doyouhike.app.bbs.biz.event.open.DeleteNodeEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.ResponseEventAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/10/18
 * 描述：
 */
@ResponseEventAnnotation(DeleteNodeEvent.class)
public class NodeDelete extends BasePostRequest {
    private String node_id;

    public NodeDelete(String node_id) {
        this.node_id = node_id;
        setRequestMethod(RequestMethod.DELETE);
        setCacheMode(CacheMode.DEFAULT);
        setExtraInfo(node_id);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.NODES + node_id;
    }
}
