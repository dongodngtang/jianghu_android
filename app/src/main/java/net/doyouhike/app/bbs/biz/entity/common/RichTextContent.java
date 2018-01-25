package net.doyouhike.app.bbs.biz.entity.common;

import android.text.TextUtils;

import com.google.gson.annotations.Expose;

/**
 * Created by zengjiang on 16/6/15.
 * 富文本内容格式
 */
public class RichTextContent {



    private String text;
    private String real_file;
    private String small_file;
    private String photo_id;
    @Expose
    private String type;// "text",
    @Expose
    private String content;// "万岁万岁万万岁"

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReal_file() {
        return real_file;
    }

    public void setReal_file(String real_file) {
        this.real_file = real_file;
    }

    public String getSmall_file() {
        return small_file;
    }

    public void setSmall_file(String small_file) {
        this.small_file = small_file;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isTextType(){

        if (!TextUtils.isEmpty(type)){
            return "text".equals(type);
        }

        return false;
    }
}
