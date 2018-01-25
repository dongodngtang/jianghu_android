package net.doyouhike.app.bbs.biz.newnetwork.model.request.get.message;

import net.doyouhike.app.bbs.biz.newnetwork.Event.MsgLikeEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.openapi.request.BaseListGet;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.message.GetLikeMeListResp;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.newnetwork.net.ResponseEventAnnotation;

/**
 * 作者：luochangdong on 16/8/31
 * 描述：消息-赞
 */
@RequestUrlAnnotation(Content.REQ_GET_MY_LIKE_LIST)
@ResponseEventAnnotation(MsgLikeEvent.class)
public class GetLikeMeListParam extends BaseListGet {




    public GetLikeMeListParam() {
    }

    public GetLikeMeListParam(int page, int limit, String token) {
        setPage_size(limit);
        setPage_index(page);
        setToken(token);
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<>(GetLikeMeListResp.class);
    }


}
