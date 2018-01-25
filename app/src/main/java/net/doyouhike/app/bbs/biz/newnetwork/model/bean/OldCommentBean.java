package net.doyouhike.app.bbs.biz.newnetwork.model.bean;

import net.doyouhike.app.bbs.biz.entity.action.CommentLastListData;
import net.doyouhike.app.bbs.biz.entity.common.RichTextContent;

import java.util.ArrayList;
import java.util.List;

/**
 * 老格式 评论样式
 * Created by zengjiang on 16/7/6.
 */
public class OldCommentBean {

    /**
     * content : 腹肌纠结
     * created : 1467610869
     * user_id : 1276298
     * comment_id : 4486216
     * source_type : ios
     * reply_to : 0
     * ip : 218.18.253.203
     * user_name : liqinkan
     * nick_name : 侃爷
     * avatar : http://static.test.doyouhike.net/files/faces/2/3/23f7b128c.jpg
     */

    private String content;
    private long created;
    private String user_id;
    private String comment_id;
    private String source_type;
    private String reply_to;
    private String ip;
    private String user_name;
    private String nick_name;
    private String avatar;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
    }

    public String getReply_to() {
        return reply_to;
    }

    public void setReply_to(String reply_to) {
        this.reply_to = reply_to;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public CommentLastListData toNewCommentLastListData(){


        CommentLastListData comment=new CommentLastListData();

        comment.setAvatar(getAvatar());
        comment.setReply_to(getReply_to());
        comment.setComment_id(getComment_id());
        comment.setCreated(getCreated());
        comment.setUser_name(getUser_name());
        comment.setNick_name(getNick_name());
        comment.setUser_id(getUser_id());
        List<RichTextContent> contents=new ArrayList<>();
        RichTextContent content=new RichTextContent();
        content.setType("text");
        content.setContent(getContent());
        comment.setContent(contents);

        return comment;
    }
}
