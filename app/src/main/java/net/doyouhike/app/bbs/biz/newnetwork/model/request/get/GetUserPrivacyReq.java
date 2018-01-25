package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetUserPrivacyResp;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

/**
 * Filework: 获取用户隐私设置
 * Author: luochangdong
 * Date:16-3-29
 */
@RequestUrlAnnotation(Content.REQ_GET_GET_USER_PRIVACY)
public class GetUserPrivacyReq extends BaseTokenGetParams {
    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<GetUserPrivacyResp>(GetUserPrivacyResp.class);
    }
}
