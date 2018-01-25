package net.doyouhike.app.bbs.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;

import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.fragment.BaseFragment;
import net.doyouhike.app.bbs.biz.event.im.GetOtherHxIdEvent;
import net.doyouhike.app.bbs.biz.event.im.GetUserInfoByHxIdEvent;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.ActionListPage;
import net.doyouhike.app.bbs.biz.event.action.ActionResaerchLocationEvent;
import net.doyouhike.app.bbs.biz.helper.list_helper.SimpleListHelper;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;
import net.doyouhike.app.bbs.biz.presenter.action.NewActionFragPresenter;
import net.doyouhike.app.bbs.ui.activity.action.ActionSelectCityActicity;
import net.doyouhike.app.bbs.ui.widget.action.ViActionContent;
import net.doyouhike.app.bbs.ui.widget.common.IUpdateView;
import net.doyouhike.app.bbs.util.AmapLocationUtil;
import net.doyouhike.app.bbs.util.GetCityIDUtils;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SpTools;
import net.doyouhike.app.bbs.util.StrUtils;
import net.doyouhike.app.library.ui.uistate.SimpleUiHandler;
import net.doyouhike.app.library.ui.uistate.UiState;
import net.doyouhike.app.library.ui.widgets.XSwipeRefreshLayout;

import java.util.List;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;

/**
 * 功能：
 * 作者：曾江
 * 日期：15-12-28.
 */
public class ActionFragment extends BaseFragment implements
        ViActionContent.IViActionContentListener, SwipeRefreshLayout.OnRefreshListener, IUpdateView {


    NewActionFragPresenter presenterActionFrag;

    SimpleListHelper mListHelper;
    ActionListPage mActionPage;

    private String currentCity = "";//当前所选城市
    private String localtionCity = "";//定位城市
    // 是否显示城市选择对话框,如果已经显示过了,或者已经选择了城市,则不用再显示了
    private boolean isShowDialog = true;

    /**
     * 是否可见,为了在定位回调时,当界面不可见是不显示对话框,做个标志
     */
    private boolean isVisibleToUser = false;

    PullToRefreshListView plvAction;

    /**
     * 活动列表内容
     */
    @InjectView(R.id.vi_frag_action_content)
    ViActionContent viActionContent;


    /**
     * 上拉刷新
     */
    @InjectView(R.id.ptr_frag_action_refresh)
    XSwipeRefreshLayout ptrFragActionRefresh;

    /**
     * 高德地图定位工具类
     */
    private AmapLocationUtil mapLocationUtil;


    /**
     * 获取离线活动items
     *
     * @param actionInfos 活动内容
     * @param isRefreash  是否刷新，true 为刷新，false为加载更多
     */
    public void updateOfflineItems(List<EventsListResp.ItemsBean> actionInfos, boolean isRefreash) {

        resetStateVi();
        //填充列表数据
        mActionPage.updateItem(actionInfos, isRefreash);
        //重设内容宽度,若为空,不能上下滚动
        viActionContent.setContentView(mActionPage.isEmpty());
        //通知数据变化
        mListHelper.getAdapter().notifyDataSetChanged();
        //显示公告栏
        viActionContent.post(new Runnable() {
            @Override
            public void run() {
                viActionContent.showAd();
            }
        });

    }


    /**
     * 定位城市对话框选中
     *
     * @param city 定位城市
     */
    public void setLocaltionCity(String city) {
        currentCity = city;
        viActionContent.setLocation(currentCity);
        SpTools.setString(mContext, "LASTCITY", currentCity);
        setCityID();
        regetActionList();
    }


    public ViActionContent getViActionContent() {
        return viActionContent;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //获取上次选择的城市
        currentCity = SpTools.getString(mContext, "LASTCITY", "");
        if (!TextUtils.isEmpty(currentCity)) {
            viActionContent.setLocation(currentCity);
            setCityID();
        }


    }

    @Override
    protected void onFirstUserVisible() {
        initLocation();//初始化定位

        presenterActionFrag.initData();
        mListHelper.getData(true);

        viActionContent.post(new Runnable() {
            @Override
            public void run() {
                viActionContent.showAd();
            }
        });

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.d(TAG_LOG, "setUserVisibleHint" + isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;

        if (isVisibleToUser) {
            showLocationDialog();
            setSystemBarTintDrawable(-1);
        } else {
            setSystemBarTintDrawable(R.color.status_bg);
        }
    }


    @Override
    protected void onUserVisible() {
        viActionContent.getAdDataPlayer().startTask();
        viActionContent.showAd();
    }

    @Override
    protected void onUserInvisible() {
        viActionContent.getAdDataPlayer().stopTask();
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void initViewsAndEvents() {

        plvAction = viActionContent.getListView();


        presenterActionFrag = new NewActionFragPresenter(getActivity(), this);
        viActionContent.setListener(this);

        mActionPage = new ActionListPage(getContext());

        mListHelper = new SimpleListHelper<EventsListResp.ItemsBean>(ptrFragActionRefresh, plvAction, this, mActionPage) {
            /**
             * 网络获取数据后会调用此方法
             * @param hasMore   有无更多数据
             * @param isRefresh 是否刷新
             */
            @Override
            public void onResponse(boolean hasMore, boolean isRefresh) {
                resetStateVi();
                if (isRefresh) {
                    viActionContent.setContentView(mActionPage.isEmpty());
                }
                super.onResponse(hasMore, isRefresh);
            }
        };
        //关闭下拉刷新
        ptrFragActionRefresh.setEnabled(false);

    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_action_new;
    }

    @Override
    protected View getLoadingTargetView() {
        return ptrFragActionRefresh;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null || resultCode != Activity.RESULT_OK) {
            return;
        }
        currentCity = data.getExtras().getString("selectCity");// 获取返回的数据
        viActionContent.setLocation(currentCity);
        SpTools.setString(mContext, "LASTCITY", currentCity);
        isShowDialog = false;
        // 重新获取城市ID，请求网络数据
        setCityID();
        regetActionList();
    }

    @Override
    public void onDestroyView() {
        try {
            //暂停定位
            if (null != mapLocationUtil) {
                mapLocationUtil.stopLocation();
                mapLocationUtil.onDestory();
            }
            mListHelper.onDestroy();
            viActionContent.onDestroyView();
            presenterActionFrag.onDestroy();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroyView();

    }

    /**
     * @param viewId 对应的文本框ID
     * @param id     时间.活动类型 id
     */
    @Override
    public void updateConditionSelected(int viewId, int id) {


        switch (viewId) {
            case R.id.ll_action_research_go_soon:
                //"search_type": 1/2 , 1、即将出发 2、离我最近（需传经纬度）
                mActionPage.getRequestParam().setSearch_type(id + "");
                break;
            case R.id.ll_action_research_type:
                //类型选择
                mActionPage.getRequestParam().setTag_id(id == 0 ? null : id + "");
                break;
            case R.id.cb_action_research_has_fd:
                //好友参加
                mActionPage.getRequestParam().setHas_friend(id + "");
                break;
            case R.id.rl_action_title_location:
                // 跳转到城市选择界面
                Intent intent = new Intent(getActivity(),
                        ActionSelectCityActicity.class);
                intent.putExtra("city", TextUtils.isEmpty(currentCity) ? "其他" : currentCity);
                startActivityForResult(intent, 1);
                return;
        }

        regetActionList();
    }


    @Override
    public void isListViewTop(boolean isTop) {
//        if (null != ptrFragActionRefresh) {
//            ptrFragActionRefresh.setEnabled(isTop);
//        }
    }

    float mPercentage = 0;

    @Override
    public void onAdViewScroll(float percentage) {
        mPercentage = percentage;
        showSystemBarTint();
    }

    /**
     * 请求活动列表数据
     */
    @Override
    public void requestEventTypeData() {
        presenterActionFrag.requestEventTypeData();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        ptrFragActionRefresh.setRefreshing(false);
        regetActionList();
    }


    /**
     * 地图定位 回调
     *
     * @param event 位置信息
     */
    public void onEvent(ActionResaerchLocationEvent event) {
        // 获取位置信息
        if (null != event && null != event.getaMapLocation()) {
            String cityName = event.getaMapLocation().getCity();
            int cityID = GetCityIDUtils.getCityID(getActivity(), currentCity);
            mActionPage.getRequestParam().setCity_id(cityID + "");
            LogUtil.d(TAG_LOG, "Events city_id" + event.getaMapLocation().getCityCode());

            if (!TextUtils.isEmpty(cityName) && cityName.endsWith("市")) {
                cityName = cityName.replace("市", "");
            }
            localtionCity = cityName;
            // 获取位置信息

            LogUtil.d(TAG_LOG, "onLocationChanged:" + localtionCity);

            if (TextUtils.isEmpty(currentCity)) {//初次进入app,不提醒
                isShowDialog = false;
                currentCity = localtionCity;
                viActionContent.setLocation(currentCity);
                SpTools.setString(mContext, "LASTCITY", currentCity);//保存当前城市

                setCityID();
                regetActionList();
            } else if (isVisibleToUser) {
                showLocationDialog();
            }
        }

    }


    private void showLocationDialog() {
        //本界面可见时若定位城市和上次不符合，弹框提醒
        if (isShowDialog && StrUtils.hasContent(localtionCity)
                && (!currentCity.equals(localtionCity))) {
            presenterActionFrag.showChangeCityDialog(localtionCity);
            isShowDialog = false;
        }
    }


    /**
     * 启动定位  每次只需定位一次
     */
    private void initLocation() {
        if (null == mapLocationUtil)
            mapLocationUtil = new AmapLocationUtil(new ActionResaerchLocationEvent());
        mapLocationUtil.startLocation(true, 0);
    }


    /**
     * 刷新活动列表，从第一页获取
     */
    private void regetActionList() {
        mListHelper.getData(true);
    }


    /**
     * 重置更新状态的绑定视图
     */
    private void resetStateVi() {

        updateView(UiState.NORMAL);

        if (uiStateController.getUiStateHandle().getContentView() == plvAction) {
            return;
        }

        uiStateController.setUiStateHandle(new SimpleUiHandler.Builder().setHelper(plvAction).getUiHandler());
        updateView(UiState.NORMAL);
    }


    /**
     * 显示沉浸式
     */
    private void showSystemBarTint() {

        if (!isVisibleToUser) {
            return;
        }
        if (mPercentage > 0.25f) {
            setSystemBarTintAlpha(R.color.color_theme, mPercentage);
        } else {
            setSystemBarTintDrawable(-1);
        }
    }


    /**
     * 获取定位城市的ID
     */
    private void setCityID() {
        int cityID = GetCityIDUtils.getCityID(getActivity(), currentCity);
        mActionPage.getRequestParam().setCity_id(cityID + "");
    }

}
