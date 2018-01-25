package net.doyouhike.app.bbs.biz.openapi.response.routes;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/25
 * 描述：
 */
public class DestsRouteChildResp {

    /**
     * page_index : 1
     * page_size : 20
     * page_count : 2
     * total_records : 38
     */

    private PageBean page;
    /**
     * node_slug : china
     * node_name : 中国
     * node_cat : country
     */

    private List<ChildDestsBean> child_dests;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<ChildDestsBean> getChild_dests() {
        return child_dests;
    }

    public void setChild_dests(List<ChildDestsBean> child_dests) {
        this.child_dests = child_dests;
    }

    public static class PageBean {
        private int page_index;
        private int page_size;
        private int page_count;
        private int total_records;

        public int getPage_index() {
            return page_index;
        }

        public void setPage_index(int page_index) {
            this.page_index = page_index;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }

        public int getTotal_records() {
            return total_records;
        }

        public void setTotal_records(int total_records) {
            this.total_records = total_records;
        }
    }

    public static class ChildDestsBean {
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
