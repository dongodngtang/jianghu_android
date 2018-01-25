package net.doyouhike.app.bbs.biz.openapi.request.account;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.events.StartAdBannerResp;

/**
 * 作者：luochangdong on 16/10/18
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.AD_LOADING)
public class AdLoadingGet extends BaseGetRequest {
    @Override
    protected void setMapValue() {

    }

    public AdLoadingGet() {
        setTime_out(1500);
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<Response<StartAdBannerResp>>(StartAdBannerResp.class);
    }
}
