package net.doyouhike.app.bbs.biz.openapi.response;

/**
 * 作者：luochangdong on 16/9/7
 * 描述：
 */
public class LoginUser {

    /**
     * openapi_token : 78d1a51ca0c95683b4253ffb67539b80
     * user : {"internal_id":1508715,"user_id":"261852f69458e2ea034e56d009b02744","nick_name":"凝固","user_name":null,"sex":"female","user_desc":" ","signature":"","avatar":"files/faces/7/3/73417f104.jpg","authed":true,"mobile":"18873688664","email":"","email_alt":null,"home_page":null,"qq":null,"msn":null,"city_id":220100,"city_name":"长春","city_slug":null,"hide_mail":false,"karma":0,"karma_max":0,"blog_name":null,"weibo":"","font_size":"14px","admin":false,"reg_date":"2015-11-24","user_level":0,"super_mod":false,"coreapi1_token":"bdaff82202767ff40ceff407874ce5bd","photo_domain_path":"http://static.test.doyouhike.net/","user_stats":{"follows_num":43,"fans_num":11,"events_num":18,"events_join_num":4,"minilogs_num":1,"posts_num":2}}
     */

    private String openapi_token;

    /**
     * internal_id : 1508715
     * user_id : 261852f69458e2ea034e56d009b02744
     * nick_name : 凝固
     * user_name : null
     * sex : female
     * user_desc :
     * signature :
     * avatar : files/faces/7/3/73417f104.jpg
     * authed : true
     * mobile : 18873688664
     * email :
     * email_alt : null
     * home_page : null
     * qq : null
     * msn : null
     * city_id : 220100
     * city_name : 长春
     * city_slug : null
     * hide_mail : false
     * karma : 0
     * karma_max : 0
     * blog_name : null
     * weibo :
     * font_size : 14px
     * admin : false
     * reg_date : 2015-11-24
     * user_level : 0
     * super_mod : false
     * coreapi1_token : bdaff82202767ff40ceff407874ce5bd
     * photo_domain_path : http://static.test.doyouhike.net/
     * user_stats : {"follows_num":43,"fans_num":11,"events_num":18,"events_join_num":4,"minilogs_num":1,"posts_num":2}
     */

    private UserBean user;

    public String getOpenapi_token() {
        return openapi_token;
    }

    public void setOpenapi_token(String openapi_token) {
        this.openapi_token = openapi_token;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class UserBean {
        private String internal_id;
        private String user_id;
        private String nick_name;
        private String user_name;
        private String sex;
        private String user_desc;
        private String signature;
        private String avatar;
        private boolean authed;
        private String mobile;
        private String email;
        private Object email_alt;
        private Object home_page;
        private Object qq;
        private Object msn;
        private int city_id;
        private String city_name;
        private Object city_slug;
        private boolean hide_mail;
        private int karma;
        private int karma_max;
        private Object blog_name;
        private String weibo;
        private String font_size;
        private boolean admin;
        private String reg_date;
        private int user_level;
        private boolean super_mod;
        private String coreapi1_token;
        private String sign_wap_topic_token;
        private String photo_domain_path;

        public String getSign_wap_topic_token() {
            return sign_wap_topic_token;
        }

        public void setSign_wap_topic_token(String sign_wap_topic_token) {
            this.sign_wap_topic_token = sign_wap_topic_token;
        }

        @Override
        public String toString() {
            return "UserBean{" +
                    "internal_id=" + internal_id +
                    ", user_id='" + user_id + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", user_name=" + user_name +
                    ", sex='" + sex + '\'' +
                    ", user_desc='" + user_desc + '\'' +
                    ", signature='" + signature + '\'' +
                    ", avatar='" + avatar + '\'' +
                    ", authed=" + authed +
                    ", mobile='" + mobile + '\'' +
                    ", email='" + email + '\'' +
                    ", email_alt=" + email_alt +
                    ", home_page=" + home_page +
                    ", qq=" + qq +
                    ", msn=" + msn +
                    ", city_id=" + city_id +
                    ", city_name='" + city_name + '\'' +
                    ", city_slug=" + city_slug +
                    ", hide_mail=" + hide_mail +
                    ", karma=" + karma +
                    ", karma_max=" + karma_max +
                    ", blog_name=" + blog_name +
                    ", weibo='" + weibo + '\'' +
                    ", font_size='" + font_size + '\'' +
                    ", admin=" + admin +
                    ", reg_date='" + reg_date + '\'' +
                    ", user_level=" + user_level +
                    ", super_mod=" + super_mod +
                    ", coreapi1_token='" + coreapi1_token + '\'' +
                    ", photo_domain_path='" + photo_domain_path + '\'' +
                    ", user_stats=" + user_stats +
                    '}';
        }

        /**
         * follows_num : 43
         * fans_num : 11
         * events_num : 18
         * events_join_num : 4
         * minilogs_num : 1
         * posts_num : 2
         */

        private UserStatsBean user_stats;

        public String getInternal_id() {
            return internal_id;
        }

        public void setInternal_id(String internal_id) {
            this.internal_id = internal_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getUser_desc() {
            return user_desc;
        }

        public void setUser_desc(String user_desc) {
            this.user_desc = user_desc;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public boolean isAuthed() {
            return authed;
        }

        public void setAuthed(boolean authed) {
            this.authed = authed;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Object getEmail_alt() {
            return email_alt;
        }

        public void setEmail_alt(Object email_alt) {
            this.email_alt = email_alt;
        }

        public Object getHome_page() {
            return home_page;
        }

        public void setHome_page(Object home_page) {
            this.home_page = home_page;
        }

        public Object getQq() {
            return qq;
        }

        public void setQq(Object qq) {
            this.qq = qq;
        }

        public Object getMsn() {
            return msn;
        }

        public void setMsn(Object msn) {
            this.msn = msn;
        }

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
            this.city_id = city_id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public Object getCity_slug() {
            return city_slug;
        }

        public void setCity_slug(Object city_slug) {
            this.city_slug = city_slug;
        }

        public boolean isHide_mail() {
            return hide_mail;
        }

        public void setHide_mail(boolean hide_mail) {
            this.hide_mail = hide_mail;
        }

        public int getKarma() {
            return karma;
        }

        public void setKarma(int karma) {
            this.karma = karma;
        }

        public int getKarma_max() {
            return karma_max;
        }

        public void setKarma_max(int karma_max) {
            this.karma_max = karma_max;
        }

        public Object getBlog_name() {
            return blog_name;
        }

        public void setBlog_name(Object blog_name) {
            this.blog_name = blog_name;
        }

        public String getWeibo() {
            return weibo;
        }

        public void setWeibo(String weibo) {
            this.weibo = weibo;
        }

        public String getFont_size() {
            return font_size;
        }

        public void setFont_size(String font_size) {
            this.font_size = font_size;
        }

        public boolean isAdmin() {
            return admin;
        }

        public void setAdmin(boolean admin) {
            this.admin = admin;
        }

        public String getReg_date() {
            return reg_date;
        }

        public void setReg_date(String reg_date) {
            this.reg_date = reg_date;
        }

        public int getUser_level() {
            return user_level;
        }

        public void setUser_level(int user_level) {
            this.user_level = user_level;
        }

        public boolean isSuper_mod() {
            return super_mod;
        }

        public void setSuper_mod(boolean super_mod) {
            this.super_mod = super_mod;
        }

        public String getCoreapi1_token() {
            return coreapi1_token;
        }

        public void setCoreapi1_token(String coreapi1_token) {
            this.coreapi1_token = coreapi1_token;
        }

        public String getPhoto_domain_path() {
            return photo_domain_path;
        }

        public void setPhoto_domain_path(String photo_domain_path) {
            this.photo_domain_path = photo_domain_path;
        }

        public UserStatsBean getUser_stats() {
            return user_stats;
        }

        public void setUser_stats(UserStatsBean user_stats) {
            this.user_stats = user_stats;
        }

        public static class UserStatsBean {
            private int follows_num;
            private int fans_num;
            private int events_num;
            private int events_join_num;
            private int minilogs_num;
            private int posts_num;

            public int getFollows_num() {
                return follows_num;
            }

            public void setFollows_num(int follows_num) {
                this.follows_num = follows_num;
            }

            public int getFans_num() {
                return fans_num;
            }

            public void setFans_num(int fans_num) {
                this.fans_num = fans_num;
            }

            public int getEvents_num() {
                return events_num;
            }

            public void setEvents_num(int events_num) {
                this.events_num = events_num;
            }

            public int getEvents_join_num() {
                return events_join_num;
            }

            public void setEvents_join_num(int events_join_num) {
                this.events_join_num = events_join_num;
            }

            public int getMinilogs_num() {
                return minilogs_num;
            }

            public void setMinilogs_num(int minilogs_num) {
                this.minilogs_num = minilogs_num;
            }

            public int getPosts_num() {
                return posts_num;
            }

            public void setPosts_num(int posts_num) {
                this.posts_num = posts_num;
            }
        }
    }


}
