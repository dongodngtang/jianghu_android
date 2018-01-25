//package net.doyouhike.app.bbs.biz.presenter.action;
//
//import android.content.Context;
//
//import com.flyco.dialog.listener.OnBtnClickL;
//
//import net.doyouhike.app.bbs.biz.entity.ActionInfo;
//import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
//import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
//import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
//import net.doyouhike.app.bbs.biz.newnetwork.model.request.get.GetAdDataReq;
//import net.doyouhike.app.bbs.biz.newnetwork.model.request.get.GetEventTypesParam;
//import net.doyouhike.app.bbs.biz.newnetwork.model.request.post.SearchEventsGetReq;
//import net.doyouhike.app.bbs.biz.newnetwork.model.response.action.AdDataResp;
//import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetEventTypeSucRepo;
//import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
//import net.doyouhike.app.bbs.biz.newnetwork.service.OfflineReq;
//import net.doyouhike.app.bbs.ui.fragment.ActionFragment;
//import net.doyouhike.app.bbs.ui.util.TipUtil;
//import net.doyouhike.app.bbs.util.LogUtil;
//import net.doyouhike.app.bbs.util.RequestUtil;
//import net.doyouhike.app.bbs.util.UserInfoUtil;
//
//import java.util.List;
//
///**
// * 功能：
// * 作者：曾江
// * 日期：15-12-28.
// */
//public class ActionFragmentPresenter {
//
//    ActionFragment iView;
//    Context context;
//
//    boolean hasGetAdDataFromNet = false;
//
//
//    public ActionFragmentPresenter(Context context, ActionFragment iView) {
//        this.context = context;
//        this.iView = iView;
//
//    }
//
//
//    public void initData() {
//
//
//        //请求活动类型数据
//        requestEventTypeData();
//
//        getAdData();
//        getOfflineAdData();
//    }
//
//    public void requestEventTypeData() {
//
//        //从网络获取活动类型
//        OfflineReq.doGet(new GetEventTypesParam(), getEventTypeListener);
//        ApiReq.doGet(new GetEventTypesParam(), getEventTypeListener);
//    }
//
//
//    public void getActionItems(final SearchEventsGetReq param) {
//        param.setExtraInfo(param.getPage());
//        RequestUtil.getInstances().cancelRequest(Content.REQ_SEARCH_EVENTS);
//        ApiReq.doGet(param, searchActionListener);
//        if (!hasGetAdDataFromNet) {
//            getAdData();
//        }
//    }
//
//    public void getOfflineEvents(SearchEventsGetReq param) {
//        param.setExtraInfo(param.getPage());
//        OfflineReq.doGet(param, searchActionListener);
//    }
//
//
//    /**
//     * @param nowCity 提醒切换定位
//     */
//    public void showChangeCityDialog(final String nowCity) {
//
//        TipUtil.alert(iView.getContext(), "切换到" + nowCity + "？",
//                "是否切换到现在所在城市",
//                new String[]{"取消", "切换"}, new OnBtnClickL() {
//                    @Override
//                    public void onBtnClick() {
//
//                    }
//                }, new OnBtnClickL() {
//                    @Override
//                    public void onBtnClick() {
//                        // 切换到现在城市
//                        iView.setLocaltionCity(nowCity);
//                    }
//                });
//    }
//
//
//    public void onDestroy() {
//        iView = null;
//    }
//
//    //#######start#########
//    //活动类型相关
//
//    IOnResponseListener getEventTypeListener = new IOnResponseListener<Response<List<GetEventTypeSucRepo>>>() {
//
//        @Override
//        public void onSuccess(Response<List<GetEventTypeSucRepo>> response) {
//            if (null != iView) {
//                iView.getViActionContent().updateEventData(response.getData());
//            }
//        }
//
//        @Override
//        public void onError(Response response) {
//
//        }
//    };
//
//    //活动类型相关
//    //#######end#########
//
//
//    //#######start#########
//    //查询活动相关
//    IOnResponseListener searchActionListener = new IOnResponseListener<Response<List<ActionInfo>>>() {
//        @Override
//        public void onSuccess(Response<List<ActionInfo>> response) {
//            //获取请求标签，如果是从第1页开始，则是刷新
//            boolean isRefreash = (int) response.getExtraTag() == 1;
//            LogUtil.d("LOG CACHE", "searchActionListener");
//            if (null != iView)
//                iView.updateOfflineItems(response.getData(), isRefreash);
//        }
//
//        @Override
//        public void onError(Response response) {
//            //获取请求标签，如果是从第1页开始，则是刷新
//        }
//    };
//    //查询活动相关
//    //#######end#########
//
//    //#######start#########
//    //获取广告相关
//    IOnResponseListener getAdDataListener = new IOnResponseListener<Response<List<AdDataResp>>>() {
//        @Override
//        public void onSuccess(Response<List<AdDataResp>> response) {
//            if (null != iView)
//                iView.getViActionContent().updateAd(response.getData());
//            //离线数据标志
//            boolean getFromOfflineData = (boolean) response.getExtraTag();
//            hasGetAdDataFromNet = !getFromOfflineData;
//
//        }
//
//        @Override
//        public void onError(Response response) {
//
//        }
//    };
//
//    private void getAdData() {
//
//        GetAdDataReq adDataReq = new GetAdDataReq();
//        adDataReq.setToken(UserInfoUtil.getInstance().getToken());
//        adDataReq.setExtraInfo(false);//设置获取离线数据标志
//        RequestUtil.getInstances().cancelRequest(Content.REQ_SERVER_GET_ADDATA);
//        ApiReq.doGet(adDataReq, getAdDataListener);
//    }
//
//    private void getOfflineAdData() {
//
//        GetAdDataReq adDataReq = new GetAdDataReq();
//        adDataReq.setToken(UserInfoUtil.getInstance().getToken());
//        //设置获取离线数据标志
//        adDataReq.setExtraInfo(true);
//        OfflineReq.doGet(adDataReq, getAdDataListener);
//
//    }
//    //获取广告相关
//    //#######end#########
//}
