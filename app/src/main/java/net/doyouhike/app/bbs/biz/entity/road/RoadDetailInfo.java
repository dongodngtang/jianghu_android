package net.doyouhike.app.bbs.biz.entity.road;

import net.doyouhike.app.bbs.biz.newnetwork.model.response.road.FlagPoint;

import java.util.List;

/**
 * 线路详情 轨迹
 * Created by terry on 5/8/16.
 */
public class RoadDetailInfo {


    /**
     * mark : 4VNK
     * route_name : 深圳东部海滨栈道徒步
     * route_desc : 深圳的海滨有很多地方都很美，不过他们更适合一些户外人士行走，比较适合公众游览的有两个地方，一个是深圳湾海滨栈道，另一个最美的海滨栈道则是主体部分在盐田的深圳东部海滨栈道。在这里你可以感受到惊涛拍岸的体验，这个目前已经建成的部分全长19.5公里的海滨栈道，被称为世界第一长“海滨玉带”。
     * prompt_notice : 海滨栈道的起点是明斯克航母世界，门票130元/人。栈道上并木直接的下撤点，但是距离公路很近，可搭乘公交下撤。
     * route_type_name : 健行
     * route_type_id : 2
     * mileage : 18.896477461705217
     * alt : 0
     * difficulty_level : 1
     * season_ids : 1,2,3,4
     * travel_time : 1
     * campsite_count : null
     * tag_custom : 垂钓
     * travel_line : null
     * traffic_info : 起点明斯克航母： 途经公交车有85路、103路、103a路、103b路、202路、205路、308路、358路、387路、b619路、b625路、b701路、j1路、m348路、m362路、观光线、机场6线； 终点大梅沙： 经过大梅沙的线路有103路、380B路、B703路、J1路快线、M362路、M437路、观光1号线公交线路。 自驾、自行车均可前往。
     * map_line_id : 13
     * main_photo : 2015/03/18/e/e1e7288a413a8088ce378cc5df88a870
     * offset_lngi : 114.304
     * offset_lati : 22.5872
     * update_at : 2015-04-09T11:10:02.000Z
     * campsites : []
     * tags : [{"id":14,"tag_name":"海滨栈道","tag_desc":null,"route_type_id":2},{"id":15,"tag_name":"绿道","tag_desc":null,"route_type_id":2},{"id":16,"tag_name":"亲子","tag_desc":null,"route_type_id":2},{"id":17,"tag_name":"远足","tag_desc":null,"route_type_id":2}]
     * flag_points : []
     */

    private String mark;
    private String route_name;
    private String route_desc;
    private String prompt_notice;
    private String route_type_name;
    private int route_type_id;
    private String mileage;
    private String alt;
    private int difficulty_level;
    private String season_ids;
    private String travel_time;
    private String campsite_count;
    private String tag_custom;
    private String travel_line;
    private String traffic_info;
    private int map_line_id;
    private String main_photo;
    private String offset_lngi;
    private String offset_lati;
    private String update_at;
    private List<Campsite> campsites;
    /**
     * id : 14
     * tag_name : 海滨栈道
     * tag_desc : null
     * route_type_id : 2
     */

    private List<TagsBean> tags;
    private List<FlagPoint> flag_points;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getRoute_name() {
        return route_name;
    }

    public void setRoute_name(String route_name) {
        this.route_name = route_name;
    }

    public String getRoute_desc() {
        return route_desc;
    }

    public void setRoute_desc(String route_desc) {
        this.route_desc = route_desc;
    }

    public String getPrompt_notice() {
        return prompt_notice;
    }

    public void setPrompt_notice(String prompt_notice) {
        this.prompt_notice = prompt_notice;
    }

    public String getRoute_type_name() {
        return route_type_name;
    }

    public void setRoute_type_name(String route_type_name) {
        this.route_type_name = route_type_name;
    }

    public int getRoute_type_id() {
        return route_type_id;
    }

    public void setRoute_type_id(int route_type_id) {
        this.route_type_id = route_type_id;
    }

    public String getMileage() {
        return mileage;
    }

    public void setMileage(String mileage) {
        this.mileage = mileage;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public int getDifficulty_level() {
        return difficulty_level;
    }

    public void setDifficulty_level(int difficulty_level) {
        this.difficulty_level = difficulty_level;
    }

    public String getSeason_ids() {
        return season_ids;
    }

    public void setSeason_ids(String season_ids) {
        this.season_ids = season_ids;
    }

    public String getTravel_time() {
        return travel_time;
    }

    public void setTravel_time(String travel_time) {
        this.travel_time = travel_time;
    }

    public String getCampsite_count() {
        return campsite_count;
    }

    public void setCampsite_count(String campsite_count) {
        this.campsite_count = campsite_count;
    }

    public String getTag_custom() {
        return tag_custom;
    }

    public void setTag_custom(String tag_custom) {
        this.tag_custom = tag_custom;
    }

    public String getTravel_line() {
        return travel_line;
    }

    public void setTravel_line(String travel_line) {
        this.travel_line = travel_line;
    }

    public String getTraffic_info() {
        return traffic_info;
    }

    public void setTraffic_info(String traffic_info) {
        this.traffic_info = traffic_info;
    }

    public int getMap_line_id() {
        return map_line_id;
    }

    public void setMap_line_id(int map_line_id) {
        this.map_line_id = map_line_id;
    }

    public String getMain_photo() {
        return main_photo;
    }

    public void setMain_photo(String main_photo) {
        this.main_photo = main_photo;
    }

    public String getOffset_lngi() {
        return offset_lngi;
    }

    public void setOffset_lngi(String offset_lngi) {
        this.offset_lngi = offset_lngi;
    }

    public String getOffset_lati() {
        return offset_lati;
    }

    public void setOffset_lati(String offset_lati) {
        this.offset_lati = offset_lati;
    }

    public String getUpdate_at() {
        return update_at;
    }

    public void setUpdate_at(String update_at) {
        this.update_at = update_at;
    }

    public List<Campsite> getCampsites() {
        return campsites;
    }

    public void setCampsites(List<Campsite> campsites) {
        this.campsites = campsites;
    }

    public List<TagsBean> getTags() {
        return tags;
    }

    public void setTags(List<TagsBean> tags) {
        this.tags = tags;
    }

    public List<FlagPoint> getFlag_points() {
        return flag_points;
    }

    public void setFlag_points(List<FlagPoint> flag_points) {
        this.flag_points = flag_points;
    }

    public static class TagsBean {
        private int id;
        private String tag_name;
        private String tag_desc;
        private int route_type_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTag_name() {
            return tag_name;
        }

        public void setTag_name(String tag_name) {
            this.tag_name = tag_name;
        }

        public String getTag_desc() {
            return tag_desc;
        }

        public void setTag_desc(String tag_desc) {
            this.tag_desc = tag_desc;
        }

        public int getRoute_type_id() {
            return route_type_id;
        }

        public void setRoute_type_id(int route_type_id) {
            this.route_type_id = route_type_id;
        }
    }
    public class Campsite {

        private String id;

        private String campsite_name;

        private String campsite_desc;


        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCampsite_name() {
            return campsite_name;
        }

        public void setCampsite_name(String campsite_name) {
            this.campsite_name = campsite_name;
        }

        public String getCampsite_desc() {
            return campsite_desc;
        }

        public void setCampsite_desc(String campsite_desc) {
            this.campsite_desc = campsite_desc;
        }


    }
}
