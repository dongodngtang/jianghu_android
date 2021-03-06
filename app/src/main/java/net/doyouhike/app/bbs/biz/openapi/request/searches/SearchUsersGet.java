package net.doyouhike.app.bbs.biz.openapi.request.searches;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.users.SearchUserResp;

/**
 * 作者：luochangdong on 16/10/8
 * 描述：
 */
public class SearchUsersGet extends BaseGetRequest {

    private String keyword;

    public SearchUsersGet(String keyword) {
        this.keyword = keyword;
    }

    @Override
    protected void setMapValue() {

    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<SearchUserResp>(SearchUserResp.class);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.SEARCH_USERS + keyword;
    }
}
