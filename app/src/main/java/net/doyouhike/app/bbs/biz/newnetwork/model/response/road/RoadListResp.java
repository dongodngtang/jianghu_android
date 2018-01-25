package net.doyouhike.app.bbs.biz.newnetwork.model.response.road;

import net.doyouhike.app.bbs.biz.entity.common.PageOutPut;
import net.doyouhike.app.bbs.biz.entity.road.RoadListInfo;

import java.util.List;

/**
 * 线路列表返回
 * Created by terry on 5/8/16.
 */
public class RoadListResp {

    public List<RoadListInfo> getLists() {
        return lists;
    }

    public void setLists(List<RoadListInfo> lists) {
        this.lists = lists;
    }

    public PageOutPut getPage() {
        return page;
    }

    public void setPage(PageOutPut page) {
        this.page = page;
    }

    private List<RoadListInfo> lists;

    private PageOutPut page;
}
