package net.doyouhike.app.bbs.biz.openapi.response.users;

import net.doyouhike.app.bbs.biz.entity.RecommendUser;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/8
 * 描述：
 */
public class RecommendUsersResp {

    /**
     * items : [{"user_id":"285ae85e4541f7f91db256d009039990","internal_id":655312,"user_name":"孙旭东","nick_name":"孙旭东","sex":"male","avatar":"files/faces/7/e/7eb64e09f.jpg","user_desc":"西丽--悟空"}]
     * photo_domain_path : http://192.168.1.231:8002/
     */

    private String photo_domain_path;
    /**
     * user_id : 285ae85e4541f7f91db256d009039990
     * internal_id : 655312
     * user_name : 孙旭东
     * nick_name : 孙旭东
     * sex : male
     * avatar : files/faces/7/e/7eb64e09f.jpg
     * user_desc : 西丽--悟空
     */

    private List<RecommendUser> items;

    public String getPhoto_domain_path() {
        return photo_domain_path;
    }

    public void setPhoto_domain_path(String photo_domain_path) {
        this.photo_domain_path = photo_domain_path;
    }

    public List<RecommendUser> getItems() {
        return items;
    }

    public void setItems(List<RecommendUser> items) {
        this.items = items;
    }

}
