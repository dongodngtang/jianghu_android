package net.doyouhike.app.bbs.biz.entity.road;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by terry on 5/11/16.
 */
public class MapPoint implements Parcelable {

    private String activity_id;
    private String lat;
    private String lng;
    private String alt;
    private String distance_next;
    private String distance_dest;

    public MapPoint(){}

    protected MapPoint(Parcel in) {
        activity_id = in.readString();
        lat = in.readString();
        lng = in.readString();
        alt = in.readString();
        distance_next = in.readString();
        distance_dest = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(activity_id);
        dest.writeString(lat);
        dest.writeString(lng);
        dest.writeString(alt);
        dest.writeString(distance_next);
        dest.writeString(distance_dest);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MapPoint> CREATOR = new Creator<MapPoint>() {
        @Override
        public MapPoint createFromParcel(Parcel in) {
            return new MapPoint(in);
        }

        @Override
        public MapPoint[] newArray(int size) {
            return new MapPoint[size];
        }
    };

    public String getActivity_id() {
        return activity_id;
    }

    public void setActivity_id(String activity_id) {
        this.activity_id = activity_id;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getDistance_dest() {
        return distance_dest;
    }

    public void setDistance_dest(String distance_dest) {
        this.distance_dest = distance_dest;
    }

    public String getDistance_next() {
        return distance_next;
    }

    public void setDistance_next(String distance_next) {
        this.distance_next = distance_next;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }


}
