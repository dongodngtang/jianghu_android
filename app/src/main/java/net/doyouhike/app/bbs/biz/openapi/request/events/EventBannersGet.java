package net.doyouhike.app.bbs.biz.openapi.request.events;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventBannersResp;

/**
 * 作者：luochangdong on 16/9/9
 * 描述：活动页广告
 */
@RequestUrlAnnotation(OpenApiUrl.EVENT_BANNERS)
public class EventBannersGet extends BaseGetRequest {
    @Override
    protected void setMapValue() {

    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<Response<EventBannersResp>>(EventBannersResp.class);
    }
}
