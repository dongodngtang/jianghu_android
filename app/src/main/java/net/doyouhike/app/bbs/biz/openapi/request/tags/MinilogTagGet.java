package net.doyouhike.app.bbs.biz.openapi.request.tags;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.users.MinilogTagResp;

/**
 * 作者：luochangdong on 16/10/17
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.MINILOG_TAGS)
public class MinilogTagGet extends BaseGetRequest {
    @Override
    protected void setMapValue() {

    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<MinilogTagResp>(MinilogTagResp.class);
    }
}
