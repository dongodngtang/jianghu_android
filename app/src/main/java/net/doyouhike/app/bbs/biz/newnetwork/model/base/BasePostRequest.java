package net.doyouhike.app.bbs.biz.newnetwork.model.base;

import com.yolanda.nohttp.rest.CacheMode;

/**
 * Created by luochangdong on 15-11-27.
 */
public class BasePostRequest extends BaseRequest {
    public BasePostRequest() {
        setCacheMode(CacheMode.DEFAULT);
    }
}
