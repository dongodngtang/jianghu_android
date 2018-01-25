package net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;

/**
 * 作者：luochangdong on 16/10/10
 * 描述：
 */
public class UserTimelineGet extends BaseListGet {

    private String user_id;

    @Override
    protected void setMapValue() {
        super.setMapValue();
    }

    public UserTimelineGet(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS + user_id + "/timelines";
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<NodeTimeline>(NodeTimeline.class);
    }


}
