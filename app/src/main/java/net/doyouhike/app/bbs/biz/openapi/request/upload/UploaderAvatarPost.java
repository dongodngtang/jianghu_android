package net.doyouhike.app.bbs.biz.openapi.request.upload;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.uploader.UploadAvatarResp;

/**
 * 作者：luochangdong on 16/10/9
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.UPLOADER_AVATAR)
public class UploaderAvatarPost extends BaseUploaderPost {

    public UploaderAvatarPost() {
        setExtraInfo(this);
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<UploadAvatarResp>(UploadAvatarResp.class);
    }

}
