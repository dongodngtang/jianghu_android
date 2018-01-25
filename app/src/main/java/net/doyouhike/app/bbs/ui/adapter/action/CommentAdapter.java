package net.doyouhike.app.bbs.ui.adapter.action;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.zhy.base.adapter.ViewHolder;
import com.zhy.base.adapter.abslistview.CommonAdapter;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.event.open.DeleteCommentEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.NodesHelper;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventCommentListResp;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.home.NewLiveAdapter;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.util.UserHeadNickClickHelper;
import net.doyouhike.app.bbs.ui.util.CommentContentHelper;
import net.doyouhike.app.bbs.ui.util.OnPopupMenuItemClickListener;
import net.doyouhike.app.bbs.ui.util.PopupMenuUtil;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.glide.GlideHelper;
import net.doyouhike.app.library.ui.utils.DateUtils;

import java.util.List;

import de.greenrobot.event.EventBus;

/**
 * 评论列表适配
 * Created by zengjiang on 16/5/25.
 */
public class CommentAdapter extends CommonAdapter<EventCommentListResp.ItemsBean> {

    /**
     * 主题id
     */
    private String mNodeId;
    /**
     * 是否能够删除
     */
    private boolean isCanDel = false;

    private int minilog_type = NewLiveAdapter.LIVE_TYPE_TEXT_IMAGE;

    public CommentAdapter(Context context, List<EventCommentListResp.ItemsBean> datas) {
        super(context, datas, R.layout.item_live_comment);
    }

    /**
     * 设置直播id
     *
     * @param nodeId 主题id
     */
    public void setNodeId(String nodeId) {
        this.mNodeId = nodeId;
    }

    /**
     * @param minilog_type 类型 活动还是帖子,还是直播
     */
    public void setMinilog_type(int minilog_type) {
        this.minilog_type = minilog_type;
    }

    /**
     * @param canDel 是否能删除
     */
    public void setCanDel(boolean canDel) {
        isCanDel = canDel;
    }

    @Override
    public void convert(final ViewHolder holder, final EventCommentListResp.ItemsBean item) {
        //时间
        holder.setText(R.id.tv_item_live_comment_time, DateUtils.getStrDate(mContext, item.getCreated_at() * 1000));
        //昵称
        holder.setText(R.id.tv_item_live_comment_user_name, item.getUser().getNick_name());
        //头像
        ImageView ivAvatar = holder.getView(R.id.iv_item_live_comment_avatar);
        String avatorStr = Constant.PHOTO_DOMAIN_PATH + item.getUser().getAvatar();
        GlideHelper.displayHeader(mContext, ivAvatar, avatorStr);
        //设置头像打开他人主页
        UserHeadNickClickHelper.getInstance().setClickListener(ivAvatar,
                item.getUser().getNick_name(),
                item.getUser().getUser_id(),
                item.getUser().getUser_id(),
                item.getUser().getAvatar()
        );
        //点击昵称,打开他人主页
        holder.setOnClickListener(R.id.tv_item_live_comment_user_name, new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ActivityRouter.openOtherPageActivity(mContext, item.getUser().getUser_id());
            }
        });


        final View viAnchor = holder.getView(R.id.vi_item_live_comment_popupmenu_anchor);

//        //内容
        final TextView tvComment = holder.getView(R.id.tv_item_live_comment_comment);

        //设置内容
        CommentContentHelper.setReplyToContent(
                tvComment,
                item.contentToString(),
                item.getReply_to().getNick_name(),
                item.getReply_to().getUser_id()
        );


        final View parent = holder.getView(R.id.ll_item_live_comment_parent);
        //长按弹出菜单
        parent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupMenu(viAnchor, item);
                return true;
            }
        });
        //点击回复评论
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyComment(v, item);
            }
        });

        tvComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replyComment(v, item);
            }
        });

        tvComment.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showPopupMenu(viAnchor, item);
                return true;
            }
        });

        //touch事件,按下评论,item背景变色
//        tvComment.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        parent.setSelected(true);
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        parent.setSelected(false);
//                        break;
//                    case MotionEvent.ACTION_CANCEL:
//                        parent.setSelected(false);
//                        break;
//                    case MotionEvent.ACTION_OUTSIDE:
//                        parent.setSelected(false);
//                        break;
//                }
//
//                return false;
//            }
//        });
    }


    /**
     * 显示弹框菜单
     *
     * @param anchorView 描点View
     * @param itemsBean  评论数据item
     */
    private void showPopupMenu(final View anchorView, final EventCommentListResp.ItemsBean itemsBean) {

        String[] items;

        if (UserInfoUtil.getInstance().isCurrentUser(itemsBean.getUser().getUser_id()) || isCanDel) {
            //只有自己发布的评论才能删除,版主能删除评论,但是约伴版主,不能删除
            items = new String[]{"回复", "删除"};
        } else {
            //只有回复按钮
            items = new String[]{"回复"};
        }

        PopupMenuUtil.showMenu(anchorView
                , new OnPopupMenuItemClickListener() {
                    @Override
                    public void onItemClick(int position) {
                        switch (position) {
                            case 0:
                                //评论
                                replyComment(anchorView, itemsBean);
                                break;
                            case 1:
                                //删除
                                delComment(itemsBean);
                                break;
                        }


                    }
                }
                , items);

    }


    private void delComment(final EventCommentListResp.ItemsBean commentLastListData) {
        //删除评论
        NodesHelper.getInstance().deleteNodeComments(mContext, mNodeId,
                commentLastListData.getComment_id(), new IOnResponseListener() {
                    @Override
                    public void onSuccess(Response response) {
                        mDatas.remove(commentLastListData);
                        notifyDataSetChanged();
                        EventBus.getDefault().post(new DeleteCommentEvent(mNodeId));
                    }

                    @Override
                    public void onError(Response response) {
                        TipUtil.alert(mContext, response.getMsg());
                    }
                });

    }


    private void replyComment(View v, EventCommentListResp.ItemsBean commentLastListData) {


        if (!UserInfoUtil.getInstance().isLogin()) {
            StringUtil.showSnack(v.getContext(), "请登录后,再评论");
            Intent intent = new Intent(v.getContext(), LoginActivity.class);
            v.getContext().startActivity(intent);
            return;
        }


        //回复评论
        ActivityRouter.openReplyCommentActivity(
                mContext,
                commentLastListData.getComment_id(),
                commentLastListData.getUser().getNick_name(),
                commentLastListData.getUser().getUser_id(),
                mNodeId,
                commentLastListData.getUser().getAvatar());
    }

    public void setList(List<EventCommentListResp.ItemsBean> list) {
        mDatas = list;
    }
}
