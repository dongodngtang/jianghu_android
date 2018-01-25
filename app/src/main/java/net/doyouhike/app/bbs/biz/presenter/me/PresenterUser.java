package net.doyouhike.app.bbs.biz.presenter.me;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.request.get.TimeLineReq;
import net.doyouhike.app.bbs.biz.newnetwork.model.request.get.UserEventsReq;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.nohttp.CallServer;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines.UserTimelineGet;
import net.doyouhike.app.bbs.biz.openapi.request.users.UserEventsGet;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.ui.activity.me.IViewUser;

/**
 * 功能：
 *
 * @author：曾江 日期：16-3-22.
 */
public abstract class PresenterUser {


    protected IViewUser iView;

    public PresenterUser(IViewUser iView) {
        this.iView = iView;
    }

    public void setView(IViewUser iView) {
        this.iView = iView;
    }

    /**
     * @param param 获取活动列表参数
     */
    public void getUserEvent(UserEventsGet param) {
        CallServer.getRequestInstance().cancelBySign(Content.REQ_USER_EVENTS);
        param.setExtraInfo(param.getPage_index());
        ApiReq.doGet(param, getUserEventListener);

    }

    /**
     * @param param 获取我的直播列表参数
     */
    public void getTimeLine(UserTimelineGet param) {

        param.setExtraInfo(param.getPage_index());
        ApiReq.doGet(param, getTmLineListener);
    }



    public void onDestroy() {
        iView = null;
    }


    //#######start#########
    //获取用户的活动列表
    IOnResponseListener getUserEventListener = new IOnResponseListener<Response<EventsListResp>>() {
        @Override
        public void onSuccess(Response<EventsListResp> response) {
            //获取请求标签，如果是从第1页开始，则是刷新
            boolean isRefreash = (int) response.getExtraTag() == 1;
            if (null != iView)
                iView.updateUserEvent(response.getData().getItems(), isRefreash);

        }

        @Override
        public void onError(Response response) {
            //获取请求标签，如果是从第1页开始，则是刷新
            boolean isRefreash = (int) response.getExtraTag() == 1;
            if (null != iView)
                iView.getItemsErr(IViewUser.ERR_EVENT, response.getCode(), response.getMsg(), isRefreash);
        }
    };
    //获取用户的活动列表
    //#######end#########

    //获取我的直播
    //#######start#########

    IOnResponseListener getTmLineListener = new IOnResponseListener<Response<NodeTimeline>>() {
        @Override
        public void onSuccess(Response<NodeTimeline> response) {
            //获取请求标签，如果是从第1页开始，则是刷新
            boolean isRefreash = (int) response.getExtraTag() == 1;
            if (null != iView)
                iView.updateTmLine(response.getData().getItems(), isRefreash);
        }

        @Override
        public void onError(Response response) {
            //获取请求标签，如果是从第1页开始，则是刷新
            boolean isRefreash = (int) response.getExtraTag() == 1;
            if (null != iView)
                iView.getItemsErr(IViewUser.ERR_LIVE, response.getCode(), response.getMsg(), isRefreash);
        }
    };
    //获取我的直播
    //#######end#########


}
