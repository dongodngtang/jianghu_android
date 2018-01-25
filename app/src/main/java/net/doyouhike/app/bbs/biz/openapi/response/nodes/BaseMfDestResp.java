package net.doyouhike.app.bbs.biz.openapi.response.nodes;

import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetDestByKeywordResp;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/13
 * 描述：
 */
public class BaseMfDestResp {

    /**
     * node_id : 3414
     * node_name : 大南山
     * node_slug : dananshan
     * ancestors : 1,8,242,2016
     * node_cat : node_type
     */

    private List<GetDestByKeywordResp> items;

    public List<GetDestByKeywordResp> getItems() {
        return items;
    }

    public void setItems(List<GetDestByKeywordResp> items) {
        this.items = items;
    }


}
