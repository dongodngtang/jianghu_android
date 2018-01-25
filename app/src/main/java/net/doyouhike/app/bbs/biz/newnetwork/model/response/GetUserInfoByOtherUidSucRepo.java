package net.doyouhike.app.bbs.biz.newnetwork.model.response;

import java.util.List;

/**
 * Created by zaitu on 15-12-1.
 */
public class GetUserInfoByOtherUidSucRepo {

    /**
     * Intro : [{"UserID":"51105","RealName":"曹大军","CredNum":null,"Mobile":"13439308778","Address":null,"TempAddress":"","Med":"0","MedDetail":"","Insurance":"","ExtraInfo":null,"ContactMethod":null,"Intro":"","ContactName1":"","ContactTel1":"","ContactName2":"","ContactTel2":"","InsuranceName":"","InsuranceNumber":""}]
     * UserDesc :
     * Signature :
     * RegDate : 2003-11-20
     * PostsNum : 0
     * group_count : 0
     * EventDate : null
     * Mobile :
     * FollowCount : 1
     * FollowMeCount : 0
     * NodeCount : 0
     * BookMarkCount : 0
     * ActivityCount : 0
     * Gender : unknown
     * UserID : 51105
     * UserName : echo姐姐
     * NickName : echo姐姐
     * Face : http://static.test.doyouhike.net/files/faces/none.gif
     * CityName : 深圳
     * Authed : 0
     * is_follow : 0
     */

    private String UserDesc;
    private String Signature;
    private String RegDate;
    private String PostsNum;
    private String group_count;
    private Object EventDate;
    private String Mobile;
    private int FollowCount;
    private int FollowMeCount;
    private int NodeCount;
    private int BookMarkCount;
    private int ActivityCount;
    private String Gender;
    private String UserID;
    private String UserName;
    private String NickName;
    private String Face;
    private String CityName;
    private String Authed;
    private int is_follow;
    /**
     * UserID : 51105
     * RealName : 曹大军
     * CredNum : null
     * Mobile : 13439308778
     * Address : null
     * TempAddress :
     * Med : 0
     * MedDetail :
     * Insurance :
     * ExtraInfo : null
     * ContactMethod : null
     * Intro :
     * ContactName1 :
     * ContactTel1 :
     * ContactName2 :
     * ContactTel2 :
     * InsuranceName :
     * InsuranceNumber :
     */

    private List<IntroEntity> Intro;

    public void setUserDesc(String UserDesc) {
        this.UserDesc = UserDesc;
    }

    public void setSignature(String Signature) {
        this.Signature = Signature;
    }

    public void setRegDate(String RegDate) {
        this.RegDate = RegDate;
    }

    public void setPostsNum(String PostsNum) {
        this.PostsNum = PostsNum;
    }

    public void setGroup_count(String group_count) {
        this.group_count = group_count;
    }

    public void setEventDate(Object EventDate) {
        this.EventDate = EventDate;
    }

    public void setMobile(String Mobile) {
        this.Mobile = Mobile;
    }

    public void setFollowCount(int FollowCount) {
        this.FollowCount = FollowCount;
    }

    public void setFollowMeCount(int FollowMeCount) {
        this.FollowMeCount = FollowMeCount;
    }

    public void setNodeCount(int NodeCount) {
        this.NodeCount = NodeCount;
    }

    public void setBookMarkCount(int BookMarkCount) {
        this.BookMarkCount = BookMarkCount;
    }

    public void setActivityCount(int ActivityCount) {
        this.ActivityCount = ActivityCount;
    }

    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    public void setUserID(String UserID) {
        this.UserID = UserID;
    }

    public void setUserName(String UserName) {
        this.UserName = UserName;
    }

    public void setNickName(String NickName) {
        this.NickName = NickName;
    }

    public void setFace(String Face) {
        this.Face = Face;
    }

    public void setCityName(String CityName) {
        this.CityName = CityName;
    }

    public void setAuthed(String Authed) {
        this.Authed = Authed;
    }

    public void setIs_follow(int is_follow) {
        this.is_follow = is_follow;
    }

    public void setIntro(List<IntroEntity> Intro) {
        this.Intro = Intro;
    }

    public String getUserDesc() {
        return UserDesc;
    }

    public String getSignature() {
        return Signature;
    }

    public String getRegDate() {
        return RegDate;
    }

    public String getPostsNum() {
        return PostsNum;
    }

    public String getGroup_count() {
        return group_count;
    }

    public Object getEventDate() {
        return EventDate;
    }

    public String getMobile() {
        return Mobile;
    }

    public int getFollowCount() {
        return FollowCount;
    }

    public int getFollowMeCount() {
        return FollowMeCount;
    }

    public int getNodeCount() {
        return NodeCount;
    }

    public int getBookMarkCount() {
        return BookMarkCount;
    }

    public int getActivityCount() {
        return ActivityCount;
    }

    public String getGender() {
        return Gender;
    }

    public String getUserID() {
        return UserID;
    }

    public String getUserName() {
        return UserName;
    }

    public String getNickName() {
        return NickName;
    }

    public String getFace() {
        return Face;
    }

    public String getCityName() {
        return CityName;
    }

    public String getAuthed() {
        return Authed;
    }

    public int getIs_follow() {
        return is_follow;
    }

    public List<IntroEntity> getIntro() {
        return Intro;
    }

    public static class IntroEntity {
        private String UserID;
        private String RealName;
        private Object CredNum;
        private String Mobile;
        private Object Address;
        private String TempAddress;
        private String Med;
        private String MedDetail;
        private String Insurance;
        private Object ExtraInfo;
        private Object ContactMethod;
        private String Intro;
        private String ContactName1;
        private String ContactTel1;
        private String ContactName2;
        private String ContactTel2;
        private String InsuranceName;
        private String InsuranceNumber;

        public void setUserID(String UserID) {
            this.UserID = UserID;
        }

        public void setRealName(String RealName) {
            this.RealName = RealName;
        }

        public void setCredNum(Object CredNum) {
            this.CredNum = CredNum;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public void setAddress(Object Address) {
            this.Address = Address;
        }

        public void setTempAddress(String TempAddress) {
            this.TempAddress = TempAddress;
        }

        public void setMed(String Med) {
            this.Med = Med;
        }

        public void setMedDetail(String MedDetail) {
            this.MedDetail = MedDetail;
        }

        public void setInsurance(String Insurance) {
            this.Insurance = Insurance;
        }

        public void setExtraInfo(Object ExtraInfo) {
            this.ExtraInfo = ExtraInfo;
        }

        public void setContactMethod(Object ContactMethod) {
            this.ContactMethod = ContactMethod;
        }

        public void setIntro(String Intro) {
            this.Intro = Intro;
        }

        public void setContactName1(String ContactName1) {
            this.ContactName1 = ContactName1;
        }

        public void setContactTel1(String ContactTel1) {
            this.ContactTel1 = ContactTel1;
        }

        public void setContactName2(String ContactName2) {
            this.ContactName2 = ContactName2;
        }

        public void setContactTel2(String ContactTel2) {
            this.ContactTel2 = ContactTel2;
        }

        public void setInsuranceName(String InsuranceName) {
            this.InsuranceName = InsuranceName;
        }

        public void setInsuranceNumber(String InsuranceNumber) {
            this.InsuranceNumber = InsuranceNumber;
        }

        public String getUserID() {
            return UserID;
        }

        public String getRealName() {
            return RealName;
        }

        public Object getCredNum() {
            return CredNum;
        }

        public String getMobile() {
            return Mobile;
        }

        public Object getAddress() {
            return Address;
        }

        public String getTempAddress() {
            return TempAddress;
        }

        public String getMed() {
            return Med;
        }

        public String getMedDetail() {
            return MedDetail;
        }

        public String getInsurance() {
            return Insurance;
        }

        public Object getExtraInfo() {
            return ExtraInfo;
        }

        public Object getContactMethod() {
            return ContactMethod;
        }

        public String getIntro() {
            return Intro;
        }

        public String getContactName1() {
            return ContactName1;
        }

        public String getContactTel1() {
            return ContactTel1;
        }

        public String getContactName2() {
            return ContactName2;
        }

        public String getContactTel2() {
            return ContactTel2;
        }

        public String getInsuranceName() {
            return InsuranceName;
        }

        public String getInsuranceNumber() {
            return InsuranceNumber;
        }

        @Override
        public String toString() {
            return "IntroEntity{" +
                    "UserID='" + UserID + '\'' +
                    ", RealName='" + RealName + '\'' +
                    ", CredNum=" + CredNum +
                    ", Mobile='" + Mobile + '\'' +
                    ", Address=" + Address +
                    ", TempAddress='" + TempAddress + '\'' +
                    ", Med='" + Med + '\'' +
                    ", MedDetail='" + MedDetail + '\'' +
                    ", Insurance='" + Insurance + '\'' +
                    ", ExtraInfo=" + ExtraInfo +
                    ", ContactMethod=" + ContactMethod +
                    ", Intro='" + Intro + '\'' +
                    ", ContactName1='" + ContactName1 + '\'' +
                    ", ContactTel1='" + ContactTel1 + '\'' +
                    ", ContactName2='" + ContactName2 + '\'' +
                    ", ContactTel2='" + ContactTel2 + '\'' +
                    ", InsuranceName='" + InsuranceName + '\'' +
                    ", InsuranceNumber='" + InsuranceNumber + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GetUserInfoByOtherUidSucRepo{" +
                "UserDesc='" + UserDesc + '\'' +
                ", Signature='" + Signature + '\'' +
                ", RegDate='" + RegDate + '\'' +
                ", PostsNum='" + PostsNum + '\'' +
                ", group_count='" + group_count + '\'' +
                ", EventDate=" + EventDate +
                ", Mobile='" + Mobile + '\'' +
                ", FollowCount=" + FollowCount +
                ", FollowMeCount=" + FollowMeCount +
                ", NodeCount=" + NodeCount +
                ", BookMarkCount=" + BookMarkCount +
                ", ActivityCount=" + ActivityCount +
                ", Gender='" + Gender + '\'' +
                ", UserID='" + UserID + '\'' +
                ", UserName='" + UserName + '\'' +
                ", NickName='" + NickName + '\'' +
                ", Face='" + Face + '\'' +
                ", CityName='" + CityName + '\'' +
                ", Authed='" + Authed + '\'' +
                ", is_follow=" + is_follow +
                ", Intro=" + Intro +
                '}';
    }
}
