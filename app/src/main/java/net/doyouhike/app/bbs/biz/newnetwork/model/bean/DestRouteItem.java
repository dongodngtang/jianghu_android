package net.doyouhike.app.bbs.biz.newnetwork.model.bean;

/**
 * 通过关键字获取目的地线路列表
 * Created by zengjiang on 16/5/31.
 */
public class DestRouteItem {

    /**
     * node_slug : haishangshijie
     * node_name : 海上世界
     * node_cat : poi
     */

    private String node_slug;
    private String node_name;
    private String node_cat;

    public String getNode_slug() {
        return node_slug;
    }

    public void setNode_slug(String node_slug) {
        this.node_slug = node_slug;
    }

    public String getNode_name() {
        return node_name;
    }

    public void setNode_name(String node_name) {
        this.node_name = node_name;
    }

    public String getNode_cat() {
        return node_cat;
    }

    public void setNode_cat(String node_cat) {
        this.node_cat = node_cat;
    }
}
