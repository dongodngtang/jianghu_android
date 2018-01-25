package net.doyouhike.app.bbs.biz.helper.im.action;

import android.text.TextUtils;

import net.doyouhike.app.bbs.biz.db.dao.UserInfoDbUtil;
import net.doyouhike.app.bbs.biz.db.green.entities.ChatUserInfo;
import net.doyouhike.app.bbs.biz.helper.im.HxidDispatcher;
import net.doyouhike.app.bbs.biz.helper.im.base.BaseAction;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.HxUserInfo;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetUserImResponse;
import net.doyouhike.app.bbs.biz.openapi.request.users.ims.UsersImsGet;
import net.doyouhike.app.bbs.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengjiang on 16/7/30.
 */

public class RequestIdAction extends BaseAction<String, GetUserImResponse> {


    @Override
    public void processResponse(Response<GetUserImResponse> response) {

        UserInfoDbUtil.getInstance().updateImId(response.getData().getItems());
        setAction(IHxIdAction.REQUEST_COMPLETE);
    }

    /**
     * @param dispatcher
     */
    @Override
    public void processRequest(HxidDispatcher dispatcher) {

        ChatUserInfo chatUserInfo = UserInfoDbUtil.getInstance().getUser(getParams());
        if (null != chatUserInfo && !TextUtils.isEmpty(chatUserInfo.getIm_id())) {
            LogUtil.d("HxidStore", "get from db success:  " + chatUserInfo.toString());
            dispatcher.callBackSuccess(getParams(), chatUserInfo, IHxIdAction.REQUEST_ID);
            setAction(IHxIdAction.REQUEST_COMPLETE);
        } else {
            //从网络获取
            setAction(IHxIdAction.GET_FROM_NET);
        }
    }

    @Override
    public UsersImsGet getRequestParam() {
        UsersImsGet userImReqParam = new UsersImsGet();
        List<String> user_id = new ArrayList<>();
        user_id.add(getParams());

        userImReqParam.setUser_ids(user_id);
        return userImReqParam;
    }

    @Override
    public void callBackSuccess(HxidDispatcher dispatcher) {
        String userId = getParams();
        ChatUserInfo userInfo = UserInfoDbUtil.getInstance().getUser(userId);
        dispatcher.callBackSuccess(userId, userInfo, IHxIdAction.REQUEST_ID);
    }

    @Override
    public void onCallBackError(HxidDispatcher dispatcher, Response response) {

        dispatcher.onCallBackError(getParams(), response, IHxIdAction.REQUEST_ID);
    }
}
