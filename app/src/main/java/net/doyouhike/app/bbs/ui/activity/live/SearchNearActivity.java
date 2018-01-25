/*
 * -----------------------------------------------------------------
 * Copyright (C) 2013-2015, by Gemo Info., All rights reserved.
 * -----------------------------------------------------------------
 *
 * File: SearchNearActivity.java
 * Author: wu-yoline(伍建鹏)
 * Version: 1.0
 * Create: 2015-10-8
 *
 * Changes (from 2015-10-8)
 * -----------------------------------------------------------------
 * 2015-10-8 : 	1、创建本类，实现父类onCreate方法(wu-yoline);
 * 				2、调用父类setLayoutId()设置布局文件(wu-yoline)
 * -----------------------------------------------------------------
 */
package net.doyouhike.app.bbs.ui.activity.live;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.services.core.PoiItem;
import com.google.gson.Gson;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.LocationInfo;
import net.doyouhike.app.bbs.biz.event.SearchNearLocationEvent;
import net.doyouhike.app.bbs.ui.activity.BaseActivity;
import net.doyouhike.app.bbs.ui.adapter.action.SearchNearResultAdapter;
import net.doyouhike.app.bbs.ui.adapter.action.SearchNearResultAdapter.OnClickItemListener;
import net.doyouhike.app.bbs.ui.listener.SearchNearManager;
import net.doyouhike.app.bbs.ui.listener.SearchNearResultListener;
import net.doyouhike.app.bbs.ui.listener.SearchNearResultListener.OnSearchResultListener;
import net.doyouhike.app.bbs.util.AmapLocationUtil;
import net.doyouhike.app.bbs.util.CommonUtil;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索附近界面
 *
 * @author wu-yoline
 */
public class SearchNearActivity extends BaseActivity {

    /**
     * 该activity结束的时候使用setResult(...)方法时的Intent的key，
     * 对应的值是用户选择了的附近地点LocationInfo对象的json字符串
     */
    public static final String INTENT_KEY_SELECTED_RESULT = "selected_result_near";
    /**
     * 使用setResult(...)方法时的Intent的结果码
     */
    public static final int RESULT_CODE_SELECTED_NEAR = 1733;

    /**
     * 启动搜索界面的时候的邀请码
     */
    public static final int REQUEST_CODE_TO_SEARCH_NEARS = 1819;

    /**
     * 启动系统位置服务设置的邀请码
     */
    protected static final int REQUEST_CODE_TO_OPEN_LOCATION_SERVICE = 1601;

    /**
     * 没有开启定位时要显示的布局
     */
    private LinearLayout llytNoLocation;
    /**
     * 开启了定位时要显示的布局
     */
    private LinearLayout llytHasLocation;
    /**
     * 定位到的城市名称
     */
    private TextView tvCity;
    /**
     * 定位到的附近地点
     */
    private ListView lvLocations;

    /**
     * 是否已经打开了定位服务
     */
    private boolean isOpenLocation = false;

    /**
     * 定位到的城市名称，还没定位时值为null
     */
    private LocationInfo city;

    /**
     * 定位到的附近地点信息
     */
    private List<PoiItem> nears;

    /**
     * 定位和搜索附近实现类
     */
    private SearchNearManager searchManager;

    /**
     * 定位和搜索附近结果监听类
     */
    private SearchNearResultListener locationListener;

    /**
     * 搜索附近列表的适配器
     */
    private SearchNearResultAdapter nearsAdapter;

    /**
     * 等待提示框
     */
    private ProgressDialog dialog;

    private boolean isRequestData = true;

    /**
     * 是否已经获取了附近信息
     */
    private boolean isGetNearsSuccess = false;

    /**
     * 高德地图定位工具类
     */
    private AmapLocationUtil mapLocationUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.setLayoutId(R.layout.activity_search_near);
        super.onCreate(savedInstanceState);

        initData();

        findView();

        initView();
    }

    /**
     * 进行定位，请求获取附近地点的数据
     */
    private void requestData() {
        boolean isOpenLocation = CommonUtil.isOpenLocationService(this);
        updateBody(isOpenLocation);
        if (isOpenLocation && isRequestData) {
            // TODO 搜索附近地点
            if (searchManager == null) {
                searchManager = new SearchNearManager(this,
                        getLocationListener());
            }
            //开始定位当前城市
            if (null == mapLocationUtil) {
                mapLocationUtil = new AmapLocationUtil(new SearchNearLocationEvent());
            }
            mapLocationUtil.startLocation(false, 0l);
            showWaitDialog();
        }
    }

    private void showWaitDialog() {
        // TODO 暂时使用原生dialog
        if (dialog == null) {
            dialog = new ProgressDialog(this);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setIndeterminate(false);
            dialog.setMessage("定位中..."); //TODO
        }
        dialog.show();
    }

    private void dismissWaitDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void searchNear() {
        boolean isOpenLocation = CommonUtil.isOpenLocationService(this);
        updateBody(isOpenLocation);
        if (isOpenLocation) {
            if (city != null) {
                searchManager.queryNearList("", city.getLatitude(),
                        city.getLongitude(), city.getCityCode());
            } else {
                requestData();
            }
        }
    }

    /**
     * 获取定位和搜索附近成功和失败的监听类
     *
     * @return
     */
    private SearchNearResultListener getLocationListener() {
        if (locationListener == null) {
            locationListener = new SearchNearResultListener(this,
                    new OnSearchResultListener() {
                        @Override
                        public void onSearchNearSucceed(List<PoiItem> pois) {
                            updateNears(pois, city);
                            dismissWaitDialog();
                            isRequestData = false;
                            isGetNearsSuccess = true;
                        }

                        @Override
                        public void onSearchNearFailed() {
                            makeToast(R.string.search_near_fail);
                            searchNear();
                        }

                        @Override
                        public void onLocationSucceed(LocationInfo info) {
                            city = info;
                            if (city != null) {
                                updateCityName(city);
                                searchNear();
                            } else {
                                makeToast(R.string.location_fail);
                                requestData();
                            }
                        }

                        @Override
                        public void onLocationFailed(String erreMsg) {
                            if (UIUtils.isShowToast) {
                                makeToast("定位失败，errorMsg = " + erreMsg);
                            } else {
                                makeToast(R.string.location_fail);
                            }
                            requestData();
                        }
                    });
        }
        return locationListener;
    }

    @Override
    public void onResume() {
        if (!isGetNearsSuccess) {
            requestData();
            //定位当前城市
            startLocation();
        }
        super.onResume();
    }

    /**
     * 初始化本类的属性
     */
    private void initData() {
        isOpenLocation = CommonUtil
                .isOpenLocationService(SearchNearActivity.this);
        nears = new ArrayList<PoiItem>();
    }

    private void updateCityName(LocationInfo city) {
        if (city != null && tvCity != null) {
            this.city = city;
            tvCity.setText(city.getCity());
        }
    }

    /**
     * 初始化组件
     */
    private void initView() {
        initSearchBtn();
        initOpenSetting();
        initBody();
        tvCity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                city.setLocation(city.getCity());
                clickItem(city);
            }
        });
    }

    /**
     * 初始化顶部栏下的所有内容
     */
    private void initBody() {
        if (isOpenLocation) {
            llytHasLocation.setVisibility(View.VISIBLE);
            llytNoLocation.setVisibility(View.GONE);
        } else {
            llytHasLocation.setVisibility(View.GONE);
            llytNoLocation.setVisibility(View.VISIBLE);
        }
        initNearContent();
    }

    /**
     * 初始化顶部栏下的所有内容
     */
    private void updateBody(boolean isOpenLocation) {
        this.isOpenLocation = isOpenLocation;
        if (isOpenLocation) {
            llytHasLocation.setVisibility(View.VISIBLE);
            llytNoLocation.setVisibility(View.GONE);
        } else {
            llytHasLocation.setVisibility(View.GONE);
            llytNoLocation.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 定位前对附近的内容信息进行初始化
     */
    private void initNearContent() {
        if (nears == null) {
            nears = new ArrayList<PoiItem>();
        }
        nearsAdapter = new SearchNearResultAdapter(SearchNearActivity.this,
                nears, new OnClickItemListener() {
            @Override
            public void onclickItem(LocationInfo info) {
                clickItem(info);
            }

        }, city);
        lvLocations.setAdapter(nearsAdapter);
    }
    private void clickItem(LocationInfo info) {
        Intent intent = new Intent();
        String json = getInfoJson(info);
        intent.putExtra(INTENT_KEY_SELECTED_RESULT, json);
        SearchNearActivity.this.setResult(
                RESULT_CODE_SELECTED_NEAR, intent);
        SearchNearActivity.this.finish();
    }


    private String getInfoJson(LocationInfo info) {
        String json = null;
        try {
            json = new Gson().toJson(info);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }
    /**
     * 更新附近地点的数据显示
     *
     * @param infos 新的附近地点数据
     */
    public void updateNears(List<PoiItem> infos, LocationInfo cityInfo) {
        this.nears = infos;
        updateCityName(cityInfo);
        nearsAdapter.setLocations(infos);
        nearsAdapter.setCityInfo(cityInfo);
        nearsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 初始化还没打开位置服务时显示的跳到系统设置界面的按钮
     */
    private void initOpenSetting() {
        TextView tvOpenLocation = (TextView) this
                .findViewById(R.id.tv_open_location);
        tvOpenLocation.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult((new Intent(
                                "android.settings.LOCATION_SOURCE_SETTINGS")),
                        REQUEST_CODE_TO_OPEN_LOCATION_SERVICE);
            }
        });
    }

    /**
     * 初始化顶部跳到搜索界面的按钮
     */
    private void initSearchBtn() {
        TextView tvSearch = (TextView) this
                .findViewById(R.id.tv_search_near_location);
        tvSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city != null) {
                    Intent intent = new Intent(SearchNearActivity.this,
                            ToSearchActivity.class);
                    intent.putExtra(
                            ToSearchActivity.INTENT_EXTRA_NAME_SEARCH_TYPE_INT,
                            ToSearchActivity.SEARCH_NEAR);
                    intent.putExtra(
                            ToSearchActivity.INTENT_KEY_CITY_LOCATIONINFO,
                            new Gson().toJson(city));
                    SearchNearActivity.this.startActivityForResult(intent,
                            REQUEST_CODE_TO_SEARCH_NEARS);
                } else {
                    makeToast(R.string.please_wait_location_finish);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_TO_SEARCH_NEARS
                && resultCode == ToSearchActivity.RESULT_CODE_SELECTED_NEAR) {
            SearchNearActivity.this.setResult(RESULT_CODE_SELECTED_NEAR, data);
            SearchNearActivity.this.finish();
        } else if (requestCode == REQUEST_CODE_TO_OPEN_LOCATION_SERVICE) {
            requestData();
        }
    }

    /**
     * 绑定组件
     */
    private void findView() {
        llytNoLocation = (LinearLayout) this
                .findViewById(R.id.llyt_not_location);
        llytHasLocation = (LinearLayout) this
                .findViewById(R.id.llyt_have_location);
        tvCity = (TextView) this.findViewById(R.id.tv_city);
        lvLocations = (ListView) this.findViewById(R.id.lv_location_list);
    }

    /**
     * TODO:定位当前城市
     * 日期: 3/30/16
     *
     * @author zhulin
     */
    private void startLocation() {
        if (null == mapLocationUtil)
            mapLocationUtil = new AmapLocationUtil(new SearchNearLocationEvent());
        mapLocationUtil.startLocation(false, 0l);
    }

    /**
     * 地图定位回调 定位完成后开始搜索附近
     *
     * @param event
     */
    public void onEvent(SearchNearLocationEvent event) {
        if (null != event && null != event.getaMapLocation()) {
            if(null==dialog){
                if (dialog.isShowing()) {
                    dialog.setMessage("搜索中...");
                } else
                    dialog.show();
            }


            if (null == city) {
                city = new LocationInfo();
            }
            city.setAltitude(event.getaMapLocation().getAltitude());
            city.setCity(event.getaMapLocation().getCity());
            city.setCityCode(event.getaMapLocation().getCityCode());
            city.setLatitude(event.getaMapLocation().getLatitude());
            city.setLongitude(event.getaMapLocation().getLongitude());
            city.setPoiId(event.getaMapLocation().getPoiName());

            //开始搜索附近
//            searchManager.requestLocationData("", "生活服务|餐饮服务|商务住宅", 0, event.getaMapLocation());
        } else {
            StringUtil.showSnack(this, "定位失败，请重新打开该页面");
        }
    }

}
