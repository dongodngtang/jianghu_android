package net.doyouhike.app.bbs.biz.openapi.request.tags;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.SearchTagsResp;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/10/12
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.BASE_MF_TYPES)
public class BaseMfTypesGet extends BaseGetRequest {
    @Override
    protected void setMapValue() {

    }

    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<SearchTagsResp>(SearchTagsResp.class);
    }
}
