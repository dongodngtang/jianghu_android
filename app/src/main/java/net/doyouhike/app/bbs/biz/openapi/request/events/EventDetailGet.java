package net.doyouhike.app.bbs.biz.openapi.request.events;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventDetailResp;

/**
 * 作者：luochangdong on 16/9/12
 * 描述：
 */
public class EventDetailGet extends BaseGetRequest{

    private String node_id;
    private String get_event_type = "app";

    public EventDetailGet(String node_id) {
        this.node_id = node_id;
    }

    @Override
    protected void setMapValue() {
      map.put("get_event_type",get_event_type);
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<Response<EventDetailResp>>(EventDetailResp.class);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.EVENTS+"/"+node_id;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public String getGet_event_type() {
        return get_event_type;
    }

    public void setGet_event_type(String get_event_type) {
        this.get_event_type = get_event_type;
    }
}
