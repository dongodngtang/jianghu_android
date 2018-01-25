package net.doyouhike.app.bbs.biz.openapi.request.upload;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;

/**
 * 作者：luochangdong on 16/10/9
 * 描述：
 */
public class BaseUploaderPost extends BasePostRequest {
    private String photoPath;

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
}
