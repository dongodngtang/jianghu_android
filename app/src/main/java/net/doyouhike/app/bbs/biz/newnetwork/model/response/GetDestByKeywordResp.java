package net.doyouhike.app.bbs.biz.newnetwork.model.response;

import java.io.Serializable;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-3-22
 */
public class GetDestByKeywordResp implements Serializable{


    /**
     * node_id : 2008
     * node_name : 深圳
     * node_cat : city
     */

    private String node_id;
    private String node_name;
    private String node_cat;
    /**
     * node_slug : dananshan
     * ancestors : 1,8,242,2016
     */

    private String node_slug;
    private String ancestors;


    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
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

    public String getNode_slug() {
        return node_slug;
    }

    public void setNode_slug(String node_slug) {
        this.node_slug = node_slug;
    }

    public String getAncestors() {
        return ancestors;
    }

    public void setAncestors(String ancestors) {
        this.ancestors = ancestors;
    }
}
