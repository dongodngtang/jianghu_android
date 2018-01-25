package net.doyouhike.app.bbs.biz.newnetwork.model.request.get.message;

import net.doyouhike.app.bbs.biz.newnetwork.Event.MsgCommentEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.message.GetCommentMeListResp;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.newnetwork.net.ResponseEventAnnotation;

/**
 * 作者:luochangdong on 16/6/14 15:09
 * 描述: 消息获取对评论的人
 */
@RequestUrlAnnotation(Content.REQ_GET_COMMENT_ME_LEST)
@ResponseEventAnnotation(MsgCommentEvent.class)
public class GetCommentMeListParam extends BaseListGet {


    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<>(GetCommentMeListResp.class);
    }

    public GetCommentMeListParam() {
    }

    public GetCommentMeListParam(int page, int limit, String token) {
        setPage_index(page);
        setPage_size(limit);
        setToken(token);
    }


}
