package net.doyouhike.app.bbs.biz.openapi.response.users;

import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/17
 * 描述：
 */
public class MinilogTagResp {

    /**
     * type : tag
     * tag_id : 1
     * title : 山野穿越
     * desc : 山野穿越就是城市周边的登山徒步活动，如千米山、排牙山、三水线那种
     */

    private List<BaseTag> tags;
    /**
     * type : tag
     * tag_id : 112
     * title : 美食
     * desc :
     */

    private List<BaseTag> s_tags;

    public List<BaseTag> getTags() {
        return tags;
    }

    public void setTags(List<BaseTag> tags) {
        this.tags = tags;
    }

    public List<BaseTag> getS_tags() {
        return s_tags;
    }

    public void setS_tags(List<BaseTag> s_tags) {
        this.s_tags = s_tags;
    }



}
