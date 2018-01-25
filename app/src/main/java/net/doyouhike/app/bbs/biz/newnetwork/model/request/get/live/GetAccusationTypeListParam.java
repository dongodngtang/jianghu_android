package net.doyouhike.app.bbs.biz.newnetwork.model.request.get.live;

import com.google.gson.reflect.TypeToken;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.live.GetAccusationTypeListResp;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

import java.util.List;

/**
 * 作者:luochangdong on 16/6/30 10:37
 * 描述:
 */
@RequestUrlAnnotation(Content.REQ_GET_ACCUSATION_TYPE_LIST)
public class GetAccusationTypeListParam extends BaseGetRequest{
    @Override
    protected void setMapValue() {

    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<List<GetAccusationTypeListResp>>(new TypeToken<List<GetAccusationTypeListResp>>(){}.getType());
    }
}
