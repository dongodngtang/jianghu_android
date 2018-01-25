package net.doyouhike.app.bbs.biz.event;

import com.amap.api.location.AMapLocation;

/**
 * @author terry
 * @TODO:  基础Event
 * @date: $date$ $time$
 */
public class BaseLocationEvent {


    private AMapLocation aMapLocation;


    public AMapLocation getaMapLocation() {
        return aMapLocation;
    }

    public void setaMapLocation(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
    }
}
