package net.doyouhike.app.bbs.biz.entity.dynamic;

/**
 * Created by terry on 5/30/16.
 * 线路 目的地 广告
 */
public class DesAndRoadListAdInfo {


    private String user_name;
    /**
     * photo_path : 2014/04/03/9/9914c682e91c794e5d3ab33c650310f7
     * photo_ext : jpg
     */

    private String photo_path;
    private String photo_ext;


    public String getPath() {
        return photo_path + "." + photo_ext;
    }


    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }


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
