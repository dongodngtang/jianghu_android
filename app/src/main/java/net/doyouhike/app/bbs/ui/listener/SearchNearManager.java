package net.doyouhike.app.bbs.ui.listener;

import android.content.Context;

import com.amap.api.location.AMapLocation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.poisearch.PoiSearch.Query;
import com.amap.api.services.poisearch.PoiSearch.SearchBound;

public class SearchNearManager {

    private Context context;


    /**
     * 搜索附近
     */
    private SearchNearResultListener nearListener;

    public SearchNearManager(Context context,
                             SearchNearResultListener nearListener) {
        this.context = context;
        this.nearListener = nearListener;
    }


    /**
     * 定位
     */
    public void requestLocationData(String keyword, String content, int currentPage, AMapLocation aMapLocation) {
        Query query = new Query(keyword, content, aMapLocation.getCityCode());
        // keyWord表示搜索字符串，第二个参数表示POI搜索类型，默认为：生活服务、餐饮服务、商务住宅
        //共分为以下20种：汽车服务|汽车销售|
        //汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        //住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        //金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        //cityCode表示POI搜索区域，（这里可以传空字符串，空字符串代表全国在全国范围内进行搜索）
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(currentPage);//设置查第一页
        PoiSearch poiSearch = new PoiSearch(context, query);
        poiSearch.setBound(new SearchBound(new LatLonPoint(aMapLocation.getLatitude(),
                aMapLocation.getLongitude()), 6 * 1000));//设置周边搜索的中心点以及区域
        poiSearch.setOnPoiSearchListener(nearListener);//设置数据返回的监听器
        poiSearch.searchPOIAsyn();//开始搜索
    }


    /**
     * 查询附近地点 使用高德接口
     *
     * @param latitude
     * @param longitude
     * @param cityCode
     */
    public void queryNearList(String keyword, double latitude,
                              double longitude, String cityCode) {
        Query query = new Query(keyword, "", cityCode);
        // keyWord表示搜索字符串，
        // 第二个参数表示POI搜索类型，二者选填其一，
        // POI搜索类型共分为以下20种：汽车服务|汽车销售|
        // 汽车维修|摩托车服务|餐饮服务|购物服务|生活服务|体育休闲服务|医疗保健服务|
        // 住宿服务|风景名胜|商务住宅|政府机构及社会团体|科教文化服务|交通设施服务|
        // 金融保险服务|公司企业|道路附属设施|地名地址信息|公共设施
        // cityCode表示POI搜索区域的编码，是必须设置参数
        query.setPageSize(20);// 设置每页最多返回多少条poiitem
        query.setPageNum(0);// 设置查询页码
        PoiSearch poiSearch = new PoiSearch(context, query);// 初始化poiSearch对象
        poiSearch.setOnPoiSearchListener(nearListener);// 设置回调数据的监听器
        poiSearch.setBound(new SearchBound(
                new LatLonPoint(latitude, longitude), 1000));// 设置周边搜索的中心点以及区域

        poiSearch.searchPOIAsyn();// 开始搜索

    }


    public SearchNearResultListener getNearListener() {
        return nearListener;
    }

    public void setNearListener(SearchNearResultListener nearListener) {
        this.nearListener = nearListener;
    }
}
