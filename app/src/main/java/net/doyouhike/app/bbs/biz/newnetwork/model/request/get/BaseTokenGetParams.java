package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;

/**
 * Created by zaitu on 15-11-30.
 */
public class BaseTokenGetParams extends BaseGetRequest {

    /**
     * token : "token"
     */

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    protected void setMapValue() {
        map.put("token",token);
    }
}
