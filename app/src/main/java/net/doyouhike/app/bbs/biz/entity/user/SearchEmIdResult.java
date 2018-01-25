package net.doyouhike.app.bbs.biz.entity.user;

import net.doyouhike.app.bbs.biz.newnetwork.model.bean.HxUserInfo;

import java.util.List;

/**
 * Created by zengjiang on 16/7/30.
 */

public class SearchEmIdResult {

    List<HxUserInfo> existEmIdList;
    List<String> emptyList;


    public List<HxUserInfo>  getExistEmIdList() {
        return existEmIdList;
    }

    public void setExistEmIdList(List<HxUserInfo>  existEmIdList) {
        this.existEmIdList = existEmIdList;
    }

    public List<String> getEmptyList() {
        return emptyList;
    }

    public void setEmptyList(List<String> emptyList) {
        this.emptyList = emptyList;
    }
}
