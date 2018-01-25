package net.doyouhike.app.bbs.biz.openapi.request.nodes;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeAccusationResp;

/**
 * 作者：luochangdong on 16/10/18
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.NODE_ACCUSATION_LIST)
public class NodeAccusationListGet extends BaseGetRequest {
    @Override
    protected void setMapValue() {

    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<NodeAccusationResp>(NodeAccusationResp.class);
    }
}
