package net.doyouhike.app.bbs.biz.openapi.response.account;

import net.doyouhike.app.bbs.biz.db.green.entities.UserFans;

import java.util.List;

/**
 * 作者：luochangdong on 16/9/13
 * 描述：
 */
public class UsersFansResp {


    /**
     * page_index : 1
     * page_size : 20
     * page_count : 1
     * total_records : 11
     */

    private PageBean page;
    /**
     * fans : [{"follow_each":true,"user_id":"58e10e8ca7ada284118e56d00908f2f2","nick_name":"syne","sex":"unknown","avatar":"files/faces/none.gif"},{"follow_each":true,"user_id":"26319e067d0da69c58a356d00940cc37","nick_name":"可以二十个字哈哈哈哈哈嘎哈哈哈哈哈哈哈哈","sex":"male","avatar":"files/faces/d/1/d1cb7fa2d.jpg"},{"follow_each":true,"user_id":"265d5e7549c2bbfb26da56d00996c9a6","nick_name":"z1947","sex":"unknown","avatar":"files/faces/1/a/1a0b6ac4d.jpg"},{"follow_each":true,"user_id":"440f8685026ee1a22a2d56d0098f89f0","nick_name":"wowabc","sex":"male","avatar":"files/faces/b/5/b56bd487b.jpg"},{"follow_each":true,"user_id":"441301e888668934862a56d00990674b","nick_name":"root","sex":"male","avatar":"files/faces/a/2/a22fcff51.jpg"},{"follow_each":true,"user_id":"9a9356656bdb186a0ac256d009367892","nick_name":"JTam我","sex":"male","avatar":"files/faces/9/0/90e9662ba.jpg"},{"follow_each":true,"user_id":"df4801faa5228af41858571de71046cf","nick_name":"Hawake","sex":"unknown","avatar":"files/faces/none_header.gif"},{"follow_each":true,"user_id":"440bfeb13f64d308fdd356d00931727a","nick_name":"老大","sex":"unknown","avatar":"files/faces/b/a/ba6f01bab.jpg"},{"follow_each":true,"user_id":"cda884c5ce4d96933c0056d009f03b2e","nick_name":"江湖骗子啊;;kkj","sex":"female","avatar":"files/faces/3/9/39dfbfeb7.jpg"},{"follow_each":true,"user_id":"653260d3c20416bc5ecb56d009f814a1","nick_name":"侃爷","sex":"female","avatar":"files/faces/8/e/8ede07e12.jpg"},{"follow_each":true,"user_id":"4417b1d0efa10f1372c456d009a42df6","nick_name":"coffee","sex":"female","avatar":"files/faces/b/b/bbb8e9633.jpg"}]
     * page : {"page_index":1,"page_size":20,"page_count":1,"total_records":11}
     * photo_domain_path : http://static.test.doyouhike.net/
     */

    private String photo_domain_path;
    /**
     * follow_each : true
     * user_id : 58e10e8ca7ada284118e56d00908f2f2
     * nick_name : syne
     * sex : unknown
     * avatar : files/faces/none.gif
     */

    private List<UserFans> fans;

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public String getPhoto_domain_path() {
        return photo_domain_path;
    }

    public void setPhoto_domain_path(String photo_domain_path) {
        this.photo_domain_path = photo_domain_path;
    }

    public List<UserFans> getFans() {
        return fans;
    }

    public void setFans(List<UserFans> fans) {
        this.fans = fans;
    }

    public static class PageBean {
        private int page_index;
        private int page_size;
        private int page_count;
        private int total_records;

        public int getPage_index() {
            return page_index;
        }

        public void setPage_index(int page_index) {
            this.page_index = page_index;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getPage_count() {
            return page_count;
        }

        public void setPage_count(int page_count) {
            this.page_count = page_count;
        }

        public int getTotal_records() {
            return total_records;
        }

        public void setTotal_records(int total_records) {
            this.total_records = total_records;
        }
    }


}
