package net.doyouhike.app.bbs.biz.entity.dynamic;

import java.util.List;

/**
 * Created by terry on 5/31/16.
 * 线路 目的地详情
 */
public class DesAndRoadInfo {


    /**
     * node_id : 43
     * ancestors : 1
     * node_name : 斯里兰卡
     * node_slug : sri-lanka
     * pic : 2014/03/13/a/a0415f6975eadd5bab4dc9ffe4fae509.jpg
     * node_type : region
     * node_cat : country
     * latitude : 0
     * longitude : 0
     * node_template : 3
     * create_time : 1380273035
     * update_time : 1394694648
     * create_by : system
     * update_by : law去旅行
     */

    private DestBaseBean dest_base;
    /**
     * path : 2013/08/14/4/46c7a9173bba4430fcf90ec1ba042231
     * user_name : 甲壳虫蕊蕊
     */

    private List<DesAndRoadListAdInfo> banners;
    /**
     * node_slug : galle
     * node_name : 加勒
     * node_cat : city
     */

    private List<HotChildDestsBean> hot_child_dests;

    public DestBaseBean getDest_base() {
        return dest_base;
    }

    public void setDest_base(DestBaseBean dest_base) {
        this.dest_base = dest_base;
    }

    public List<DesAndRoadListAdInfo> getBanners() {
        return banners;
    }

    public void setBanners(List<DesAndRoadListAdInfo> banners) {
        this.banners = banners;
    }

    public List<HotChildDestsBean> getHot_child_dests() {
        return hot_child_dests;
    }

    public void setHot_child_dests(List<HotChildDestsBean> hot_child_dests) {
        this.hot_child_dests = hot_child_dests;
    }

    public static class DestBaseBean {
        private int node_id;
        private String ancestors;
        private String node_name;
        private String node_slug;
        private String pic;
        private String node_type;
        private String node_cat;
        private double latitude;
        private double longitude;
        private int node_template;
        private int create_time;
        private int update_time;
        private String create_by;
        private String update_by;

        public int getNode_id() {
            return node_id;
        }

        public void setNode_id(int node_id) {
            this.node_id = node_id;
        }

        public String getAncestors() {
            return ancestors;
        }

        public void setAncestors(String ancestors) {
            this.ancestors = ancestors;
        }

        public String getNode_name() {
            return node_name;
        }

        public void setNode_name(String node_name) {
            this.node_name = node_name;
        }

        public String getNode_slug() {
            return node_slug;
        }

        public void setNode_slug(String node_slug) {
            this.node_slug = node_slug;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public String getNode_type() {
            return node_type;
        }

        public void setNode_type(String node_type) {
            this.node_type = node_type;
        }

        public String getNode_cat() {
            return node_cat;
        }

        public void setNode_cat(String node_cat) {
            this.node_cat = node_cat;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }

        public int getNode_template() {
            return node_template;
        }

        public void setNode_template(int node_template) {
            this.node_template = node_template;
        }

        public int getCreate_time() {
            return create_time;
        }

        public void setCreate_time(int create_time) {
            this.create_time = create_time;
        }

        public int getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(int update_time) {
            this.update_time = update_time;
        }

        public String getCreate_by() {
            return create_by;
        }

        public void setCreate_by(String create_by) {
            this.create_by = create_by;
        }

        public String getUpdate_by() {
            return update_by;
        }

        public void setUpdate_by(String update_by) {
            this.update_by = update_by;
        }
    }

    public static class HotChildDestsBean {
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
}
