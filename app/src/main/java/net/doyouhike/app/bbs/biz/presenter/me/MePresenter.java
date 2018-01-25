package net.doyouhike.app.bbs.biz.presenter.me;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.nohttp.CallServer;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines.UserFavoritesTimelineGet;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.ui.activity.me.IViewUser;
import net.doyouhike.app.bbs.ui.widget.me.IViewMeFrag;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.UserInfoUtil;

/**
 * 功能：
 * 作者：曾江
 * 日期：16-1-8.
 */
public class MePresenter extends PresenterUser {

    public MePresenter(IViewMeFrag iView) {
        super(iView);
    }

    /**
     * 获取查看自己的资料
     */
    public void getMyProfile() {

        String user_id = UserInfoUtil.getInstance().getUserId();
        UsersHelper.getSingleTon().getUserProfile(user_id, getUserInfoDetailListener);

    }




    /**
     * @param param 获取我的收藏列表参数
     */
    public void getMyCollectList(UserFavoritesTimelineGet param) {
        CallServer.getRequestInstance().cancelBySign(Content.REQ_GET_MY_COLLECT_LIST);
        param.setExtraInfo(param.getPage_index());
        ApiReq.doGet(param, getCollectListener);

    }




    //获取我的收藏
    //#######start#########

    IOnResponseListener getCollectListener = new IOnResponseListener<Response<NodeTimeline>>() {
        @Override
        public void onSuccess(Response<NodeTimeline> response) {
            //获取请求标签，如果是从第1页开始，则是刷新
            boolean isRefreash = (int) response.getExtraTag() == 1;
            if (null != iView)
                ((IViewMeFrag) iView).updateCollects(response.getData().getItems(), isRefreash);

        }

        @Override
        public void onError(Response response) {
            //获取请求标签，如果是从第1页开始，则是刷新
            boolean isRefreash = (int) response.getExtraTag() == 1;
            if (null != iView)
                iView.getItemsErr(IViewMeFrag.ERR_COLLECT, response.getCode(), response.getMsg(), isRefreash);
        }
    };

    //获取我的收藏
    //#######end#########

    //获取用户资料详情
    //#######start#########

    protected IOnResponseListener getUserInfoDetailListener = new IOnResponseListener<Response<CurrentUserDetails>>() {
        @Override
        public void onSuccess(Response<CurrentUserDetails> response) {

            CurrentUserDetails details = response.getData();

            if (null == details) {
                return;
            }

            //更新用户信息
            SharedPreferencesManager.setUserDetailsInfo(
                    MyApplication.getInstance().getApplicationContext(), details);
            //刷新界面
            if (null != iView)
                iView.updateProfile(response.getData());
        }

        @Override
        public void onError(Response response) {
            //获取请求标签，如果是从第1页开始，则是刷新
            if (null != iView)
                iView.onRequestErr(IViewUser.ERR_COLLECT, response.getMsg());
        }
    };

    //获取用户资料详情
    //#######end#########
}
