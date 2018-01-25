package net.doyouhike.app.bbs.biz.newnetwork.model.request.post.dynamic;


import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.entity.dynamic.DesAndRoadInfo;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

/**
 * 线路目的地
 * zhulin 2016.5.31
 *
 */
@RequestUrlAnnotation(Content.REQ_POST_DES_AND_ROAD)
public class DesAndRoadListReq extends BasePostRequest {


    public String getNode_slug() {
        return node_slug;
    }

    public void setNode_slug(String node_slug) {
        this.node_slug = node_slug;
    }

    @Expose
    private String node_slug;

    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<DesAndRoadInfo>(DesAndRoadInfo.class);
    }
}
