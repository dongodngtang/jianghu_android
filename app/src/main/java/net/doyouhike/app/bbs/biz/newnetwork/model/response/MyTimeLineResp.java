package net.doyouhike.app.bbs.biz.newnetwork.model.response;

import java.util.List;

/**
 * Created by luochangdong on 15-11-30.
 */
public class MyTimeLineResp {

    /**
     * minilog_type : 1
     * node_id : 2354234
     * tags : {"id":["1","2","3"],"desc":["登山","穿越","流浪"]}
     * user_info : {"avatar":"https://gitlab.zaitu.cn/assets/logo-white-8741ca66242e138fc2e3efead1e2d7c3.png","user_name":"宅成自闭","user_id":1}
     * location : {"latitude":2134.34,"longitude":30453.23,"altitude":3434.3434,"city_id":400300,"city":"上海","location":"上海虹桥机场"}
     * time : 1489423600
     * content : 傍晚是夏天最舒服的时候，不如约起家人或朋友，吹吹海风，听听浪声，在海滨栈道想象有关大海的故事
     * is_like : 1
     * is_favorite : 1
     * like_num : 3
     * hit_num : 234
     * post_num : 342
     * favourite_num : 34
     */

    private List<DataEntity> data;

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private int minilog_type;
        private int node_id;
        private TagsEntity tags;
        /**
         * avatar : https://gitlab.zaitu.cn/assets/logo-white-8741ca66242e138fc2e3efead1e2d7c3.png
         * user_name : 宅成自闭
         * user_id : 1
         */

        private UserInfoEntity user_info;
        /**
         * latitude : 2134.34
         * longitude : 30453.23
         * altitude : 3434.3434
         * city_id : 400300
         * city : 上海
         * location : 上海虹桥机场
         */

        private LocationEntity location;
        private int time;
        private String content;
        private int is_like;
        private int is_favorite;
        private int like_num;
        private int hit_num;
        private int post_num;
        private int favourite_num;

        public void setMinilog_type(int minilog_type) {
            this.minilog_type = minilog_type;
        }

        public void setNode_id(int node_id) {
            this.node_id = node_id;
        }

        public void setTags(TagsEntity tags) {
            this.tags = tags;
        }

        public void setUser_info(UserInfoEntity user_info) {
            this.user_info = user_info;
        }

        public void setLocation(LocationEntity location) {
            this.location = location;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setIs_like(int is_like) {
            this.is_like = is_like;
        }

        public void setIs_favorite(int is_favorite) {
            this.is_favorite = is_favorite;
        }

        public void setLike_num(int like_num) {
            this.like_num = like_num;
        }

        public void setHit_num(int hit_num) {
            this.hit_num = hit_num;
        }

        public void setPost_num(int post_num) {
            this.post_num = post_num;
        }

        public void setFavourite_num(int favourite_num) {
            this.favourite_num = favourite_num;
        }

        public int getMinilog_type() {
            return minilog_type;
        }

        public int getNode_id() {
            return node_id;
        }

        public TagsEntity getTags() {
            return tags;
        }

        public UserInfoEntity getUser_info() {
            return user_info;
        }

        public LocationEntity getLocation() {
            return location;
        }

        public int getTime() {
            return time;
        }

        public String getContent() {
            return content;
        }

        public int getIs_like() {
            return is_like;
        }

        public int getIs_favorite() {
            return is_favorite;
        }

        public int getLike_num() {
            return like_num;
        }

        public int getHit_num() {
            return hit_num;
        }

        public int getPost_num() {
            return post_num;
        }

        public int getFavourite_num() {
            return favourite_num;
        }

        public static class TagsEntity {
            private List<String> id;
            private List<String> desc;

            public void setId(List<String> id) {
                this.id = id;
            }

            public void setDesc(List<String> desc) {
                this.desc = desc;
            }

            public List<String> getId() {
                return id;
            }

            public List<String> getDesc() {
                return desc;
            }
        }

        public static class UserInfoEntity {
            private String avatar;
            private String user_name;
            private int user_id;

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public void setUser_name(String user_name) {
                this.user_name = user_name;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getAvatar() {
                return avatar;
            }

            public String getUser_name() {
                return user_name;
            }

            public int getUser_id() {
                return user_id;
            }
        }

        public static class LocationEntity {
            private double latitude;
            private double longitude;
            private double altitude;
            private int city_id;
            private String city;
            private String location;

            public void setLatitude(double latitude) {
                this.latitude = latitude;
            }

            public void setLongitude(double longitude) {
                this.longitude = longitude;
            }

            public void setAltitude(double altitude) {
                this.altitude = altitude;
            }

            public void setCity_id(int city_id) {
                this.city_id = city_id;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setLocation(String location) {
                this.location = location;
            }

            public double getLatitude() {
                return latitude;
            }

            public double getLongitude() {
                return longitude;
            }

            public double getAltitude() {
                return altitude;
            }

            public int getCity_id() {
                return city_id;
            }

            public String getCity() {
                return city;
            }

            public String getLocation() {
                return location;
            }
        }
    }
}
