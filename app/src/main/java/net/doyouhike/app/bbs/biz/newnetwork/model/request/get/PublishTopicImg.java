package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;

import java.util.List;

/**
 * Created by luochangdong on 15-11-27.
 */
public class PublishTopicImg extends BaseGetRequest {

    /**
     * type : image
     * tagId : 标签ID(多个需要英文逗号分隔的string)
     * cityId : //城市ID
     * content : //内容
     * imgIds : [{"id":1,"desc":"xxx"},{"id":2,"desc":"xxx"}]
     * isDraft : //区分草稿 1为草稿 0为正文
     */

    private String token;
    private String type = "image";
    private String tagId;
    private String cityId;
    private String content;
    private String isDraft;
    /**
     * id : 1
     * desc : xxx
     */

    private List<ImgIdsEntity> imgIds;


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setIsDraft(String isDraft) {
        this.isDraft = isDraft;
    }

    public void setImgIds(List<ImgIdsEntity> imgIds) {
        this.imgIds = imgIds;
    }

    public String getType() {
        return type;
    }

    public String getTagId() {
        return tagId;
    }

    public String getCityId() {
        return cityId;
    }

    public String getContent() {
        return content;
    }

    public String getIsDraft() {
        return isDraft;
    }

    public List<ImgIdsEntity> getImgIds() {
        return imgIds;
    }

    @Override
    protected void setMapValue() {

    }


    public static class ImgIdsEntity {
        private int id;
        private String desc;

        public void setId(int id) {
            this.id = id;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getId() {
            return id;
        }

        public String getDesc() {
            return desc;
        }
    }
}
