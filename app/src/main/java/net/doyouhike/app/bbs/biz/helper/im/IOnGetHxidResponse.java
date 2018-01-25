package net.doyouhike.app.bbs.biz.helper.im;

import net.doyouhike.app.bbs.biz.newnetwork.model.bean.HxUserInfo;

/**
 * Created by zengjiang on 16/7/30.
 */

public interface IOnGetHxidResponse {

    void onSuccess(String userId, HxUserInfo info);

    void onError(String userId);
}
