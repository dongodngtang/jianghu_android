package net.doyouhike.app.bbs.ui.listener;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiItemDetail;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener;

import net.doyouhike.app.bbs.biz.entity.LocationInfo;

import java.util.List;

public class SearchNearResultListener implements AMapLocationListener,
        OnPoiSearchListener {

    private Context context;
    private OnSearchResultListener onSearchResultListener;

    public SearchNearResultListener(Context context,
                                    OnSearchResultListener onSearchResultListener) {
        this.context = context;
        this.onSearchResultListener = onSearchResultListener;
    }


    @Override
    public void onPoiItemDetailSearched(PoiItemDetail arg0, int arg1) {
    }

    @Override
    public void onPoiSearched(PoiResult result, int arg1) {
        if (result != null) {
            List<PoiItem> pois = result.getPois();
            onSearchResultListener.onSearchNearSucceed(pois);
        } else {
            onSearchResultListener
                    .onSearchNearFailed();
        }
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation == null) {
            return;
        }

        if (amapLocation.getErrorCode() == 0) {
            LocationInfo info = new LocationInfo();
            info.setAltitude(amapLocation.getAltitude());
            info.setCity(amapLocation.getCity());
            info.setCityCode(amapLocation.getCityCode());
            info.setLatitude(amapLocation.getLatitude());
            info.setLongitude(amapLocation.getLongitude());
            info.setPoiId(amapLocation.getPoiName());
            onSearchResultListener.onLocationSucceed(info);
        } else {
            onSearchResultListener.onLocationFailed(amapLocation.getErrorInfo());
        }
    }

    public OnSearchResultListener getOnSearchResultListener() {
        return onSearchResultListener;
    }

    public void setOnSearchResultListener(
            OnSearchResultListener onSearchResultListener) {
        this.onSearchResultListener = onSearchResultListener;
    }

    public interface OnSearchResultListener {
        /**
         * 定位成功
         *
         * @param info 定位信息
         */
        public void onLocationSucceed(LocationInfo info);

        /**
         * 定位失败
         *
         * @param erreMsg 定位失败原因
         */
        public void onLocationFailed(String erreMsg);

        /**
         * 搜索附近成功
         *
         * @param pois
         */
        public void onSearchNearSucceed(List<PoiItem> pois);

        /**
         * 搜索附近失败
         *
         * @param pois
         */
        public void onSearchNearFailed();
    }

}
