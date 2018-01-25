package net.doyouhike.app.bbs.biz.openapi.response.users;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/17
 * 描述：
 */
public class UserMsgEventResp {


    /**
     * msgs : [{"id":7486,"is_read":2,"created_time":1476345044,"node_id":6008315,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":5,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7484,"is_read":2,"created_time":1476345042,"node_id":6008314,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":3,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7482,"is_read":2,"created_time":1476345041,"node_id":6008313,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":4,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7480,"is_read":2,"created_time":1476345039,"node_id":6008312,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":2,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7473,"is_read":2,"created_time":1476345036,"node_id":6008311,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7470,"is_read":2,"created_time":1476345034,"node_id":6008309,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":4,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7459,"is_read":2,"created_time":1476321667,"node_id":6008272,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":5,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7457,"is_read":2,"created_time":1476321666,"node_id":6008271,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":3,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7455,"is_read":2,"created_time":1476321664,"node_id":6008270,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":4,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7453,"is_read":2,"created_time":1476321662,"node_id":6008269,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":2,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7446,"is_read":2,"created_time":1476321659,"node_id":6008268,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7443,"is_read":2,"created_time":1476321656,"node_id":6008266,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":4,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7422,"is_read":2,"created_time":1476189973,"node_id":6008216,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":5,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7420,"is_read":2,"created_time":1476189971,"node_id":6008215,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":3,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7418,"is_read":2,"created_time":1476189969,"node_id":6008214,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":4,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7416,"is_read":2,"created_time":1476189967,"node_id":6008213,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":2,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7409,"is_read":2,"created_time":1476189962,"node_id":6008212,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7406,"is_read":2,"created_time":1476189957,"node_id":6008210,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":4,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7398,"is_read":2,"created_time":1476188960,"node_id":6008175,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":5,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"},{"id":7396,"is_read":2,"created_time":1476188958,"node_id":6008174,"node_type":"event","memo":"","event_title":"this is breeze test example","is_organizer":0,"role":3,"mini_log_type":"activity","user_name":"coffee","nick_name":"cofsd","face":"files/faces/b/b/bbb8e9633.jpg","internal_id":18,"user_id":"4417b1d0efa10f1372c456d009a42df6"}]
     * photo_domain_path : http://192.168.1.231:8002/
     */

    private String photo_domain_path;
    /**
     * id : 7486
     * is_read : 2
     * created_time : 1476345044
     * node_id : 6008315
     * node_type : event
     * memo :
     * event_title : this is breeze test example
     * is_organizer : 0
     * role : 5
     * mini_log_type : activity
     * user_name : coffee
     * nick_name : cofsd
     * face : files/faces/b/b/bbb8e9633.jpg
     * internal_id : 18
     * user_id : 4417b1d0efa10f1372c456d009a42df6
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
        private String node_id;
        private String node_type;
        private String memo;
        private String event_title;
        private int is_organizer;
        private int role;
        private String mini_log_type;
        private String user_name;
        private String nick_name;
        private String face;
        private int internal_id;
        private String user_id;

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

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getEvent_title() {
            return event_title;
        }

        public void setEvent_title(String event_title) {
            this.event_title = event_title;
        }

        public int getIs_organizer() {
            return is_organizer;
        }

        public void setIs_organizer(int is_organizer) {
            this.is_organizer = is_organizer;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public String getMini_log_type() {
            return mini_log_type;
        }

        public void setMini_log_type(String mini_log_type) {
            this.mini_log_type = mini_log_type;
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
    }
}
