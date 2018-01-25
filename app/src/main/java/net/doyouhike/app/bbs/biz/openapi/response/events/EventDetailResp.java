package net.doyouhike.app.bbs.biz.openapi.response.events;

import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.chat.helper.Constant;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：luochangdong on 16/9/12
 * 描述：
 */
public class EventDetailResp {

    /**
     * nick_name : 江湖骗子啊
     * user_name : 江湖骗子啊ba39
     * user_id : 8d038308bb263c81465757691475bb59
     * internal_id : 1519526
     * avatar : files/faces/0/c/0c283e7f7.jpg
     * sex : female
     * events_num : 89
     */

    private LeaderUserBean leader_user;
    /**
     * leader_user : {"nick_name":"江湖骗子啊","user_name":"江湖骗子啊ba39","user_id":"8d038308bb263c81465757691475bb59","internal_id":1519526,"avatar":"files/faces/0/c/0c283e7f7.jpg","sex":"female","events_num":89}
     * ip : 113.87.127.41
     * source_type : PC
     * node_id : 6007225
     * node_type : event
     * title : 山野
     * created_at : 1473061084
     * gather_date : 0
     * begin_date : 1473609600
     * end_date : 1474905599
     * days : 15
     * fee_type : AA
     * event_status : recruiting
     * member_limit : 3
     * member_num : 2
     * old_topic_url :
     * from : {"city_id":440300,"name":"深圳","slug":"shenzhen"}
     * to : [{"dest_id":0,"dest_name":"林","dest_cat":"city"}]
     * tags : [{"tag_name":"山野","tag_id":179}]
     * banner : {"photo_path":"images/event/v2/no_pic2","photo_ext":"jpg"}
     * photo_domain_path : http://static.test.doyouhike.net/
     * content : [{"type":"text","content":" "}]
     */

    private String ip;
    private String source_type;
    private String node_id;
    private String node_type;
    private String title;
    private int created_at;
    private String gather_date;
    private String begin_date;
    private String end_date;
    private int days;
    private String fee_type;
    private String event_status;
    private int member_limit;
    private int member_num;
    private String old_topic_url;
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
    private String photo_domain_path;
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

    private List<BaseTag> tags;
    /**
     * type : text
     * content :
     */

    private List<ContentBean> content;

    public LeaderUserBean getLeader_user() {
        return leader_user;
    }

    public void setLeader_user(LeaderUserBean leader_user) {
        this.leader_user = leader_user;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getSource_type() {
        return source_type;
    }

    public void setSource_type(String source_type) {
        this.source_type = source_type;
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

    public String getGather_date() {
        return gather_date;
    }

    public void setGather_date(String gather_date) {
        this.gather_date = gather_date;
    }

    public String getBegin_date() {
        return begin_date;
    }

    public void setBegin_date(String begin_date) {
        this.begin_date = begin_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getEvent_status() {
        return event_status;
    }

    public void setEvent_status(String event_status) {
        this.event_status = event_status;
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

    public String getOld_topic_url() {
        return old_topic_url;
    }

    public void setOld_topic_url(String old_topic_url) {
        this.old_topic_url = old_topic_url;
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

    public String getPhoto_domain_path() {
        return photo_domain_path;
    }

    public void setPhoto_domain_path(String photo_domain_path) {
        this.photo_domain_path = photo_domain_path;
    }

    public List<ToBean> getTo() {
        return to;
    }

    public void setTo(List<ToBean> to) {
        this.to = to;
    }

    public List<BaseTag> getTags() {
        return tags;
    }

    public void setTags(List<BaseTag> tags) {
        this.tags = tags;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class LeaderUserBean {
        private String nick_name;
        private String user_name;
        private String user_id;
        private int internal_id;
        private String avatar;
        private String sex;
        private String event_num;

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

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getEvent_num() {
            return event_num;
        }

        public void setEvent_num(String events_num) {
            this.event_num = events_num;
        }
    }

    public static class FromBean {
        private int city_id;
        private String name;
        private String slug;

        public int getCity_id() {
            return city_id;
        }

        public void setCity_id(int city_id) {
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

    public static class ToBean {
        private String dest_id;
        private String dest_name;
        private String dest_cat;
        private String node_slug;

        public String getNode_slug() {
            return node_slug;
        }

        public void setNode_slug(String node_slug) {
            this.node_slug = node_slug;
        }

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


    public static class ContentBean implements Serializable {
        private String type = Constant.IMAGE;
        private String content;
        private String whole_photo_path;
        private String photo_id;

        private String is_new;
        public static final int UPLOAD_OK = 1;
        public static final int UPLOADING = 2;
        public static final int UPLOAD_FAIL = 0;
        private int status = UPLOAD_FAIL;

        public ContentBean() {
        }

        public ContentBean(String whole_photo_path) {
            this.whole_photo_path = whole_photo_path;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof ContentBean)) return false;
            final ContentBean other = (ContentBean) o;
            if (this.getWhole_photo_path().equals(other.getWhole_photo_path()))
                return true;
            else
                return false;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }

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

        public String getWhole_photo_path() {
            return whole_photo_path;
        }

        public void setWhole_photo_path(String whole_photo_path) {
            this.whole_photo_path = whole_photo_path;
        }

        public String getPhoto_id() {
            return photo_id;
        }

        public void setPhoto_id(String photo_id) {
            this.photo_id = photo_id;
        }
    }
}
