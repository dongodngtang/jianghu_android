package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.MySubscibesRespons;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

/**
 * 功能：获取用户自定义标签
 *
 * @author：曾江 日期：16-1-27.
 */
@RequestUrlAnnotation(Content.REQ_TAGDATA_GET_MY_SUBSCRIBES)
public class GetMySubscribesReq extends BaseTokenGetParams {

    public GetMySubscribesReq(String token) {
        setToken(token);
    }

    public GetMySubscribesReq() {
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<MySubscibesRespons>(MySubscibesRespons.class);
    }
}
