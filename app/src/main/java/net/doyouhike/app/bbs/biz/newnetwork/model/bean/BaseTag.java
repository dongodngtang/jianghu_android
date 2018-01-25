package net.doyouhike.app.bbs.biz.newnetwork.model.bean;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.ui.home.topic.TimelineRequestType;

import java.io.Serializable;
import java.util.List;

/**
 * 功能：
 * 作者：曾江
 * 日期：16-1-27.
 */
public class BaseTag implements Serializable{

    public static final String TYPE_TAG_ID_HOT = "-100";
    public static final String TYPE_TAG_ID_NEARBY = "-101";
    public static final String TYPE_TAG_ID_ATTEND = "-102";
    public static final String TYPE_SUB_TAG_FIXED = "fixedTag";

    public static final String TYPE_TITLE_TAG = "标签";
    public static final String TYPE_SUB_TAG = "tag";
    public static final String TYPE_TAG_ID = "-1";

    public static final String TYPE_SECTION_ID = "-2";
    public static final String TYPE_TITLE_SECTION = "版块";
    public static final String TYPE_SUB_FORUM = "forum";



    @Expose
    String tag_id;
    String tag_name;
    String desc;
    @Expose
    String type;
    String s_type;

    boolean selected=false;

    List<BaseTag> subItems;
    TimelineRequestType LiveType=TimelineRequestType.TOPIC;
    String parentTitle;
    BaseTag protetype;

    public BaseTag(String tag_id) {
        this.tag_id = tag_id;
    }

    public BaseTag() {
    }

    public String getTag_id() {
        return tag_id;
    }

    public void setTag_id(String tag_id) {
        this.tag_id = tag_id;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<BaseTag> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<BaseTag> subItems) {
        this.subItems = subItems;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public String getS_type() {
        return s_type;
    }

    public void setS_type(String s_type) {
        this.s_type = s_type;
    }

    public String getParentTitle() {
        return parentTitle;
    }

    public void setParentTitle(String parentTitle) {
        this.parentTitle = parentTitle;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public BaseTag getProtetype() {
        return protetype;
    }

    public void setProtetype(BaseTag protetype) {
        this.protetype = protetype;
    }

    public TimelineRequestType getLiveType() {
        return LiveType;
    }

    public void setLiveType(TimelineRequestType liveType) {
        LiveType = liveType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BaseTag tag = (BaseTag) o;

        if (tag_id != null ? !tag_id.equals(tag.tag_id) : tag.tag_id != null) return false;
        return !(s_type != null ? !s_type.equals(tag.s_type) : tag.s_type != null);

    }

    @Override
    public int hashCode() {
        int result = tag_id.hashCode();
        result = 31 * result + s_type.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return tag_name;
    }

    public BaseTag clone() {

        BaseTag tag = new BaseTag();
        tag.setTag_id(tag_id);
        tag.setTag_name(tag_name);
        tag.setDesc(desc);
        tag.setParentTitle(parentTitle);
        tag.setS_type(s_type);
        tag.setType(type);
        tag.setProtetype(this);

        return tag;
    }
}
