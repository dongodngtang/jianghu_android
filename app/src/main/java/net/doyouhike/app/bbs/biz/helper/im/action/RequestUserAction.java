package net.doyouhike.app.bbs.biz.helper.im.action;

import android.text.TextUtils;

import net.doyouhike.app.bbs.biz.db.dao.UserInfoDbUtil;
import net.doyouhike.app.bbs.biz.db.green.entities.ChatUserInfo;
import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.helper.im.HxidDispatcher;
import net.doyouhike.app.bbs.biz.helper.im.base.BaseAction;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.request.users.UserInfoGet;
import net.doyouhike.app.bbs.util.LogUtil;

/**
 * 作者：luochangdong on 16/8/15
 * 描述：
 */
public class RequestUserAction extends BaseAction<String, CurrentUserDetails> {

    @Override
    public void processResponse(Response<CurrentUserDetails> response) {

        ChatUserInfo chatUserInfo = new ChatUserInfo(response.getData());
        UserInfoDbUtil.getInstance().saveUser(chatUserInfo);
        setAction(IHxIdAction.REQUEST_COMPLETE);
    }

    @Override
    public void processRequest(HxidDispatcher dispatcher) {
        ChatUserInfo chatUserInfo = UserInfoDbUtil.getInstance().getUserFromImId(getParams());
        if (null != chatUserInfo && !TextUtils.isEmpty(chatUserInfo.getIm_id())) {
            LogUtil.d("HxidStore", "get from db success:  " + chatUserInfo.toString());
            dispatcher.callBackSuccess(getParams(), chatUserInfo, IHxIdAction.REQUEST_BY_HX_ID);
            setAction(IHxIdAction.REQUEST_COMPLETE);
        } else {
            //从网络获取
            setAction(IHxIdAction.GET_FROM_NET);
        }
    }

    @Override
    public UserInfoGet getRequestParam() {
        UserInfoGet param = new UserInfoGet(getParams());

        return param;
    }

    @Override
    public void callBackSuccess(HxidDispatcher dispatcher) {
        String userId = getParams();
        ChatUserInfo userInfo = UserInfoDbUtil.getInstance().getUser(userId);
        dispatcher.callBackSuccess(userId, userInfo, IHxIdAction.REQUEST_BY_HX_ID);
    }

    @Override
    public void onCallBackError(HxidDispatcher dispatcher, Response response) {

        dispatcher.onCallBackError(getParams(), response, IHxIdAction.REQUEST_BY_HX_ID);
    }
}
