package net.doyouhike.app.bbs.biz.openapi.request.nodes;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.event.CommentEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.ResponseEventAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventCommentResp;

import java.util.List;

/**
 * 作者：luochangdong on 16/9/14
 * 描述：
 */
@ResponseEventAnnotation(CommentEvent.class)
public class NodesCommentPost extends BasePostRequest {

    /**
     * refrence_id : (被回复评论的comment_id) 可不传
     * reply_to : (被回复评论的user_id) 可不传
     * city_id :  (用户所在城市id)可不传
     * content : [{"type":"image","is_new":1,"content":"6000001"}]
     */
    @Expose
    private String refrence_id;
    @Expose
    private String reply_to;
    @Expose
    private String city_id;

    private String node_id;

    @Override
    public String getSubUrl() {
        return OpenApiUrl.NODES + "/" + node_id + "/comments";
    }

    /**
     * type : image
     * is_new : 1
     * content : 6000001
     */
    @Expose
    private List<ContentBean> content;

    public String getRefrence_id() {
        return refrence_id;
    }

    public void setRefrence_id(String refrence_id) {
        this.refrence_id = refrence_id;
    }

    public String getReply_to() {
        return reply_to;
    }

    public void setReply_to(String reply_to) {
        this.reply_to = reply_to;
    }

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public String getNode_id() {
        return node_id;
    }

    public void setNode_id(String node_id) {
        this.node_id = node_id;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        @Expose
        private String type;
        @Expose
        private int is_new;
        @Expose
        private String content;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getIs_new() {
            return is_new;
        }

        public void setIs_new(int is_new) {
            this.is_new = is_new;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<EventCommentResp>(EventCommentResp.class);
    }
}
