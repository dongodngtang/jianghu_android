package net.doyouhike.app.bbs.biz.openapi.request.tags;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.MySubscibesRespons;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/10/12
 * 描述：
 */
public class UserSubscriptionsGet extends BaseGetRequest{
    @Override
    protected void setMapValue() {

    }
    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<MySubscibesRespons>(MySubscibesRespons.class);
    }
    private String user_id;

    public UserSubscriptionsGet(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS+user_id+"/subscriptions";
    }
}
