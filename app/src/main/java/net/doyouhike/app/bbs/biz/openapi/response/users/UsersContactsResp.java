package net.doyouhike.app.bbs.biz.openapi.response.users;

import net.doyouhike.app.bbs.biz.entity.RecommendUser;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/8
 * 描述：
 */
public class UsersContactsResp {

    /**
     * items : [{"user":{"user_id":"26728fc5ceaf506c375e56d009b746b6","internal_id":1508790,"mobile":"13609628232","user_name":"mf_13609628232","nick_name":"mf_13609628232cr45","sex":"male","avatar":"files/faces/none_header.gif","user_desc":null},"social":"not_follow"},{"user":{"user_id":"265d5e7549c2bbfb26da56d00996c9a6","internal_id":1508772,"mobile":"13686858184","user_name":"aas1900000","nick_name":"z1947","sex":"unknown","avatar":"files/faces/1/a/1a0b6ac4d.jpg","user_desc":" haha"},"social":"follow_each"},{"user":{"user_id":"265fac5e5968a64ebfd656d009d8f3a9","internal_id":1508776,"mobile":"13809891296","user_name":"mf_13809891296","nick_name":"mf_13809891296gg42","sex":"male","avatar":"files/faces/none_header.gif","user_desc":null},"social":"not_follow"},{"user":{"user_id":"9abd9b153121a1df0898576a23daf722","internal_id":1519528,"mobile":"18681563101","user_name":"干你妹pp26","nick_name":"干你妹","sex":"unknown","avatar":"files/faces/none_header.gif","user_desc":null},"social":"not_follow"}]
     * photo_domain_path : http://192.168.1.231:8002/
     */

    private String photo_domain_path;
    /**
     * user : {"user_id":"26728fc5ceaf506c375e56d009b746b6","internal_id":1508790,"mobile":"13609628232","user_name":"mf_13609628232","nick_name":"mf_13609628232cr45","sex":"male","avatar":"files/faces/none_header.gif","user_desc":null}
     * social : not_follow
     */

    private List<ItemsBean> items;

    public String getPhoto_domain_path() {
        return photo_domain_path;
    }

    public void setPhoto_domain_path(String photo_domain_path) {
        this.photo_domain_path = photo_domain_path;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ItemsBean {
        /**
         * user_id : 26728fc5ceaf506c375e56d009b746b6
         * internal_id : 1508790
         * mobile : 13609628232
         * user_name : mf_13609628232
         * nick_name : mf_13609628232cr45
         * sex : male
         * avatar : files/faces/none_header.gif
         * user_desc : null
         */

        private RecommendUser user;
        private String social;

        public RecommendUser getUser() {
            return user;
        }

        public void setUser(RecommendUser user) {
            this.user = user;
        }

        public String getSocial() {
            return social;
        }

        public void setSocial(String social) {
            this.social = social;
        }

    }
}
