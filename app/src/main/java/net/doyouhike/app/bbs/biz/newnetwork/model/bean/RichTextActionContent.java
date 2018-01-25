package net.doyouhike.app.bbs.biz.newnetwork.model.bean;

import com.google.gson.annotations.Expose;

/**
 * Created by zengjiang on 16/6/29.
 * 发送活动时,富文本内容,以此格式发送给服务器
 */
public class RichTextActionContent {

    @Expose
    private Integer photo_id;
    @Expose
    private String text;
    @Expose
    private String is_new;

    public String getIs_new() {
        return is_new;
    }

    public void setIs_new(String is_new) {
        this.is_new = is_new;
    }

    public int getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(int photo_id) {
        this.photo_id = photo_id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
