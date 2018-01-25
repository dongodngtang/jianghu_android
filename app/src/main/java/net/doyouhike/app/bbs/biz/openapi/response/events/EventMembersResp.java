package net.doyouhike.app.bbs.biz.openapi.response.events;

import java.util.List;

/**
 * 作者：luochangdong on 16/9/13
 * 描述：
 */
public class EventMembersResp {


    /**
     * items : [{"user":{"user_id":"261852f69458e2ea034e56d009b02744","internal_id":1508715,"nick_name":"凝固","user_name":"凝固","avatar":"files/faces/7/3/73417f104.jpg","sex":"female","mobile":"18873688664","real_name":"L"},"contact_name":"","contact_tel":"","insurance_number":"","role":1,"join_date":1472635320,"memo":"","source_type":"android"}]
     * photo_domain_path : http://static.test.doyouhike.net/
     */

    private String photo_domain_path;
    /**
     * user : {"user_id":"261852f69458e2ea034e56d009b02744","internal_id":1508715,"nick_name":"凝固","user_name":"凝固","avatar":"files/faces/7/3/73417f104.jpg","sex":"female","mobile":"18873688664","real_name":"L"}
     * contact_name :
     * contact_tel :
     * insurance_number :
     * role : 1
     * join_date : 1472635320
     * memo :
     * source_type : android
     */

    private List<ItemsBean> items;

    public String getPhoto_domain_path() {
        return photo_domain_path;
    }

    public void setPhoto_domain_path(String photo_domain_path) {
        this.photo_domain_path = photo_domain_path;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * user_id : 261852f69458e2ea034e56d009b02744
         * internal_id : 1508715
         * nick_name : 凝固
         * user_name : 凝固
         * avatar : files/faces/7/3/73417f104.jpg
         * sex : female
         * mobile : 18873688664
         * real_name : L
         */

        private UserBean user;
        private String contact_name;
        private String contact_tel;
        private String insurance_number;
        private int role;
        private int join_date;
        private String memo;
        private String source_type;

        private int isFollow;

        public int getIsFollow() {
            return isFollow;
        }

        public void setIsFollow(int isFollow) {
            this.isFollow = isFollow;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public String getContact_name() {
            return contact_name;
        }

        public void setContact_name(String contact_name) {
            this.contact_name = contact_name;
        }

        public String getContact_tel() {
            return contact_tel;
        }

        public void setContact_tel(String contact_tel) {
            this.contact_tel = contact_tel;
        }

        public String getInsurance_number() {
            return insurance_number;
        }

        public void setInsurance_number(String insurance_number) {
            this.insurance_number = insurance_number;
        }

        public int getRole() {
            return role;
        }

        public void setRole(int role) {
            this.role = role;
        }

        public int getJoin_date() {
            return join_date;
        }

        public void setJoin_date(int join_date) {
            this.join_date = join_date;
        }

        public String getMemo() {
            return memo;
        }

        public void setMemo(String memo) {
            this.memo = memo;
        }

        public String getSource_type() {
            return source_type;
        }

        public void setSource_type(String source_type) {
            this.source_type = source_type;
        }

        public static class UserBean {
            private String user_id;
            private int internal_id;
            private String nick_name;
            private String user_name;
            private String avatar;
            private String sex;
            private String mobile;
            private String real_name;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public int getInternal_id() {
                return internal_id;
            }

            public void setInternal_id(int internal_id) {
                this.internal_id = internal_id;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
            }

            public String getUser_name() {
                return user_name;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }
        }
    }
}
