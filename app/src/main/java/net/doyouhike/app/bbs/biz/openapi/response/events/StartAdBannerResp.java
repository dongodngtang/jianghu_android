package net.doyouhike.app.bbs.biz.openapi.response.events;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/19
 * 描述：
 */
public class StartAdBannerResp {

    /**
     * id : 4
     * link_type : link
     * image_url : http://c1.zdb.io/files/recommend/2016/08/30/0/0a5939de601c80a2cd281fc332741a94.jpg
     * link_url : http://www.doyouhike.net/forum/globe/2429169,0,0,1.html
     * node_type : 1
     */

    private List<EventBannersResp.ItemsBean> ad_result;

    public List<EventBannersResp.ItemsBean> getAd_result() {
        return ad_result;
    }

    public void setAd_result(List<EventBannersResp.ItemsBean> ad_result) {
        this.ad_result = ad_result;
    }


}
