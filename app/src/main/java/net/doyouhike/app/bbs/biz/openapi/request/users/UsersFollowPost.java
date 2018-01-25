package net.doyouhike.app.bbs.biz.openapi.request.users;

import net.doyouhike.app.bbs.biz.db.green.entities.Follow;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/9/14
 * 描述：
 */
public class UsersFollowPost extends BasePostRequest {
    private String user_id;

    public UsersFollowPost(String user_id) {
        this.user_id = user_id;
        setExtraInfo(user_id);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS + user_id + "/follows";
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<Follow>(Follow.class);
    }
}
