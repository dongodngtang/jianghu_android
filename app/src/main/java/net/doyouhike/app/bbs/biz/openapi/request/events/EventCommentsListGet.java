package net.doyouhike.app.bbs.biz.openapi.request.events;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventCommentListResp;

/**
 * 作者：luochangdong on 16/9/14
 * 描述：获取评论
 */
public class EventCommentsListGet extends BaseListGet {
    @Override
    protected void setMapValue() {

        map.put("page_index", page_index + "");
        map.put("page_size", page_size + "");
        map.put("order", order);
        if (last_id != null)
            map.put("last_id", last_id);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.EVENTS + "/" + node_id + "/comments";
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<EventCommentListResp>(EventCommentListResp.class);
    }

    public EventCommentsListGet() {
    }

    public EventCommentsListGet(String node_id) {
        this.node_id = node_id;
    }

    private String node_id;

    private int page_index = 1;
    private int page_size = 20;
    /**
     * desc/倒序   asc/正序
     */
    private String order = "desc";
    /**
     * 最后一条评论的id
     */
    private String last_id;


    public int getPage_index() {
        return page_index;
    }

    public void setPage_index(int page_index) {
        this.page_index = page_index;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getLast_id() {
        return last_id;
    }

    public void setLast_id(String last_id) {
        this.last_id = last_id;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }
}
