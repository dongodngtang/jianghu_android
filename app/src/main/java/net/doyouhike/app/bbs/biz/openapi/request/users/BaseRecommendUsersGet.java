package net.doyouhike.app.bbs.biz.openapi.request.users;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.users.RecommendUsersResp;

/**
 * 作者：luochangdong on 16/10/8
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.USERS_RECOMMEND)
public class BaseRecommendUsersGet extends BaseGetRequest {
    @Override
    protected void setMapValue() {

    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<RecommendUsersResp>(RecommendUsersResp.class);
    }
}
