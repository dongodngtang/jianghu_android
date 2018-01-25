package net.doyouhike.app.bbs.biz.openapi.response.users;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/11
 * 描述：
 */
public class UserNodeStateResp {

    /**
     * node_id : 6007355
     * favorited : false
     * liked : true
     * social : not_follow
     */

    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        private String node_id;
        private boolean favorited;
        private boolean liked;
        private String social;

        public String getNode_id() {
            return node_id;
        }

        public void setNode_id(String node_id) {
            this.node_id = node_id;
        }

        public boolean isFavorited() {
            return favorited;
        }

        public void setFavorited(boolean favorited) {
            this.favorited = favorited;
        }

        public boolean isLiked() {
            return liked;
        }

        public void setLiked(boolean liked) {
            this.liked = liked;
        }

        public String getSocial() {
            return social;
        }

        public void setSocial(String social) {
            this.social = social;
        }
    }
}
