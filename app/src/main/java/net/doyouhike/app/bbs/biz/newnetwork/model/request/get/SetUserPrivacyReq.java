package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;

import net.doyouhike.app.bbs.biz.entity.action.ActionDetailInfo;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

/**
 * Filework: 设置用户隐私
 * Author: luochangdong
 * Date:16-3-29
 */
@RequestUrlAnnotation(Content.REQ_GET_SET_USER_PRIVACY)
public class SetUserPrivacyReq extends BaseTokenGetParams {


    /**
     * findByPhone : 0
     * findMeNearly : 1
     */

    private String findByPhone;
    private String findMeNearly;

    public String getFindByPhone() {
        return findByPhone;
    }

    public void setFindByPhone(String findByPhone) {
        this.findByPhone = findByPhone;
    }

    public String getFindMeNearly() {
        return findMeNearly;
    }

    public void setFindMeNearly(String findMeNearly) {
        this.findMeNearly = findMeNearly;
    }

    @Override
    protected void setMapValue() {
        super.setMapValue();
        map.put("findByPhone", findByPhone);
        map.put("findMeNearly", findMeNearly);

    }



    @Override
    public IResponseProcess getProcess() {
       return new DataJsonParser<ActionDetailInfo>(ActionDetailInfo.class);
    }

}
