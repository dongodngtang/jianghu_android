package net.doyouhike.app.bbs.biz.openapi.response.users;

import java.util.List;

import rx.Observable;
import rx.functions.Func1;

/**
 * 作者：luochangdong on 16/10/13
 * 描述：
 */
public class UserMsgCommentsResp {
    /**
     * msgs : [{"id":6127,"is_read":2,"created_time":1467791983,"comment_id":4486298,"reply_to_id":18,"reply_to_username":"coffee","reply_to_nickname":"cofsd","content":[{"type":"text","content":"6"},{"type":"text","content":"5"},{"type":"text","content":"4"},{"type":"text","content":"2"}],"reference_id":4486267,"ref_content":[{"type":"text","content":"近距离经历"}],"is_show_content":true,"node_id":6006615,"node_type":"event","mini_log_type":"activity","event_title":"屈原汨罗江","user_name":"aas1900000","nick_name":"z1947","face":"files/faces/1/a/1a0b6ac4d.jpg","internal_id":1508772,"user_id":"265d5e7549c2bbfb26da56d00996c9a6"}]
     * photo_domain_path : http://192.168.1.231:8002/
     */

    private String photo_domain_path;
    /**
     * id : 6127
     * is_read : 2
     * created_time : 1467791983
     * comment_id : 4486298
     * reply_to_id : 18
     * reply_to_username : coffee
     * reply_to_nickname : cofsd
     * content : [{"type":"text","content":"6"},{"type":"text","content":"5"},{"type":"text","content":"4"},{"type":"text","content":"2"}]
     * reference_id : 4486267
     * ref_content : [{"type":"text","content":"近距离经历"}]
     * is_show_content : true
     * node_id : 6006615
     * node_type : event
     * mini_log_type : activity
     * event_title : 屈原汨罗江
     * user_name : aas1900000
     * nick_name : z1947
     * face : files/faces/1/a/1a0b6ac4d.jpg
     * internal_id : 1508772
     * user_id : 265d5e7549c2bbfb26da56d00996c9a6
     */

    private List<MsgsBean> msgs;

    public String getPhoto_domain_path() {
        return photo_domain_path;
    }

    public void setPhoto_domain_path(String photo_domain_path) {
        this.photo_domain_path = photo_domain_path;
    }

    public List<MsgsBean> getMsgs() {
        return msgs;
    }

    public void setMsgs(List<MsgsBean> msgs) {
        this.msgs = msgs;
    }

    public static class MsgsBean {
        private int id;
        private int is_read;
        private long created_time;
        private String comment_id;
        private String reply_to_id;
        private String reply_to_username;
        private String reply_to_nickname;
        private int reference_id;
        private boolean is_show_content;
        private String node_id;
        private String node_type;
        private String mini_log_type;
        private String event_title;
        private String user_name;
        private String nick_name;
        private String face;
        private int internal_id;
        private String user_id;
        private String first_photo;

        public String getFirst_photo() {
            return first_photo;
        }

        public void setFirst_photo(String first_photo) {
            this.first_photo = first_photo;
        }

        /**
         * type : text
         * content : 6
         */

        private List<ContentBean> content;
        /**
         * type : text
         * content : 近距离经历
         */

        private List<ContentBean> ref_content;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public long getCreated_time() {
            return created_time;
        }

        public void setCreated_time(long created_time) {
            this.created_time = created_time;
        }

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public String getReply_to_id() {
            return reply_to_id;
        }

        public void setReply_to_id(String reply_to_id) {
            this.reply_to_id = reply_to_id;
        }

        public String getReply_to_username() {
            return reply_to_username;
        }

        public void setReply_to_username(String reply_to_username) {
            this.reply_to_username = reply_to_username;
        }

        public String getReply_to_nickname() {
            return reply_to_nickname;
        }

        public void setReply_to_nickname(String reply_to_nickname) {
            this.reply_to_nickname = reply_to_nickname;
        }

        public int getReference_id() {
            return reference_id;
        }

        public void setReference_id(int reference_id) {
            this.reference_id = reference_id;
        }

        public boolean isIs_show_content() {
            return is_show_content;
        }

        public void setIs_show_content(boolean is_show_content) {
            this.is_show_content = is_show_content;
        }

        public String getNode_id() {
            return node_id;
        }

        public void setNode_id(String node_id) {
            this.node_id = node_id;
        }

        public String getNode_type() {
            return node_type;
        }

        public void setNode_type(String node_type) {
            this.node_type = node_type;
        }

        public String getMini_log_type() {
            return mini_log_type;
        }

        public void setMini_log_type(String mini_log_type) {
            this.mini_log_type = mini_log_type;
        }

        public String getEvent_title() {
            return event_title;
        }

        public void setEvent_title(String event_title) {
            this.event_title = event_title;
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

        public String getFace() {
            return face;
        }

        public void setFace(String face) {
            this.face = face;
        }

        public int getInternal_id() {
            return internal_id;
        }

        public void setInternal_id(int internal_id) {
            this.internal_id = internal_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public List<ContentBean> getContent() {
            return content;
        }

        public void setContent(List<ContentBean> content) {
            this.content = content;
        }

        public List<ContentBean> getRef_content() {
            return ref_content;
        }

        public void setRef_content(List<ContentBean> ref_content) {
            this.ref_content = ref_content;
        }

        public static class ContentBean {
            private String type;
            private String content;

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
        }

    }

}
