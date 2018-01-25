package net.doyouhike.app.bbs.biz.newnetwork.model.response;

import java.util.List;

/**
 * Created by zaitu on 15-12-1.
 */
public class GetMyCollectList {

    /**
     * minilog_type : 2
     * node_id : 6000982
     * tags : []
     * time : 1448454416
     * content : 上课都快开学快出来吃
     * like_num : 4
     * hit_num : 0
     * post_num : 1
     * favorite_num : 1
     * location : {"latitude":"0.000000000","longitude":"0.000000000","altitude":"0.0","city_id":"0","city":"","location":""}
     * FWNodeID : 0
     * user_info : {"avatar":"http://static.test.doyouhike.net/files/faces/0/1/015a51619.jpg","user_name":"执到宝","nick_name":"执到宝","user_id":"1270555"}
     * images : [{"PhotoID":"6657188","real_file":"http://static.test.doyouhike.net/files/2015/11/25/d/d70c75e7f6778d704bbdde71d54517c9.jpg","small_file":"http://static.test.doyouhike.net/files/2015/11/25/d/d70c75e7f6778d704bbdde71d54517c9_s.jpg","content":"0","height":"1000","width":"563"}]
     * is_follow : 1
     * is_like : 0
     * is_favorite : 1
     */

    private String minilog_type;
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
     * avatar : http://static.test.doyouhike.net/files/faces/0/1/015a51619.jpg
     * user_name : 执到宝
     * nick_name : 执到宝
     * user_id : 1270555
     */

    private UserInfoEntity user_info;
    private int is_follow;
    private int is_like;
    private int is_favorite;
    private List<?> tags;
    /**
     * PhotoID : 6657188
     * real_file : http://static.test.doyouhike.net/files/2015/11/25/d/d70c75e7f6778d704bbdde71d54517c9.jpg
     * small_file : http://static.test.doyouhike.net/files/2015/11/25/d/d70c75e7f6778d704bbdde71d54517c9_s.jpg
     * content : 0
     * height : 1000
     * width : 563
     */

    private List<ImagesEntity> images;

    public void setMinilog_type(String minilog_type) {
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

    public void setUser_info(UserInfoEntity user_info) {
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

    public void setTags(List<?> tags) {
        this.tags = tags;
    }

    public void setImages(List<ImagesEntity> images) {
        this.images = images;
    }

    public String getMinilog_type() {
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

    public UserInfoEntity getUser_info() {
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

    public List<?> getTags() {
        return tags;
    }

    public List<ImagesEntity> getImages() {
        return images;
    }

    public static class LocationEntity {
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

        @Override
        public String toString() {
            return "LocationEntity{" +
                    "latitude='" + latitude + '\'' +
                    ", longitude='" + longitude + '\'' +
                    ", altitude='" + altitude + '\'' +
                    ", city_id='" + city_id + '\'' +
                    ", city='" + city + '\'' +
                    ", location='" + location + '\'' +
                    '}';
        }
    }

    public static class UserInfoEntity {
        private String avatar;
        private String user_name;
        private String nick_name;
        private String user_id;

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getAvatar() {
            return avatar;
        }

        public String getUser_name() {
            return user_name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public String getUser_id() {
            return user_id;
        }

        @Override
        public String toString() {
            return "UserInfoEntity{" +
                    "avatar='" + avatar + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", nick_name='" + nick_name + '\'' +
                    ", user_id='" + user_id + '\'' +
                    '}';
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

        @Override
        public String toString() {
            return "ImagesEntity{" +
                    "PhotoID='" + PhotoID + '\'' +
                    ", real_file='" + real_file + '\'' +
                    ", small_file='" + small_file + '\'' +
                    ", content='" + content + '\'' +
                    ", height='" + height + '\'' +
                    ", width='" + width + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetMyCollectList{" +
                "minilog_type='" + minilog_type + '\'' +
                ", node_id='" + node_id + '\'' +
                ", time='" + time + '\'' +
                ", content='" + content + '\'' +
                ", like_num='" + like_num + '\'' +
                ", hit_num='" + hit_num + '\'' +
                ", post_num='" + post_num + '\'' +
                ", favorite_num='" + favorite_num + '\'' +
                ", location=" + location +
                ", FWNodeID='" + FWNodeID + '\'' +
                ", user_info=" + user_info +
                ", is_follow=" + is_follow +
                ", is_like=" + is_like +
                ", is_favorite=" + is_favorite +
                ", tags=" + tags +
                ", images=" + images +
                '}';
    }
}
