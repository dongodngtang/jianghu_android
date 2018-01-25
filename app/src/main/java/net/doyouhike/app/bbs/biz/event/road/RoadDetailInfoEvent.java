package net.doyouhike.app.bbs.biz.event.road;

import com.amap.api.maps2d.model.LatLng;

import net.doyouhike.app.bbs.biz.entity.road.MapPoint;
import net.doyouhike.app.bbs.biz.entity.road.RoadDetailInfo;

import java.util.List;

/**
 * 线路详情
 * Created by terry on 5/8/16.
 */

public class RoadDetailInfoEvent {

    private  boolean isSuccess = true;

    private RoadDetailInfo info;



    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }



    public RoadDetailInfo getInfo() {
        return info;
    }

    public void setInfo(RoadDetailInfo info) {
        this.info = info;
    }


}
