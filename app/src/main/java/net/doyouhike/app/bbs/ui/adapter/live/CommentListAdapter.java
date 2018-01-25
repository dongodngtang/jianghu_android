package net.doyouhike.app.bbs.ui.adapter.live;

import android.content.Context;

import com.zhy.base.adapter.ViewHolder;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.action.CommentLastListData;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventCommentListResp;
import net.doyouhike.app.bbs.ui.adapter.action.CommentAdapter;

import java.util.List;

/**
 * Created by zengjiang on 16/7/1.
 * 评论列表适配器
 */
public class CommentListAdapter extends CommentAdapter {

    public CommentListAdapter(Context context, List<EventCommentListResp.ItemsBean> datas) {
        super(context, datas);
    }

    @Override
    public void convert(ViewHolder holder, EventCommentListResp.ItemsBean commentLastListData) {
        super.convert(holder, commentLastListData);
        holder.setBackgroundRes(R.id.ll_item_live_comment_parent,R.drawable.selector_list_item_bg);


    }
}
