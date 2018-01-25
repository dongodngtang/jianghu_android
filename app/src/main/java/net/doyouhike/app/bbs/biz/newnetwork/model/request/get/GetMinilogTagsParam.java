package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetMinilogTagResp;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-1-27
 */
@RequestUrlAnnotation(Content.REQ_GET_MINILOG_TAGS)
public class GetMinilogTagsParam extends BaseTokenGetParams {

    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<GetMinilogTagResp>(GetMinilogTagResp.class);
    }
}
