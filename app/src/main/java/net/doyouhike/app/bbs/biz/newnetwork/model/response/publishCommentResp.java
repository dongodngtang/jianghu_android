package net.doyouhike.app.bbs.biz.newnetwork.model.response;

/**
 * Created by luochangdong on 15-11-30.
 */
public class publishCommentResp {

    /**
     * user_id : 1297846
     * user_name : test007
     * content : 试试
     * comment_id : 22607
     * created : 1446796727
     * avatar : http://static.test.doyouhike.net/files/faces/6/5/652792a73.jpg
     */

    private String user_id;
    private String user_name;
    private String content;
    private int comment_id;
    private int created;
    private String avatar;

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public void setCreated(int created) {
        this.created = created;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getContent() {
        return content;
    }

    public int getComment_id() {
        return comment_id;
    }

    public int getCreated() {
        return created;
    }

    public String getAvatar() {
        return avatar;
    }
}
