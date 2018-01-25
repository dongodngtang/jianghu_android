package net.doyouhike.app.bbs.biz.openapi.request.users;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/9/26
 * 描述：
 */
public class UsersFavoritesGet extends BaseGetRequest {

    private String user_id;

    @Override
    protected void setMapValue() {

    }

    public UsersFavoritesGet(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS + user_id + "/favorites";
    }
}
