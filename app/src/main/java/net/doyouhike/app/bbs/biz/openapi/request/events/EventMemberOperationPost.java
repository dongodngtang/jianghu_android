package net.doyouhike.app.bbs.biz.openapi.request.events;

import net.doyouhike.app.bbs.biz.event.action.ActionManageOperationEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.ResponseEventAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/9/19
 * 描述：
 */
@ResponseEventAnnotation(ActionManageOperationEvent.class)
public class EventMemberOperationPost extends BasePostRequest {

    private String node_id;
    private String user_id;
    private String operation;

    public EventMemberOperationPost(String node_id, String user_id, String operation) {
        this.node_id = node_id;
        this.user_id = user_id;
        this.operation = operation;
        setExtraInfo(this);
    }

    @Override
    public String getSubUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(OpenApiUrl.EVENTS)
                .append("/")
                .append(node_id)
                .append("/member/")
                .append(user_id)
                .append("/")
                .append(operation);
        return sb.toString();
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }
}
