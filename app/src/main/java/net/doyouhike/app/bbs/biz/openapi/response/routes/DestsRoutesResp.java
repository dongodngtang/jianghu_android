package net.doyouhike.app.bbs.biz.openapi.response.routes;

import net.doyouhike.app.bbs.chat.helper.Constant;

import java.util.List;

/**
 * 作者：luochangdong on 16/9/21
 * 描述：
 */
public class DestsRoutesResp {

    /**
     * items : [{"node_slug":"dongbuhaibinzhandao-hiking","route_name":"深圳东部海滨栈道徒步","route_type_name":"健行","route_type_id":2,"banner":{"photo_path":"files/2015/03/18/e/e1e7288a413a8088ce378cc5df88a870","photo_ext":"jpg"}},{"node_slug":"shenzhenmeilinshuiku-hiking","route_name":"深圳梅林水库-二线关休闲徒步","route_type_name":"健行","route_type_id":2,"banner":{"photo_path":"files/2009/03/17/f/f6d4895f561065f0c33a3db25160daf5","photo_ext":"jpg"}},{"node_slug":"shaoguanyunjishan-mountaineering","route_name":"韶关云髻山穿越","route_type_name":"山野穿越","route_type_id":1,"banner":{"photo_path":"files/2013/04/15/8/8e893eb36b44256f17f564ea26c4b162","photo_ext":"jpg"}},{"node_slug":"changshayuelushan-hiking","route_name":"长沙岳麓山登顶","route_type_name":"健行","route_type_id":2,"banner":{"photo_path":"files/2014/12/13/2/209188c8f8556fbfac449645502a9804","photo_ext":"jpg"}},{"node_slug":"dongguanlianshuixian-mountaineering","route_name":"东莞莲水线穿越","route_type_name":"山野穿越","route_type_id":1,"banner":{"photo_path":"files/2013/01/10/0/05c388f7c5db7687e8dd4828243849d1","photo_ext":"jpg"}},{"node_slug":"huizhouheipaijiao-mountaineering","route_name":"惠州黑排角穿越","route_type_name":"山野穿越","route_type_id":1,"banner":{"photo_path":"files/2013/12/23/4/49eb70fbcef344979b3970c57750b948","photo_ext":"jpg"}},{"node_slug":"meizhoujiulongzhang-mountaineering","route_name":"梅州九龙嶂穿越","route_type_name":"山野穿越","route_type_id":1,"banner":{"photo_path":"files/2013/05/13/c/cdebd0f0fcdef18addc45f9338eba219","photo_ext":"jpg"}},{"node_slug":"meizhouqimuzhang-mountaineering","route_name":"梅州七目嶂穿越","route_type_name":"山野穿越","route_type_id":1,"banner":{"photo_path":"files/2013/01/03/4/4d9f7a16c5d43df91f72c0e5788ed98f","photo_ext":"jpg"}},{"node_slug":"huizhoubaikalian-mountaineering","route_name":"惠州白卡莲重装穿越","route_type_name":"山野穿越","route_type_id":1,"banner":{"photo_path":"files/2013/01/28/d/ddf38f7dc1fc361162474acec09c1d2a","photo_ext":"jpg"}},{"node_slug":"meizhoutongguzhangnanbei-mountaineering","route_name":"梅州铜鼓嶂南北穿越","route_type_name":"山野穿越","route_type_id":1,"banner":{"photo_path":"files/2014/12/11/9/93deffa71767dc527b9609423289dff9","photo_ext":"jpg"}},{"node_slug":"luokeng-xindong-mountaineering","route_name":"韶关罗坑-船底顶-新洞穿越","route_type_name":"山野穿越","route_type_id":1,"banner":{"photo_path":"files/2014/06/19/b/ba1083571d8dc2d144c60f258f782487","photo_ext":"jpg"}},{"node_slug":"mingshan-tongguzhang-mountaineering","route_name":"梅州明山嶂-铜鼓嶂穿越","route_type_name":"山野穿越","route_type_id":1,"banner":{"photo_path":"files/2013/06/16/f/f6f3e722a758296b12291364702ee103","photo_ext":"jpg"}},{"node_slug":"meihuading-xuehuading-mountaineering","route_name":"韶关黄思脑-梅花顶-雪花顶穿越","route_type_name":"山野穿越","route_type_id":1,"banner":{"photo_path":"files/2011/11/01/2/272fc86a5daf0a07a81adc60d4f0c117","photo_ext":"jpg"}},{"node_slug":"shaoguangouweizhang-mountaineering","route_name":"韶关狗尾嶂穿越","route_type_name":"山野穿越","route_type_id":1,"banner":{"photo_path":"files/2012/02/21/e/ec730fd41bcb75ef6204bfd07e928b05","photo_ext":"jpg"}},{"node_slug":"huizhoubaiyunzhang-mountaineering","route_name":"惠州白云嶂穿越","route_type_name":"山野穿越","route_type_id":1,"banner":{"photo_path":"files/2013/10/15/8/8fd6312c37c38ba96c0aab42e9deb4b3","photo_ext":"jpeg"}},{"node_slug":"beijingxilingshan-mountaineering","route_name":"北京西灵山穿越","route_type_name":"山野穿越","route_type_id":1,"banner":{"photo_path":"files/2014/06/19/e/e158e01100d7e7529866d08bf2b14368","photo_ext":"jpg"}},{"node_slug":"heyuanwanluhu-hiking","route_name":"河源万绿湖徒步","route_type_name":"健行","route_type_id":2,"banner":{"photo_path":"files/2013/10/06/1/1e5e23982be06f19dab6548131a24774","photo_ext":"jpg"}},{"node_slug":"shenzhendalugang-egongwan-mountaineering","route_name":"深圳大鹿港-鹅公湾穿越","route_type_name":"山野穿越","route_type_id":1,"banner":{"photo_path":"files/2014/03/17/8/8d5d94ff143e32cbac9faba1e534fc92","photo_ext":"jpg"}},{"node_slug":"hangzhou-marathon-running","route_name":"杭州国际马拉松全程线跑步","route_type_name":"跑步","route_type_id":4,"banner":{"photo_path":"files/2010/11/07/3/32a65e18fddc0316a996bc053ef985a9","photo_ext":"jpg"}},{"node_slug":"yantianshandi-marathon-running","route_name":"深圳盐田山地马拉松跑","route_type_name":"跑步","route_type_id":4,"banner":{"photo_path":"files/2013/12/02/f/fd0ca407c9a0a16660cfe853e8ed76e1","photo_ext":"jpg"}}]
     * photo_domain_path : http://192.168.1.231:8002/
     * page : {"page_index":1,"page_size":20,"page_count":25,"total_records":500}
     */

    private String photo_domain_path;
    /**
     * page_index : 1
     * page_size : 20
     * page_count : 25
     * total_records : 500
     */

    private PageBean page;
    /**
     * node_slug : dongbuhaibinzhandao-hiking
     * route_name : 深圳东部海滨栈道徒步
     * route_type_name : 健行
     * route_type_id : 2
     * banner : {"photo_path":"files/2015/03/18/e/e1e7288a413a8088ce378cc5df88a870","photo_ext":"jpg"}
     */

    private List<ItemsBean> items;

    public String getPhoto_domain_path() {
        return photo_domain_path;
    }

    public void setPhoto_domain_path(String photo_domain_path) {
        this.photo_domain_path = photo_domain_path;
    }

    public PageBean getPage() {
        return page;
    }

    public void setPage(PageBean page) {
        this.page = page;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
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

    public static class ItemsBean {
        private String node_slug;
        private String route_name;
        private String route_type_name;
        private int route_type_id;
        /**
         * photo_path : files/2015/03/18/e/e1e7288a413a8088ce378cc5df88a870
         * photo_ext : jpg
         */

        private BannerBean banner;

        public String getNode_slug() {
            return node_slug;
        }

        public void setNode_slug(String node_slug) {
            this.node_slug = node_slug;
        }

        public String getRoute_name() {
            return route_name;
        }

        public void setRoute_name(String route_name) {
            this.route_name = route_name;
        }

        public String getRoute_type_name() {
            return route_type_name;
        }

        public void setRoute_type_name(String route_type_name) {
            this.route_type_name = route_type_name;
        }

        public int getRoute_type_id() {
            return route_type_id;
        }

        public void setRoute_type_id(int route_type_id) {
            this.route_type_id = route_type_id;
        }

        public BannerBean getBanner() {
            return banner;
        }

        public void setBanner(BannerBean banner) {
            this.banner = banner;
        }

        public static class BannerBean {
            private String photo_path;
            private String photo_ext;

            public String getMain_photo() {
                return Constant.PHOTO_DOMAIN_PATH + photo_path + "." + photo_ext;
            }

            public String getPhoto_path() {
                return photo_path;
            }

            public void setPhoto_path(String photo_path) {
                this.photo_path = photo_path;
            }

            public String getPhoto_ext() {
                return photo_ext;
            }

            public void setPhoto_ext(String photo_ext) {
                this.photo_ext = photo_ext;
            }
        }
    }
}
