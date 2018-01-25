package net.doyouhike.app.bbs.ui.release.map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.PoiItem;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.LocationInfo;
import net.doyouhike.app.bbs.biz.event.SearchNearLocationEvent;
import net.doyouhike.app.bbs.ui.listener.SearchNearManager;
import net.doyouhike.app.bbs.ui.listener.SearchNearResultListener;
import net.doyouhike.app.bbs.ui.release.yueban.destination.SelectDestActivity;
import net.doyouhike.app.bbs.util.AmapLocationUtil;
import net.doyouhike.app.bbs.util.CommonUtil;
import net.doyouhike.app.bbs.util.GetCityIDUtils;
import net.doyouhike.app.bbs.util.InputTools;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.library.ui.uistate.UiState;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 作者:luochangdong on 16/5/27 10:05
 * 描述:
 */
public class PresenterSearchInMap {

    private static final String TAG = "PresenterSearchInMap";
    /**
     * 启动系统位置服务设置的邀请码
     */
    protected static final int REQUEST_CODE_TO_OPEN_LOCATION_SERVICE = 1601;
    /**
     * 服务类
     */
    SearchInMapActivity baseActivity;

    /**
     * 是否已经打开了定位服务
     */
    private boolean isOpenLocation = false;

    /**
     *
     */
    private boolean isRequestData = true;


    /**
     * 定位到的城市名称，还没定位时值为null
     */
    private LocationInfo city;

    /**
     * 定位的参数
     */
    private AMapLocation aMapLocation;

    /**
     * 定位到的附近地点信息
     */
    private List<LocationInfo> nearsData = new ArrayList<>();

    /**
     * 定位和搜索附近实现类
     */
    private SearchNearManager searchManager;

    /**
     * 定位和搜索附近结果监听类
     */
    private SearchNearResultListener locationListener;
    /**
     * 高德地图定位工具类
     */
    private AmapLocationUtil mapLocationUtil;
    /**
     * 等待提示框
     */
    private ProgressDialog dialog;
    /**
     * 搜索附近列表的适配器
     */
    private SearchInMapAdapter nearsAdapter;
    /**
     * 是否已经获取了附近信息
     */
    private boolean isGetNearsSuccess = false;

    /**
     * 搜索关键字
     */
    private String keyword = "";


    public PresenterSearchInMap(SearchInMapActivity activity) {
        baseActivity = activity;
        isOpenLocation();
        init();
    }


    private String getSearchWord(){
        return baseActivity.etActivityKeyWord.getText().toString();
    }

    /**
     * 初始化 控件 数据
     */
    private void init(){
        nearsAdapter = new SearchInMapAdapter(baseActivity, nearsData);
        baseActivity.lvSearchInMapList.setAdapter(nearsAdapter);
        etSearchInMapListClick();
        etSearchBtn();
        tvCancelClick();

    }

    /**
     * 取消点击
     */
    private void tvCancelClick() {
        baseActivity.tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                baseActivity.finish();
                SelectDestActivity.Instance.finish();
            }
        });
    }

    /**
     * ListView 点击事件
     */
    private void etSearchInMapListClick() {
        baseActivity.lvSearchInMapList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LocationInfo info = nearsAdapter.getItem(position);
//                StringBuilder sb =new StringBuilder();
//                sb.append(city.getCity())
//                        .append("·")
//                        .append(info.getLocationName());
//                info.setLocationName(sb.toString());
                EventBus.getDefault().post(info);
                baseActivity.finish();
                SelectDestActivity.Instance.finish();
            }
        });
    }

    /**
     * 搜索
     */
    private void etSearchBtn() {
        baseActivity.etActivityKeyWord.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    InputTools.HideKeyboard(baseActivity.etActivityKeyWord);
                    keyword = getSearchWord();
                    if (StringUtil.isNotEmpty(keyword)&& aMapLocation != null) {
                        nearsAdapter.setKeyword(keyword);
                        searchManager.requestLocationData(keyword,"", 0,aMapLocation);
                        baseActivity.updateView(UiState.LOADING);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * 判断是否打开定位服务
     */
    private void isOpenLocation() {
        isOpenLocation = CommonUtil.isOpenLocationService(baseActivity);
        if (isOpenLocation) {
            baseActivity.updateView(UiState.NORMAL);
        } else {
            baseActivity.updateView(UiState.CUSTOM.setMsg("无法获取位置", "请允许磨房访问位置信息", "开启定位")
                    .setImgId(R.drawable.icon_radar_disable), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                    baseActivity.startActivityForResult(intent, REQUEST_CODE_TO_OPEN_LOCATION_SERVICE);
                }
            });
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_TO_OPEN_LOCATION_SERVICE) {
            requestData();
        }
    }

    /**
     * 进行定位，请求获取附近地点的数据
     */
    private void requestData() {
        isOpenLocation();
        if (isOpenLocation && isRequestData) {
            // TODO 搜索附近地点
            if (searchManager == null) {
                searchManager = new SearchNearManager(baseActivity,
                        getLocationListener());
            }
            //开始定位当前城市
            startLocation();
            showWaitDialog();
        }
    }

    /**
     * 加载Loading
     */
    private void showWaitDialog() {
        // TODO 暂时使用原生dialog
        if (dialog == null) {
            dialog = new ProgressDialog(baseActivity);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setIndeterminate(false);
            dialog.setMessage("定位中..."); //TODO
        }
        dialog.show();
    }

    /**
     * 更新附近地点的数据显示
     *
     * @param items 新的附近地点数据
     */
    public void updateNears(List<PoiItem> items, LocationInfo cityInfo) {

        if (items != null) {
            if(items.size()>0){
                baseActivity.updateView(UiState.NORMAL);
                nearsData.clear();
                LocationInfo info;
                for (PoiItem item : items) {
                    info = getLocationInfo(item);
                    nearsData.add(info);
                }
                cityInfo.setLocation(cityInfo.getCity());
                nearsData.add(0, cityInfo);
                nearsAdapter.notifyDataSetChanged();
            }else{
                baseActivity.updateView(UiState.CUSTOM.setMsg("无结果","地图中未找到此地点")
                        .setImgId(R.drawable.icon_search_empty));
            }

        }

    }


    private LocationInfo getLocationInfo(PoiItem poiItem) {
        LocationInfo info = new LocationInfo();
        if (city != null) {
            info.setAltitude(city.getAltitude());
        }
        info.setCity(poiItem.getCityName());
        info.setCityCode(poiItem.getCityCode());
        info.setLatitude(poiItem.getLatLonPoint().getLatitude());
        if (poiItem.getTitle().equals(poiItem.getCityName())) {
            info.setLocation(poiItem.getCityName());
        } else {
            info.setLocation(poiItem.getTitle());
        }

        info.setLongitude(poiItem.getLatLonPoint().getLongitude());
        info.setPoiId(poiItem.getPoiId());
        int cityId = GetCityIDUtils.getCityID(baseActivity, poiItem.getCityName());
        info.setCity_id(cityId + "");
        info.setSnippet(poiItem.getSnippet());
        return info;
    }

    private void dismissWaitDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 搜索附近
     */
    private void searchNear() {
        isOpenLocation();
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
            locationListener = new SearchNearResultListener(baseActivity,
                    new SearchNearResultListener.OnSearchResultListener() {
                        @Override
                        public void onSearchNearSucceed(List<PoiItem> pois) {
                            updateNears(pois, city);
                            dismissWaitDialog();
                            isRequestData = false;
                            isGetNearsSuccess = true;
                            LogUtil.d(TAG,"onSearchNearSucceed");

                        }

                        @Override
                        public void onSearchNearFailed() {
                            baseActivity.showToast(R.string.search_near_fail);
                            searchNear();
                            LogUtil.d(TAG,"onSearchNearFailed");
                        }

                        @Override
                        public void onLocationSucceed(LocationInfo info) {
                            LogUtil.d(TAG,"onLocationSucceed");
                            city = info;
                            if (city != null) {
                                searchNear();
                            } else {
                                baseActivity.showToast(R.string.location_fail);
                                requestData();
                            }
                        }

                        @Override
                        public void onLocationFailed(String erreMsg) {
                            LogUtil.d(TAG,"onLocationFailed");
                            if (UIUtils.isShowToast) {
                                baseActivity.showToast("定位失败，errorMsg = " + erreMsg);
                            } else {
                                baseActivity.showToast(R.string.location_fail);
                            }
                            requestData();
                        }
                    });
        }
        return locationListener;
    }

    /**
     * 响应搜索附近的信息
     *
     * @param event
     */
    public void onEventSearchNearLocation(SearchNearLocationEvent event) {
        if (null != event && null != event.getaMapLocation()) {
            if (null == dialog) {
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


            aMapLocation = event.getaMapLocation();

            //开始搜索附近
            searchManager.requestLocationData(keyword, "生活服务|餐饮服务|商务住宅", 0, event.getaMapLocation());
        } else {
            baseActivity.showToast("定位失败，请重新打开该页面");
        }
    }

    public void onResumeEvent() {
        if (!isGetNearsSuccess) {
            requestData();
            //定位当前城市

        }
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
        mapLocationUtil.startLocation(true, 0l);
    }

    /**
     * Activity传值
     * @param extras
     */
    public void getBundleExtras(Bundle extras) {
        if(extras != null && extras.getString(SelectDestActivity.SEARCH_WORD) != null){
            keyword = extras.getString(SelectDestActivity.SEARCH_WORD);
            baseActivity.etActivityKeyWord.setText(keyword);
            nearsAdapter.setKeyword(keyword);
        }
    }
}
