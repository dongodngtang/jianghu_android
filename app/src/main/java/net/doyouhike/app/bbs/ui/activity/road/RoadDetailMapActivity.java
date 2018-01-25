package net.doyouhike.app.bbs.ui.activity.road;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.event.road.BaseRoadDetailMapEvent;
import net.doyouhike.app.bbs.biz.event.road.RoadDetailMapViewEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.road.FlagPoint;
import net.doyouhike.app.bbs.biz.presenter.road.RoadDetailMapPresenter;
import net.doyouhike.app.bbs.util.BitmapUtil;
import net.doyouhike.app.bbs.util.DensityUtil;
import net.doyouhike.app.bbs.util.StatisticsEventUtil;
import net.doyouhike.app.bbs.util.StringUtil;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * 线路详情
 * 2016.5.10
 * zhulin
 */
public class RoadDetailMapActivity extends BaseActivity implements View.OnClickListener,AMapLocationListener,LocationSource {

    private final String TAG = RoadDetailMapActivity.class.getSimpleName();


    @InjectView(R.id.rl_road_detail_back)
    LinearLayout rl_road_detail_back;

    @InjectView(R.id.mv_road_detail_map)
    MapView mv_road_detail_map;

    @InjectView(R.id.rl_road_detail_currentLocation)
    RelativeLayout rl_road_detail_currentLocation;



    private RoadDetailMapPresenter roadDetailMapPresenter;

    private String road_slug;

    /**
     * 营点路点
     */
    private ArrayList<FlagPoint> mFlagPoints;


    //当前定位
    private AMapLocation currentLocation;

    private OnLocationChangedListener mListener;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isSetSystemBar = false;
        super.onCreate(savedInstanceState);

       //setTranslucentStatus(true);
        //透明状态栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        mv_road_detail_map.onCreate(savedInstanceState);

        //mv_road_detail_map.getMap().setLocationSource(this);// 设置定位监听
        setUpMap();

        //友盟统计
        StatisticsEventUtil.collectRouteDetailMap(this);
    }



    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_road_detail_map;
    }


    @Override
    protected void initViewsAndEvents() {
        rl_road_detail_back.setOnClickListener(this);
        if(null==roadDetailMapPresenter)
            roadDetailMapPresenter = new RoadDetailMapPresenter(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        //获取路点营点信息
        mFlagPoints=bundle.getParcelableArrayList("roadDetailFlagPoints");
        road_slug = bundle.getString("road_slug","");// 获取定位的城市
        Parcelable parcelable = bundle.getParcelable("roadDetailMapEvent");
        if(null!=parcelable){
            roadDetailMapPresenter.getRoadMapData(road_slug,(BaseRoadDetailMapEvent) parcelable);
        } else
            roadDetailMapPresenter.getRoadMapData(road_slug,null);





        rl_road_detail_currentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(null!=currentLocation){
                    mListener.onLocationChanged(currentLocation);
                    Log.i(TAG," -----   --------   zoomBy    ---------------------------");
                    mv_road_detail_map.getMap().moveCamera(CameraUpdateFactory.newLatLng(new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude())));
                    mv_road_detail_map.getMap().moveCamera(CameraUpdateFactory.zoomBy(18f));
                }
                else {


                }
            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            mv_road_detail_map.onResume();
            mv_road_detail_map.getMap().setMyLocationEnabled(true);
            mv_road_detail_map.getMap().getUiSettings().setMyLocationButtonEnabled(false);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            deactivate();
            mv_road_detail_map.onPause();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    @Override
    public void finish() {
        super.finish();
        try {
            if (null != mv_road_detail_map) {
                Log.i(TAG,"        ------------ finish  Map   clear      --------------------------       ");
                mv_road_detail_map.destroyDrawingCache();
                mv_road_detail_map.getMap().clear();
                //mv_road_detail_map = null;
                System.gc();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            if(null != mv_road_detail_map)
                mv_road_detail_map.onDestroy();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        try{
            mv_road_detail_map.onSaveInstanceState(outState);
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void onEvent(RoadDetailMapViewEvent event){
        setFlagPoint();
        if(null!= event.getPointList() && event.getPointList().size()>0){
            //设置坐标
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.addAll(event.getPointList());
            polylineOptions.color(getResources().getColor(R.color.bg_road_detail_map_line));
            //polylineOptions.geodesic(true);
            polylineOptions.visible(true);
            polylineOptions.width(DensityUtil.dip2px(this,6));
            mv_road_detail_map.getMap().addPolyline(polylineOptions);

            //添加 起点终点 图片
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.visible(true);
            markerOptions.position(event.getPointList().get(0));
            Bitmap bitmap = BitmapUtil.readBitmapFromRes(this,R.drawable.ico_road_detail_map_start,DensityUtil.dip2px(this,21),
                    DensityUtil.dip2px(this,26));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            mv_road_detail_map.getMap().addMarker(markerOptions);

            MarkerOptions markerOptions2 = new MarkerOptions();
            markerOptions2.visible(true);
            markerOptions2.position(event.getPointList().get(event.getPointList().size() -1));
            Bitmap bitmap2 = BitmapUtil.readBitmapFromRes(this,R.drawable.ico_road_detail_map_stop,DensityUtil.dip2px(this,21),
                    DensityUtil.dip2px(this,26));
            markerOptions2.icon(BitmapDescriptorFactory.fromBitmap(bitmap2));
            mv_road_detail_map.getMap().addMarker(markerOptions2);

            //mv_road_detail_map.getMap().getUiSettings().setMyLocationButtonEnabled(true);
        }
        else {//轨迹不存在
            if(StringUtil.isNotEmptyObj(event.getCenterLatLng())){
                MarkerOptions markerOptions2 = new MarkerOptions();
                markerOptions2.visible(true);
                markerOptions2.position(event.getCenterLatLng());
                Bitmap bitmap2 = BitmapUtil.readBitmapFromRes(this,R.drawable.ico_road_detail_map_stop,DensityUtil.dip2px(this,21),
                        DensityUtil.dip2px(this,26));
                markerOptions2.icon(BitmapDescriptorFactory.fromBitmap(bitmap2));
                mv_road_detail_map.getMap().addMarker(markerOptions2);
            }

        }
        if(null!= event&&StringUtil.isNotEmptyObj(event.getCenterLatLng())){
            mv_road_detail_map.getMap().invalidate();
            mv_road_detail_map.getMap().moveCamera(CameraUpdateFactory.newLatLng(event.getCenterLatLng()));
            mv_road_detail_map.getMap().moveCamera(CameraUpdateFactory.zoomTo(12f));
        }

    }




    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return true;
    }


    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_road_detail_back:
                this.finish();
                break;
        }
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        try {
            if (aMapLocation != null) {
                currentLocation = aMapLocation;
                Log.i(TAG,"        ------------ aMapLocation  code      --------------------------       "+ aMapLocation.getErrorCode());
                if (aMapLocation.getErrorCode() == 0) {

                    if(!mv_road_detail_map.getMap().isMyLocationEnabled())
                        mv_road_detail_map.getMap().setMyLocationEnabled(true);
                    currentLocation = aMapLocation;
                    Log.i(TAG,"        ------------ aMapLocation  back      --------------------------       ");
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + aMapLocation.getErrorCode() + ", errInfo:"
                            + aMapLocation.getErrorInfo());
                }
                if(mv_road_detail_map.getMap().getUiSettings().isMyLocationButtonEnabled()) {
                    mv_road_detail_map.getMap().getUiSettings().setMyLocationButtonEnabled(false);
                    mv_road_detail_map.invalidate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        Log.i(TAG,"        ------------ aMapLocation  start      --------------------------       ");

        roadDetailMapPresenter.startLocation(this, 2000);
    }

    @Override
    public void deactivate() {
        try{
            mListener = null;
            roadDetailMapPresenter.destoryLocation();
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        //不显示缩放
        mv_road_detail_map.getMap().getUiSettings().setZoomControlsEnabled(false);


        // 自定义系统定位小蓝点
       /* MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.ico_road_current_location));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(100, 0, 0, 180));// 设置圆形的填充颜色
        // myLocationStyle.anchor(int,int)//设置小蓝点的锚点
        myLocationStyle.strokeWidth(1.0f);// 设置圆形的边框粗细*/
       // mv_road_detail_map.getMap().setMyLocationStyle(myLocationStyle);
        mv_road_detail_map.getMap().setLocationSource(this);// 设置定位监听
        //设置logo位置
        mv_road_detail_map.getMap().getUiSettings().setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);

        // aMap.setMyLocationType()
    }


    /**
     * 设置营点路点
     */
    private void setFlagPoint() {
        if (null!=mFlagPoints){

            for (FlagPoint point:mFlagPoints){


                if (point.getFlag_type().equals(FlagPoint.FLAG_TYPE_CAMP)) {
                    //营点

                    LatLng latLng = new LatLng(point.getOffset_lati(), point.getOffset_lngi());

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.visible(true);
                    markerOptions.position(latLng);
                    Bitmap bitmap = BitmapUtil.readBitmapFromRes(this, R.drawable.path_icon_location_camp, DensityUtil.dip2px(this, 21),
                            DensityUtil.dip2px(this, 26));
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    mv_road_detail_map.getMap().addMarker(markerOptions);

                }else if (point.getFlag_type().equals(FlagPoint.FLAG_TYPE_ROAD)){
                    //路点

                    LatLng latLng = new LatLng(point.getOffset_lati(), point.getOffset_lngi());

                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.visible(true);
                    markerOptions.position(latLng);
                    Bitmap bitmap = BitmapUtil.readBitmapFromRes(this, R.drawable.ico_road_detail_map_stop, DensityUtil.dip2px(this, 21),
                            DensityUtil.dip2px(this, 26));
                    markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
                    mv_road_detail_map.getMap().addMarker(markerOptions);
                }

            }
        }
    }
}
