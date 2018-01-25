package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.ShareUrl;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

/**
 * Created by zaitu on 15-11-28.
 * 获取分享地址
 */
@RequestUrlAnnotation(Content.REQ_REQ_SHARE_URL)
public class GetShareUrlReq extends BaseGetRequest {

    private String nodeID;

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    @Override
    protected void setMapValue() {
        map.put("nodeID",nodeID);
    }


    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<ShareUrl>(ShareUrl.class);
    }
}
