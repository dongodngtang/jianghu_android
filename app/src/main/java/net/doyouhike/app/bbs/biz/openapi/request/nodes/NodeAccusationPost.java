package net.doyouhike.app.bbs.biz.openapi.request.nodes;

import com.google.gson.annotations.Expose;
import com.yolanda.nohttp.rest.CacheMode;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/10/18
 * 描述：
 */
public class NodeAccusationPost extends BasePostRequest {
    private String node_id;
    @Expose
    private int accusation_type;

    @Override
    public String getSubUrl() {
        return OpenApiUrl.NODES + node_id + "/accusation";
    }

    public NodeAccusationPost(String node_id) {
        this.node_id = node_id;
    }

    public int getAccusation_type() {
        return accusation_type;
    }

    public void setAccusation_type(int accusation_type) {
        this.accusation_type = accusation_type;
    }
}
