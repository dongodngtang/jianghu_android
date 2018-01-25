package net.doyouhike.app.bbs.biz.newnetwork.model.bean;

/**
 * Created by zaitu on 15-11-26.
 */
public class UserInfo{

    /**
     *
     "UserID": "54", //用户id
     "PFUserID": "1", //用户平台id
     "MobArea": "86", //区号
     "MobPhone": "13537646345", //手机号
     "NickName": "新驴54号", //昵称
     "Avatar": "http://jhapi.ezaitu.com/files/faces/none_header.gif", //头像
     "Email": "", //email地址
     "Sex": "1", //性别 1男 0女
     "Signature": "", //签名
     "CityID": "0", //所在城市ID
     "CityName": "", //所在城市名称
     "CreateAt": "1432630573", //注册时间
     "FavoNum": "0", //用户收藏数量
     "Token": "3de942bc2d20d6f318a0ff3adbcef224" //用户token
     *
     */

    private String UserID;
    private String PFUserID;
    private String MobArea;
    private String MobPhone;
    private String NickName;
    private String Avatar;
    private String Email;
    private String Sex;
    private String Signature;
    private String CityID;
    private String CityName;
    private String CreateAt;
    private String FavoNum;
    private String Token;

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public void setPFUserID(String PFUserID) {
        this.PFUserID = PFUserID;
    }

    public void setMobArea(String MobArea) {
        this.MobArea = MobArea;
    }

    public void setMobPhone(String MobPhone) {
        this.MobPhone = MobPhone;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public void setAvatar(String Avatar) {
        this.Avatar = Avatar;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setSex(String Sex) {
        this.Sex = Sex;
    }

    public void setSignature(String Signature) {
        this.Signature = Signature;
    }

    public void setCityID(String CityID) {
        this.CityID = CityID;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public void setCreateAt(String CreateAt) {
        this.CreateAt = CreateAt;
    }

    public void setFavoNum(String FavoNum) {
        this.FavoNum = FavoNum;
    }

    public void setToken(String Token) {
        this.Token = Token;
    }

    public String getUserID() {
        return UserID;
    }

    public String getPFUserID() {
        return PFUserID;
    }

    public String getMobArea() {
        return MobArea;
    }

    public String getMobPhone() {
        return MobPhone;
    }

    public String getNickName() {
        return NickName;
    }

    public String getAvatar() {
        return Avatar;
    }

    public String getEmail() {
        return Email;
    }

    public String getSex() {
        return Sex;
    }

    public String getSignature() {
        return Signature;
    }

    public String getCityID() {
        return CityID;
    }

    public String getCityName() {
        return CityName;
    }

    public String getCreateAt() {
        return CreateAt;
    }

    public String getFavoNum() {
        return FavoNum;
    }

    public String getToken() {
        return Token;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "UserID='" + UserID + '\'' +
                ", PFUserID='" + PFUserID + '\'' +
                ", MobArea='" + MobArea + '\'' +
                ", MobPhone='" + MobPhone + '\'' +
                ", NickName='" + NickName + '\'' +
                ", Avatar='" + Avatar + '\'' +
                ", Email='" + Email + '\'' +
                ", Sex='" + Sex + '\'' +
                ", Signature='" + Signature + '\'' +
                ", CityID='" + CityID + '\'' +
                ", CityName='" + CityName + '\'' +
                ", CreateAt='" + CreateAt + '\'' +
                ", FavoNum='" + FavoNum + '\'' +
                ", Token='" + Token + '\'' +
                '}';
    }
}
