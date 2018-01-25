package net.doyouhike.app.bbs.biz.openapi.response.uploader;

/**
 * 作者：luochangdong on 16/10/9
 * 描述：
 */
public class UploadAvatarResp {

    /**
     * user_id : 4417b1d0efa10f1372c456d009a42df6
     * nick_name : coffee
     * sex : female
     * user_desc : 刚好健康看看
     * avatar : files/faces/5/c/5c1926276.jpg
     * city_name : 深圳
     * photo_domain_path : http://static.test.doyouhike.net/
     */

    private String user_id;
    private String nick_name;
    private String sex;
    private String user_desc;
    private String avatar;
    private String city_name;
    private String photo_domain_path;

    public String getImageUrl() {
        return photo_domain_path + avatar;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getUser_desc() {
        return user_desc;
    }

    public void setUser_desc(String user_desc) {
        this.user_desc = user_desc;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getPhoto_domain_path() {
        return photo_domain_path;
    }

    public void setPhoto_domain_path(String photo_domain_path) {
        this.photo_domain_path = photo_domain_path;
    }
}
