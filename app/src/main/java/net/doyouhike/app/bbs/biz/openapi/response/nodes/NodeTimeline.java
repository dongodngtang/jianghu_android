package net.doyouhike.app.bbs.biz.openapi.response.nodes;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetDestByKeywordResp;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventDetailResp;
import net.doyouhike.app.bbs.chat.helper.Constant;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：luochangdong on 16/10/9
 * 描述：时间线
 */
public class NodeTimeline {


    /**
     * items : [{"node":{"node_id":6007387,"node_type":"event","minilog_type":"event","time":1475927706,"created_at":"2016-10-08T19:55:06.000+08:00","updated_at":"2016-10-08T19:55:06.000+08:00","updated_by":"coffee","topic":{"topic_id":2439621,"url":"http://www.doyouhike.net/group/10137/2439621,0,0,0.html?jhsign=9b451b6b9bd477ebe143e50ad9188ff4&uid=3001337","title":"0914testing","banner":"","created_at":"","updated_at":"","updated_by":0},"forward":{"node":{"user":{"user_id":"440d2d7b52b74c3fd4e356d009f42e5a","internal_id":8,"user_name":"spy","nick_name":"spy","sex":"male","avatar":"files/faces/face_image38.gif","user_desc":""},"node_id":6250877,"node_type":"discussion","minilog_type":"discussion","time":"1473748912","topic":{"topic_id":2439621,"url":"http://www.doyouhike.net/group/10137/2439621,0,0,0.html?jhsign=9b451b6b9bd477ebe143e50ad9188ff4&uid=3001337","title":"0914testing"},"node_counter":{"hit_num":0,"like_num":0,"post_num":0,"favorite_num":0,"comments_num":0}},"created_at":"","updated_at":"","updated_by":0},"content":{"text":"","photos":[{"photo_id":3,"path":"files/2016/09/18/e/e1c5124345b51426e7e7682bc6a79c43","ext":"jpg","content":"0","height":"809","width":"1079"}]},"location":{"latitude":"0.000000000","longitude":"0.000000000","altitude":"0.0","city_id":"0","city":"深圳","location":"深圳","location_slug":{"node_name":"深圳","node_slug":"shenzhen","node_cat":"city"}},"event":{"title":"【山水情缘】 第211期 梅后夜登（逢周一）","event_status":1,"image":"files/2016/09/13/0/01d133efb7870a5195796d06793f71e1_s.jpg"},"tags":[{"tag_name":"骑行","tag_id":3}],"user":{"user_id":"4417b1d0efa10f1372c456d009a42df6","internal_id":18,"user_name":"coffee","nick_name":"coffee","sex":"female","avatar":"files/faces/e/2/e2bfd05b5.jpg","user_desc":"刚好健康看看"}},"node_counter":{"hit_num":20,"like_num":4,"post_num":13,"favorite_num":20,"comments_num":17}}]
     * photo_domain_path : http://static.test.doyouhike.net/
     */

    private String photo_domain_path;

    private String doyouhike_domain_path;

    public String getDoyouhike_domain_path() {
        return doyouhike_domain_path;
    }

    public void setDoyouhike_domain_path(String doyouhike_domain_path) {
        this.doyouhike_domain_path = doyouhike_domain_path;
    }

    /**
     * node : {"node_id":6007387,"node_type":"event","minilog_type":"event","time":1475927706,"created_at":"2016-10-08T19:55:06.000+08:00","updated_at":"2016-10-08T19:55:06.000+08:00","updated_by":"coffee","topic":{"topic_id":2439621,"url":"http://www.doyouhike.net/group/10137/2439621,0,0,0.html?jhsign=9b451b6b9bd477ebe143e50ad9188ff4&uid=3001337","title":"0914testing","banner":"","created_at":"","updated_at":"","updated_by":0},"forward":{"node":{"user":{"user_id":"440d2d7b52b74c3fd4e356d009f42e5a","internal_id":8,"user_name":"spy","nick_name":"spy","sex":"male","avatar":"files/faces/face_image38.gif","user_desc":""},"node_id":6250877,"node_type":"discussion","minilog_type":"discussion","time":"1473748912","topic":{"topic_id":2439621,"url":"http://www.doyouhike.net/group/10137/2439621,0,0,0.html?jhsign=9b451b6b9bd477ebe143e50ad9188ff4&uid=3001337","title":"0914testing"},"node_counter":{"hit_num":0,"like_num":0,"post_num":0,"favorite_num":0,"comments_num":0}},"created_at":"","updated_at":"","updated_by":0},"content":{"text":"","photos":[{"photo_id":3,"path":"files/2016/09/18/e/e1c5124345b51426e7e7682bc6a79c43","ext":"jpg","content":"0","height":"809","width":"1079"}]},"location":{"latitude":"0.000000000","longitude":"0.000000000","altitude":"0.0","city_id":"0","city":"深圳","location":"深圳","location_slug":{"node_name":"深圳","node_slug":"shenzhen","node_cat":"city"}},"event":{"title":"【山水情缘】 第211期 梅后夜登（逢周一）","event_status":1,"image":"files/2016/09/13/0/01d133efb7870a5195796d06793f71e1_s.jpg"},"tags":[{"tag_name":"骑行","tag_id":3}],"user":{"user_id":"4417b1d0efa10f1372c456d009a42df6","internal_id":18,"user_name":"coffee","nick_name":"coffee","sex":"female","avatar":"files/faces/e/2/e2bfd05b5.jpg","user_desc":"刚好健康看看"}}
     * node_counter : {"hit_num":20,"like_num":4,"post_num":13,"favorite_num":20,"comments_num":17}
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            NodeTimeline.ItemsBean timeline = (NodeTimeline.ItemsBean) o;
            return (this.getNode().node_id != null ?
                    this.getNode().node_id.equals(timeline.getNode().node_id)
                    : timeline.getNode().node_id == null);


        }

        @Override
        public int hashCode() {
            int result = getNode().node_id != null ? getNode().node_id.hashCode() : 0;
            return result;
        }

        private String node_share_url;

        public String getNode_share_url() {
            return node_share_url;
        }

        public void setNode_share_url(String node_share_url) {
            this.node_share_url = node_share_url;
        }

        /**
         * node_id : 6007387
         * node_type : event
         * minilog_type : event
         * time : 1475927706
         * created_at : 2016-10-08T19:55:06.000+08:00
         * updated_at : 2016-10-08T19:55:06.000+08:00
         * updated_by : coffee
         * topic : {"topic_id":2439621,"url":"http://www.doyouhike.net/group/10137/2439621,0,0,0.html?jhsign=9b451b6b9bd477ebe143e50ad9188ff4&uid=3001337","title":"0914testing","banner":"","created_at":"","updated_at":"","updated_by":0}
         * forward : {"node":{"user":{"user_id":"440d2d7b52b74c3fd4e356d009f42e5a","internal_id":8,"user_name":"spy","nick_name":"spy","sex":"male","avatar":"files/faces/face_image38.gif","user_desc":""},"node_id":6250877,"node_type":"discussion","minilog_type":"discussion","time":"1473748912","topic":{"topic_id":2439621,"url":"http://www.doyouhike.net/group/10137/2439621,0,0,0.html?jhsign=9b451b6b9bd477ebe143e50ad9188ff4&uid=3001337","title":"0914testing"},"node_counter":{"hit_num":0,"like_num":0,"post_num":0,"favorite_num":0,"comments_num":0}},"created_at":"","updated_at":"","updated_by":0}
         * content : {"text":"","photos":[{"photo_id":3,"path":"files/2016/09/18/e/e1c5124345b51426e7e7682bc6a79c43","ext":"jpg","content":"0","height":"809","width":"1079"}]}
         * location : {"latitude":"0.000000000","longitude":"0.000000000","altitude":"0.0","city_id":"0","city":"深圳","location":"深圳","location_slug":{"node_name":"深圳","node_slug":"shenzhen","node_cat":"city"}}
         * event : {"title":"【山水情缘】 第211期 梅后夜登（逢周一）","event_status":1,"image":"files/2016/09/13/0/01d133efb7870a5195796d06793f71e1_s.jpg"}
         * tags : [{"tag_name":"骑行","tag_id":3}]
         * user : {"user_id":"4417b1d0efa10f1372c456d009a42df6","internal_id":18,"user_name":"coffee","nick_name":"coffee","sex":"female","avatar":"files/faces/e/2/e2bfd05b5.jpg","user_desc":"刚好健康看看"}
         */

        private NodeBean node;
        /**
         * hit_num : 20
         * like_num : 4
         * post_num : 13
         * favorite_num : 20
         * comments_num : 17
         */

        private NodeCounterBean node_counter;

        public NodeBean getNode() {
            return node;
        }

        public void setNode(NodeBean node) {
            this.node = node;
        }

        public NodeCounterBean getNode_counter() {
            return node_counter;
        }

        public void setNode_counter(NodeCounterBean node_counter) {
            this.node_counter = node_counter;
        }

        public static class NodeBean implements Serializable {
            public NodeBean(String node_id) {
                this.node_id = node_id;
            }

            public NodeBean() {
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


            private String node_id;
            private String node_type;
            private String minilog_type;
            private long time;
            private String created_at;
            private String updated_at;
            private String updated_by;

            /**
             * topic_id : 2439621
             * url : http://www.doyouhike.net/group/10137/2439621,0,0,0.html?jhsign=9b451b6b9bd477ebe143e50ad9188ff4&uid=3001337
             * title : 0914testing
             * banner :
             * created_at :
             * updated_at :
             * updated_by : 0
             */

            private TopicBean topic;
            /**
             * node : {"user":{"user_id":"440d2d7b52b74c3fd4e356d009f42e5a","internal_id":8,"user_name":"spy","nick_name":"spy","sex":"male","avatar":"files/faces/face_image38.gif","user_desc":""},"node_id":6250877,"node_type":"discussion","minilog_type":"discussion","time":"1473748912","topic":{"topic_id":2439621,"url":"http://www.doyouhike.net/group/10137/2439621,0,0,0.html?jhsign=9b451b6b9bd477ebe143e50ad9188ff4&uid=3001337","title":"0914testing"},"node_counter":{"hit_num":0,"like_num":0,"post_num":0,"favorite_num":0,"comments_num":0}}
             * created_at :
             * updated_at :
             * updated_by : 0
             */

            private ForwardBean forward;
            /**
             * text :
             * photos : [{"photo_id":3,"path":"files/2016/09/18/e/e1c5124345b51426e7e7682bc6a79c43","ext":"jpg","content":"0","height":"809","width":"1079"}]
             */

            private ContentBean content;
            /**
             * latitude : 0.000000000
             * longitude : 0.000000000
             * altitude : 0.0
             * city_id : 0
             * city : 深圳
             * location : 深圳
             * location_slug : {"node_name":"深圳","node_slug":"shenzhen","node_cat":"city"}
             */

            private LocationBean location;
            /**
             * title : 【山水情缘】 第211期 梅后夜登（逢周一）
             * event_status : 1
             * image : files/2016/09/13/0/01d133efb7870a5195796d06793f71e1_s.jpg
             */

            private EventBean event;
            /**
             * user_id : 4417b1d0efa10f1372c456d009a42df6
             * internal_id : 18
             * user_name : coffee
             * nick_name : coffee
             * sex : female
             * avatar : files/faces/e/2/e2bfd05b5.jpg
             * user_desc : 刚好健康看看
             */

            private UserBean user;
            /**
             * tag_name : 骑行
             * tag_id : 3
             */

            private List<BaseTag> tags;

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

            public String getMinilog_type() {
                return minilog_type;
            }

            public void setMinilog_type(String minilog_type) {
                this.minilog_type = minilog_type;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
            }

            public String getUpdated_by() {
                return updated_by;
            }

            public void setUpdated_by(String updated_by) {
                this.updated_by = updated_by;
            }

            public TopicBean getTopic() {
                return topic;
            }

            public void setTopic(TopicBean topic) {
                this.topic = topic;
            }

            public ForwardBean getForward() {
                return forward;
            }

            public void setForward(ForwardBean forward) {
                this.forward = forward;
            }

            public ContentBean getContent() {
                return content;
            }

            public void setContent(ContentBean content) {
                this.content = content;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public EventBean getEvent() {
                return event;
            }

            public void setEvent(EventBean event) {
                this.event = event;
            }

            public UserBean getUser() {
                return user;
            }

            public void setUser(UserBean user) {
                this.user = user;
            }

            public List<BaseTag> getTags() {
                return tags;
            }

            public void setTags(List<BaseTag> tags) {
                this.tags = tags;
            }

            public static class TopicBean {
                private int topic_id;
                private String url;
                private String title;
                private String banner;
                private String created_at;
                private String updated_at;
                private int updated_by;

                public int getTopic_id() {
                    return topic_id;
                }

                public void setTopic_id(int topic_id) {
                    this.topic_id = topic_id;
                }

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

                public String getBanner() {
                    return banner;
                }

                public void setBanner(String banner) {
                    this.banner = banner;
                }

                public String getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(String created_at) {
                    this.created_at = created_at;
                }

                public String getUpdated_at() {
                    return updated_at;
                }

                public void setUpdated_at(String updated_at) {
                    this.updated_at = updated_at;
                }

                public int getUpdated_by() {
                    return updated_by;
                }

                public void setUpdated_by(int updated_by) {
                    this.updated_by = updated_by;
                }
            }

            public static class ForwardBean {
                /**
                 * user : {"user_id":"440d2d7b52b74c3fd4e356d009f42e5a","internal_id":8,"user_name":"spy","nick_name":"spy","sex":"male","avatar":"files/faces/face_image38.gif","user_desc":""}
                 * node_id : 6250877
                 * node_type : discussion
                 * minilog_type : discussion
                 * time : 1473748912
                 * topic : {"topic_id":2439621,"url":"http://www.doyouhike.net/group/10137/2439621,0,0,0.html?jhsign=9b451b6b9bd477ebe143e50ad9188ff4&uid=3001337","title":"0914testing"}
                 * node_counter : {"hit_num":0,"like_num":0,"post_num":0,"favorite_num":0,"comments_num":0}
                 */

                private NodeBean node;
                private String created_at;
                private String updated_at;
                private int updated_by;

                public NodeBean getNode() {
                    return node;
                }

                public void setNode(NodeBean node) {
                    this.node = node;
                }

                public String getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(String created_at) {
                    this.created_at = created_at;
                }

                public String getUpdated_at() {
                    return updated_at;
                }

                public void setUpdated_at(String updated_at) {
                    this.updated_at = updated_at;
                }

                public int getUpdated_by() {
                    return updated_by;
                }

                public void setUpdated_by(int updated_by) {
                    this.updated_by = updated_by;
                }

            }

            public static class ContentBean {

                private boolean expand = false;

                public boolean isExpand() {
                    return expand;
                }

                public void setExpand(boolean expand) {
                    this.expand = expand;
                }

                private String text;
                /**
                 * photo_id : 3
                 * path : files/2016/09/18/e/e1c5124345b51426e7e7682bc6a79c43
                 * ext : jpg
                 * content : 0
                 * height : 809
                 * width : 1079
                 */

                private List<PhotosBean> photos;

                public String getText() {
                    return text;
                }

                public void setText(String text) {
                    this.text = text;
                }

                public List<PhotosBean> getPhotos() {
                    return photos;
                }

                public void setPhotos(List<PhotosBean> photos) {
                    this.photos = photos;
                }

                public static class PhotosBean {
                    private int photo_id;
                    private String path;
                    private String ext;
                    private String content;
                    private String height;
                    private String width;

                    public String getWhole_photo_path() {
                        return whole_photo_path;
                    }

                    public void setWhole_photo_path(String whole_photo_path) {
                        this.whole_photo_path = whole_photo_path;
                    }

                    private String whole_photo_path;

                    public String getPhotoUrl() {
                        if (whole_photo_path == null)
                            return Constant.PHOTO_DOMAIN_PATH + path + "." + ext;
                        else
                            return whole_photo_path;
                    }

                    public int getPhoto_id() {
                        return photo_id;
                    }

                    public void setPhoto_id(int photo_id) {
                        this.photo_id = photo_id;
                    }

                    public String getPath() {
                        return path;
                    }

                    public void setPath(String path) {
                        this.path = path;
                    }

                    public String getExt() {
                        return ext;
                    }

                    public void setExt(String ext) {
                        this.ext = ext;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getHeight() {
                        return height;
                    }

                    public void setHeight(String height) {
                        this.height = height;
                    }

                    public String getWidth() {
                        return width;
                    }

                    public void setWidth(String width) {
                        this.width = width;
                    }
                }
            }

            public static class LocationBean {
                @Expose
                private String latitude;
                @Expose
                private String longitude;
                @Expose
                private String altitude;
                @Expose
                private String city_id;
                private String city;
                @Expose
                private String location_name;
                /**
                 * node_name : 深圳
                 * node_slug : shenzhen
                 * node_cat : city
                 */

                private String dest_id;

                private Dest dest;

                public String getDest_id() {
                    return dest_id;
                }

                public void setDest_id(String dest_id) {
                    this.dest_id = dest_id;
                }

                public String getLatitude() {
                    return latitude;
                }

                public void setLatitude(String latitude) {
                    this.latitude = latitude;
                }

                public String getLongitude() {
                    return longitude;
                }

                public void setLongitude(String longitude) {
                    this.longitude = longitude;
                }

                public String getAltitude() {
                    return altitude;
                }

                public void setAltitude(String altitude) {
                    this.altitude = altitude;
                }

                public String getCity_id() {
                    return city_id;
                }

                public void setCity_id(String city_id) {
                    this.city_id = city_id;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getLocationName() {
                    return location_name;
                }

                public void setLocationName(String location) {
                    this.location_name = location;
                }

                public Dest getDest() {
                    return dest;
                }

                public void setDest(Dest location_slug) {
                    this.dest = location_slug;
                }

                public static class Dest {
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

            public static class EventBean {
                private String title;
                private String event_status;
                /**
                 * photo_id : 6664520
                 * photo_path : null
                 * photo_ext : null
                 */

                private BannerBean banner;


                /*=============================未发布约伴添加字段=========================================*/

                private String gather_date;
                private String begin_date;
                private String end_date;
                private String days;
                private String fee_type;
                private String member_limit;
                private String departure_id;
                private String departure_name;
                private List<GetDestByKeywordResp> dests;
                private String joined;
                private String event_state;

                private List<EventDetailResp.ContentBean> event_contents;
                private List<String> del_photo_ids;

                /*=============================END=========================================*/

                public List<String> getDel_photo_ids() {
                    return del_photo_ids;
                }

                public void setDel_photo_ids(List<String> del_photo_ids) {
                    this.del_photo_ids = del_photo_ids;
                }

                public List<EventDetailResp.ContentBean> getEvent_contents() {
                    return event_contents;
                }

                public void setEvent_contents(List<EventDetailResp.ContentBean> contents) {
                    this.event_contents = contents;
                }

                public String getEvent_state() {
                    return event_state;
                }

                public void setEvent_state(String event_state) {
                    this.event_state = event_state;
                }

                public String getJoined() {
                    return joined;
                }

                public void setJoined(String joined) {
                    this.joined = joined;
                }

                public List<GetDestByKeywordResp> getDests() {
                    return dests;
                }

                public void setDests(List<GetDestByKeywordResp> dests) {
                    this.dests = dests;
                }

                public String getDeparture_name() {
                    return departure_name;
                }

                public void setDeparture_name(String departure_name) {
                    this.departure_name = departure_name;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getEvent_status() {
                    return event_status;
                }

                public String getImageUrl() {
                    if (banner != null)
                        return banner.getPhoto_path() + "." + banner.getPhoto_ext();
                    else
                        return "";
                }

                public void setEvent_status(String event_status) {
                    this.event_status = event_status;
                }

                public BannerBean getBanner() {
                    return banner;
                }

                public void setBanner(BannerBean banner) {
                    this.banner = banner;
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

                public String getMember_limit() {
                    return member_limit;
                }

                public void setMember_limit(String member_limit) {
                    this.member_limit = member_limit;
                }

                public String getDeparture_id() {
                    return departure_id;
                }

                public void setDeparture_id(String departure_id) {
                    this.departure_id = departure_id;
                }


                public static class BannerBean {
                    private int photo_id;
                    private String photo_path;
                    private String photo_ext;

                    public int getPhoto_id() {
                        return photo_id;
                    }

                    public void setPhoto_id(int photo_id) {
                        this.photo_id = photo_id;
                    }

                    public Object getPhoto_path() {
                        return photo_path;
                    }

                    public void setPhoto_path(String photo_path) {
                        this.photo_path = photo_path;
                    }

                    public Object getPhoto_ext() {
                        return photo_ext;
                    }

                    public void setPhoto_ext(String photo_ext) {
                        this.photo_ext = photo_ext;
                    }
                }
            }

            public static class UserBean {
                private String user_id;
                private String internal_id;
                private String user_name;
                private String nick_name;
                private String sex;
                private String avatar;
                private String user_desc;

                public String getUser_id() {
                    return user_id;
                }

                public void setUser_id(String user_id) {
                    this.user_id = user_id;
                }

                public String getInternal_id() {
                    return internal_id;
                }

                public void setInternal_id(String internal_id) {
                    this.internal_id = internal_id;
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

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public String getAvatar() {
                    return avatar;
                }

                public void setAvatar(String avatar) {
                    this.avatar = avatar;
                }

                public String getUser_desc() {
                    return user_desc;
                }

                public void setUser_desc(String user_desc) {
                    this.user_desc = user_desc;
                }
            }

        }

        public static class NodeCounterBean {
            private int hit_num;
            private int like_num;
            private int post_num;
            private int favorite_num;
            private int comments_num;
            /**
             * favorited : false
             * liked : false
             * social :
             */

            private boolean favorited;
            private boolean liked;
            private String social;
            private Integer attend;

            public Integer getAttend() {
                return attend;
            }

            public void setAttend(Integer attend) {
                this.attend = attend;
            }

            public int getHit_num() {
                return hit_num;
            }

            public void setHit_num(int hit_num) {
                this.hit_num = hit_num;
            }

            public int getLike_num() {
                return like_num;
            }

            public void setLike_num(int like_num) {
                this.like_num = like_num;
            }

            public int getPost_num() {
                return post_num;
            }

            public void setPost_num(int post_num) {
                this.post_num = post_num;
            }

            public int getFavorite_num() {
                return favorite_num;
            }

            public void setFavorite_num(int favorite_num) {
                this.favorite_num = favorite_num;
            }

            public int getComments_num() {
                return comments_num;
            }

            public void setComments_num(int comments_num) {
                this.comments_num = comments_num;
            }

            public boolean isFavorited() {
                return favorited;
            }

            public void setFavorited(boolean favorited) {
                this.favorited = favorited;
            }

            public boolean isLiked() {
                return liked;
            }

            public void setLiked(boolean liked) {
                this.liked = liked;
            }

            public String getSocial() {
                return social;
            }

            public void setSocial(String social) {
                this.social = social;
            }
        }
    }
}
