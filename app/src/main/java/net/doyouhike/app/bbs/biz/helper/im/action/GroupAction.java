package net.doyouhike.app.bbs.biz.helper.im.action;

import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

import net.doyouhike.app.bbs.biz.db.dao.UserInfoDbUtil;
import net.doyouhike.app.bbs.biz.entity.im.GroupMsgDetail;
import net.doyouhike.app.bbs.biz.entity.user.SearchEmIdResult;
import net.doyouhike.app.bbs.biz.event.GlobalDialogEvent;
import net.doyouhike.app.bbs.biz.helper.im.HxidDispatcher;
import net.doyouhike.app.bbs.biz.helper.im.base.BaseAction;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.HxUserInfo;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetUserImResponse;
import net.doyouhike.app.bbs.biz.openapi.request.users.ims.UsersImsGet;
import net.doyouhike.app.bbs.util.LogUtil;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 群发消息
 * Created by zengjiang on 16/7/30.
 */

public class GroupAction extends BaseAction<List<String>,GetUserImResponse> {


    private GroupMsgDetail mDraftDetail;

    private String msg;

    /**
     * 处理从网络获取的结果
     *
     * @param response 网络响应
     */
    @Override
    public void processResponse(Response<GetUserImResponse> response) {
        UserInfoDbUtil.getInstance().updateImId(response.getData().getItems());
        sendGroupMsg(response.getData().getItems());

    }

    @Override
    public UsersImsGet getRequestParam() {
        UsersImsGet userImReqParam = new UsersImsGet();
        userImReqParam.setUser_ids(params);
        return  userImReqParam;
    }


    /**
     * 初步处理本地获取的数据
     *
     * @param dispatcher 分发器,用于成功回调
     */
    @Override
    public void processRequest(HxidDispatcher dispatcher) {
        //从本地获取数据
        SearchEmIdResult result = UserInfoDbUtil.getInstance().getImId(getParams());
        //处理结果
        handleLocalSearchResult(result);
    }


    /**
     * 处理本地搜索
     * @param result 本地搜索结果
     */
    private void handleLocalSearchResult(SearchEmIdResult result) {


        List<HxUserInfo> items = result.getExistEmIdList();

        sendGroupMsg(items);

    }

    @Override
    public void processError() {
        super.processError();
        //保存失败草稿
        mDraftDetail.setUuids(params);
        //重试次数递增
        mDraftDetail.setRetryCount(mDraftDetail.getRetryCount()+1);
        UserInfoDbUtil.getInstance().saveDraft(mDraftDetail);
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    private void sendGroupMsg(List<HxUserInfo> items) {

        if (null == items || items.isEmpty()) {
            return;
        }

        synchronized (params) {

            for (HxUserInfo userInfo : items) {

                if (params.contains(userInfo.getUser_uuid())) {
                    //是否封禁用户
                    if (userInfo.isIm_enabled()) {
                        //发送环信消息
                        sendEmMsg(userInfo.getIm_id());
                    }else {


                        String nickName= userInfo.getNickName();

                        if (null==nickName){
                            nickName="";
                        }

                        EventBus.getDefault().post(GlobalDialogEvent.getGlobalDialogEvent( "无法发送", nickName+"账号被冻结，有疑问请联系磨房网"));
                    }
                    //移除已经获取到环信ID的用户
                    params.remove(userInfo.getUser_uuid());

                }

            }


        }


        if (params.isEmpty()) {
            setAction(IHxIdAction.REQUEST_COMPLETE);
        } else {
            setAction(IHxIdAction.GET_FROM_NET);
        }

    }

    private void sendEmMsg(String emId) {
        //创建一条文本消息，content为消息文字内容，toChatUsername为对方用户或者群聊的id，后文皆是如此
        EMMessage message = EMMessage.createTxtSendMessage(msg, emId);
        message.setChatType(EMMessage.ChatType.Chat);
        LogUtil.d("sendMsg", "emId:" + emId + "   msg:" + msg);
        //发送消息
        EMClient.getInstance().chatManager().sendMessage(message);
    }

    public void setDraftDetail(GroupMsgDetail msgDetail) {
        this.mDraftDetail = msgDetail;
    }
}
