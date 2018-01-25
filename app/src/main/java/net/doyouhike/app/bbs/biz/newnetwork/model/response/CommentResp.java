package net.doyouhike.app.bbs.biz.newnetwork.model.response;

/**
 * 作者:luochangdong on 16/5/4 15:30
 * 描述:
 */
public class CommentResp {


    /**
     * user_id : 1508715
     * user_name : 凝固
     * nick_name : 凝固
     * content : dbbxbxhxhbs
     * comment_id : 25571
     * created : 1462346033
     * avatar : http://dev.static.doyouhike.com/files/faces/2/0/20582a279.jpg
     */

    private String user_id;
    private String user_name;
    private String nick_name;
    private String content;
    private String comment_id;
    private String created;
    private String avatar;

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

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
