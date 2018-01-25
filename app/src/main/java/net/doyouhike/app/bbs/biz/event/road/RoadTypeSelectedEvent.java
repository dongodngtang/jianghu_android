package net.doyouhike.app.bbs.biz.event.road;

import net.doyouhike.app.bbs.biz.entity.road.RoadListType;

import java.util.List;

/**
 * 线路列表 类型选中
 * Created by terry on 5/9/16.
 */
public class RoadTypeSelectedEvent {


    private RoadListType roadListType;

    public RoadListType getRoadListType() {
        return roadListType;
    }

    public void setRoadListType(RoadListType roadListType) {
        this.roadListType = roadListType;
    }
}
