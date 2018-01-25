package net.doyouhike.app.bbs.biz.openapi.request.users.messages;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgCommentsResp;

/**
 * 作者：luochangdong on 16/10/9
 * 描述：
 */
public class MessagesCommentsGet extends BaseListGet {
    @Override
    protected void setMapValue() {
        super.setMapValue();
    }

    private String user_id;

    public MessagesCommentsGet(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<UserMsgCommentsResp>(UserMsgCommentsResp.class);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS + user_id + "/messages/comments";
    }
}
