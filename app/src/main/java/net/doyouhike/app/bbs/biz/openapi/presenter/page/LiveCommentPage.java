package net.doyouhike.app.bbs.biz.openapi.presenter.page;

import android.content.Context;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.PageBase;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.comment.CommentListInfo;
import net.doyouhike.app.bbs.biz.openapi.request.events.EventCommentsListGet;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventCommentListResp;
import net.doyouhike.app.bbs.ui.adapter.live.CommentListAdapter;

import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 评论页
 * Created by zengjiang on 16/5/25.
 */
public class LiveCommentPage extends PageBase<EventCommentListResp.ItemsBean> {


    public LiveCommentPage(Context context) {
        items = new ArrayList<>();
        adapter = new CommentListAdapter(context, items);
        mRequest = new EventCommentsListGet();

    }

    @Override
    public EventCommentsListGet getRequestParam(boolean isRefresh) {
        String lastId = "";

        if (!isRefresh && !getItems().isEmpty()) {
            //不是刷新,获取最后一项的评论id
            lastId = getItems().get(getItems().size() - 1).getComment_id();
        }

        getRequestParam().setLast_id(lastId);
        return getRequestParam();
    }

    @Override
    public void handleResponse(Response response, boolean isRefresh) {

        List<EventCommentListResp.ItemsBean> items= ((Response<EventCommentListResp>) response).getData().getItems();
        EventCommentListResp.PageBean pageBean= ((Response<EventCommentListResp>) response).getData().getPage();

        CommentListInfo node_info = new CommentListInfo();
        node_info.setChildNum(pageBean.getTotal_records());

        updateItem(items, isRefresh);


        //刷新评论数量,在标题栏
        EventBus.getDefault().post(node_info);
    }

    @Override
    public String getRequestTag() {
        return Content.REQ_GET_NODE_COMMENT_LIST;
    }

    @Override
    public EventCommentsListGet getRequestParam() {
        return (EventCommentsListGet) mRequest;
    }

    @Override
    public CommentListAdapter getAdapter() {
        return (CommentListAdapter)adapter;
    }

    @Override
    public String[] getEmptyTip() {
        return super.getEmptyTip();
    }

    @Override
    public boolean needEmptyRefresh() {
        return false;
    }
}
