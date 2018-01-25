package net.doyouhike.app.bbs.biz.newnetwork.model.bean;

/**
 * Created by luochangdong on 15-11-26.
 */
public class TokenInfo {


    /**
     * UserID : 9
     * PFUserID : 1
     * MobArea : 86
     * MobPhone : 135000000000
     * NickName : 新驴9
     * Avatar : http://jhapi.ezaitu.com/files/faces/none_header.gif
     * Email :
     * Sex : 1
     * Signature :
     * CityID : 0
     * CityName :
     * BanAt : 0
     * RenewAt : 0
     * CreateAt : 1432192145
     * UpdateAt : 0
     * DeleteAt : 0
     * FavoNum : 0
     * Token : xxxxxx
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
    private String BanAt;
    private String RenewAt;
    private String CreateAt;
    private String UpdateAt;
    private String DeleteAt;
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

    public void setBanAt(String BanAt) {
        this.BanAt = BanAt;
    }

    public void setRenewAt(String RenewAt) {
        this.RenewAt = RenewAt;
    }

    public void setCreateAt(String CreateAt) {
        this.CreateAt = CreateAt;
    }

    public void setUpdateAt(String UpdateAt) {
        this.UpdateAt = UpdateAt;
    }

    public void setDeleteAt(String DeleteAt) {
        this.DeleteAt = DeleteAt;
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

    public String getBanAt() {
        return BanAt;
    }

    public String getRenewAt() {
        return RenewAt;
    }

    public String getCreateAt() {
        return CreateAt;
    }

    public String getUpdateAt() {
        return UpdateAt;
    }

    public String getDeleteAt() {
        return DeleteAt;
    }

    public String getFavoNum() {
        return FavoNum;
    }

    public String getToken() {
        return Token;
    }

    @Override
    public String toString() {
        return "TokenInfo{" +
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
                ", BanAt='" + BanAt + '\'' +
                ", RenewAt='" + RenewAt + '\'' +
                ", CreateAt='" + CreateAt + '\'' +
                ", UpdateAt='" + UpdateAt + '\'' +
                ", DeleteAt='" + DeleteAt + '\'' +
                ", FavoNum='" + FavoNum + '\'' +
                ", Token='" + Token + '\'' +
                '}';
    }
}
