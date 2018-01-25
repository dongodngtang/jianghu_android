package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.DestRouteListResp;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;

/**
 * 通过关键字获取目的地线路列表
 * Created by zengjiang on 16/5/31.
 */
@RequestUrlAnnotation(Content.REQ_GET_DEST_ROUTE_LIST)
public class DestRouteListReq extends BaseListGet {

    @Override
    protected void setMapValue() {
        super.setMapValue();
        putValue("node_slug",node_slug);
    }


    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<DestRouteListResp>(DestRouteListResp.class);
    }

    private String node_slug;



    public String getNode_slug() {
        return node_slug;
    }

    public void setNode_slug(String node_slug) {
        this.node_slug = node_slug;
    }
}
