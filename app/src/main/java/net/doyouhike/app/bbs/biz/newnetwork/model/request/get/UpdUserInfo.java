package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;


/**
 * Created by zaitu on 15-11-26.
 */
public class UpdUserInfo extends BaseGetRequest {


    /**
     *
     token: 'xxx'//访问令牌 （必选）
     phoneNumber: 'xxx'//手机号 （可选）
     gender: 'xxx'//性别 0女1男（可选）
     signature: 'xxx'//个人签名（可选）
     cityId:'xxx'//所在城市ID（可选）
     *
     */
    @Expose
    private String token;
    @Expose
    private String phoneNumber;
    @Expose
    private String gender;
    @Expose
    private String signature;
    @Expose
    private String cityId;

    @Override
    protected void setMapValue() {

    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getToken() {
        return token;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public String getSignature() {
        return signature;
    }

    public String getCityId() {
        return cityId;
    }
}
