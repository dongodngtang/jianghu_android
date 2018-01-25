package net.doyouhike.app.bbs.ui.release.tags;


import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetMinilogTagResp;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-1-27
 */
public interface IReleaseTagActivityView {

    void initData(GetMinilogTagResp getMinilogTagResp);

    void getNetDataError(String errorMsg);

    void tvComplete();

    void tipExceeding();


}
