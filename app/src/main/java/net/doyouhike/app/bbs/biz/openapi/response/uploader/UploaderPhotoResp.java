package net.doyouhike.app.bbs.biz.openapi.response.uploader;

/**
 * 作者：luochangdong on 16/10/9
 * 描述：
 */
public class UploaderPhotoResp {

    /**
     * photo_id : 1711288
     * photo_path : files/2016/10/09/6/653d5edeb488ae1ddf097ccd0c954b89
     * photo_ext : jpg
     * photo_size : 22286
     * photo_name : 1473139150240.jpg
     * photo_domain_path : http://static.test.doyouhike.net/
     */

    private String photo_id;
    private String photo_path;
    private String photo_ext;
    private int photo_size;
    private String photo_name;
    private String photo_domain_path;

    public String getImageUrl() {
        return photo_domain_path + photo_path + "." + photo_ext;
    }

    public String getPhoto_id() {
        return photo_id;
    }

    public void setPhoto_id(String photo_id) {
        this.photo_id = photo_id;
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

    public int getPhoto_size() {
        return photo_size;
    }

    public void setPhoto_size(int photo_size) {
        this.photo_size = photo_size;
    }

    public String getPhoto_name() {
        return photo_name;
    }

    public void setPhoto_name(String photo_name) {
        this.photo_name = photo_name;
    }

    public String getPhoto_domain_path() {
        return photo_domain_path;
    }

    public void setPhoto_domain_path(String photo_domain_path) {
        this.photo_domain_path = photo_domain_path;
    }
}
