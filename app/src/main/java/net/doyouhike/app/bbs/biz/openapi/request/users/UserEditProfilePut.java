package net.doyouhike.app.bbs.biz.openapi.request.users;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/9/28
 * 描述：
 */
public class UserEditProfilePut extends BasePostRequest {

    public final static String SEX = "sex";
    public final static String NICK_NAME = "nick_name";
    public final static String CITY_ID = "city_id";
    public final static String CITY_NAME = "city_name";
    public final static String USER_DESC = "user_desc";


    private String user_id;

    public UserEditProfilePut(String user_id) {
        this.user_id = user_id;
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<CurrentUserDetails>(CurrentUserDetails.class);
    }

    @Override
    public String getSubUrl() {
        return OpenApiUrl.ACCOUNT_USERS + user_id + "/profile";
    }

    //性别
    @Expose
    private String sex;
    //用户的uuid
    @Expose
    private String user_uuid;
    //用户昵称
    @Expose
    private String nick_name;
    //个人说明
    @Expose
    private String user_desc;
    //签名
    @Expose
    private String signature;
    //用户的城市id
    @Expose
    private String city_id;
    //用户的城市名
    @Expose
    private String city_name;
    //用户的城市中文拼音
    @Expose
    private String city_slug;
    //个人主页
    @Expose
    private String home_page;
    @Expose
    private String qq;
    @Expose
    private String msn;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getUser_uuid() {
        return user_uuid;
    }

    public void setUser_uuid(String user_uuid) {
        this.user_uuid = user_uuid;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getUser_desc() {
        return user_desc;
    }

    public void setUser_desc(String user_desc) {
        this.user_desc = user_desc;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getCity_slug() {
        return city_slug;
    }

    public void setCity_slug(String city_slug) {
        this.city_slug = city_slug;
    }

    public String getHome_page() {
        return home_page;
    }

    public void setHome_page(String home_page) {
        this.home_page = home_page;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }
}
