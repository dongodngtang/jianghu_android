package net.doyouhike.app.bbs.biz.newnetwork.model.response.action;

import net.doyouhike.app.bbs.biz.entity.DetailMembers;

import java.util.List;

/**
 * 活动成员
 * Created by zengjiang on 16/7/19.
 */
public class ActionMemberResponse {

    List<DetailMembers> members;

    private int confirmed_count;
    private int unconfirm_count;
    private int member_count;


    public List<DetailMembers> getMembers() {
        return members;
    }

    public void setMembers(List<DetailMembers> members) {
        this.members = members;
    }

    public int getConfirmed_count() {
        return confirmed_count;
    }

    public void setConfirmed_count(int confirmed_count) {
        this.confirmed_count = confirmed_count;
    }

    public int getUnconfirm_count() {
        return unconfirm_count;
    }

    public void setUnconfirm_count(int unconfirm_count) {
        this.unconfirm_count = unconfirm_count;
    }

    public int getMember_count() {
        return member_count;
    }

    public void setMember_count(int member_count) {
        this.member_count = member_count;
    }
}
