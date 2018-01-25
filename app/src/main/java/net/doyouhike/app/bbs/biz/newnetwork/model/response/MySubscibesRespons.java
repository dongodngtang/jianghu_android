package net.doyouhike.app.bbs.biz.newnetwork.model.response;


import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;

import java.util.List;

/**
 * 功能：
 *
 * @author：曾江 日期：16-1-27.
 */
public class MySubscibesRespons {

    private List<BaseTag> items;

    public List<BaseTag> getMy_subscribes() {
        return items;
    }

    public void setMy_subscribes(List<BaseTag> my_subscribes) {
        this.items = my_subscribes;
    }

}
