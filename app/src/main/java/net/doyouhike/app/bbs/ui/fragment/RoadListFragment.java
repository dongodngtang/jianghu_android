/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info , All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: MessageFragment.java
 * Author: ChenWeiZhen
 * Version: 1.0
 * Create: 2015-11-05
 *
 * Changes (from 2015-11-05)
 * -----------------------------------------------------------------
 * 2015-11-05 创建MessageFragment.java (ChenWeiZhen);
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.dialog.listener.OnBtnClickL;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.fragment.BaseFragment;
import net.doyouhike.app.bbs.biz.entity.SelectCityModel;
import net.doyouhike.app.bbs.biz.entity.road.RoadListDes;
import net.doyouhike.app.bbs.biz.event.road.RoadListEvent;
import net.doyouhike.app.bbs.biz.event.road.RoadLocationEvent;
import net.doyouhike.app.bbs.biz.event.road.RoadTypeEvent;
import net.doyouhike.app.bbs.biz.event.road.RoadTypeSelectedEvent;
import net.doyouhike.app.bbs.biz.event.road.ShowRoadTypeEvent;
import net.doyouhike.app.bbs.biz.openapi.response.routes.DestsRoutesResp;
import net.doyouhike.app.bbs.biz.presenter.road.RoadListPresenter;
import net.doyouhike.app.bbs.ui.activity.action.ActionAndRoadSearchActivity;
import net.doyouhike.app.bbs.ui.activity.road.RoadSelectCityActicity;
import net.doyouhike.app.bbs.ui.adapter.road.RoadListAdapter;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.widget.road.RoadLitTypeDialog;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.PullToRefreshLVUtil;
import net.doyouhike.app.bbs.util.SpTools;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.library.ui.uistate.UiState;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 线路
 * 2016.5.6
 */
public class RoadListFragment extends BaseFragment implements View.OnClickListener {

    public static final int CITY_LIST_REQUEST = 511;

    @InjectView(R.id.lv_road_list)
    PullToRefreshListView pullToRefreshListView;

    int currentPage;


    //类型  布局
    @InjectView(R.id.ll_road_list_type)
    LinearLayout ll_road_list_type;

    @InjectView(R.id.iv_road_list_type)
    ImageView iv_road_list_type;

    @InjectView(R.id.tv_roadList_type_name)
    TextView tv_roadList_type_name;

    /**
     * 城市名称
     */
    @InjectView(R.id.ll_action_location)
    LinearLayout ll_action_location;

    /**
     * 城市布局
     */
    @InjectView(R.id.tv_action_city_name)
    TextView tv_action_city_name;

    /**
     * 搜索 布局
     */
    @InjectView(R.id.rl_roadList_search)
    RelativeLayout rl_roadList_search;


    //标记 是否 是最后一页,修改城市 或者类型时  需要还原设置
    private boolean isLastPage = false;


    private RoadListAdapter roadListAdapter;

    private List<DestsRoutesResp.ItemsBean> listInfo = new ArrayList<>();

    private RoadListPresenter roadListPresenter;

    //线路类型
    private RoadLitTypeDialog roadLitTypeDialog;


    private boolean isFristIn = true;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_road_list;
    }

    @Override
    public boolean isNeedPaddingTop() {
        return true;
    }

    @Override
    protected void initViewsAndEvents() {
        if (null == roadListAdapter)
            roadListAdapter = new RoadListAdapter(getActivity(), listInfo);
        pullToRefreshListView.setAdapter(roadListAdapter);

        if (null == roadListPresenter)
            roadListPresenter = new RoadListPresenter(getActivity());

        ll_action_location.setOnClickListener(this);
        rl_roadList_search.setOnClickListener(this);

        if (null == roadLitTypeDialog)
            roadLitTypeDialog = new RoadLitTypeDialog(getActivity());
        ll_road_list_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roadListPresenter.getRoadTypeInfo();
            }
        });

        /**
         * 类型 选择 监听
         */
        roadLitTypeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                //设置图片
                iv_road_list_type.setImageDrawable(getResources().getDrawable(R.drawable.nav_icon_indicator_expand));
                tv_roadList_type_name.setText(roadListPresenter.getRoad_type_name());
            }
        });


        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
                // 更新最后刷新时间
                PullToRefreshLVUtil.setLastUpdatedLabel(
                        getActivity(), refreshView);
                if (isLastPage) {
                    if (listInfo.size() > 0) {
                        pullToRefreshListView.post(new Runnable() {
                            @Override
                            public void run() {
                                pullToRefreshListView.onRefreshComplete();
                            }
                        });
                        StringUtil.showSnack(getActivity(), "已经是最后一页");
                    }

                } else {
                    roadListPresenter.setPage(currentPage + 1);
                    roadListPresenter.getListData(null);
                }


            }


        });
        //获取 城市列表数据
        roadListPresenter.getRoadDesList();
        //设置 定位城市
        if (StringUtil.isNotEmpty(roadListPresenter.getCity_name())) {
            tv_action_city_name.setText(roadListPresenter.getCity_name());
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtil.d(TAG_LOG, "setUserVisibleHint" + isVisibleToUser);
        if (getUserVisibleHint() && StringUtil.isNotEmpty(roadListPresenter.getCity_name_location())) {
            if (!roadListPresenter.getCity_name().equals(roadListPresenter.getCity_name_location()))
                showChangeCityDialog(roadListPresenter.getCity_name_location());
        }
    }


    @Override
    protected View getLoadingTargetView() {
        return pullToRefreshListView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_action_location: //点击 地区选择
                onpenRoadSelectedActivity();
                break;
            case R.id.rl_roadList_search:
                Intent intent = new Intent(getActivity(), ActionAndRoadSearchActivity.class);
                intent.putExtra(ActionAndRoadSearchActivity.I_CHECKOUT_PAGE, false);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onFirstUserVisible() {
        updateView(UiState.LOADING, null);
        //首次查询 查询所有  列表数据
        roadListPresenter.getListData(null);
        roadListPresenter.setRequestAgain(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        if (resultCode == CITY_LIST_REQUEST) {
            //如果 是 当前city 则不用请求数据 如果 不是 则重新请求数据
            String selectCity = data.getExtras().getString("selectCity");// 获取返回的数据
            Serializable serializable = data.getExtras().getSerializable("roadListDesList");
            if (null != serializable)
                roadListPresenter.setRoadListDesList((List<RoadListDes>) serializable);
            if (!selectCity.equals(roadListPresenter.getCity_name())) {
                //重置界面
                resetQuest();
                //显示 加载
                updateView(UiState.LOADING, null);
                //设置 已选择的 城市
                SpTools.setString(mContext, "LASTCITY", selectCity);//保存当前城市
                tv_action_city_name.setText(selectCity);
                roadListPresenter.setCity_name(selectCity);
                roadListPresenter.resetCity_sulg(selectCity);


            }
        }
    }


    public void notifyDataSetChanged() {
        roadListAdapter.notifyDataSetChanged();
    }


    /**
     * 线路列表
     *
     * @param event
     */
    public void onEventMainThread(RoadListEvent event) {
        //第一次加载成功
        if (event.isSuccess()) {
            //隐藏进度
            if (isFristIn) {
                isFristIn = false;

            }
            updateView(UiState.NORMAL);
            currentPage = (int) event.getExtraTag();
            if (roadListPresenter.isRequestAgain()) {
                listInfo.clear();
                roadListPresenter.setRequestAgain(false);
            }
            if (null != event.getData() && event.getData().getItems().size() > 0) {
                listInfo.addAll(event.getData().getItems());
            } else
                isLastPage = true;
            roadListAdapter.notifyDataSetChanged();
            if (listInfo.size() == 0)
                updateView(UiState.EMPTY.setMsg(mContext.getString(R.string.common_no_content)));

        } else if (isFristIn || listInfo.size() == 0) {
            updateView(UiState.ERROR.setMsg(getString(R.string.common_error_msg),
                    getString(R.string.try_to_click_refresh)), refreshListener);
        }
        pullToRefreshListView.onRefreshComplete();

    }

    /**
     * 刷新按钮
     */
    private View.OnClickListener refreshListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            updateView(UiState.LOADING, null);
            //请求 列表数据
            roadListPresenter.getListData(null);
            roadListPresenter.setRequestAgain(true);
        }
    };


    /**
     * 线路类型 数据回调
     *
     * @param event
     */
    public void onEventMainThread(RoadTypeEvent event) {
        roadLitTypeDialog.setTypeList(event.getRoadTypeInfoList());
    }

    /**
     * 显示线路类型对话框
     *
     * @param event
     */
    public void onEventMainThread(ShowRoadTypeEvent event) {

        if (null == roadLitTypeDialog) {
            return;
        }

        roadLitTypeDialog.show();
        //修改箭头方向
        iv_road_list_type.setImageDrawable(getResources().getDrawable(R.drawable.ic_home_topic_up));
    }

    /**
     * 定位回调
     *
     * @param event
     */
    public void onEventMainThread(RoadLocationEvent event) {
        //定位成功
        if (null != event.getaMapLocation()) {
            String cityName = event.getaMapLocation().getCity();
            if (!TextUtils.isEmpty(cityName) && cityName.endsWith("市")) {
                cityName = cityName.replace("市", "");
            }
            //首次定位 保存 数据
            if (StringUtil.isEmpty(roadListPresenter.getCity_name())) {
                tv_action_city_name.setText(cityName);
                SpTools.setString(mContext, "LASTCITY", cityName);//保存当前城市
                roadListPresenter.setCity_name(cityName);
                roadListPresenter.setCity_name_location(cityName);
                if (roadListPresenter.getRoadListDesList().size() > 0) {
                    roadListPresenter.resetCity_sulg(roadListPresenter.getCity_name());
                }
            } else {//不在当前城市 显示 对话框
                roadListPresenter.setCity_name_location(cityName);
            }
        } else {  //定位失败

        }

    }

    public void onEventMainThread(RoadTypeSelectedEvent event) {
        resetQuest();
        updateView(UiState.LOADING, null);
        roadListPresenter.getListData(event);
        roadListPresenter.setRequestAgain(true);
        //设置
    }


    /**
     * @param nowCity 提醒切换定位
     */
    public void showChangeCityDialog(final String nowCity) {


        TipUtil.alert(mContext, "切换到" + nowCity + "？", "是否切换到现在所在城市", new String[]{"取消", "切换"}, new OnBtnClickL() {
            @Override
            public void onBtnClick() {

            }
        }, new OnBtnClickL() {
            @Override
            public void onBtnClick() {
                // 切换到现在城市
                roadListPresenter.setCity_name(nowCity);
                tv_action_city_name.setText(nowCity);
                SpTools.setString(mContext, "LASTCITY", nowCity);
                //重新请求列表数据
                roadListPresenter.setCity_name_location(nowCity);
                roadListPresenter.resetCity_sulg(nowCity);
                //显示 加载
                resetQuest();
                updateView(UiState.LOADING, null);
                roadListPresenter.getListData(null);
                roadListPresenter.setRequestAgain(true);
            }
        });
    }

    private void onpenRoadSelectedActivity() {
        Intent intent = new Intent(getActivity(), RoadSelectCityActicity.class);
        Bundle bundle = new Bundle();
        bundle.putString("city", StringUtil.isEmpty(roadListPresenter.getCity_name_location()) ? "其他" : roadListPresenter.getCity_name_location());
        if (null != roadListPresenter.getRoadListDesList() && roadListPresenter.getRoadListDesList().size() > 0) {
            List<SelectCityModel> list = new ArrayList<>();
            for (RoadListDes des : roadListPresenter.getRoadListDesList()) {
                SelectCityModel model = new SelectCityModel();
                model.setCity_id(des.getCity_slug());
                model.setName(des.getCity_name());
                list.add(model);
            }
            bundle.putSerializable("roadListDesList", (Serializable) list);
        }

        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
    }


    private void resetQuest() {
        //重置界面
        roadListPresenter.setPage(1);
        listInfo.clear();
        roadListAdapter.notifyDataSetChanged();
        isLastPage = false;

    }


}
