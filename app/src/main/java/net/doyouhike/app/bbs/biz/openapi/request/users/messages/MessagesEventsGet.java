package net.doyouhike.app.bbs.biz.openapi.request.users.messages;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.openapi.response.users.UserMsgEventResp;

/**
 * 作者：luochangdong on 16/10/9
 * 描述：
 */
public class MessagesEventsGet extends BaseListGet {
    private String user_id;

    public MessagesEventsGet(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS + user_id + "/messages/events";
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<UserMsgEventResp>(UserMsgEventResp.class);
    }
}
