package net.doyouhike.app.bbs.biz.openapi.request.users.messages;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgCountResp;

/**
 * 作者：luochangdong on 16/10/9
 * 描述：
 */
public class MessagesCountsGet extends BaseGetRequest {
    @Override
    protected void setMapValue() {

    }

    private String user_id;

    public MessagesCountsGet(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS + user_id + "/messages/counts";
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<UserMsgCountResp>(UserMsgCountResp.class);
    }
}
