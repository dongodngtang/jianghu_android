package net.doyouhike.app.bbs.biz.openapi.request.nodes.timelines;

import com.google.gson.annotations.Expose;
import com.yolanda.nohttp.rest.CacheMode;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventPostResp;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/17
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.MINILOGS)
public class MinilogPost extends BasePostRequest {

    public MinilogPost() {
        setTime_out(20 * 1000);
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<EventPostResp>(EventPostResp.class);
    }

    /**
     * content : test_title
     * photos : [{"temp_photo_id":1111}]
     * tag_ids : [10,110]
     * location : {"city_id":40010,"latitude":"10.00","longitude":"10.00","altitude":"10.00","location_name":"深圳西丽湖环湖跑","dest_id":"1212"}
     */
    @Expose
    private String content;
    /**
     * city_id : 40010
     * latitude : 10.00
     * longitude : 10.00
     * altitude : 10.00
     * location_name : 深圳西丽湖环湖跑
     * dest_id : 1212
     */
    @Expose
    private LocationBean location;
    /**
     * temp_photo_id : 1111
     */
    @Expose
    private List<PhotosBean> photos;
    @Expose
    private List<String> tag_ids;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocationBean getLocation() {
        return location;
    }

    public void setLocation(LocationBean location) {
        this.location = location;
    }

    public List<PhotosBean> getPhotos() {
        return photos;
    }

    public void setPhotos(List<PhotosBean> photos) {
        this.photos = photos;
    }

    public List<String> getTag_ids() {
        return tag_ids;
    }

    public void setTag_ids(List<String> tag_ids) {
        this.tag_ids = tag_ids;
    }

    public static class LocationBean {
        @Expose
        private String city_id;
        @Expose
        private String latitude;
        @Expose
        private String longitude;
        @Expose
        private String altitude;
        @Expose
        private String location_name;
        @Expose
        private String dest_id;

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
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

        public String getLocation_name() {
            return location_name;
        }

        public void setLocation_name(String location_name) {
            this.location_name = location_name;
        }

        public String getDest_id() {
            return dest_id;
        }

        public void setDest_id(String dest_id) {
            this.dest_id = dest_id;
        }
    }

    public static class PhotosBean {
        @Expose
        private String temp_photo_id;

        public String getTemp_photo_id() {
            return temp_photo_id;
        }

        public void setTemp_photo_id(String temp_photo_id) {
            this.temp_photo_id = temp_photo_id;
        }
    }
}
