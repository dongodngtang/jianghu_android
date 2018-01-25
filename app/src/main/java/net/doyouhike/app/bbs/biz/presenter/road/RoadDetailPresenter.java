package net.doyouhike.app.bbs.biz.presenter.road;

import android.content.Context;

import com.amap.api.maps2d.model.LatLng;

import net.doyouhike.app.bbs.base.util.BaseAsyncTask;
import net.doyouhike.app.bbs.base.util.BaseAsyncTaskResult;
import net.doyouhike.app.bbs.base.util.BasePresenter;
import net.doyouhike.app.bbs.biz.entity.road.MapPoint;
import net.doyouhike.app.bbs.biz.entity.road.RoadDetailInfo;
import net.doyouhike.app.bbs.biz.entity.road.RoadDetailMaps;
import net.doyouhike.app.bbs.biz.event.road.BaseRoadDetailMapEvent;
import net.doyouhike.app.bbs.biz.event.road.RoadDetailInfoEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.RouteHelper;
import net.doyouhike.app.bbs.util.StringUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * 线路详情
 * Created by terry on 5/11/16.
 */
public class RoadDetailPresenter extends BasePresenter {


    public RoadDetailPresenter(Context context) {
        super(context);
    }

    /**
     * 获取线路详情 信息
     *
     * @param city_slug
     */
    public void getRoadInfo(String city_slug) {

        IOnResponseListener listener = new IOnResponseListener<Response<RoadDetailInfo>>() {
            RoadDetailInfoEvent event = new RoadDetailInfoEvent();

            @Override
            public void onSuccess(Response<RoadDetailInfo> response) {
                event.setInfo(response.getData());
                postEvent(event);
            }

            @Override
            public void onError(Response response) {
                event.setSuccess(false);
                postEvent(event);
            }
        };
        RouteHelper.getInstance().destsRoutesSlugDetail(context, city_slug, listener);
    }

    /**
     * 获取线路详情 轨迹
     *
     * @param city_slug
     */
    public void getRoadInfoMap(String city_slug, final BaseRoadDetailMapEvent event) {

        IOnResponseListener listener = new IOnResponseListener<Response<RoadDetailMaps>>() {

            @Override
            public void onSuccess(Response<RoadDetailMaps> response) {
                handerRoadMapInfo(response.getData(), event);
            }

            @Override
            public void onError(Response response) {
                postEvent(new BaseRoadDetailMapEvent());
            }
        };

        RouteHelper.getInstance().destsRouteMaps(context, city_slug, listener);

    }

    public void handerRoadMapInfo(final RoadDetailMaps maps, final BaseRoadDetailMapEvent event) {
        BaseAsyncTask bt = new BaseAsyncTask(context) {
            @Override
            protected BaseAsyncTaskResult doInBackgroundInner(Object[] var1) throws Throwable {
                BaseAsyncTaskResult br = new BaseAsyncTaskResult();
                double maxLat = 0.0, minLat = 0.0, maxLng = 0.0, minLng = 0.0;
                List<LatLng> list = new ArrayList<>();
                int i = 0;
                //计算 中心点 最大经纬度 最小经纬度 中间值 为 中心 点
                for (MapPoint point : maps.getMap_points()) {
                    if (StringUtil.isNotEmpty(point.getLat()) && StringUtil.isNotEmpty(point.getLng())) {
                        Double lat = Double.valueOf(point.getLat());
                        Double lng = Double.valueOf(point.getLng());
                        //首次 赋值
                        if (i == 0) {
                            minLat = maxLat = lat;
                            maxLng = minLng = lng;
                            i++;
                        }
                        if (maxLat < lat)
                            maxLat = lat;
                        if (minLat > lat)
                            minLat = lat;
                        if (maxLng < lng)
                            maxLng = lng;
                        if (minLng > lng)
                            minLng = lng;
                        list.add(new LatLng(lat, lng));
                    }
                }
                event.setRoadDetailMaps(maps);
                event.setCenterLatLng(new LatLng((maxLat + minLat) / 2, (maxLng + minLng) / 2));
                event.setPointList(list);
                br.setResult(event);
                return br;
            }

            @Override
            protected void onPostExecuteSuccess(BaseAsyncTaskResult var1) {
                postEvent(var1.getResult());
            }
        };
        bt.execute();
    }


    /**
     * 处理季节 数据
     *
     * @return
     */
    public String formatSeason(String season) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("");
        if (season.contains("1")) {
            stringBuffer.append("春");
            stringBuffer.append("/");
        }
        if (season.contains("2")) {
            stringBuffer.append("夏");
            stringBuffer.append("/");
        }
        if (season.contains("3")) {
            stringBuffer.append("秋");
            stringBuffer.append("/");
        }
        if (season.contains("4")) {
            stringBuffer.append("冬");
            stringBuffer.append("/");
        }
        String result = stringBuffer.toString();
        if (result.length() > 1)
            result = result.substring(0, result.length() - 1);
        return result;
    }

    /**
     * 处理特色标签
     *
     * @return
     */
    public String formatTags(List<RoadDetailInfo.TagsBean> tagList) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("");
        for (RoadDetailInfo.TagsBean tag : tagList) {
            stringBuffer.append(tag.getTag_name());
            stringBuffer.append("/");
        }
        String result = stringBuffer.toString();
        if (result.length() > 1)
            result = result.substring(0, result.length() - 1);
        return result;
    }

    ;

    /**
     * 处理营地 设施
     *
     * @return
     */
    public String formatCampsites(List<RoadDetailInfo.Campsite> campsiteList) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("");
        for (RoadDetailInfo.Campsite campsite : campsiteList) {
            stringBuffer.append(campsite.getCampsite_name());
            stringBuffer.append("/");
        }
        String result = stringBuffer.toString();
        if (result.length() > 1)
            result = result.substring(0, result.length() - 1);
        return result;
    }

    ;


}
