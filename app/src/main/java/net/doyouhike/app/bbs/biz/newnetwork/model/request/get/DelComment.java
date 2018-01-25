package net.doyouhike.app.bbs.biz.newnetwork.model.request.get;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.message.GetCommentMeListResp;
import net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation;

/**
 * 删除评论请求
 * Created by zengjiang on 16/6/15.
 */
@RequestUrlAnnotation(Content.REQ_GET_TOPICS_DELCOMMENT)
public class DelComment extends BaseTokenGetParams {


    public DelComment(String comment_id,String token) {
        setToken(token);
        this.comment_id = comment_id;
    }

    @Override
    protected void setMapValue() {
        super.setMapValue();
        map.put("comment_id", comment_id);
    }

    @Override
    public IResponseProcess getProcess() {
        return new DataJsonParser<GetCommentMeListResp>(GetCommentMeListResp.class);
    }



    private String comment_id;

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
    }
}
