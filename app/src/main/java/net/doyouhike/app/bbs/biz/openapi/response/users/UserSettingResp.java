package net.doyouhike.app.bbs.biz.openapi.response.users;

/**
 * 作者：luochangdong on 16/10/17
 * 描述：
 */
public class UserSettingResp {


    /**
     * user_id : 4417b1d0efa10f1372c456d009a42df6
     * find_by_phone : true
     * find_by_nearly : true
     * push_comment_msg : true
     * push_event_msg : true
     * push_like_msg : true
     * push_fans_msg : true
     */

    private UserSettingBean user_setting;

    public UserSettingBean getUser_setting() {
        return user_setting;
    }

    public void setUser_setting(UserSettingBean user_setting) {
        this.user_setting = user_setting;
    }

    public static class UserSettingBean {
        private String user_id;
        private boolean find_by_phone;
        private boolean find_by_nearly;
        private boolean push_comment_msg;
        private boolean push_event_msg;
        private boolean push_like_msg;
        private boolean push_fans_msg;

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public boolean isFind_by_phone() {
            return find_by_phone;
        }

        public void setFind_by_phone(boolean find_by_phone) {
            this.find_by_phone = find_by_phone;
        }

        public boolean isFind_by_nearly() {
            return find_by_nearly;
        }

        public void setFind_by_nearly(boolean find_by_nearly) {
            this.find_by_nearly = find_by_nearly;
        }

        public boolean isPush_comment_msg() {
            return push_comment_msg;
        }

        public void setPush_comment_msg(boolean push_comment_msg) {
            this.push_comment_msg = push_comment_msg;
        }

        public boolean isPush_event_msg() {
            return push_event_msg;
        }

        public void setPush_event_msg(boolean push_event_msg) {
            this.push_event_msg = push_event_msg;
        }

        public boolean isPush_like_msg() {
            return push_like_msg;
        }

        public void setPush_like_msg(boolean push_like_msg) {
            this.push_like_msg = push_like_msg;
        }

        public boolean isPush_fans_msg() {
            return push_fans_msg;
        }

        public void setPush_fans_msg(boolean push_fans_msg) {
            this.push_fans_msg = push_fans_msg;
        }
    }
}
