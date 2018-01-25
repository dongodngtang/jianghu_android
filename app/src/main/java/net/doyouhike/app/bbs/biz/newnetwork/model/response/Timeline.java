package net.doyouhike.app.bbs.biz.newnetwork.model.response;

import com.google.gson.annotations.SerializedName;

import net.doyouhike.app.bbs.biz.entity.TimeLineUserInfoEntity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by zaitu on 15-11-26.
 */
public class Timeline implements Serializable {


    private boolean expand = false;

    public boolean isExpand() {
        return expand;
    }

    public void setExpand(boolean expand) {
        this.expand = expand;
    }

    /**
     * 约伴编辑删除的photoId
     */
    private List<String> del_edit_event_photoId;

    public List<String> getDel_edit_event_photoId() {
        return del_edit_event_photoId;
    }

    public void setDel_edit_event_photoId(List<String> del_edit_event_photoId) {
        this.del_edit_event_photoId = del_edit_event_photoId;
    }

    /**
     * 1等待发送 2 正在发送 3 发送成功 4 发送失败
     */
    private int releaseStatus = 0;

    public int getReleaseStatus() {
        return releaseStatus;
    }

    public void setReleaseStatus(int releaseStatus) {
        this.releaseStatus = releaseStatus;
    }

    /**
     * node_id : 6001960
     * user_info : {"user_id":"1502585","user_name":"wenzi222","avatar":"http://static.test.doyouhike.net/files/faces/b/7/b7ec29728.jpg","nick_name":"wenzi222"}
     * time : 1451544608
     * topic_id : 2336612
     * topic : {"url":"http://www.doyouhike.net/forum/mountain/2336612,0,0,0.html","title":"寻觅千家峒，登顶韭菜岭元旦休闲行","content":"有人说\u201c今生走过千家峒，来世都不再寂寞\u201d据《千家峒源流记》载，千家峒是瑶族先民繁衍生息、安居乐业的聚居地之一，只有一个石洞入内。峒的四周高山环绕，森林茂密，怪石峥嵘，瀑布高悬；峒内田土宽广，土质肥沃 ，有一条大河贯穿峒中。千余户瑶民同生活，共耕种，过着自由富裕的生活。到了宋、元之际，官府发现这个地方好，派官差入峒征收粮饷，瑶民热情款待，久留不归，官府误以为官差被杀，于是派兵围剿，逼得峒内瑶民纷纷出逃，背井离乡，流散到我国南方各地大山中去。千家峒是一方面积3平方公里，四周森林环绕，绿草繁茂，碧水涟绮的盆地，犹如美丽的大草原。2016/01/05/1/1975f2b4ce8200c82be2b4ff8acf1c30_b.jpg2016/01/05/6/697858924c02998a5da5e80fe7684060_b.jpg2016/01/05/9/9c6fdcb7779cd4d510871de866f591c5_b.jpg2016/01/05/1/1e78aeb1b9116e907dae809b721f65bd_b.jpg","icon":""}
     * forward_node_detail : {"minilog_type":"1","node_id":"6001948","tags":[{"tag_id":"1","desc":"山野穿越"}],"time":"1451369387","content":"估计红木哦咯呢都 趋之若鹜 猴急 5月1 花言巧语","like_num":"1","hit_num":"0","post_num":"2","favorite_num":"0","location":{"latitude":"0.000000000","longitude":"0.000000000","altitude":"0.0","city_id":"0","city":"","location":""},"FWNodeID":"0","user_info":{"avatar":"http://static.test.doyouhike.net/files/faces/b/7/b75b6102f.jpg","user_name":"yysyx","nick_name":"yysyx","user_id":"1296790"},"is_follow":0,"is_like":0,"is_favorite":0}
     * forward_node_id : 0
     * images : [{"PhotoID":"6658704","Path":"2015/12/29/c/c9c6a04718c8a3de718fb4be2dde8fcc","Ext":"jpg","desc":"0","height":"979","width":"734","real_file":"http://static.test.doyouhike.net/files/2015/12/29/c/c9c6a04718c8a3de718fb4be2dde8fcc.jpg","small_file":"http://static.test.doyouhike.net/files/2015/12/29/c/c9c6a04718c8a3de718fb4be2dde8fcc_s.jpg"},{"PhotoID":"6658705","Path":"2015/12/29/4/4b3229739cf3b018fb1b7eeac4ea38ea","Ext":"jpg","desc":"1","height":"979","width":"734","real_file":"http://static.test.doyouhike.net/files/2015/12/29/4/4b3229739cf3b018fb1b7eeac4ea38ea.jpg","small_file":"http://static.test.doyouhike.net/files/2015/12/29/4/4b3229739cf3b018fb1b7eeac4ea38ea_s.jpg"},{"PhotoID":"6658706","Path":"2015/12/29/c/c7e7c89dfd6205fddb716e4f5a4fe599","Ext":"jpg","desc":"2","height":"979","width":"734","real_file":"http://static.test.doyouhike.net/files/2015/12/29/c/c7e7c89dfd6205fddb716e4f5a4fe599.jpg","small_file":"http://static.test.doyouhike.net/files/2015/12/29/c/c7e7c89dfd6205fddb716e4f5a4fe599_s.jpg"},{"PhotoID":"6658707","Path":"2015/12/29/b/baea6a7bc0f9cdceadd2976940e41089","Ext":"jpg","desc":"3","height":"734","width":"979","real_file":"http://static.test.doyouhike.net/files/2015/12/29/b/baea6a7bc0f9cdceadd2976940e41089.jpg","small_file":"http://static.test.doyouhike.net/files/2015/12/29/b/baea6a7bc0f9cdceadd2976940e41089_s.jpg"},{"PhotoID":"6658708","Path":"2015/12/29/f/f0a74c507f25f2ed357832bdf11a774b","Ext":"jpg","desc":"4","height":"979","width":"734","real_file":"http://static.test.doyouhike.net/files/2015/12/29/f/f0a74c507f25f2ed357832bdf11a774b.jpg","small_file":"http://static.test.doyouhike.net/files/2015/12/29/f/f0a74c507f25f2ed357832bdf11a774b_s.jpg"},{"PhotoID":"6658709","Path":"2015/12/29/8/88c8dfa5b0c3f4871b2e4407959735ba","Ext":"jpg","desc":"5","height":"979","width":"734","real_file":"http://static.test.doyouhike.net/files/2015/12/29/8/88c8dfa5b0c3f4871b2e4407959735ba.jpg","small_file":"http://static.test.doyouhike.net/files/2015/12/29/8/88c8dfa5b0c3f4871b2e4407959735ba_s.jpg"},{"PhotoID":"6658710","Path":"2015/12/29/1/1c9aa5633b9027a0c4dcfaf86153c350","Ext":"jpg","desc":"6","height":"979","width":"734","real_file":"http://static.test.doyouhike.net/files/2015/12/29/1/1c9aa5633b9027a0c4dcfaf86153c350.jpg","small_file":"http://static.test.doyouhike.net/files/2015/12/29/1/1c9aa5633b9027a0c4dcfaf86153c350_s.jpg"},{"PhotoID":"6658711","Path":"2015/12/29/6/658dae201c2d2ad81410f3863be3c272","Ext":"jpg","desc":"7","height":"979","width":"734","real_file":"http://static.test.doyouhike.net/files/2015/12/29/6/658dae201c2d2ad81410f3863be3c272.jpg","small_file":"http://static.test.doyouhike.net/files/2015/12/29/6/658dae201c2d2ad81410f3863be3c272_s.jpg"},{"PhotoID":"6658712","Path":"2015/12/29/b/b48919b8d54f3dacfb5e533a9a811861","Ext":"jpg","desc":"8","height":"979","width":"734","real_file":"http://static.test.doyouhike.net/files/2015/12/29/b/b48919b8d54f3dacfb5e533a9a811861.jpg","small_file":"http://static.test.doyouhike.net/files/2015/12/29/b/b48919b8d54f3dacfb5e533a9a811861_s.jpg"}]
     * location : {"latitude":"0.000000000","longitude":"0.000000000","altitude":"0.0","city_id":"0","city":"","location":""}
     * event_id : 2285813
     * minilog_type : 4
     * event : {"title":"20151231测试","begin_date":"1451622600","end_date":"1452054600","from":"广州","to":["深圳","台湾"],"days":"5","status":3,"org_event_state":"1","joined":"1","limitation":"3","content":"去长途旅游啦","image":""}
     * content :
     * like_num : 0
     * post_num : 14
     * favorite_num : 0
     * hit_num : 0
     * is_follow : 0
     * is_like : 0
     * is_favorite : 0
     * tags : [{"tag_id":"1","desc":"山野穿越"}]
     */

    private String node_id;



    /**
     * user_id : 1502585
     * user_name : wenzi222
     * avatar : http://static.test.doyouhike.net/files/faces/b/7/b7ec29728.jpg
     * nick_name : wenzi222
     */
    private TimeLineUserInfoEntity user_info;
    private String time;
    private String topic_id;
    /**
     * url : http://www.doyouhike.net/forum/mountain/2336612,0,0,0.html
     * title : 寻觅千家峒，登顶韭菜岭元旦休闲行
     * content : 有人说“今生走过千家峒，来世都不再寂寞”据《千家峒源流记》载，千家峒是瑶族先民繁衍生息、安居乐业的聚居地之一，只有一个石洞入内。峒的四周高山环绕，森林茂密，怪石峥嵘，瀑布高悬；峒内田土宽广，土质肥沃 ，有一条大河贯穿峒中。千余户瑶民同生活，共耕种，过着自由富裕的生活。到了宋、元之际，官府发现这个地方好，派官差入峒征收粮饷，瑶民热情款待，久留不归，官府误以为官差被杀，于是派兵围剿，逼得峒内瑶民纷纷出逃，背井离乡，流散到我国南方各地大山中去。千家峒是一方面积3平方公里，四周森林环绕，绿草繁茂，碧水涟绮的盆地，犹如美丽的大草原。2016/01/05/1/1975f2b4ce8200c82be2b4ff8acf1c30_b.jpg2016/01/05/6/697858924c02998a5da5e80fe7684060_b.jpg2016/01/05/9/9c6fdcb7779cd4d510871de866f591c5_b.jpg2016/01/05/1/1e78aeb1b9116e907dae809b721f65bd_b.jpg
     * icon :
     */

    private TopicEntity topic;
    /**
     * minilog_type : 1
     * node_id : 6001948
     * tags : [{"tag_id":"1","desc":"山野穿越"}]
     * time : 1451369387
     * content : 估计红木哦咯呢都 趋之若鹜 猴急 5月1 花言巧语
     * like_num : 1
     * hit_num : 0
     * post_num : 2
     * favorite_num : 0
     * location : {"latitude":"0.000000000","longitude":"0.000000000","altitude":"0.0","city_id":"0","city":"","location":""}
     * FWNodeID : 0
     * user_info : {"avatar":"http://static.test.doyouhike.net/files/faces/b/7/b75b6102f.jpg","user_name":"yysyx","nick_name":"yysyx","user_id":"1296790"}
     * is_follow : 0
     * is_like : 0
     * is_favorite : 0
     */

    private ForwardNodeDetailEntity forward_node_detail;
    private String forward_node_id;
    private String FWNodeID;

    public String getFWNodeID() {
        return FWNodeID;
    }

    public void setFWNodeID(String FWNodeID) {
        this.FWNodeID = FWNodeID;
    }


    /**
     * latitude : 0.000000000
     * longitude : 0.000000000
     * altitude : 0.0
     * city_id : 0
     * city :
     * location :
     */

    private LocationEntity location;
    private String event_id;
    private int minilog_type;
    /**
     * title : 20151231测试
     * begin_date : 1451622600
     * end_date : 1452054600
     * from : 广州
     * to : ["深圳","台湾"]
     * days : 5
     * status : 3
     * org_event_state : 1
     * joined : 1
     * limitation : 3
     * content : 去长途旅游啦
     * image :
     */

    private EventEntity event;
    private String content;
    private String like_num;
    private String post_num;
    private String favorite_num;
    private String hit_num;
    private int is_follow;
    private int is_like;
    private int is_favorite;
    /**
     * PhotoID : 6658704
     * Path : 2015/12/29/c/c9c6a04718c8a3de718fb4be2dde8fcc
     * Ext : jpg
     * desc : 0
     * height : 979
     * width : 734
     * real_file : http://static.test.doyouhike.net/files/2015/12/29/c/c9c6a04718c8a3de718fb4be2dde8fcc.jpg
     * small_file : http://static.test.doyouhike.net/files/2015/12/29/c/c9c6a04718c8a3de718fb4be2dde8fcc_s.jpg
     */

    private List<ImagesEntity> images;
    /**
     * tag_id : 1
     * desc : 山野穿越
     */

    private List<TagsEntity> tags;

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public void setUser_info(TimeLineUserInfoEntity user_info) {
        this.user_info = user_info;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public void setTopic(TopicEntity topic) {
        this.topic = topic;
    }

    public void setForward_node_detail(ForwardNodeDetailEntity forward_node_detail) {
        this.forward_node_detail = forward_node_detail;
    }

    public void setForward_node_id(String forward_node_id) {
        this.forward_node_id = forward_node_id;
    }

    public void setLocation(LocationEntity location) {
        this.location = location;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public void setMinilog_type(int minilog_type) {
        this.minilog_type = minilog_type;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setLike_num(String like_num) {
        this.like_num = like_num;
    }

    public void setPost_num(String post_num) {
        this.post_num = post_num;
    }

    public void setFavorite_num(String favorite_num) {
        this.favorite_num = favorite_num;
    }

    public void setHit_num(String hit_num) {
        this.hit_num = hit_num;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public void setIs_like(int is_like) {
        this.is_like = is_like;
    }

    public void setIs_favorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }

    public void setImages(List<ImagesEntity> images) {
        this.images = images;
    }

    public void setTags(List<TagsEntity> tags) {
        this.tags = tags;
    }

    public String getNode_id() {
        return node_id;
    }

    public TimeLineUserInfoEntity getUser_info() {
        return user_info;
    }

    public String getTime() {
        return time;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public TopicEntity getTopic() {
        return topic;
    }

    public ForwardNodeDetailEntity getForward_node_detail() {
        return forward_node_detail;
    }

    public String getForward_node_id() {
        return forward_node_id;
    }

    public LocationEntity getLocation() {
        return location;
    }

    public String getEvent_id() {
        return event_id;
    }

    public int getMinilog_type() {
        return minilog_type;
    }

    public EventEntity getEvent() {
        return event;
    }

    public String getContent() {
        return content;
    }

    public String getLike_num() {
        return like_num;
    }

    public String getPost_num() {
        return post_num;
    }

    public String getFavorite_num() {
        return favorite_num;
    }

    public String getHit_num() {
        return hit_num;
    }

    public int getIs_follow() {
        return is_follow;
    }

    public int getIs_like() {
        return is_like;
    }

    public int getIs_favorite() {
        return is_favorite;
    }

    public List<ImagesEntity> getImages() {
        return images;
    }

    public List<TagsEntity> getTags() {
        return tags;
    }


    public static class TopicEntity implements Serializable{
        private String url;
        private String title;
        private String content;
        private String icon;

        public void setUrl(String url) {
            this.url = url;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getUrl() {
            return url;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

        public String getIcon() {
            return icon;
        }
    }

    public static class ForwardNodeDetailEntity implements Serializable{

        /**
         * PhotoID : 7081422
         * real_file : http://c1.zdb.io/files/2016/01/07/8/80cced7cc3cae5b16e13a1d15af01a70.jpg
         * small_file : http://c1.zdb.io/files/2016/01/07/8/80cced7cc3cae5b16e13a1d15af01a70_s.jpg
         * content : 0
         * height : 734
         * width : 734
         */

        private List<ImagesEntity> images;

        public List<ImagesEntity> getImages() {
            return images;
        }

        public void setImages(List<ImagesEntity> images) {
            this.images = images;
        }

        /**
         * 原帖是否被删除　１已删除
         */
        private String isDeleted;

        public void setIsDeleted(String isDeleted) {
            this.isDeleted = isDeleted;
        }

        public String getIsDeleted() {
            return isDeleted;
        }

        private int minilog_type;
        private String node_id;
        private String time;
        private String content;
        private String like_num;
        private String hit_num;
        private String post_num;
        private String favorite_num;
        /**
         * latitude : 0.000000000
         * longitude : 0.000000000
         * altitude : 0.0
         * city_id : 0
         * city :
         * location :
         */

        private LocationEntity location;
        private String FWNodeID;
        /**
         * avatar : http://static.test.doyouhike.net/files/faces/b/7/b75b6102f.jpg
         * user_name : yysyx
         * nick_name : yysyx
         * user_id : 1296790
         */

        private TimeLineUserInfoEntity user_info;
        private int is_follow;
        private int is_like;
        private int is_favorite;


        /**
         * 转发帖子
         */
        private TopicBean topic;

        public TopicBean getTopic() {
            return topic;
        }

        public void setTopic(TopicBean topic) {
            this.topic = topic;
        }

        /**
         * 转发活动

         */
        private EventEntity event;

        public EventEntity getEvent() {
            return event;
        }

        public void setEvent(EventEntity event) {
            this.event = event;
        }

        /**
         * tag_id : 1
         * desc : 山野穿越
         */

        private List<TagsEntity> tags;

        public void setMinilog_type(int minilog_type) {
            this.minilog_type = minilog_type;
        }

        public void setNode_id(String node_id) {
            this.node_id = node_id;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setLike_num(String like_num) {
            this.like_num = like_num;
        }

        public void setHit_num(String hit_num) {
            this.hit_num = hit_num;
        }

        public void setPost_num(String post_num) {
            this.post_num = post_num;
        }

        public void setFavorite_num(String favorite_num) {
            this.favorite_num = favorite_num;
        }

        public void setLocation(LocationEntity location) {
            this.location = location;
        }

        public void setFWNodeID(String FWNodeID) {
            this.FWNodeID = FWNodeID;
        }

        public void setUser_info(TimeLineUserInfoEntity user_info) {
            this.user_info = user_info;
        }

        public void setIs_follow(int is_follow) {
            this.is_follow = is_follow;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }

        public void setIs_favorite(int is_favorite) {
            this.is_favorite = is_favorite;
        }

        public void setTags(List<TagsEntity> tags) {
            this.tags = tags;
        }

        public int getMinilog_type() {
            return minilog_type;
        }

        public String getNode_id() {
            return node_id;
        }

        public String getTime() {
            return time;
        }

        public String getContent() {
            return content;
        }

        public String getLike_num() {
            return like_num;
        }

        public String getHit_num() {
            return hit_num;
        }

        public String getPost_num() {
            return post_num;
        }

        public String getFavorite_num() {
            return favorite_num;
        }

        public LocationEntity getLocation() {
            return location;
        }

        public String getFWNodeID() {
            return FWNodeID;
        }

        public TimeLineUserInfoEntity getUser_info() {
            return user_info;
        }

        public int getIs_follow() {
            return is_follow;
        }

        public int getIs_like() {
            return is_like;
        }

        public int getIs_favorite() {
            return is_favorite;
        }

        public List<TagsEntity> getTags() {
            return tags;
        }

        public static class LocationEntity implements Serializable{
            private String latitude;
            private String longitude;
            private String altitude;
            private String city_id;
            private String city;
            private String location;

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public void setAltitude(String altitude) {
                this.altitude = altitude;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public String getLatitude() {
                return latitude;
            }

            public String getLongitude() {
                return longitude;
            }

            public String getAltitude() {
                return altitude;
            }

            public String getCity_id() {
                return city_id;
            }

            public String getCity() {
                return city;
            }

            public String getLocation() {
                return location;
            }
        }

        public static class TopicBean {
            private String url;
            private String title;
            private String content;
            private String icon;

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }




        public static class ImagesEntity {
            private String PhotoID;
            private String real_file;
            private String small_file;
            private String content;
            private String height;
            private String width;

            public void setPhotoID(String PhotoID) {
                this.PhotoID = PhotoID;
            }

            public void setReal_file(String real_file) {
                this.real_file = real_file;
            }

            public void setSmall_file(String small_file) {
                this.small_file = small_file;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getPhotoID() {
                return PhotoID;
            }

            public String getReal_file() {
                return real_file;
            }

            public String getSmall_file() {
                return small_file;
            }

            public String getContent() {
                return content;
            }

            public String getHeight() {
                return height;
            }

            public String getWidth() {
                return width;
            }
        }

        public static class EventEntity {
            private String title;
            private String begin_date;
            private String end_date;
            private String days;
            private String content;
            private String image;
            private String status;
            private int me_role;

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setBegin_date(String begin_date) {
                this.begin_date = begin_date;
            }

            public void setEnd_date(String end_date) {
                this.end_date = end_date;
            }

            public void setDays(String days) {
                this.days = days;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public void setMe_role(int me_role) {
                this.me_role = me_role;
            }

            public String getTitle() {
                return title;
            }

            public String getBegin_date() {
                return begin_date;
            }

            public String getEnd_date() {
                return end_date;
            }

            public String getDays() {
                return days;
            }

            public String getContent() {
                return content;
            }

            public String getImage() {
                return image;
            }

            public int getMe_role() {
                return me_role;
            }

        }

    }


    public static class LocationEntity implements Serializable{
        private String latitude;
        private String longitude;
        private String altitude;
        private String city_id;
        //        private String city;
        private String location;
        //发布的热点位置
        private String poiId;
        /**
         * node_name : 深圳水库
         * node_slug : shenzuoshuiku
         * node_cat : poi
         */

        private LocationSlugBean location_slug;


        public String getPoiId() {
            return poiId;
        }

        public void setPoiId(String poiId) {
            this.poiId = poiId;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public void setAltitude(String altitude) {
            this.altitude = altitude;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

//        public void setCity(String city) {
//            this.city = city;
//        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getAltitude() {
            return altitude;
        }

        public String getCity_id() {
            return city_id;
        }

//        public String getCity() {
//            return city;
//        }

        public String getLocation() {
            return location;
        }

        public LocationSlugBean getLocation_slug() {
            return location_slug;
        }

        public void setLocation_slug(LocationSlugBean location_slug) {
            this.location_slug = location_slug;
        }

        public static class LocationSlugBean {
            private String node_name;
            private String node_slug;
            private String node_cat;

            public String getNode_name() {
                return node_name;
            }

            public void setNode_name(String node_name) {
                this.node_name = node_name;
            }

            public String getNode_slug() {
                return node_slug;
            }

            public void setNode_slug(String node_slug) {
                this.node_slug = node_slug;
            }

            public String getNode_cat() {
                return node_cat;
            }

            public void setNode_cat(String node_cat) {
                this.node_cat = node_cat;
            }
        }
    }

    public static class EventEntity implements Serializable{
        private String title;
        private String begin_date;
        private String end_date;
        private String from;
        private String days;
        private String status;
        private String org_event_state;
        private String joined;
        private String limitation;
        private String image;
        @SerializedName("otherTo")
        private List<ToBean> to;
        private List<GetDestByKeywordResp> dests;
        private int fee_type;
        private String gather_date;

        public String getGather_date() {
            return gather_date;
        }

        public void setGather_date(String gather_date) {
            this.gather_date = gather_date;
        }

        public int getFee_type() {
            return fee_type;
        }

        public void setFee_type(int fee_type) {
            this.fee_type = fee_type;
        }

        public List<GetDestByKeywordResp> getDests() {
            return dests;
        }

        public void setDests(List<GetDestByKeywordResp> dests) {
            this.dests = dests;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setBegin_date(String begin_date) {
            this.begin_date = begin_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public void setOrg_event_state(String org_event_state) {
            this.org_event_state = org_event_state;
        }

        public void setJoined(String joined) {
            this.joined = joined;
        }

        public void setLimitation(String limitation) {
            this.limitation = limitation;
        }


        public void setImage(String image) {
            this.image = image;
        }

        public void setTo(List<ToBean> to) {
            this.to = to;
        }

        public String getTitle() {
            return title;
        }

        public String getBegin_date() {
            return begin_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public String getFrom() {
            return from;
        }

        public String getDays() {
            return days;
        }

        public String getStatus() {
            return status;
        }

        public String getOrg_event_state() {
            return org_event_state;
        }

        public String getJoined() {
            return joined;
        }

        public String getLimitation() {
            return limitation;
        }

        public String getImage() {
            return image;
        }

        public List<ToBean> getTo() {
            return to;
        }
    }
    public static class ToBean implements Serializable{
        private String dest_id;
        private String dest_name;
        private Object dest_cat;

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

        public Object getDest_cat() {
            return dest_cat;
        }

        public void setDest_cat(Object dest_cat) {
            this.dest_cat = dest_cat;
        }
    }

    public static class ImagesEntity implements Serializable{

        public static final int UPLOAD_OK = 1;
        public static final int UPLOADING = 2;
        public static final int UPLOAD_FAIL = 0;

        private int status=UPLOAD_FAIL;
        private String PhotoID;
        private String Path;
        private String Ext;
        private String desc;
        private String height;
        private String width;
        private String real_file;
        private String small_file;
        private String inputStr;
        private int is_new;

        public int getIs_new() {
            return is_new;
        }

        public void setIs_new(int is_new) {
            this.is_new = is_new;
        }

        public String getInputStr() {
            return inputStr;
        }

        public void setInputStr(String inputStr) {
            this.inputStr = inputStr;
        }

        public void setPhotoID(String PhotoID) {
            this.PhotoID = PhotoID;
        }

        public void setPath(String Path) {
            this.Path = Path;
        }

        public void setExt(String Ext) {
            this.Ext = Ext;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public void setWidth(String width) {
            this.width = width;
        }

        public void setReal_file(String real_file) {
            this.real_file = real_file;
        }

        public void setSmall_file(String small_file) {
            this.small_file = small_file;
        }

        public String getPhotoID() {
            return PhotoID;
        }

        public String getPath() {
            return Path;
        }

        public String getExt() {
            return Ext;
        }

        public String getDesc() {
            return desc;
        }

        public String getHeight() {
            return height;
        }

        public String getWidth() {
            return width;
        }

        public String getReal_file() {
            return real_file;
        }

        public String getSmall_file() {
            return small_file;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }

    public static class TagsEntity implements Serializable{
        private String tag_id;
        private String desc;

        public void setTag_id(String tag_id) {
            this.tag_id = tag_id;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getTag_id() {
            return tag_id;
        }

        public String getDesc() {
            return desc;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Timeline timeline = (Timeline) o;

        if (minilog_type != timeline.minilog_type) return false;
        return !(node_id != null ? !node_id.equals(timeline.node_id) : timeline.node_id != null);

    }

    @Override
    public int hashCode() {
        int result = node_id != null ? node_id.hashCode() : 0;
        result = 31 * result + minilog_type;
        return result;
    }

    @Override
    public String toString() {
        return "Timeline{" +
                "node_id='" + node_id + '\'' +
                ", user_info=" + user_info +
                ", time='" + time + '\'' +
                ", topic_id='" + topic_id + '\'' +
                ", topic=" + topic +
                ", forward_node_detail=" + forward_node_detail +
                ", forward_node_id='" + forward_node_id + '\'' +
                ", location=" + location +
                ", event_id='" + event_id + '\'' +
                ", minilog_type=" + minilog_type +
                ", event=" + event +
                ", content='" + content + '\'' +
                ", like_num='" + like_num + '\'' +
                ", post_num='" + post_num + '\'' +
                ", favorite_num='" + favorite_num + '\'' +
                ", hit_num='" + hit_num + '\'' +
                ", is_follow=" + is_follow +
                ", is_like=" + is_like +
                ", is_favorite=" + is_favorite +
                ", images=" + images +
                ", tags=" + tags +
                '}';
    }
}

