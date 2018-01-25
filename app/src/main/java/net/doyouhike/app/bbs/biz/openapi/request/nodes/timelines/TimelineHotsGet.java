package net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;

/**
 * 作者：luochangdong on 16/9/26
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.TIMELINE_HOTS)
public class TimelineHotsGet extends BaseListGet {
    @Override
    protected void setMapValue() {
        super.setMapValue();
    }
    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<NodeTimeline>(NodeTimeline.class);
    }
}
