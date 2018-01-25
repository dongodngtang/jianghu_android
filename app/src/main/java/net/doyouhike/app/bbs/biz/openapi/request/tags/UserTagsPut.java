package net.doyouhike.app.bbs.biz.openapi.request.tags;

import com.google.gson.annotations.Expose;
import com.yolanda.nohttp.RequestMethod;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/14
 * 描述：
 */
public class UserTagsPut extends BasePostRequest {

    private String user_id;

    public UserTagsPut(String user_id) {
        this.user_id = user_id;
        setRequestMethod(RequestMethod.PUT);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.USERS + user_id + "/subscriptions";
    }

    /**
     * id : 102
     * type : type
     */

    @Expose
    private List<BaseTag> subscriptions;

    public List<BaseTag> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<BaseTag> subscriptions) {
        this.subscriptions = subscriptions;
    }

}
