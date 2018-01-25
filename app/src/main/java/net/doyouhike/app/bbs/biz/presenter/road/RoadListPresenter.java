package net.doyouhike.app.bbs.biz.presenter.road;

import android.content.Context;
import android.util.Log;

import net.doyouhike.app.bbs.base.util.BasePresenter;
import net.doyouhike.app.bbs.biz.entity.road.RoadListDes;
import net.doyouhike.app.bbs.biz.entity.road.RoadListInfo;
import net.doyouhike.app.bbs.biz.entity.road.RoadListType;
import net.doyouhike.app.bbs.biz.event.road.RoadListEvent;
import net.doyouhike.app.bbs.biz.event.road.RoadLocationEvent;
import net.doyouhike.app.bbs.biz.event.road.RoadTypeEvent;
import net.doyouhike.app.bbs.biz.event.road.RoadTypeSelectedEvent;
import net.doyouhike.app.bbs.biz.event.road.ShowRoadTypeEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.biz.openapi.presenter.RouteHelper;
import net.doyouhike.app.bbs.biz.openapi.request.routes.DestsRoutesGet;
import net.doyouhike.app.bbs.biz.openapi.response.routes.DestsRoutesResp;
import net.doyouhike.app.bbs.util.AmapLocationUtil;
import net.doyouhike.app.bbs.util.SpTools;
import net.doyouhike.app.bbs.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 线路列表
 * Created by terry on 5/8/16.
 */
public class RoadListPresenter extends BasePresenter {

    private final String TAG = RoadListPresenter.class.getSimpleName();

    //当前页数
    private int page_index = 1;

    private int  page_size = 20;

    private List<RoadListType> roadTypeInfoList = new ArrayList<>();



    private List<RoadListDes> roadListDesList = new ArrayList<>();


    /**
     * 城市 类型
     */
    private String city_slug ="";


    /**
     * 显示城市
     */
    private String city_name="";


    /**
     * 定位城市
     */
    private String city_name_location = "";

    /**
     * 线路类型 ID  默认 空 为 全部类型
     */
    private String road_type_id="";


    private String road_type_name="全部类型";



    /**
     * 是否  重新请求数据
     */
    private boolean isRequestAgain = false;


    /**
     * 高德地图定位工具类
     */
    private AmapLocationUtil mapLocationUtil;

    public RoadListPresenter(Context context) {
        super(context);
        if(mapLocationUtil==null)
            mapLocationUtil = new AmapLocationUtil(new RoadLocationEvent());
        mapLocationUtil.startLocation(false, 0l);
        initLocationData();
    }

    //初始化  定位 数据
    private void initLocationData(){
        //获取 默认城市
        //获取上次选择的城市
        city_name = SpTools.getString(context, "LASTCITY", "");
        city_slug = SpTools.getString(context, "LASTCITY_SLUG", "");
    }

    /**
     * 获取线路目的地城市 列表
     */
    public void getRoadDesList(){
       IOnResponseListener listener = new IOnResponseListener<Response<List<RoadListDes>>>(){
            @Override
            public void onSuccess(Response<List<RoadListDes>> response) {
                if (response.getCode() == 0){
                    roadListDesList.addAll(response.getData());
                    //根据定位 查找 cit_sulg
                    Log.i(TAG,"        ------------------   getRoadDesList  isRequestAgain      ----------------------  city_name:    "+city_name);
                    if(StringUtil.isNotEmpty(city_name)){
                       resetCity_sulg(city_name);
                    }
                }
            }
            @Override
            public void onError(Response response) {

            }
        };
        RouteHelper.getInstance().destsCities(context,listener);
    }


    public void getListData(RoadTypeSelectedEvent event){
        if(null!=event){
            road_type_id = event.getRoadListType().getId();
            road_type_name = event.getRoadListType().getRoute_type();
        }

        DestsRoutesGet get = new DestsRoutesGet();
        get.setCancelSign(context);
        get.setCity_slug(city_slug);
        get.setKeyword("");
        get.setPage_size(page_size);
        get.setPage_index(page_index);
        get.setRoute_type_id(road_type_id);
        get.setExtraInfo(page_index);
        ApiReq.doGet(get);

    }

    private List<RoadListInfo> getList(){
        List<RoadListInfo> list = new ArrayList<>();
        for(int i  =0;i<10;i++){
            RoadListInfo info = new RoadListInfo();
            info.setMain_photo("");
            info.setRoute_name("东莞松山湖自由行");
            info.setRoute_type_name("骑行");
            list.add(info);
        }
        return list;
    }





    /**
     * 获取线路 类型数据
     */
    public void getRoadTypeInfo(){
        //有数据 直接使用
        if(null!=roadTypeInfoList && roadTypeInfoList.size()>0){
            //标记选中
            for(RoadListType info:roadTypeInfoList){
                if(info.getId().equals(road_type_id))
                    info.setSelected(true);
                else
                    info.setSelected(false);
            }
            RoadTypeEvent event = new RoadTypeEvent();
            event.setRoadTypeInfoList(roadTypeInfoList);
            postEvent(new ShowRoadTypeEvent());
        }
        else
            getRoadTypeFromNet(road_type_id);

    }

    //获取 线路类型数据  如果内存中 默认  有 则 不取数据
    public void getRoadTypeFromNet(final String road_id){

        IOnResponseListener listener=  new IOnResponseListener<Response<List<RoadListType>>>(){
            @Override
            public void onSuccess(Response<List<RoadListType>> response) {
                if (response.getCode() == 0){
                    roadTypeInfoList.clear();
                    roadTypeInfoList.addAll(response.getData());
                    //添加全部类型
                    RoadListType type = RoadListType.getDefaultItem();
                    roadTypeInfoList.add(0,type);

                    //刷新数据
                    RoadTypeEvent event = new RoadTypeEvent();
                    event.setRoadTypeInfoList(roadTypeInfoList);
                    postEvent(event);

                    getRoadTypeInfo();
                }
                else {
                    StringUtil.showSnack(context,"数据解析出问题啦");
                }
            }
            @Override
            public void onError(Response response) {
                StringUtil.showSnack(context,"获取类型数据失败,请稍后再试");
            }
        };

        RouteHelper.getInstance().destsRouteType(context,listener);
    }




    public String getRoad_type_name() {
        return road_type_name;
    }

    public String getCity_name() {
        return city_name;
    }

    public String getCity_slug() {
        return city_slug;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_name_location() {
        return city_name_location;
    }

    public void setCity_name_location(String city_name_location) {
        this.city_name_location = city_name_location;
    }

    public List<RoadListDes> getRoadListDesList() {
        return roadListDesList;
    }

    public void setRoadListDesList(List<RoadListDes> roadListDesList) {
        this.roadListDesList = roadListDesList;
    }

    public  void resetCity_sulg(String city_name){
        for (RoadListDes des:roadListDesList){
            if(des.getCity_name().equals(city_name)){
                city_slug = des.getCity_slug();
                SpTools.setString(context, "LASTCITY_SLUG", city_slug);//保存当前城市
                //请求 列表数据
                getListData(null);
                isRequestAgain = true;
                break;
            }

        }
    }

    public int getPage() {
        return page_index;
    }

    public void setPage(int page) {
        this.page_index = page;
    }


    public boolean isRequestAgain() {
        return isRequestAgain;
    }

    public void setRequestAgain(boolean requestAgain) {
        isRequestAgain = requestAgain;
    }

}
