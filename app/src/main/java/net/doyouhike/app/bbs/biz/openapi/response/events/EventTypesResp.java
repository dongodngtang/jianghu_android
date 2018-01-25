package net.doyouhike.app.bbs.biz.openapi.response.events;

import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;

import java.util.List;

/**
 * 作者：luochangdong on 16/9/9
 * 描述：
 */
public class EventTypesResp {


    /**
     * id : 100
     * sort_num : 1
     * type_name : 休闲户外
     */

    private List<BaseTag> items;

    public List<BaseTag> getItems() {
        return items;
    }

    public void setItems(List<BaseTag> items) {
        this.items = items;
    }


}
