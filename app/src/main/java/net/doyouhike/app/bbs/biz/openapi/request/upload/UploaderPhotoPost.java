package net.doyouhike.app.bbs.biz.openapi.request.upload;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.uploader.UploaderPhotoResp;

/**
 * 作者：luochangdong on 16/9/28
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.UPLOAD_PHOTO)
public class UploaderPhotoPost extends BaseUploaderPost {

    private String upload_type;

    public UploaderPhotoPost() {
        setExtraInfo(this);
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<UploaderPhotoResp>(UploaderPhotoResp.class);
    }

    public String getUpload_type() {
        return upload_type;
    }

    public void setUpload_type(String upload_type) {
        this.upload_type = upload_type;
    }


}
