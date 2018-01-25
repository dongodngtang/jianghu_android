package net.doyouhike.app.bbs.biz.openapi.request.users.ims;

import com.google.gson.annotations.Expose;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;
import net.doyouhike.app.bbs.biz.openapi.OpenApiUrl;

/**
 * 作者：luochangdong on 16/11/4
 * q q:2270333671
 * 描述：
 */
@RequestUrlAnnotation(OpenApiUrl.MESSAGE_PMS)
public class MessagePmsPost extends BasePostRequest {


    /**
     * from_id : 发送者uuid
     * to_id : 接受者uuid
     * subject : 可以不用传
     * body : 可以不用传
     */

    @Expose
    private String from_id;
    @Expose
    private String to_id;
    @Expose
    private String subject;
    @Expose
    private String body;

    public String getFrom_id() {
        return from_id;
    }

    public void setFrom_id(String from_id) {
        this.from_id = from_id;
    }

    public String getTo_id() {
        return to_id;
    }

    public void setTo_id(String to_id) {
        this.to_id = to_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
