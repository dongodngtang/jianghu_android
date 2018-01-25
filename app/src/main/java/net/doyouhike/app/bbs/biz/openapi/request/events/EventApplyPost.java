package net.doyouhike.app.bbs.biz.openapi.request.events;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/9/20
 * 描述：报名活动
 */
public class EventApplyPost extends BasePostRequest {


    private String node_id;
    /**
     * real_name : 真实姓名
     * insurance_name :  保险名
     * insurance_number :  保险单号
     * contact_name : 紧急联系人
     * contact_tel : 紧急联系人电话
     * memo :  活动留言
     */
    @Expose
    private String real_name;
    @Expose
    private String insurance_name;
    @Expose
    private String insurance_number;
    @Expose
    private String contact_name;
    @Expose
    private String contact_tel;
    @Expose
    private String memo;

    public EventApplyPost(String node_id) {
        this.node_id = node_id;
    }

    @Override
    public String getSubUrl() {

        return OpenApiUrl.EVENTS + "/" + node_id + "/apply";
    }


    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    public String getInsurance_name() {
        return insurance_name;
    }

    public void setInsurance_name(String insurance_name) {
        this.insurance_name = insurance_name;
    }

    public String getInsurance_number() {
        return insurance_number;
    }

    public void setInsurance_number(String insurance_number) {
        this.insurance_number = insurance_number;
    }

    public String getContact_name() {
        return contact_name;
    }

    public void setContact_name(String contact_name) {
        this.contact_name = contact_name;
    }

    public String getContact_tel() {
        return contact_tel;
    }

    public void setContact_tel(String contact_tel) {
        this.contact_tel = contact_tel;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
}
