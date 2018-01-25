package net.doyouhike.app.bbs.biz.newnetwork.model.response;


import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;

import java.util.List;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-1-27
 */
public class GetMinilogTagResp extends Response {

    /**
     * id : 1
     * title : 山野穿越
     * desc : 山野穿越就是城市周边的登山徒步活动，如千米山、排牙山、三水线那种
     */

    private List<TagsEntity> tags;
    /**
     * id : 149
     * title : 公益活动
     * desc :
     */

    private List<STagsEntity> s_tags;

    public void setTags(List<TagsEntity> tags) {
        this.tags = tags;
    }

    public void setS_tags(List<STagsEntity> s_tags) {
        this.s_tags = s_tags;
    }

    public List<TagsEntity> getTags() {
        return tags;
    }

    public List<STagsEntity> getS_tags() {
        return s_tags;
    }

    public static class TagsEntity {
        private String id;
        private String title;
        private String desc;
        /**
         * 是否被选中
         */
        private Boolean selected = false;

        public Boolean getSelected() {
            return selected;
        }

        public void setSelected(Boolean selected) {
            this.selected = selected;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDesc() {
            return desc;
        }
    }

    public static class STagsEntity {
        private String id;
        private String title;
        private String desc;

        /**
         * 是否被选中
         */
        private Boolean selected = false;

        public Boolean getSelected() {
            return selected;
        }

        public void setSelected(Boolean selected) {
            this.selected = selected;
        }

        public void setId(String id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getDesc() {
            return desc;
        }
    }
}
