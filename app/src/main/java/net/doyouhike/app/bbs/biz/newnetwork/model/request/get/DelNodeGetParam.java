package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

/**
 * Created by zaitu on 15-11-30.
 * 删除江湖信息
 */
@RequestUrlAnnotation(Content.REQ_DELETE_NODE)
public class DelNodeGetParam extends BaseTokenGetParams{

    private String nodeID;


    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }

    public String getNodeID() {
        return nodeID;
    }

    @Override
    protected void setMapValue() {
        super.setMapValue();
        map.put("nodeID",nodeID);
    }

}
