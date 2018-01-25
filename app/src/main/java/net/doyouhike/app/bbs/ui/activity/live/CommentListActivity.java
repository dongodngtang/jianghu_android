package net.doyouhike.app.bbs.ui.activity.live;

import android.content.Intent;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.LiveCommentPage;
import net.doyouhike.app.bbs.biz.event.open.DeleteCommentEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.comment.CommentListInfo;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventCommentListResp;
import net.doyouhike.app.bbs.ui.activity.AddCommentActivity;
import net.doyouhike.app.bbs.ui.adapter.NodeTimelineAdapter;
import net.doyouhike.app.bbs.ui.home.NewLiveAdapter;
import net.doyouhike.app.bbs.ui.widget.common.IUpdateView;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.biz.helper.list_helper.SimpleListHelper;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.uistate.UiState;

import butterknife.InjectView;

/**
 * 评论列表
 */
public class CommentListActivity extends BaseActivity implements IUpdateView {

    /**
     * 主题ID
     */
    public static final String I_NODE_ID = "LiveCommentActivity_param1";
    /**
     * 能否删除自己的评论,版主有权限删除
     */
    public static final String I_CAN_DELETE = "LiveCommentActivity_param3";
    /**
     * 标题格式
     */
    private final String TITLE_FORMAT = "评论(%d)";

    /**
     * 标题栏
     */
    @InjectView(R.id.navigation_title)
    TitleView navigationTitle;
    /**
     * 评论列表
     */
    @InjectView(R.id.lv_activity_comment_list)
    PullToRefreshListView mLvComment;
    /**
     * 直播id
     */
    String mNodeId;

    private SimpleListHelper simpleListHelper;
    private LiveCommentPage mPage;

    private CommentListInfo mNodeInfo;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == AddCommentActivity.RESULT_CODE_TO_COMMENT_FINISH
                && requestCode == AddCommentActivity.RESULT_CODE_TO_COMMENT_FINISH) {
            //评论界面返回
            if (data != null) {
                handleCommentCallBack(data);
            }
        }


    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_live_coment;
    }

    @Override
    protected View getLoadingTargetView() {
        return mLvComment;
    }


    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void initViewsAndEvents() {
        initView();

        initListener();

        initData();

    }


    private void initData() {
        mPage = new LiveCommentPage(this);
        simpleListHelper = new SimpleListHelper(mLvComment, this, mPage);


        //传递nodeId
        mNodeId = getIntent().getStringExtra(I_NODE_ID);
        mPage.getRequestParam().setNode_id(mNodeId);
        mPage.getAdapter().setNodeId(mNodeId);

        //传递是否可以删除,版主有权限删除
        boolean isCanDel = getIntent().getBooleanExtra(I_CAN_DELETE, false);
        mPage.getAdapter().setCanDel(isCanDel);

        //刷新数据
        simpleListHelper.getData(true);
    }

    private void initListener() {
        navigationTitle.setListener(new TitleView.ClickListener() {
            @Override
            public void clickLeft() {
                //返回
                CommentListActivity.this.finish();
            }

            @Override
            public void clickRight() {
                //添加评论
                ActivityRouter.openAddCommentActivity(CommentListActivity.this, mNodeId);
            }
        });
    }

    private void initView() {
        //没登陆,隐藏添加评论按钮
        UIUtils.showView(navigationTitle.getRight_text(), UserInfoUtil.getInstance().isLogin());
    }

    /**
     * @param count 获取评论后,刷新数量
     */
    private void setCount(int count) {
        navigationTitle.setTitle(String.format(TITLE_FORMAT, count));
    }

    /**
     * 处理添加评论
     *
     * @param data
     */
    private void handleCommentCallBack(Intent data) {
        String commentInfoStr = data
                .getStringExtra(AddCommentActivity.INTENT_EXTRA_NAME_COMMENT_INFO);

        if (!TextUtils.isEmpty(commentInfoStr)) {

            EventCommentListResp.ItemsBean newCommentInfo = new Gson().fromJson(
                    commentInfoStr, EventCommentListResp.ItemsBean.class);
            updateReplyComment(newCommentInfo);

        }
    }


    /**
     * 更新回复评论
     *
     * @param newCommentInfo 回复评论内容
     */
    public void updateReplyComment(EventCommentListResp.ItemsBean newCommentInfo) {
        if (simpleListHelper == null)
            return;
        //添加回复评论数据
        simpleListHelper.getItems().add(0, newCommentInfo);
        //刷新列表数据
        simpleListHelper.getAdapter().notifyDataSetChanged();
        //刷新标题数量
        if (null != mNodeInfo) {
            int commentCount = mNodeInfo.getChildNum();
            mNodeInfo.setChildNum(++commentCount);
            setCount(commentCount);
        }

        updateView(UiState.NORMAL);
    }


    /**
     * @param event 删除评论消息
     */
    public void onEventMainThread(DeleteCommentEvent event) {
        //刷新标题数量
        if (null != mNodeInfo) {
            int commentCount = mNodeInfo.getChildNum();
            mNodeInfo.setChildNum(--commentCount);
            setCount(commentCount);

            if (simpleListHelper.getItems().isEmpty()) {
                updateView(UiState.EMPTY.setMsg(getString(R.string.common_no_content)));
            }
        }


    }

    /**
     * 获取评论列表后,发送此消息
     *
     * @param node_info 评论列表信息
     */
    public void onEventMainThread(CommentListInfo node_info) {
        mNodeInfo = node_info;

        if (null != mNodeInfo) {
            setCount(mNodeInfo.getChildNum());

        }
    }

    @Override
    protected void onDestroy() {
        simpleListHelper.onDestroy();
        super.onDestroy();
    }
}
