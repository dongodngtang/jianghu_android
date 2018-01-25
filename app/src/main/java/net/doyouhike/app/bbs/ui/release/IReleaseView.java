package net.doyouhike.app.bbs.ui.release;


import net.doyouhike.app.bbs.biz.newnetwork.model.response.Timeline;

import java.util.List;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-1-26
 */
public interface IReleaseView {

    void releasePicFail(List<UploadImage> picUrl, Timeline timeline);

    void uploadAllSuccess(List<UploadImage> picUrl, Timeline timeline);


}
