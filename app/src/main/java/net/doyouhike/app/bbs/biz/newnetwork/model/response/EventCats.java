package net.doyouhike.app.bbs.biz.newnetwork.model.response;


import net.doyouhike.app.bbs.biz.newnetwork.model.bean.Cat;

import java.util.List;

/**
 * 功能：获取目的地
 * 作者：曾江
 * 日期：15-12-29.
 */
public class EventCats {

    /**
     * id : backpacking
     * title : 长假远行
     * cats : [{"fid":"18","cat_id":"1020","cat_name":"云贵"},{"fid":"18","cat_id":"1021","cat_name":"川渝"},{"fid":"18","cat_id":"1022","cat_name":"西藏"},{"fid":"18","cat_id":"1023","cat_name":"华南"},{"fid":"18","cat_id":"1121","cat_name":"港澳台"},{"fid":"18","cat_id":"1024","cat_name":"两湖"},{"fid":"18","cat_id":"1025","cat_name":"华东"},{"fid":"18","cat_id":"1026","cat_name":"西北"},{"fid":"18","cat_id":"1027","cat_name":"华北"},{"fid":"18","cat_id":"1028","cat_name":"东北"},{"fid":"18","cat_id":"1029","cat_name":"海南"}]
     */

    private String id;
    private String title;
    /**
     * fid : 18
     * cat_id : 1020
     * cat_name : 云贵
     */

    private List<Cat> cats;

    public void setId(String id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCats(List<Cat> cats) {
        this.cats = cats;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public List<Cat> getCats() {
        return cats;
    }

}
