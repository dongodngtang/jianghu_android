package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.SearchTagsResp;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

/**
 * 功能：获取首页可查询标签
 *
 * @author：曾江 日期：16-1-27.
 */
@RequestUrlAnnotation(Content.REQ_TAGDATA_GET_SEARCH_TAGS)
public class GetSearchTagsReq extends BaseGetRequest {

    @Override
    public IResponseProcess getProcess() {
        return  new DataJsonParser<SearchTagsResp>(SearchTagsResp.class);
    }

    @Override
    protected void setMapValue() {

    }
}
