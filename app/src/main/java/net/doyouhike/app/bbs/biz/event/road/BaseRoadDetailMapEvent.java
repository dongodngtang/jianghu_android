package net.doyouhike.app.bbs.biz.event.road;

import android.os.Parcel;
import android.os.Parcelable;

import com.amap.api.maps2d.model.LatLng;

import net.doyouhike.app.bbs.biz.entity.road.MapPoint;
import net.doyouhike.app.bbs.biz.entity.road.RoadDetailMaps;
import net.doyouhike.app.bbs.biz.entity.road.RoadListInfo;

import java.io.Serializable;
import java.util.List;

/**
 * 线路详情 轨迹
 * Created by terry on 5/8/16.
 */

public class BaseRoadDetailMapEvent implements Parcelable {

    private RoadDetailMaps roadDetailMaps;

    //地图中心点 坐标
    private LatLng centerLatLng;

    //轨迹点
    private List<LatLng> pointList;

    public BaseRoadDetailMapEvent(){}

    protected BaseRoadDetailMapEvent(Parcel in) {
        centerLatLng = in.readParcelable(LatLng.class.getClassLoader());
        pointList = in.createTypedArrayList(LatLng.CREATOR);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(centerLatLng, flags);
        dest.writeTypedList(pointList);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<BaseRoadDetailMapEvent> CREATOR = new Creator<BaseRoadDetailMapEvent>() {
        @Override
        public BaseRoadDetailMapEvent createFromParcel(Parcel in) {
            return new BaseRoadDetailMapEvent(in);
        }

        @Override
        public BaseRoadDetailMapEvent[] newArray(int size) {
            return new BaseRoadDetailMapEvent[size];
        }
    };

    public List<LatLng> getPointList() {
        return pointList;
    }

    public void setPointList(List<LatLng> pointList) {
        this.pointList = pointList;
    }

    public LatLng getCenterLatLng() {
        return centerLatLng;
    }

    public void setCenterLatLng(LatLng centerLatLng) {
        this.centerLatLng = centerLatLng;
    }




    public RoadDetailMaps getRoadDetailMaps() {
        return roadDetailMaps;
    }

    public void setRoadDetailMaps(RoadDetailMaps roadDetailMaps) {
        this.roadDetailMaps = roadDetailMaps;
    }





}
