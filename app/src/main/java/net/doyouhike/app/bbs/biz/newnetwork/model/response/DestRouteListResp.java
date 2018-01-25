package net.doyouhike.app.bbs.biz.newnetwork.model.response;

import net.doyouhike.app.bbs.biz.entity.common.PageOutPut;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.DestRouteItem;

import java.util.List;

/**
 * 通过关键字获取目的地线路列表
 * Created by zengjiang on 16/5/31.
 */
public class DestRouteListResp {


    /**
     * page_index : 1
     * page_size : 20
     * page_count : 10
     * total_records : 190
     */

    private PageOutPut page;

    /**
     * node_slug : wutongshan
     * node_name : 梧桐山
     * node_cat : node_type
     */

    private List<DestRouteItem> child_dests;

    public PageOutPut getPage() {
        return page;
    }

    public void setPage(PageOutPut page) {
        this.page = page;
    }

    public List<DestRouteItem> getChild_dests() {
        return child_dests;
    }

    public void setChild_dests(List<DestRouteItem> child_dests) {
        this.child_dests = child_dests;
    }
}
