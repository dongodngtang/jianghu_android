package net.doyouhike.app.bbs.biz.newnetwork.model.response.road;

import net.doyouhike.app.bbs.biz.entity.action.CommentLastListData;
import net.doyouhike.app.bbs.biz.entity.common.RichTextContent;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者:luochangdong on 16/5/25 10:12
 * 描述:
 */
public class PublishCommentRepo {

    /**
     * user_id : 1297846
     * user_name : test007
     * content : 试试
     * comment_id : 22607
     * created : 1446796727
     * avatar : http://static.test.doyouhike.net/files/faces/6/5/652792a73.jpg
     */

    private String avatar;
    private int comment_id;
    private Object content;
    private long created;
    private String user_id;
    private String user_name;
    private String nick_name;
    private String reply_to_user_name;//回复谁谁谁用户名
    private String reply_to_nick_name;//回复谁谁谁昵称
    private String reply_to;//回复谁谁谁用户id

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getReply_to_user_name() {
        return reply_to_user_name;
    }

    public void setReply_to_user_name(String reply_to_user_name) {
        this.reply_to_user_name = reply_to_user_name;
    }

    public String getReply_to_nick_name() {
        return reply_to_nick_name;
    }

    public void setReply_to_nick_name(String reply_to_nick_name) {
        this.reply_to_nick_name = reply_to_nick_name;
    }

    public String getReply_to() {
        return reply_to;
    }

    public void setReply_to(String reply_to) {
        this.reply_to = reply_to;
    }

    public CommentLastListData toListData(List<RichTextContent> arrayContent) {


        CommentLastListData lastListData=new CommentLastListData();


        lastListData.setUser_id(String.valueOf(getUser_id()));
        lastListData.setUser_name(getUser_name());
        lastListData.setNick_name(getNick_name());
        lastListData.setComment_id(String.valueOf(getComment_id()));
        lastListData.setCreated(getCreated());
        lastListData.setAvatar(getAvatar());
        lastListData.setReply_to(getReply_to());
        lastListData.setReply_to_nick_name(getReply_to_nick_name());

        lastListData.setContent(arrayContent);

        return lastListData;

    }
}
