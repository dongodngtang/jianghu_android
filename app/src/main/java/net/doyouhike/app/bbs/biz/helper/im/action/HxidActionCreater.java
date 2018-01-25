package net.doyouhike.app.bbs.biz.helper.im.action;

import net.doyouhike.app.bbs.biz.entity.im.GroupMsgDetail;

import java.util.List;

/**
 * 环信行为相关处理
 * Created by zengjiang on 16/7/30.
 */

public class HxidActionCreater {


    /**
     * @param detail    群发消息细节
     * @return 群发消息行为
     */
    public static GroupAction createGroupMsgAction(GroupMsgDetail detail){
        GroupAction action=new GroupAction();
        action.setAction(IHxIdAction.GROUP_MSG);
        action.setDraftDetail(detail);
        action.setParams(detail.getUuids());
        action.setMsg(detail.getMsg());
        return action;
    }

    /**
     * 获取用户信息
     * @param hxId
     * @return
     */
    public static RequestUserAction createRequestUserAction(String hxId){
        RequestUserAction action = new RequestUserAction();
        action.setAction(IHxIdAction.REQUEST_BY_HX_ID);
        action.setParams(hxId);
        return action;
    }

    /**
     *
     * @param requestIds 请求用户ID
     * @return 批量请求环信信息的行为
     */
    public static RequestIdsAction createListRequestAction(List<String> requestIds){
        RequestIdsAction action=new RequestIdsAction();
        action.setAction(IHxIdAction.REQUEST_IDS);
        action.setParams(requestIds);
        return action;
    }

    /**
     * @param userId 请求环信id的用户ID
     * @return 请求环信ID行为
     */
    public static RequestIdAction createRequestIdAction(String userId){
        RequestIdAction action=new RequestIdAction();
        action.setAction(IHxIdAction.REQUEST_ID);
        action.setParams(userId);
        return action;
    }







}
