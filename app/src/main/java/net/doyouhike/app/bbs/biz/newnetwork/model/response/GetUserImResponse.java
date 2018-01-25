package net.doyouhike.app.bbs.biz.newnetwork.model.response;

import net.doyouhike.app.bbs.biz.newnetwork.model.bean.HxUserInfo;

import java.util.List;

/**
 * Created by zengjiang on 16/8/8.
 */

public class GetUserImResponse {

    private List<HxUserInfo> items;

    public List<HxUserInfo> getItems() {
        return items;
    }

    public void setItems(List<HxUserInfo> items) {
        this.items = items;
    }
}
