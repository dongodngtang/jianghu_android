package net.doyouhike.app.bbs.biz.newnetwork.model.response.comment;

import net.doyouhike.app.bbs.biz.entity.action.CommentLastListData;
import net.doyouhike.app.bbs.biz.entity.common.RichTextContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zengjiang on 16/6/17.
 */
public class AddCommentResponse {

    /**
     * user_id : 1508772
     * user_name : aas1900000
     * nick_name : zz1947
     * content : 好
     * comment_id : 3442052
     * created : 1466130165
     * avatar : http://dev.static.doyouhike.com/files/faces/c/5/c5de2f8df.jpg
     * reply_to : 1508772
     */

    private int user_id;
    private String user_name;
    private String nick_name;
    private String content;
    private int comment_id;
    private int created;
    private String avatar;
    private String reply_to;

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getCreated() {
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

    public String getReply_to() {
        return reply_to;
    }

    public void setReply_to(String reply_to) {
        this.reply_to = reply_to;
    }

    public CommentLastListData toListData( String reply_to_nick_name) {


        CommentLastListData lastListData=new CommentLastListData();


        lastListData.setUser_id(String.valueOf(getUser_id()));
        lastListData.setUser_name(getUser_name());
        lastListData.setNick_name(getNick_name());
        lastListData.setComment_id(String.valueOf(getComment_id()));
        lastListData.setCreated(getCreated());
        lastListData.setAvatar(getAvatar());
        lastListData.setReply_to(getReply_to());
        lastListData.setReply_to_nick_name(reply_to_nick_name);

        List<RichTextContent> contents=new ArrayList<>();
        RichTextContent content=new RichTextContent();
        content.setText("text");
        content.setContent(getContent());
        contents.add(content);

        lastListData.setContent(contents);

        return lastListData;

    }
}
