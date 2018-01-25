package net.doyouhike.app.bbs.biz.presenter.action;

import android.content.Context;

import com.flyco.dialog.listener.OnBtnClickL;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.EventHelper;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventBannersResp;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventTypesResp;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;
import net.doyouhike.app.bbs.ui.fragment.ActionFragment;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.util.LogUtil;

/**
 * 作者：luochangdong on 16/9/9
 * 描述：
 */
public class NewActionFragPresenter {
    ActionFragment iView;
    Context context;

    public NewActionFragPresenter(Context context, ActionFragment iView) {
        this.context = context;
        this.iView = iView;

    }


    public void initData() {


        //请求活动类型数据
        requestEventTypeData();
        //获取EventBanners
        getAdData();
    }

    public void requestEventTypeData() {

        //从网络获取活动类型
        EventHelper.getInstance().getEventTypes(iView.getContext(), getEventTypeListener);

    }


    /**
     * @param nowCity 提醒切换定位
     */
    public void showChangeCityDialog(final String nowCity) {

        TipUtil.alert(iView.getContext(), "切换到" + nowCity + "？",
                "是否切换到现在所在城市",
                new String[]{"取消", "切换"}, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {

                    }
                }, new OnBtnClickL() {
                    @Override
                    public void onBtnClick() {
                        // 切换到现在城市
                        iView.setLocaltionCity(nowCity);
                    }
                });
    }


    public void onDestroy() {
        iView = null;
    }

    //#######start#########
    //活动类型相关

    IOnResponseListener getEventTypeListener = new IOnResponseListener<Response<EventTypesResp>>() {

        @Override
        public void onSuccess(Response<EventTypesResp> response) {
            if (null != iView) {
                iView.getViActionContent().updateEventData(response.getData().getItems());
            }
        }

        @Override
        public void onError(Response response) {

        }
    };

    //活动类型相关
    //#######end#########


    //#######start#########
    //查询活动相关
    IOnResponseListener searchActionListener = new IOnResponseListener<Response<EventsListResp>>() {
        @Override
        public void onSuccess(Response<EventsListResp> response) {
            //获取请求标签，如果是从第1页开始，则是刷新
            boolean isRefreash = (int) response.getExtraTag() == 1;
            LogUtil.d("LOG CACHE", "searchActionListener");
            if (null != iView)
                iView.updateOfflineItems(response.getData().getItems(), isRefreash);
        }

        @Override
        public void onError(Response response) {
            //获取请求标签，如果是从第1页开始，则是刷新
        }
    };
    //查询活动相关
    //#######end#########

    //#######start#########
    //获取广告相关
    IOnResponseListener getAdDataListener = new IOnResponseListener<Response<EventBannersResp>>() {
        @Override
        public void onSuccess(Response<EventBannersResp> response) {
            if (null != iView)
                iView.getViActionContent().updateAd(response.getData().getItems());

        }

        @Override
        public void onError(Response response) {

        }
    };

    private void getAdData() {
        //获取广告相关
        EventHelper.getInstance().getEventBanner(iView.getContext(), getAdDataListener);

    }

}
