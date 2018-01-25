package net.doyouhike.app.bbs.biz.openapi.request.users;

import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/9/26
 * 描述：
 */
public class UserProfileGet extends BaseGetRequest {

    private String user_id;

    public UserProfileGet(String user_id) {
        this.user_id = user_id;
    }

    @Override
    protected void setMapValue() {

    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<CurrentUserDetails>(CurrentUserDetails.class);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.ACCOUNT_USERS + user_id + "/profile";
    }
}
