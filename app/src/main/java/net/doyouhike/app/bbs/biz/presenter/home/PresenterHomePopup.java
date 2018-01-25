package net.doyouhike.app.bbs.biz.presenter.home;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.MySubscibesRespons;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.nohttp.CallServer;
import net.doyouhike.app.bbs.biz.openapi.request.tags.UserSubscriptionsGet;
import net.doyouhike.app.bbs.ui.widget.live.tag.HomeTagDialog;
import net.doyouhike.app.bbs.util.UserInfoUtil;

/**
 * 功能：
 *
 * @author：曾江 日期：16-2-26.
 */
public class PresenterHomePopup {

    HomeTagDialog iView;

    public PresenterHomePopup(HomeTagDialog iView) {
        this.iView = iView;
    }

    public void initData() {

        if (UserInfoUtil.getInstance().isLogin()){
            getSubscribTag();
        }
    }

    public void onDestroy() {
        iView = null;
    }

    private void getSubscribTag() {

        //获取我订阅的标签
        UserSubscriptionsGet get = new UserSubscriptionsGet(UserInfoUtil.getInstance().getUserId());
        get.setExtraInfo(false);

        CallServer.getRequestInstance().cancelBySign(Content.REQ_TAGDATA_GET_MY_SUBSCRIBES);
        ApiReq.doGet(get, getMySubscriListener);
    }

    //获取订阅标签
    IOnResponseListener getMySubscriListener = new IOnResponseListener<Response<MySubscibesRespons>>() {


        @Override
        public void onSuccess(Response<MySubscibesRespons> response) {

            boolean isOffline = (boolean) response.getExtraTag();
            if (null != iView)
                iView.onGetSubcribeTags(response.getData().getMy_subscribes(), isOffline);
        }

        @Override
        public void onError(Response response) {
            if (null != iView)
                iView.onGetSubscribeTagErr(response);
        }
    };
}
