package net.doyouhike.app.bbs.biz.presenter.road;

import android.content.Context;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.base.util.BasePresenter;
import net.doyouhike.app.bbs.biz.entity.road.MapPoint;
import net.doyouhike.app.bbs.biz.event.road.BaseRoadDetailMapEvent;
import net.doyouhike.app.bbs.biz.event.road.RoadDetailMapViewEvent;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 线路详情  地图
 * Created by terry on 5/11/16.
 */
public class RoadDetailMapPresenter extends BasePresenter {

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;


    public RoadDetailMapPresenter(Context context) {
        super(context);
    }

    /**
     * 判断数据来源
     * @param city_slug
     * @param event
     */
    public void getRoadMapData(String city_slug,BaseRoadDetailMapEvent event){
        if(null!= event) {
            RoadDetailMapViewEvent roadDetailMapViewEvent = new RoadDetailMapViewEvent();
            roadDetailMapViewEvent.setPointList(event.getPointList());
            roadDetailMapViewEvent.setCenterLatLng(event.getCenterLatLng());
            roadDetailMapViewEvent.setRoadDetailMaps(event.getRoadDetailMaps());
            postEvent(roadDetailMapViewEvent);
        }
        //如果 无数据 重新 从网络获取
        else {
            new RoadDetailPresenter(context).getRoadInfoMap(city_slug,new RoadDetailMapViewEvent());
        }
    }

    public void startLocation(AMapLocationListener locationListener,long time){
            initParam(locationListener,time);
            mLocationClient.startLocation();
    }


    private void initParam(AMapLocationListener locationListener,long time) {
        //初始化定位
        mLocationClient = new AMapLocationClient(MyApplication.getInstance().getApplicationContext());
        mLocationClient.setLocationListener(locationListener);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        mLocationOption.setInterval(time);
        mLocationClient.setLocationOption(mLocationOption);
    }

    public void stopLocation(){
        try{
            if(null!=mLocationClient && mLocationClient.isStarted())
                mLocationClient.stopLocation();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void destoryLocation(){
        try{
            if(null!=mLocationClient ){
                if(mLocationClient.isStarted())
                    mLocationClient.stopLocation();
                mLocationClient.onDestroy();
            }


                ;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private List<MapPoint> getMaps(){
        List<MapPoint> list = new ArrayList<>();
        list.add(getPonit("23.131195","113.387665"));
        list.add(getPonit("23.131134","113.387772"));
        list.add(getPonit("23.131065","113.38784"));
        list.add(getPonit("23.131018","113.387932"));
        list.add(getPonit("23.130995","113.388054"));
        list.add(getPonit("23.130978","113.388153"));
        list.add(getPonit("23.130945","113.38829"));
        list.add(getPonit("23.13092","113.388397"));
        list.add(getPonit("23.130859","113.388519"));
        list.add(getPonit("23.130842","113.388618"));
        list.add(getPonit("23.130793","113.388702"));
        list.add(getPonit("23.130741","113.388809"));
        list.add(getPonit("23.130709","113.388947"));
        list.add(getPonit("23.130678","113.389053"));
        list.add(getPonit("23.130659","113.389168"));
        list.add(getPonit("23.130642","113.389267"));
        list.add(getPonit("23.130611","113.389366"));
        list.add(getPonit("23.130615","113.389465"));
        list.add(getPonit("23.130623","113.389572"));
        list.add(getPonit("23.130621","113.389702"));
        list.add(getPonit("23.130611","113.389793"));
        list.add(getPonit("23.130602","113.3899"));
        list.add(getPonit("23.130585","113.389999"));
        list.add(getPonit("23.130556","113.390137"));
        return list;
    };
    private MapPoint getPonit(String  Lat,String Lng){
        MapPoint maps = new MapPoint();
        maps.setLat(Lat);
        maps.setLng(Lng);
        return maps;
    }


}
