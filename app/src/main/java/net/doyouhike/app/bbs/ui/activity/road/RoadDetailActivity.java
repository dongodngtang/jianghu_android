package net.doyouhike.app.bbs.ui.activity.road;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.entity.road.RoadDetailInfo;
import net.doyouhike.app.bbs.biz.event.road.BaseRoadDetailMapEvent;
import net.doyouhike.app.bbs.biz.event.road.RoadDetailInfoEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.road.FlagPoint;
import net.doyouhike.app.bbs.biz.presenter.road.RoadDetailPresenter;
import net.doyouhike.app.bbs.util.BitmapUtil;
import net.doyouhike.app.bbs.util.DensityUtil;
import net.doyouhike.app.bbs.util.StatisticsEventUtil;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.linktext.linkutil.LinkTextUtil;
import net.doyouhike.app.bbs.util.wheelview.DataTypeUtil;
import net.doyouhike.app.library.ui.uistate.UiState;

import java.util.ArrayList;

import butterknife.InjectView;

/**
 * 线路详情
 * 2016.5.10
 * zhulin
 */
public class RoadDetailActivity extends BaseActivity implements View.OnClickListener {

    private final static String TAG = RoadDetailActivity.class.getSimpleName();

    @InjectView(R.id.rl_road_detail_back)
    LinearLayout rl_road_detail_back;

    @InjectView(R.id.mv_road_detail_map)
    MapView mv_road_detail_map;

    @InjectView(R.id.sv_road_detail_info)
    ScrollView sv_road_detail_info;

    /**
     * 线路名称
     */
    @InjectView(R.id.tv_road_detail_roadName)
    TextView tv_road_detail_roadName;


    /**
     * 所属类型
     */
    @InjectView(R.id.tv_road_detail_type)
    TextView tv_road_detail_type;


    /**
     * 里程
     */
    @InjectView(R.id.tv_road_detail_distance)
    TextView tv_road_detail_distance;


    /**
     * 难度等级
     */
    @InjectView(R.id.tv_road_detail_difficult_leve)
    TextView tv_road_detail_difficult_leve;


    /**
     * 最佳季节
     */
    @InjectView(R.id.tv_road_detail_best_season)
    TextView tv_road_detail_best_season;

    /**
     * 活动时长
     */
    @InjectView(R.id.tv_road_detail_time_need)
    TextView tv_road_detail_time_need;

    /**
     * 营位个数
     */
    @InjectView(R.id.tv_road_detail_camp_num)
    TextView tv_road_detail_camp_num;

    /**
     * 特色标签
     */
    @InjectView(R.id.tv_road_detail_special_tag)
    TextView tv_road_detail_special_tag;

    /**
     * 营地 设施
     */
    @InjectView(R.id.tv_road_detail_buliding)
    TextView tv_road_detail_buliding;

    /**
     * 线路 信息
     */
    @InjectView(R.id.tv_road_detail_line_info)
    TextView tv_road_detail_line_info;


    /**
     * 交通位置
     */
    @InjectView(R.id.tv_road_detail_tranport)
    TextView tv_road_detail_tranport;
    /**
     * 简介内容
     */
    @InjectView(R.id.tv_intro_content)
    TextView tvIntroContent;
    @InjectView(R.id.tv_intro_content_all)
    TextView tvIntroContentAll;

    @InjectView(R.id.tv_intro_more)
    TextView tvIntroMore;
    /**
     * 提示内容
     */
    @InjectView(R.id.tv_tip_content)
    TextView tvTipContent;
    @InjectView(R.id.tv_tip_content_all)
    TextView tvTipContentAll;

    @InjectView(R.id.tv_tip_more)
    TextView tvTipMore;

    /**
     * 线路编号
     */
    @InjectView(R.id.tv_road_detail_road_number)
    TextView tv_road_detail_road_number;

    /**
     * 简介
     */
    @InjectView(R.id.ll_road_detail_jianjie)
    LinearLayout ll_road_detail_jianjie;


    /**
     * 重要提示  布局
     */
    @InjectView(R.id.ll_road_detail_tishi)
    LinearLayout ll_road_detail_tishi;


    private BaseRoadDetailMapEvent baseRoadDetailMapEvent;


    private RoadDetailPresenter roadDetailPresenter;

    private String road_slug;

    private String offset_lngi;

    private String offset_lati;

    /**
     * 营点\路点
     */
    private ArrayList<FlagPoint> mFlagPoints;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isSetSystemBar = false;
        super.onCreate(savedInstanceState);

        mv_road_detail_map.onCreate(savedInstanceState);
        //不显示缩放
        mv_road_detail_map.getMap().getUiSettings().setZoomControlsEnabled(false);
        //禁用 手势
        mv_road_detail_map.getMap().getUiSettings().setZoomGesturesEnabled(false);
        //禁止 移动
        mv_road_detail_map.getMap().getUiSettings().setAllGesturesEnabled(false);

        //设置logo位置
        mv_road_detail_map.getMap().getUiSettings().setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_RIGHT);
        //友盟统计
        StatisticsEventUtil.collectRouteDetail(this);
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_road_detail;
    }


    @Override
    protected void initViewsAndEvents() {
        rl_road_detail_back.setOnClickListener(this);

        if (null == roadDetailPresenter)
            roadDetailPresenter = new RoadDetailPresenter(this);
        Intent intent = this.getIntent();
        road_slug = intent.getStringExtra("road_slug");
        if (StringUtil.isNotEmpty(road_slug)) {
            updateView(UiState.LOADING, null);
            //请求 线路
            roadDetailPresenter.getRoadInfo(road_slug);
            //请求 轨迹
            roadDetailPresenter.getRoadInfoMap(road_slug, new BaseRoadDetailMapEvent());

        } else {
            StringUtil.showSnack(this, "线路为空");
            this.finish();
        }
        mv_road_detail_map.getMap().setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                toMapViewActivity();
            }
        });

        initExpandText();
    }

    private void initExpandText() {
        tvTipMore.setOnClickListener(this);
        tvIntroMore.setOnClickListener(this);
        tvTipContent.setOnClickListener(this);
        tvIntroContent.setOnClickListener(this);


    }

    @Override
    protected void onResume() {
        super.onResume();
        mv_road_detail_map.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mv_road_detail_map.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (null != mv_road_detail_map) {
                //mv_road_detail_map.getMap().clear();
                mv_road_detail_map.onDestroy();
                mv_road_detail_map = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected View getLoadingTargetView() {
        return sv_road_detail_info;
    }

    @Override
    public void finish() {
        super.finish();


        try {
            if (null != mv_road_detail_map) {
                //mv_road_detail_map.getMap().removecache();
                mv_road_detail_map.destroyDrawingCache();
                mv_road_detail_map.getMap().clear();
                //mv_road_detail_map = null;
                System.gc();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 线路详情 请求 回调
     *
     * @param event
     */
    public void onEvent(RoadDetailInfoEvent event) {
        if (event.isSuccess() && null != event.getInfo()) {
            updateView(UiState.NORMAL);
            initViewData(event.getInfo());
        } else
            updateView(UiState.ERROR.setMsg(getString(R.string.common_error_msg),
                    getString(R.string.try_to_click_refresh)), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //请求 线路 轨迹
                    updateView(UiState.LOADING);
                    roadDetailPresenter.getRoadInfo(road_slug);
                }
            });
    }


    public void onEvent(BaseRoadDetailMapEvent event) {


        if (null != event.getPointList() && event.getPointList().size() > 0) {
            mv_road_detail_map.getMap().clear();
            //设置营点 路点
            setFlagPoint();
            baseRoadDetailMapEvent = event;
            //设置坐标
            PolylineOptions polylineOptions = new PolylineOptions();
            polylineOptions.addAll(event.getPointList());
            polylineOptions.color(getResources().getColor(R.color.bg_road_detail_map_line));
            //polylineOptions.geodesic(true);
            polylineOptions.visible(true);
            polylineOptions.width(DensityUtil.dip2px(this, 6));
            //polylineOptions.zIndex(10);

            //添加 起点终点 图片
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.visible(true);
            markerOptions.position(event.getPointList().get(0));
            Bitmap bitmap = BitmapUtil.readBitmapFromRes(this, R.drawable.ico_road_detail_map_start, DensityUtil.dip2px(this, 21),
                    DensityUtil.dip2px(this, 26));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(bitmap));
            mv_road_detail_map.getMap().addMarker(markerOptions);

            MarkerOptions markerOptions2 = new MarkerOptions();
            markerOptions2.visible(true);
            markerOptions2.position(event.getPointList().get(event.getPointList().size() - 1));
            Bitmap bitmap2 = BitmapUtil.readBitmapFromRes(this, R.drawable.ico_road_detail_map_stop, DensityUtil.dip2px(this, 21),
                    DensityUtil.dip2px(this, 26));
            markerOptions2.icon(BitmapDescriptorFactory.fromBitmap(bitmap2));
            mv_road_detail_map.getMap().addMarker(markerOptions2);


            mv_road_detail_map.getMap().addPolyline(polylineOptions);


            // mv_road_detail_map.getMap().
        } else {//轨迹不存在
            //显示终点
            showEndPoint();


        }
        if (baseRoadDetailMapEvent != null && StringUtil.isNotEmptyObj(baseRoadDetailMapEvent.getCenterLatLng())) {

            mv_road_detail_map.getMap().moveCamera(CameraUpdateFactory.newLatLng(baseRoadDetailMapEvent.getCenterLatLng()));
            mv_road_detail_map.getMap().moveCamera(CameraUpdateFactory.zoomTo(12f));
            mv_road_detail_map.getMap().invalidate();
        }


    }

    /**
     * 显示终点
     */
    private void showEndPoint() {
        if (StringUtil.isNotEmpty(offset_lati) && StringUtil.isNotEmpty(offset_lngi)) {

            if (null != baseRoadDetailMapEvent.getPointList() && baseRoadDetailMapEvent.getPointList().size() > 0) {
                return;
            }

            LatLng ll = new LatLng(Double.valueOf(offset_lati), Double.valueOf(offset_lngi));
            baseRoadDetailMapEvent.setCenterLatLng(ll);
            MarkerOptions markerOptions2 = new MarkerOptions();
            markerOptions2.visible(true);
            markerOptions2.position(baseRoadDetailMapEvent.getCenterLatLng());
            Bitmap bitmap2 = BitmapUtil.readBitmapFromRes(this, R.drawable.ico_road_detail_map_stop, DensityUtil.dip2px(this, 21),
                    DensityUtil.dip2px(this, 26));
            markerOptions2.icon(BitmapDescriptorFactory.fromBitmap(bitmap2));
            mv_road_detail_map.getMap().addMarker(markerOptions2);
        }
    }

    /**
     * 填充UI数据
     *
     * @param info
     */
    private void initViewData(RoadDetailInfo info) {
        try {
            offset_lngi = info.getOffset_lngi();
            offset_lati = info.getOffset_lati();
            LatLng ll = new LatLng(Double.valueOf(offset_lati), Double.valueOf(offset_lngi));
            if (null == baseRoadDetailMapEvent)
                baseRoadDetailMapEvent = new BaseRoadDetailMapEvent();
            baseRoadDetailMapEvent.setCenterLatLng(ll);
            //线路名称
            if (StringUtil.isNotEmpty(info.getRoute_name()))
                tv_road_detail_roadName.setText(info.getRoute_name());


            UIUtils.showView(tv_road_detail_road_number, !TextUtils.isEmpty(info.getMark()));
            tv_road_detail_road_number.setText(info.getMark());

            //简介
            if (StringUtil.isNotEmpty(info.getRoute_desc())) {

                final SpannableStringBuilder spannable = new LinkTextUtil().getSpannableString(info.getRoute_desc());
                tvIntroContentAll.setMovementMethod(LinkMovementMethod.getInstance());
                tvIntroContentAll.setText(spannable);
                tvIntroContent.setText(spannable);
                tvIntroContent.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (tvIntroContent.getLineCount() > 3) {
                            tvIntroContent.setMaxLines(3);
                            tvIntroContent.setEllipsize(TextUtils.TruncateAt.END);
                            tvIntroMore.setVisibility(View.VISIBLE);
                        } else
                            tvIntroMore.setVisibility(View.GONE);
                    }
                }, 10);


            } else
                ll_road_detail_jianjie.setVisibility(View.GONE);


            //提示
            if (StringUtil.isNotEmpty(info.getPrompt_notice())) {
                tvTipContent.setText(info.getPrompt_notice());

                SpannableStringBuilder spannable = new LinkTextUtil().getSpannableString(info.getPrompt_notice());
                tvTipContentAll.setMovementMethod(LinkMovementMethod.getInstance());
                tvTipContentAll.setText(spannable);


                tvTipContent.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (tvTipContent.getLineCount() > 3) {
                            tvTipContent.setMaxLines(3);
                            tvTipContent.setEllipsize(TextUtils.TruncateAt.END);
                            tvTipMore.setVisibility(View.VISIBLE);
                        } else
                            tvTipMore.setVisibility(View.GONE);
                    }
                }, 10);

            } else
                ll_road_detail_tishi.setVisibility(View.GONE);


            //类型
            if (StringUtil.isNotEmpty(info.getRoute_type_name()))
                tv_road_detail_type.setText(info.getRoute_type_name());
            else {
                TableRow tableRow = (TableRow) findViewById(R.id.tr_road_detail_type);
                tableRow.setVisibility(View.GONE);
            }

            //高度里程
            if (StringUtil.isNotEmpty(info.getMileage()))
                tv_road_detail_distance.setText(DataTypeUtil.FormatString(info.getMileage(), "0.00") + " km");
            else {
                TableRow tableRow = (TableRow) findViewById(R.id.tr_road_detail_distance);
                tableRow.setVisibility(View.GONE);
            }
            //线路 难度
            String difficult = info.getDifficulty_level()+"";
            if (StringUtil.isNotEmpty(difficult))
                tv_road_detail_difficult_leve.setText(difficult);
            else {
                TableRow tableRow = (TableRow) findViewById(R.id.tr_road_detail_difficult_leve);
                tableRow.setVisibility(View.GONE);
            }


            //最佳季节
            if (StringUtil.isNotEmpty(info.getSeason_ids()))
                tv_road_detail_best_season.setText(roadDetailPresenter.formatSeason(info.getSeason_ids()));
            else {
                TableRow tableRow = (TableRow) findViewById(R.id.tr_road_detail_best_season);
                tableRow.setVisibility(View.GONE);
            }

            //活动时长
            if (StringUtil.isNotEmpty(info.getTravel_time()))
                tv_road_detail_time_need.setText(info.getTravel_time() + "天");
            else {
                TableRow tableRow = (TableRow) findViewById(R.id.tr_road_detail_time_need);
                tableRow.setVisibility(View.GONE);
            }

            //露营位个数
            if (StringUtil.isNotEmpty(info.getCampsite_count()))
                tv_road_detail_camp_num.setText(info.getCampsite_count());
            else {
                TableRow tableRow = (TableRow) findViewById(R.id.tr_road_detail_camp_num);
                tableRow.setVisibility(View.GONE);
            }

            //特色标签
            String tags = roadDetailPresenter.formatTags(info.getTags());
            if (StringUtil.isNotEmpty(tags))
                tv_road_detail_special_tag.setText(tags);
            else {
                TableRow tableRow = (TableRow) findViewById(R.id.tr_road_detail_special_tag);
                tableRow.setVisibility(View.GONE);
            }

            //营地设施
            String campsites = roadDetailPresenter.formatCampsites(info.getCampsites());
            if (StringUtil.isNotEmpty(campsites))
                tv_road_detail_buliding.setText(campsites);
            else {
                TableRow tableRow = (TableRow) findViewById(R.id.tr_road_detail_buliding);
                tableRow.setVisibility(View.GONE);
            }

            //线路
            if (StringUtil.isNotEmpty(info.getTravel_line()))
                tv_road_detail_line_info.setText(info.getTravel_line());
            else {
                TableRow tableRow = (TableRow) findViewById(R.id.tr_road_detail_line_info);
                tableRow.setVisibility(View.GONE);
            }

            //交通
            if (StringUtil.isNotEmpty(info.getTraffic_info()))
                tv_road_detail_tranport.setText(info.getTraffic_info());
            else {
                TableRow tableRow = (TableRow) findViewById(R.id.tr_road_detail_tranport);
                tableRow.setVisibility(View.GONE);
            }
            tv_road_detail_tranport.requestLayout();

            //营点\路点
            mFlagPoints = (ArrayList<FlagPoint>) info.getFlag_points();

            setFlagPoint();

            //显示终点
            showEndPoint();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 设置营点路点
     */
    private void setFlagPoint() {
        if (null != mFlagPoints) {

            for (FlagPoint point : mFlagPoints) {

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

                } else if (point.getFlag_type().equals(FlagPoint.FLAG_TYPE_ROAD)) {
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
        switch (v.getId()) {
            case R.id.rl_road_detail_back:
                this.finish();
                break;
            case R.id.tv_intro_more:
                tvIntroContent.setVisibility(View.GONE);
                tvIntroMore.setVisibility(View.GONE);
                tvIntroContentAll.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_tip_more:
                tvTipMore.setVisibility(View.GONE);
                tvTipContent.setVisibility(View.GONE);
                tvTipContentAll.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_intro_content:
                tvIntroContent.setVisibility(View.GONE);
                tvIntroMore.setVisibility(View.GONE);
                tvIntroContentAll.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_tip_content:
                tvTipContent.setVisibility(View.GONE);
                tvTipMore.setVisibility(View.GONE);
                tvTipContentAll.setVisibility(View.VISIBLE);
                break;

        }
    }

    private void toMapViewActivity() {
        Intent intent = new Intent(this, RoadDetailMapActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("road_slug", road_slug);
        if (null != baseRoadDetailMapEvent)
            bundle.putParcelable("roadDetailMapEvent", baseRoadDetailMapEvent);
        if (null != mFlagPoints)
            bundle.putParcelableArrayList("roadDetailFlagPoints", mFlagPoints);

        intent.putExtras(bundle);
        startActivity(intent);
    }
}
