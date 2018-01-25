package net.doyouhike.app.bbs.biz.openapi.response.account;

import net.doyouhike.app.bbs.biz.db.green.entities.Follow;

import java.util.List;

/**
 * 作者：luochangdong on 16/9/12
 * 描述：
 */
public class UsersFollowsResp {

    /**
     * follows : [{"follow_each":true,"user_id":"58e10e8ca7ada284118e56d00908f2f2","nick_name":"syne","sex":"unknown","avatar":"files/faces/none.gif"},{"follow_each":true,"user_id":"26319e067d0da69c58a356d00940cc37","nick_name":"可以二十个字哈哈哈哈哈嘎哈哈哈哈哈哈哈哈","sex":"male","avatar":"files/faces/d/1/d1cb7fa2d.jpg"},{"follow_each":false,"user_id":"45d3c239c47b05bad9ff56d009949a6f","nick_name":"edwardzhou","sex":"male","avatar":"files/faces/none_header.gif"},{"follow_each":true,"user_id":"653260d3c20416bc5ecb56d009f814a1","nick_name":"侃爷","sex":"female","avatar":"files/faces/8/e/8ede07e12.jpg"},{"follow_each":true,"user_id":"265d5e7549c2bbfb26da56d00996c9a6","nick_name":"z1947","sex":"unknown","avatar":"files/faces/1/a/1a0b6ac4d.jpg"},{"follow_each":true,"user_id":"440f8685026ee1a22a2d56d0098f89f0","nick_name":"wowabc","sex":"male","avatar":"files/faces/b/5/b56bd487b.jpg"},{"follow_each":false,"user_id":"285ae85e4541f7f91db256d009039990","nick_name":"孙旭东","sex":"male","avatar":"files/faces/7/e/7eb64e09f.jpg"},{"follow_each":true,"user_id":"441301e888668934862a56d00990674b","nick_name":"root","sex":"male","avatar":"files/faces/a/2/a22fcff51.jpg"},{"follow_each":false,"user_id":"6ec5ac472ca82e9aa80256d009ad3c1d","nick_name":"绿小苤","sex":"female","avatar":"files/faces/1/7/1778ca3fb.jpg"},{"follow_each":true,"user_id":"9a9356656bdb186a0ac256d009367892","nick_name":"JTam我","sex":"male","avatar":"files/faces/9/0/90e9662ba.jpg"},{"follow_each":false,"user_id":"c64e73e9dd264a5b9bd456d009ed4a38","nick_name":"磨小编","sex":"unknown","avatar":"files/faces/b/5/b5090ec1e.jpg"},{"follow_each":true,"user_id":"440bfeb13f64d308fdd356d00931727a","nick_name":"老大","sex":"unknown","avatar":"files/faces/b/a/ba6f01bab.jpg"},{"follow_each":true,"user_id":"df4801faa5228af41858571de71046cf","nick_name":"Hawake","sex":"unknown","avatar":"files/faces/none_header.gif"},{"follow_each":true,"user_id":"4417b1d0efa10f1372c456d009a42df6","nick_name":"coffee","sex":"female","avatar":"files/faces/b/b/bbb8e9633.jpg"},{"follow_each":false,"user_id":"33aff12f90a9ba709ea756d0095b06d7","nick_name":"百公里资讯小组","sex":"male","avatar":"files/faces/a/9/a93c936ed.jpg"},{"follow_each":false,"user_id":"e0b68b118848a08c08f856d009b4e732","nick_name":"百公里","sex":"unknown","avatar":"files/faces/c/7/c7c64d083.jpg"},{"follow_each":false,"user_id":"892a13c65f78454377ab56d009b0a771","nick_name":"雅杰","sex":"male","avatar":"files/faces/f/f/ff4952b30.jpg"},{"follow_each":false,"user_id":"3a9b96434efeab7d203c56d009e15ee8","nick_name":"Annehiking","sex":"female","avatar":"files/faces/1/3/13bbf30b4.jpg"},{"follow_each":false,"user_id":"1d4008de1febc281616156d00989c904","nick_name":"bi雷","sex":"female","avatar":"files/faces/3/1/31f036641.jpg"},{"follow_each":false,"user_id":"92261e85d7e62db6d4ab56d0094e5af0","nick_name":"萌小七","sex":"female","avatar":"files/faces/9/1/91097d56a.jpg"},{"follow_each":false,"user_id":"57f72f653b80d83b44d056d009967760","nick_name":"I'm 狼","sex":"male","avatar":"files/faces/7/b/7b1f7ea49.jpg"},{"follow_each":false,"user_id":"1b4fb600f9bbf18fa2d256d009a978c5","nick_name":"yoline","sex":"male","avatar":"files/faces/a/d/ad3f3c70d.jpg"},{"follow_each":false,"user_id":"1b55a5ba055ee32caac256d00992ee4b","nick_name":"wenzi222","sex":"unknown","avatar":"files/faces/b/7/b7ec29728.jpg"},{"follow_each":false,"user_id":"74670d45af54dce4314a56d009b8ca59","nick_name":"mumu木","sex":"female","avatar":"files/faces/5/a/5a74f5092.jpg"},{"follow_each":false,"user_id":"616da8f4a6329048461156d009d3568d","nick_name":"匡匡匡","sex":"female","avatar":"files/faces/2/6/26f45adec.jpg"},{"follow_each":false,"user_id":"bcb6addad9fe6673a96f56d009d6473d","nick_name":"kjcover","sex":"male","avatar":"files/faces/1/4/147609396.jpg"},{"follow_each":false,"user_id":"9c896b230de4cf2408bc56d009ae9b26","nick_name":"一碗呐","sex":"female","avatar":"files/faces/7/4/74712f715.jpg"},{"follow_each":false,"user_id":"9d11092c9e5ca10ab6a356d0091c9edc","nick_name":"遇见一座_城","sex":"male","avatar":"files/faces/9/e/9eaf3c373.jpg"},{"follow_each":false,"user_id":"8c3508939e691d4c722956d0098683b9","nick_name":"law去旅行","sex":"male","avatar":"files/faces/a/8/a8a9d180c.jpg"},{"follow_each":false,"user_id":"53b512019ec0e4b5e93256d009965a67","nick_name":"磨小驴","sex":"unknown","avatar":"files/faces/f/7/f75724788.jpg"},{"follow_each":false,"user_id":"c54c4e4eb2613edb43bc56d009866bb0","nick_name":"饥饿的石头","sex":"unknown","avatar":"files/faces/none_header.gif"},{"follow_each":false,"user_id":"b42b283deea30e16ba4256d009553b39","nick_name":"徐徐嘘","sex":"unknown","avatar":"files/faces/d/d/dd89abbb1.jpg"},{"follow_each":false,"user_id":"5cc40ed7dc39e61f380556d009f4c486","nick_name":"无息咏叹调","sex":"unknown","avatar":"files/faces/none_header.gif"},{"follow_each":false,"user_id":"34fde7aea6bc667f9e8556d009f2e9b0","nick_name":"狼族精英","sex":"male","avatar":"files/faces/none_header.gif"},{"follow_each":false,"user_id":"cdb25558aef35f1e992756d009c090c6","nick_name":"就这么飞来漂去","sex":"male","avatar":"files/faces/e/e/eeb3291a1.jpg"},{"follow_each":false,"user_id":"cf27269bd5cf98e29d1856d009c6aa7d","nick_name":"刹灾_cwz","sex":"male","avatar":"files/faces/8/8/88fab514c.jpg"},{"follow_each":false,"user_id":"8dd5a528bc7279b00ebb56d00936441b","nick_name":"亦亦亦亦心","sex":"female","avatar":"files/faces/8/9/89758e33b.jpg"},{"follow_each":false,"user_id":"4f77fb055f6b685abd3956d0097d56f0","nick_name":"磨房","sex":"unknown","avatar":"files/faces/0/c/0cee1ada6.jpg"},{"follow_each":false,"user_id":"a0e4a322636ca75b694c56d009d0849e","nick_name":"磨小嗨","sex":"unknown","avatar":"files/faces/9/9/99aeeba2e.jpg"},{"follow_each":false,"user_id":"cdac3d711838f55e467556d009ed2f27","nick_name":"苹果嘻嘻","sex":"male","avatar":"files/faces/4/c/4c90d1ace.jpg"},{"follow_each":false,"user_id":"445f9613f7de25f97e9e56d009d2b0af","nick_name":"风间猫又萌又帅又可爱","sex":"male","avatar":"files/faces/f/d/fd6b3e30c.jpg"},{"follow_each":true,"user_id":"cda884c5ce4d96933c0056d009f03b2e","nick_name":"江湖骗子啊;;kkj","sex":"female","avatar":"files/faces/3/9/39dfbfeb7.jpg"},{"follow_each":false,"user_id":"4a110fb1ad06713a533856d0099d2679","nick_name":"执到宝","sex":"unknown","avatar":"files/faces/0/1/015a51619.jpg"}]
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

    private List<Follow> follows;

    public String getPhoto_domain_path() {
        return photo_domain_path;
    }

    public void setPhoto_domain_path(String photo_domain_path) {
        this.photo_domain_path = photo_domain_path;
    }

    public List<Follow> getFollows() {
        return follows;
    }

    public void setFollows(List<Follow> follows) {
        this.follows = follows;
    }


}
