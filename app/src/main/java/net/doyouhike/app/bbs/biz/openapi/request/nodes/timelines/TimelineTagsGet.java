package net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;

/**
 * 作者：luochangdong on 16/9/28
 * 描述：
 */
public class TimelineTagsGet extends BaseListGet {


    private String tag_id;


    @Override
    protected void setMapValue() {
        super.setMapValue();
    }


    public TimelineTagsGet(String tag_id) {
        this.tag_id = tag_id;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.TIMELINE_TAGS + tag_id;
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<NodeTimeline>(NodeTimeline.class);
    }
}
