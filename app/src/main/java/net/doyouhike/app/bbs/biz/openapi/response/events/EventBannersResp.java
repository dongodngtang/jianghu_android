package net.doyouhike.app.bbs.biz.openapi.response.events;

import java.util.List;

/**
 * 作者：luochangdong on 16/9/9
 * 描述：
 */
public class EventBannersResp {

    /**
     * id : 1
     * link_type : mf_link
     * image_url : http://static.doyouhike.net/partner/doyouhike/activity/100KM.20150107.640x240.jpg
     * link_url : http://www.doyouhike.net/city/shenzhen/2335647,0,0,0.html
     * node_type : 1
     */

    private List<ItemsBean> ad_result;

    public List<ItemsBean> getItems() {
        return ad_result;
    }

    public void setItems(List<ItemsBean> items) {
        this.ad_result = items;
    }

    public static class ItemsBean {
        private String id;
        private String link_type;
        private String image_url;
        private String link_url;
        private String node_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLink_type() {
            return link_type;
        }

        public void setLink_type(String link_type) {
            this.link_type = link_type;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getLink_url() {
            return link_url;
        }

        public void setLink_url(String link_url) {
            this.link_url = link_url;
        }

        public String getNode_type() {
            return node_type;
        }

        public void setNode_type(String node_type) {
            this.node_type = node_type;
        }
    }
}
