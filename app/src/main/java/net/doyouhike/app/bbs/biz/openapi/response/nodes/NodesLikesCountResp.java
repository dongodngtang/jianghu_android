package net.doyouhike.app.bbs.biz.openapi.response.nodes;

/**
 * 作者：luochangdong on 16/9/13
 * 描述：
 */
public class NodesLikesCountResp {


    /**
     * like_num : 0
     * liked : false
     */

    private int like_num;
    private boolean liked;

    public int getLike_num() {
        return like_num;
    }

    public void setLike_num(int like_num) {
        this.like_num = like_num;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}
