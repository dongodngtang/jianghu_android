package net.doyouhike.app.bbs.ui.activity.live;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.entity.dynamic.DesAndRoadInfo;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.RouteHelper;
import net.doyouhike.app.bbs.ui.adapter.live.DesAndRoadListAdapter;
import net.doyouhike.app.bbs.ui.widget.action.InterceptScrollViewPager;
import net.doyouhike.app.bbs.ui.widget.live.DesAndRoadAdHandle;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.library.ui.uistate.UiState;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * 线路 目的地
 * 2016.5.30
 * zhulin
 */
public class DesAndRoadListActivity extends BaseActivity implements View.OnClickListener {

    private final static String TAG = DesAndRoadListActivity.class.getSimpleName();

    /**
     * 图片 pager
     */
    @InjectView(R.id.vp_des_road_ad)
    public InterceptScrollViewPager vp_des_road_ad;

    /**
     * 列表 目的地
     */
    @InjectView(R.id.lv_dest_road_list)
    public ListView lv_dest_road_list;


    @InjectView(R.id.tv_des_road_city)
    public TextView tv_des_road_city;

    /**
     * 内容布局
     */
    @InjectView(R.id.sv_road_detail_info)
    RelativeLayout sv_road_detail_info;


    @InjectView(R.id.rl_road_detail_back)
    LinearLayout rl_road_detail_back;


    RelativeLayout rl_des_and_road_more;



    public DesAndRoadAdHandle handler;

    private String node_slug;
    private String node_slug_city;

    /**
     * 目的地 线路 列表
     */
    private DesAndRoadListAdapter desAndRoadListAdapter;

    private List<DesAndRoadInfo.HotChildDestsBean> hotCityList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isSetSystemBar = false;
        super.onCreate(savedInstanceState);


    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_des_road_list;
    }


    @Override
    protected void initViewsAndEvents() {
        Intent intent = this.getIntent();
        node_slug = intent.getStringExtra("node_slug");
        if (StringUtil.isNotEmpty(node_slug)) {
            getListInfo();
        } else
            StringUtil.showSnack(DesAndRoadListActivity.this, "参数为空,无法获取数据");


        View view = LayoutInflater.from(this).inflate(R.layout.item_des_road_list_more, null);
        rl_des_and_road_more = (RelativeLayout) view.findViewById(R.id.rl_des_and_road_more);
        lv_dest_road_list.addFooterView(view);

        desAndRoadListAdapter = new DesAndRoadListAdapter(this, hotCityList);
        lv_dest_road_list.setAdapter(desAndRoadListAdapter);


        rl_road_detail_back.setOnClickListener(this);
        rl_des_and_road_more.setOnClickListener(this);
        handler = new DesAndRoadAdHandle(this, vp_des_road_ad);

    }

    private void getListInfo() {
        updateView(UiState.LOADING);
        RouteHelper.getInstance().destsNodesDetail(this,node_slug,listener);
    }

    IOnResponseListener listener =  new IOnResponseListener<Response<DesAndRoadInfo>>() {
        @Override
        public void onSuccess(Response<DesAndRoadInfo> response) {
                onGetData(response);
        }

        @Override
        public void onError(Response response) {

                onGetData(response);
        }
    };


    @Override
    protected View getLoadingTargetView() {
        return sv_road_detail_info;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void onGetData(Response<DesAndRoadInfo> response) {
        if (response.isSuccess()) {
            updateView(UiState.NORMAL);
            //设置头部广告
            DesAndRoadInfo info = response.getData();
            if (null != info.getBanners() && info.getBanners().size() > 0)
                handler.setAdsData(info.getBanners());
            //设置列表
            if (null != info.getHot_child_dests() && info.getHot_child_dests().size() > 0) {
                hotCityList.clear();
                hotCityList.addAll(info.getHot_child_dests());
                desAndRoadListAdapter.notifyDataSetChanged();
                UIUtils.showView(rl_des_and_road_more, true);
            }

            if (StringUtil.isNotEmptyObj(info.getDest_base()))
                node_slug_city = info.getDest_base().getNode_name();
            tv_des_road_city.setText(node_slug_city);
        } else {
            updateView(
                    UiState.ERROR.setMsg(getString(R.string.common_error_msg)
                            , getString(R.string.try_to_click_refresh)
                            , getString(R.string.refresh))
                    , new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (!TextUtils.isEmpty(node_slug)){
                                getListInfo();
                            }
                        }
                    });
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_road_detail_back:
                this.finish();
                break;
            case R.id.rl_des_and_road_more:
                Intent intent = new Intent(DesAndRoadListActivity.this, DestRouteListMoreActivity.class);
                intent.putExtra(DestRouteListMoreActivity.I_NODE_SLUG, node_slug);
                startActivity(intent);
                break;
            case R.id.tv_tip_more:

                break;

        }
    }


}
