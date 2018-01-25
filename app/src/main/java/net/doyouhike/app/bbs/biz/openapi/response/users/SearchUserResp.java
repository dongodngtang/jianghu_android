package net.doyouhike.app.bbs.biz.openapi.response.users;

import net.doyouhike.app.bbs.biz.entity.RecommendUser;

import java.util.List;

/**
 * 作者：luochangdong on 16/10/8
 * 描述：
 */
public class SearchUserResp {

    /**
     * items : [{"user":{"user_id":"45ca3e6ac86c48f9d19456d009d3620a","internal_id":410,"user_name":"Debug","nick_name":"Debug","sex":"unknown","avatar":"files/faces/10/user410.gif","user_desc":"捉虫专家"},"social":"not_follow"},{"user":{"user_id":"5f5a82d6db811c453fa456d009c8067c","internal_id":7316,"user_name":"debugsbunny","nick_name":"debugsbunny","sex":"unknown","avatar":"files/faces/none.gif","user_desc":""},"social":"not_follow"},{"user":{"user_id":"4b84496a0b9344bcc3ea56d009bee86b","internal_id":208267,"user_name":"debugger","nick_name":"debugger","sex":"male","avatar":"files/faces/face_aplomadofalcon.gif","user_desc":""},"social":"not_follow"},{"user":{"user_id":"4164dfe7fffe8f34f39f56d0095992c6","internal_id":241393,"user_name":"17debug","nick_name":"17debug","sex":"unknown","avatar":"files/faces/none.gif","user_desc":null},"social":"not_follow"},{"user":{"user_id":"cacc2ae027f146fbe64e56d00974b752","internal_id":338640,"user_name":"tdebug","nick_name":"tdebug","sex":"unknown","avatar":"files/faces/none_header.gif","user_desc":""},"social":"not_follow"},{"user":{"user_id":"60f20736b88d880d29ba56d009338219","internal_id":413902,"user_name":"hwxdebug","nick_name":"hwxdebug","sex":"unknown","avatar":"files/faces/none.gif","user_desc":"万物已空"},"social":"not_follow"},{"user":{"user_id":"75b9056d4aed633fc26556d009589aea","internal_id":674077,"user_name":"pediydebug","nick_name":"pediydebug","sex":"unknown","avatar":"files/faces/none_header.gif","user_desc":null},"social":"not_follow"},{"user":{"user_id":"433bf87e809ed253098a56d009ed924b","internal_id":775483,"user_name":"debug2012","nick_name":"debug2012","sex":"unknown","avatar":"files/faces/none_header.gif","user_desc":null},"social":"not_follow"},{"user":{"user_id":"2b6e0d5a7411dfa4e47c56d0094bd409","internal_id":1026936,"user_name":"hermandebug","nick_name":"hermandebug","sex":"unknown","avatar":"files/faces/none_header.gif","user_desc":null},"social":"not_follow"},{"user":{"user_id":"b9f934d7bfacd304f95756d009c4aaeb","internal_id":1065236,"user_name":"debugpeng","nick_name":"debugpeng","sex":"male","avatar":"files/faces/none_header.gif","user_desc":null},"social":"not_follow"},{"user":{"user_id":"12a4f8153c045892531a56d0098e3c76","internal_id":1182743,"user_name":"superdebugger","nick_name":"superdebugger","sex":"unknown","avatar":"files/faces/none_header.gif","user_desc":null},"social":"not_follow"}]
     * photo_domain_path : http://192.168.1.231:8002/
     */

    private String photo_domain_path;
    /**
     * user : {"user_id":"45ca3e6ac86c48f9d19456d009d3620a","internal_id":410,"user_name":"Debug","nick_name":"Debug","sex":"unknown","avatar":"files/faces/10/user410.gif","user_desc":"捉虫专家"}
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
         * user_id : 45ca3e6ac86c48f9d19456d009d3620a
         * internal_id : 410
         * user_name : Debug
         * nick_name : Debug
         * sex : unknown
         * avatar : files/faces/10/user410.gif
         * user_desc : 捉虫专家
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
