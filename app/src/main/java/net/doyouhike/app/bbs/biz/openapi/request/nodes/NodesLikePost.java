package net.doyouhike.app.bbs.biz.openapi.request.nodes;

import com.yolanda.nohttp.RequestMethod;

import net.doyouhike.app.bbs.biz.event.open.NodesLikeEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.ResponseEventAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/9/14
 * 描述：
 */
@ResponseEventAnnotation(NodesLikeEvent.class)
public class NodesLikePost extends BasePostRequest {
    private String node_id;

    public NodesLikePost(String node_id) {
        this.node_id = node_id;
        setExtraInfo(new LikeMode(node_id, true));
    }

    @Override
    public void setRequestMethod(RequestMethod requestMethod) {
        super.setRequestMethod(requestMethod);
        if (getRequestMethod() == RequestMethod.DELETE){
            setExtraInfo(new LikeMode(node_id, false));
        }
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.NODES + node_id + "/likes";
    }

    public class LikeMode {
        String node_id;
        boolean isLike;

        public LikeMode(String node_id, boolean isLike) {
            this.node_id = node_id;
            this.isLike = isLike;
        }

        public String getNode_id() {
            return node_id;
        }

        public void setNode_id(String node_id) {
            this.node_id = node_id;
        }

        public boolean isLike() {
            return isLike;
        }

        public void setLike(boolean like) {
            isLike = like;
        }
    }
}
