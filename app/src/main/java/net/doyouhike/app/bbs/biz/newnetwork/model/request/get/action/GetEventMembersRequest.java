package net.doyouhike.app.bbs.biz.newnetwork.model.request.get.action;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.action.ActionMemberResponse;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

/**
 * 获取活动的参与人员
 *
 * 请求地址：event/get_event_members
 * 请求方式：POST
 *
 *
 * 参数：
 * token //可选，未传递token时，is_follow 都是0, 传递token时 is_follow显示真实值
 * joinStatus:0 // 0 全部状态 1 未确认, 2 已确认
 * nodeID:1116756
 *
 * page:1 limit:2
 *
 * Created by zengjiang on 16/7/19.
 */
@RequestUrlAnnotation(Content.REQ_GET_EVENT_MEMBERS)
public class GetEventMembersRequest extends BaseListGet {

    @Override
    protected void setMapValue() {
        super.setMapValue();
        putValue("joinStatus",joinStatus);
        putValue("nodeID",nodeID);
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<ActionMemberResponse>(ActionMemberResponse.class);
    }


    private int joinStatus;// 0 全部状态 1 未确认, 2 已确认
    private String nodeID;


    public int getJoinStatus() {
        return joinStatus;
    }

    public void setJoinStatus(int joinStatus) {
        this.joinStatus = joinStatus;
    }

    public String getNodeID() {
        return nodeID;
    }

    public void setNodeID(String nodeID) {
        this.nodeID = nodeID;
    }
}
