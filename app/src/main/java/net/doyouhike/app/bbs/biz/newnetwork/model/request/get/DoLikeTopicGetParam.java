package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;


import net.doyouhike.app.bbs.biz.newnetwork.Event.DoLikeEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.newnetwork.net.ResponseEventAnnotation;

/**
 * Created by zaitu on 15-11-30.
 * 点赞一个帖子
 */
@RequestUrlAnnotation(Content.REQ_DO_LIKE_TOPIC)
@ResponseEventAnnotation(DoLikeEvent.class)
public class DoLikeTopicGetParam extends BaseTokenGetParams {

    private String topicId;//帖子id

    public String getTopicId() {
        return topicId;
    }

    public void setTopicId(String topicId) {
        this.topicId = topicId;
    }

    @Override
    protected void setMapValue() {
        super.setMapValue();
        map.put("topicId",topicId);
    }

}
