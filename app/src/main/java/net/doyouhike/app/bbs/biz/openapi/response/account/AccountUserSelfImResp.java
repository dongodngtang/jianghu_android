package net.doyouhike.app.bbs.biz.openapi.response.account;

import net.doyouhike.app.bbs.biz.newnetwork.model.bean.MyImInfo;

import java.util.List;

/**
 * 作者：luochangdong on 16/9/21
 * 描述：
 */
public class AccountUserSelfImResp {

    /**
     * im_id : 5914047c846f20859739f7327dea7fec
     * im_password : ba337f080d4804e68aa13071cfb7d455
     * im_enabled : true
     */

    private List<MyImInfo> im;

    public List<MyImInfo> getIm() {
        return im;
    }

    public void setIm(List<MyImInfo> im) {
        this.im = im;
    }


}
