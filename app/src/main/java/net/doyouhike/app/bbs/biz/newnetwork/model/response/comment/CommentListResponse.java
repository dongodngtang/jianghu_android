package net.doyouhike.app.bbs.biz.newnetwork.model.response.comment;

import net.doyouhike.app.bbs.biz.entity.action.CommentLastListData;

import java.util.ArrayList;

/**
 * 评论列表响应
 * Created by zengjiang on 16/6/17.
 */
public class CommentListResponse {

    private CommentListInfo node_info;

    private ArrayList<CommentLastListData> data_list;


    public CommentListInfo getNode_info() {
        return node_info;
    }

    public void setNode_info(CommentListInfo node_info) {
        this.node_info = node_info;
    }

    public ArrayList<CommentLastListData> getData_list() {
        return data_list;
    }

    public void setData_list(ArrayList<CommentLastListData> data_list) {
        this.data_list = data_list;
    }
}
