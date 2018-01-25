package net.doyouhike.app.bbs.biz.entity.road;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * 线路详情 轨迹
 * Created by terry on 5/8/16.
 */
public class RoadDetailMaps implements Parcelable {

    private List<MapPoint> map_points;

    private MapBase map_base;


    protected RoadDetailMaps(Parcel in) {
        map_points = in.createTypedArrayList(MapPoint.CREATOR);
        map_base = in.readParcelable(MapBase.class.getClassLoader());
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(map_points);
        dest.writeParcelable(map_base, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RoadDetailMaps> CREATOR = new Creator<RoadDetailMaps>() {
        @Override
        public RoadDetailMaps createFromParcel(Parcel in) {
            return new RoadDetailMaps(in);
        }

        @Override
        public RoadDetailMaps[] newArray(int size) {
            return new RoadDetailMaps[size];
        }
    };

    public List<MapPoint> getMap_points() {
        return map_points;
    }

    public void setMap_points(List<MapPoint> map_points) {
        this.map_points = map_points;
    }

    public MapBase getMap_base() {
        return map_base;
    }

    public void setMap_base(MapBase map_base) {
        this.map_base = map_base;
    }








}
