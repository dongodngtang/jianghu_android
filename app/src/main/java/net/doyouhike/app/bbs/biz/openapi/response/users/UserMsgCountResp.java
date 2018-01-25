package net.doyouhike.app.bbs.biz.openapi.response.users;

/**
 * 作者：luochangdong on 16/10/17
 * 描述：
 */
public class UserMsgCountResp {

    /**
     * msg_totals : 0
     * msg_data : {"id":5,"internal_id":7309,"comment_msg_num":0,"like_msg_num":0,"event_msg_num":0,"fan_msg_num":0,"minilog_num":0,"favorite_num":0,"topic_num":0,"event_num":0,"event_join_num":0,"event_coor_num":0,"follow_num":0,"fan_num":0}
     */

    private int msg_totals;
    /**
     * id : 5
     * internal_id : 7309
     * comment_msg_num : 0
     * like_msg_num : 0
     * event_msg_num : 0
     * fan_msg_num : 0
     * minilog_num : 0
     * favorite_num : 0
     * topic_num : 0
     * event_num : 0
     * event_join_num : 0
     * event_coor_num : 0
     * follow_num : 0
     * fan_num : 0
     */

    private MsgDataBean msg_data;

    public int getMsg_totals() {
        return msg_totals;
    }

    public void setMsg_totals(int msg_totals) {
        this.msg_totals = msg_totals;
    }

    public MsgDataBean getMsg_data() {
        return msg_data;
    }

    public void setMsg_data(MsgDataBean msg_data) {
        this.msg_data = msg_data;
    }

    public static class MsgDataBean {
        private int id;
        private int internal_id;
        private int comment_msg_num;
        private int like_msg_num;
        private int event_msg_num;
        private int fan_msg_num;
        private int minilog_num;
        private int favorite_num;
        private int topic_num;
        private int event_num;
        private int event_join_num;
        private int event_coor_num;
        private int follow_num;
        private int fan_num;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getInternal_id() {
            return internal_id;
        }

        public void setInternal_id(int internal_id) {
            this.internal_id = internal_id;
        }

        public int getComment_msg_num() {
            return comment_msg_num;
        }

        public void setComment_msg_num(int comment_msg_num) {
            this.comment_msg_num = comment_msg_num;
        }

        public int getLike_msg_num() {
            return like_msg_num;
        }

        public void setLike_msg_num(int like_msg_num) {
            this.like_msg_num = like_msg_num;
        }

        public int getEvent_msg_num() {
            return event_msg_num;
        }

        public void setEvent_msg_num(int event_msg_num) {
            this.event_msg_num = event_msg_num;
        }

        public int getFan_msg_num() {
            return fan_msg_num;
        }

        public void setFan_msg_num(int fan_msg_num) {
            this.fan_msg_num = fan_msg_num;
        }

        public int getMinilog_num() {
            return minilog_num;
        }

        public void setMinilog_num(int minilog_num) {
            this.minilog_num = minilog_num;
        }

        public int getFavorite_num() {
            return favorite_num;
        }

        public void setFavorite_num(int favorite_num) {
            this.favorite_num = favorite_num;
        }

        public int getTopic_num() {
            return topic_num;
        }

        public void setTopic_num(int topic_num) {
            this.topic_num = topic_num;
        }

        public int getEvent_num() {
            return event_num;
        }

        public void setEvent_num(int event_num) {
            this.event_num = event_num;
        }

        public int getEvent_join_num() {
            return event_join_num;
        }

        public void setEvent_join_num(int event_join_num) {
            this.event_join_num = event_join_num;
        }

        public int getEvent_coor_num() {
            return event_coor_num;
        }

        public void setEvent_coor_num(int event_coor_num) {
            this.event_coor_num = event_coor_num;
        }

        public int getFollow_num() {
            return follow_num;
        }

        public void setFollow_num(int follow_num) {
            this.follow_num = follow_num;
        }

        public int getFan_num() {
            return fan_num;
        }

        public void setFan_num(int fan_num) {
            this.fan_num = fan_num;
        }
    }
}
