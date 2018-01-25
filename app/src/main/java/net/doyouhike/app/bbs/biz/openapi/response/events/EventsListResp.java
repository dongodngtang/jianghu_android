package net.doyouhike.app.bbs.biz.openapi.response.events;

import java.util.List;

/**
 * 作者：luochangdong on 16/9/9
 * 描述：
 */
public class EventsListResp {


    /**
     * page_index : 1
     * page_size : 10
     * page_count : 6
     * total_records : 56
     */

    private PageBean page;


    private String photo_domain_path;
    /**
     * node_id : 6007225
     * node_type : event
     * title : 山野
     * created_at : 1473061084
     * gather_date : 0
     * begin_date : 1473609600
     * end_date : 1474905599
     * days : 15
     * fee_type : AA
     * event_state : recruiting
     * member_limit : 3
     * member_num : 2
     * from : {"city_id":440300,"name":"深圳","slug":"shenzhen"}
     * to : [{"dest_id":0,"dest_name":"林","dest_cat":"city"}]
     * banner : {"photo_path":"images/event/v2/no_pic2","photo_ext":"jpg"}
     * leader_user : {"nick_name":"江湖骗子啊","user_name":"江湖骗子啊ba39","user_id":"8d038308bb263c81465757691475bb59","internal_id":1519526,"avatar":"files/faces/0/c/0c283e7f7.jpg"}
     * tags : [{"tag_name":"山野","tag_id":179}]
     */

    private List<ItemsBean> items;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

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

    public static class PageBean {
        private int page_index;
        private int page_size;
        private int page_count;
        private int total_records;

        public int getPage_index() {
            return page_index;
        }

        public void setPage_index(int page_index) {
            this.page_index = page_index;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }

        public int getTotal_records() {
            return total_records;
        }

        public void setTotal_records(int total_records) {
            this.total_records = total_records;
        }
    }

    public static class ItemsBean {
        private String node_id;
        private String node_type;
        private String title;
        private int created_at;
        private int gather_date;
        private String begin_date;
        private int end_date;
        private String days;
        private String fee_type;
        private String event_status;
        private int member_limit;
        private int member_num;
        /**
         * city_id : 440300
         * name : 深圳
         * slug : shenzhen
         */

        private FromBean from;
        /**
         * photo_path : images/event/v2/no_pic2
         * photo_ext : jpg
         */

        private BannerBean banner;
        /**
         * nick_name : 江湖骗子啊
         * user_name : 江湖骗子啊ba39
         * user_id : 8d038308bb263c81465757691475bb59
         * internal_id : 1519526
         * avatar : files/faces/0/c/0c283e7f7.jpg
         */

        private LeaderUserBean leader_user;
        /**
         * dest_id : 0
         * dest_name : 林
         * dest_cat : city
         */

        private List<ToBean> to;
        /**
         * tag_name : 山野
         * tag_id : 179
         */

        private List<TagsBean> tags;

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

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCreated_at() {
            return created_at;
        }

        public void setCreated_at(int created_at) {
            this.created_at = created_at;
        }

        public int getGather_date() {
            return gather_date;
        }

        public void setGather_date(int gather_date) {
            this.gather_date = gather_date;
        }

        public String getBegin_date() {
            return begin_date;
        }

        public void setBegin_date(String begin_date) {
            this.begin_date = begin_date;
        }

        public int getEnd_date() {
            return end_date;
        }

        public void setEnd_date(int end_date) {
            this.end_date = end_date;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getFee_type() {
            return fee_type;
        }

        public void setFee_type(String fee_type) {
            this.fee_type = fee_type;
        }

        public String getEvent_states() {
            return event_status;
        }

        public void setEvent_states(String event_state) {
            this.event_status = event_state;
        }

        public int getMember_limit() {
            return member_limit;
        }

        public void setMember_limit(int member_limit) {
            this.member_limit = member_limit;
        }

        public int getMember_num() {
            return member_num;
        }

        public void setMember_num(int member_num) {
            this.member_num = member_num;
        }

        public FromBean getFrom() {
            return from;
        }

        public void setFrom(FromBean from) {
            this.from = from;
        }

        public BannerBean getBanner() {
            return banner;
        }

        public void setBanner(BannerBean banner) {
            this.banner = banner;
        }

        public LeaderUserBean getLeader_user() {
            return leader_user;
        }

        public void setLeader_user(LeaderUserBean leader_user) {
            this.leader_user = leader_user;
        }

        public List<ToBean> getTo() {
            return to;
        }

        public void setTo(List<ToBean> to) {
            this.to = to;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public static class FromBean {
            private String city_id;
            private String name;
            private String slug;

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getSlug() {
                return slug;
            }

            public void setSlug(String slug) {
                this.slug = slug;
            }
        }

        public static class BannerBean {
            private String photo_path;
            private String photo_ext;

            public String getPhoto_path() {
                return photo_path;
            }

            public void setPhoto_path(String photo_path) {
                this.photo_path = photo_path;
            }

            public String getPhoto_ext() {
                return photo_ext;
            }

            public void setPhoto_ext(String photo_ext) {
                this.photo_ext = photo_ext;
            }
        }

        public static class LeaderUserBean {
            private String nick_name;
            private String user_name;
            private String user_id;
            private int internal_id;
            private String avatar;

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

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }
        }

        public static class ToBean {
            private String dest_id;
            private String dest_name;
            private String dest_cat;

            public String getDest_id() {
                return dest_id;
            }

            public void setDest_id(String dest_id) {
                this.dest_id = dest_id;
            }

            public String getDest_name() {
                return dest_name;
            }

            public void setDest_name(String dest_name) {
                this.dest_name = dest_name;
            }

            public String getDest_cat() {
                return dest_cat;
            }

            public void setDest_cat(String dest_cat) {
                this.dest_cat = dest_cat;
            }
        }

        public static class TagsBean {
            private String tag_name;
            private int tag_id;

            public String getTag_name() {
                return tag_name;
            }

            public void setTag_name(String tag_name) {
                this.tag_name = tag_name;
            }

            public int getTag_id() {
                return tag_id;
            }

            public void setTag_id(int tag_id) {
                this.tag_id = tag_id;
            }
        }
    }
}
