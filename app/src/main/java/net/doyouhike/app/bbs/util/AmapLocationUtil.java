package net.doyouhike.app.bbs.util;

import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.event.BaseLocationEvent;

import de.greenrobot.event.EventBus;

/**
 * 功能：定位工具类
 * 作者：曾江
 * 日期：16-1-14.
 */
public class AmapLocationUtil implements AMapLocationListener {

    public static final int EVENT_CODE = 1001;//高德定位回调编码


    private final String TAG_LOG = AmapLocationUtil.class.getSimpleName();

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;

    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;

    //eventbus 回调
    private BaseLocationEvent event;

    //是否循环定位
    private boolean isCirculation = false;

    private LocationChangeListener listener;

    public AmapLocationUtil(BaseLocationEvent event) {
        this.event = event;
        initParam();
    }

    public AmapLocationUtil(LocationChangeListener listener) {
        this.listener = listener;
        initParam();
    }

    private void initParam() {
        //初始化定位
        mLocationClient = new AMapLocationClient(MyApplication.getInstance().getApplicationContext());
        mLocationClient.setLocationListener(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        mLocationClient.setLocationOption(mLocationOption);
    }


    public void startLocation(boolean isCirculation, long time) {
        this.isCirculation = isCirculation;
        mLocationOption.setOnceLocation(isCirculation);
        if (!isCirculation)
            mLocationOption.setInterval(time);
        mLocationClient.startLocation();
    }


    public void stopLocation() {
        mLocationClient.stopLocation();
    }

    public void onDestory() {
        mLocationClient.onDestroy();
    }

    /**
     *
     * TODO: 定位 回调  目前只设置了 返回城市信息
     * 日期: 16-3-16
     * @author zhulin
     *
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        try {
            if (aMapLocation != null) {

                    if (aMapLocation.getErrorCode() == 0) {
                        if(isCirculation)
                            stopLocation();
                        if(null!= event){
                            event.setaMapLocation(aMapLocation);
                            EventBus.getDefault().post(event);
                        }
                        else {
                            this.onLocationChanged(aMapLocation);
                            listener.onLocationChanged(aMapLocation);
                        }

                    } else {
                        if(null!=event){
                            event.setaMapLocation(null);
                            EventBus.getDefault().post(event);
                        }
                        else {
                            this.onLocationChanged(aMapLocation);
                            listener.onLocationChanged(aMapLocation);
                        }

                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError","location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public interface LocationChangeListener {
        abstract void onLocationChanged(AMapLocation aMapLocation);
    }

}