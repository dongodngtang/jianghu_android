package net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines;

import com.google.gson.annotations.Expose;
import com.yolanda.nohttp.RequestMethod;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventPostResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/13
 * 描述：
 */
public class EventPost extends BasePostRequest {
    public EventPost() {
        setTime_out(20 * 1000);
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<EventPostResp>(EventPostResp.class);
    }

    @Override
    public String getSubUrl() {
        if (node_id == null)
            return OpenApiUrl.EVENTS;
        else {
            setRequestMethod(RequestMethod.PUT);
            return OpenApiUrl.EVENTS + "/" + node_id;
        }

    }

    private String node_id;

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    /**
     * title : test_title
     * departure_id : 12
     * gather_date : 1465219060
     * dests : [{"id":12,"name":"存在于数据库的目的地"}]
     * begin_date : 1465219060
     * days : 2
     * member_limit : 4
     * fee_type : AA
     * pub_forum_id : 4
     * pub_city_id : 2
     * pub_group_id : 1
     */

    @Expose
    private EventInfoBean event_info;
    @Expose
    private NodeTimeline.ItemsBean.NodeBean.LocationBean location;
    /**
     * event_info : {"title":"test_title","departure_id":"12","gather_date":"1465219060","dests":[{"id":12,"name":"存在于数据库的目的地"}],"begin_date":"1465219060","days":"2","member_limit":4,"fee_type":"AA","pub_forum_id":4,"pub_city_id":2,"pub_group_id":1}
     * location : {}
     * tag_ids : [10,110]
     * contents : [{"text":"测试文本测试文本","photo_id":"213","is_new":1}]
     */
    @Expose
    private List<Integer> tag_ids;
    /**
     * text : 测试文本测试文本
     * photo_id : 213
     * is_new : 1
     */
    @Expose
    private List<ContentsBean> contents;

    public EventInfoBean getEvent_info() {
        return event_info;
    }

    public void setEvent_info(EventInfoBean event_info) {
        this.event_info = event_info;
    }

    public NodeTimeline.ItemsBean.NodeBean.LocationBean getLocation() {
        return location;
    }

    public void setLocation(NodeTimeline.ItemsBean.NodeBean.LocationBean location) {
        this.location = location;
    }

    public List<Integer> getTag_ids() {
        return tag_ids;
    }

    public void setTag_ids(List<Integer> tag_ids) {
        this.tag_ids = tag_ids;
    }

    public List<ContentsBean> getContents() {
        return contents;
    }

    public void setContents(List<ContentsBean> contents) {
        this.contents = contents;
    }

    public static class EventInfoBean {
        @Expose
        private String title;
        @Expose
        private String departure_id;
        @Expose
        private String gather_date;
        @Expose
        private String begin_date;
        @Expose
        private String days;
        @Expose
        private int member_limit;
        @Expose
        private String fee_type;
        @Expose
        private int pub_forum_id;
        @Expose
        private int pub_city_id;
        @Expose
        private int pub_group_id;
        /**
         * id : 12
         * name : 存在于数据库的目的地
         */
        @Expose
        private List<DestsBean> dests;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDeparture_id() {
            return departure_id;
        }

        public void setDeparture_id(String departure_id) {
            this.departure_id = departure_id;
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

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public int getMember_limit() {
            return member_limit;
        }

        public void setMember_limit(int member_limit) {
            this.member_limit = member_limit;
        }

        public String getFee_type() {
            return fee_type;
        }

        public void setFee_type(String fee_type) {
            this.fee_type = fee_type;
        }

        public int getPub_forum_id() {
            return pub_forum_id;
        }

        public void setPub_forum_id(int pub_forum_id) {
            this.pub_forum_id = pub_forum_id;
        }

        public int getPub_city_id() {
            return pub_city_id;
        }

        public void setPub_city_id(int pub_city_id) {
            this.pub_city_id = pub_city_id;
        }

        public int getPub_group_id() {
            return pub_group_id;
        }

        public void setPub_group_id(int pub_group_id) {
            this.pub_group_id = pub_group_id;
        }

        public List<DestsBean> getDests() {
            return dests;
        }

        public void setDests(List<DestsBean> dests) {
            this.dests = dests;
        }

        public static class DestsBean {
            @Expose
            private String id;
            @Expose
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }


    public static class ContentsBean {
        @Expose
        private String text;
        @Expose
        private String photo_id;
        @Expose
        private String is_new;

        private String local_image;


        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getPhoto_id() {
            return photo_id;
        }

        public void setPhoto_id(String photo_id) {
            this.photo_id = photo_id;
        }

        public String getIs_new() {
            return is_new;
        }

        public void setIs_new(String is_new) {
            this.is_new = is_new;
        }
    }
}
