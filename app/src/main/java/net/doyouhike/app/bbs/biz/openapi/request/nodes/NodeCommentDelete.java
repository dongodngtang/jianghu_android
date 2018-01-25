package net.doyouhike.app.bbs.biz.openapi.request.nodes;

import com.yolanda.nohttp.RequestMethod;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/11/1
 * q q:2270333671
 * 描述：
 */
public class NodeCommentDelete extends BasePostRequest {
    private String node_id;
    private String comment_id;

    public NodeCommentDelete(String node_id, String comment_id) {
        super();
        setRequestMethod(RequestMethod.DELETE);
        this.node_id = node_id;
        this.comment_id = comment_id;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.NODES + node_id + "/comments/" + comment_id;
    }
}
