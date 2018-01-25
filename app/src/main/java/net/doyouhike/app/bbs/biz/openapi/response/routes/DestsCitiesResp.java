package net.doyouhike.app.bbs.biz.openapi.response.routes;

/**
 * 作者：luochangdong on 16/9/8
 * 描述：
 */
public class DestsCitiesResp {

    /**
     * city_slug : beijing
     * city_name : 北京
     */

    private String city_slug;
    private String city_name;

    public String getCity_slug() {
        return city_slug;
    }

    public void setCity_slug(String city_slug) {
        this.city_slug = city_slug;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }
}
