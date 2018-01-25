package net.doyouhike.app.bbs.biz.openapi.request.routes;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.BaseMfDestResp;

/**
 * 作者：luochangdong on 16/10/13
 * 描述：
 */

public class BaseMfDestGet extends BaseGetRequest {
    private String key_word;

    public BaseMfDestGet(String key_word) {
        this.key_word = key_word;
    }

    @Override
    protected void setMapValue() {

    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<BaseMfDestResp>(BaseMfDestResp.class);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.BASE_MFDEST_NODES + key_word;
    }
}
