package net.doyouhike.app.bbs.biz.newnetwork.model.response;


import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;

import java.util.List;

/**
 * 功能：获取可查询的标签
 *
 * @author：曾江 日期：16-1-27.
 */
public class SearchTagsResp {

    /**
     * id : 1
     * title : 山野穿越
     * desc : 山野穿越就是城市周边的登山徒步活动，如千米山、排牙山、三水线那种
     */

    private List<BaseTag> items;


    public void setMinilog_tags(List<BaseTag> minilog_tags) {
        this.items = minilog_tags;
    }


    public List<BaseTag> getMinilog_tags() {
        return items;
    }


}
