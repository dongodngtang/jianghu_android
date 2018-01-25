package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;

/**
 * Created by zaitu on 15-11-28.
 */
public class CancelPublishGetReq extends BaseGetRequest {


    /**
     * token : xxx
     * topicId : xxx //帖子ID
     */

    private String token;
    private String topicId;

    @Override
    protected void setMapValue() {
        map.put("token",token);
        map.put("topicId",topicId);
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    public String getToken() {
        return token;
    }

    public String getTopicId() {
        return topicId;
    }
}
