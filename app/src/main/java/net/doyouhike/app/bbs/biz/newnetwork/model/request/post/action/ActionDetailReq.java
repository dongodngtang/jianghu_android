package net.doyouhike.app.bbs.biz.newnetwork.model.request.post.action;


import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.entity.action.ActionDetailInfo;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.request.get.BaseTokenGetParams;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

/**
 * 活动详情 request
 */
@RequestUrlAnnotation(Content.REQ_GET_EVENT_DETAIL)
public class ActionDetailReq extends BaseTokenGetParams {

    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<ActionDetailInfo>(ActionDetailInfo.class);
    }

    @Override
    protected void setMapValue() {
        super.setMapValue();
        putValue("nodeID",nodeID);
    }

    /**
     * token : xxxx
     * nodeID : xxxx
     * userID : xxxx
     */

    @Expose
    private String nodeID;


    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }


    public String getNodeID() {
        return nodeID;
    }


}
