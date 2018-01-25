package net.doyouhike.app.bbs.biz.openapi.response.nodes;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/18
 * 描述：
 */
public class NodeAccusationResp {

    /**
     * type_id : 7
     * type_name : 盈利召集
     * remark : null
     */

    private List<ItemsBean> items;

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        private int type_id;
        private String type_name;
        private String remark;

        public int getType_id() {
            return type_id;
        }

        public void setType_id(int type_id) {
            this.type_id = type_id;
        }

        public String getType_name() {
            return type_name;
        }

        public void setType_name(String type_name) {
            this.type_name = type_name;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
