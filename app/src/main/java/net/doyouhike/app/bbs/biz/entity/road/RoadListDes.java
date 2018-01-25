package net.doyouhike.app.bbs.biz.entity.road;

import java.io.Serializable;

/**
 * 线路列表 目的地城市
 * Created by terry on 5/8/16.
 */
public class RoadListDes implements Serializable{

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
