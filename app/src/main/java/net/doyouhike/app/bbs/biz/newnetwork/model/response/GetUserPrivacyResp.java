package net.doyouhike.app.bbs.biz.newnetwork.model.response;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-3-29
 */
public class GetUserPrivacyResp {

    /**
     * find_me_by_phone : 1
     * find_me_by_nearly : 1
     */
    private String find_me_by_phone;
    private String find_me_by_nearly;

    public String getFind_me_by_phone() {
        return find_me_by_phone;
    }

    public void setFind_me_by_phone(String find_me_by_phone) {
        this.find_me_by_phone = find_me_by_phone;
    }

    public String getFind_me_by_nearly() {
        return find_me_by_nearly;
    }

    public void setFind_me_by_nearly(String find_me_by_nearly) {
        this.find_me_by_nearly = find_me_by_nearly;
    }
}
