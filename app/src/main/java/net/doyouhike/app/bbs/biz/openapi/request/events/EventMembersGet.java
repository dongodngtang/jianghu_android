package net.doyouhike.app.bbs.biz.openapi.request.events;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventMembersResp;

/**
 * 作者：luochangdong on 16/9/13
 * 描述：
 */
public class EventMembersGet extends BaseListGet {

    private String node_id;
    private boolean manager;

    @Override
    protected void setMapValue() {

    }

    public EventMembersGet(String node_id, boolean manager) {
        this.node_id = node_id;
        this.manager = manager;
    }

    @Override
    public String getSubUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append(OpenApiUrl.EVENTS);
        sb.append("/");
        sb.append(node_id);
        if (manager) {
            sb.append("/members_profile");
        } else {
            sb.append("/members");
        }

        return sb.toString();
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<EventMembersResp>(EventMembersResp.class);
    }
}
