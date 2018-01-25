package net.doyouhike.app.bbs.biz.event.road;

import net.doyouhike.app.bbs.biz.entity.road.RoadListType;

import java.util.List;

/**
 * 线路列表 类型
 * Created by terry on 5/9/16.
 */
public class RoadTypeEvent {

    private List<RoadListType> roadTypeInfoList;

    public List<RoadListType> getRoadTypeInfoList() {
        return roadTypeInfoList;
    }

    public void setRoadTypeInfoList(List<RoadListType> roadTypeInfoList) {
        this.roadTypeInfoList = roadTypeInfoList;
    }


}
