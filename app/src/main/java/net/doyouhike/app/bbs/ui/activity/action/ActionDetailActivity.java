package net.doyouhike.app.bbs.ui.activity.action;

import android.content.Intent;
import android.view.View;

import com.flyco.dialog.listener.OnBtnClickL;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.activity.BaseActivity;
import net.doyouhike.app.bbs.biz.event.action.ActionEditRefreshEvent;
import net.doyouhike.app.bbs.biz.event.open.AccountUserFollowEvent;
import net.doyouhike.app.bbs.biz.event.open.NodesLikeEvent;
import net.doyouhike.app.bbs.biz.presenter.action.ActionDetailPresenter;
import net.doyouhike.app.bbs.ui.util.TipUtil;
import net.doyouhike.app.bbs.ui.widget.common.OnScrollListenerScrollView;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.util.ShareUtil;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.library.ui.uistate.UiState;

import butterknife.InjectView;

/**
 * Filework: 活动详情
 * Author: luochangdong
 * Date:16-3-18
 */
public class ActionDetailActivity extends BaseActivity implements IActionDetailView {

    /**
     * 标题
     */
    @InjectView(R.id.tar_title)
    TitleView tar_title;


    /**
     * 底部整体 布局
     */
    @InjectView(R.id.rl_action_detail_bottom)
    View rl_action_detail_bottom;

    @InjectView(R.id.sv_action_detail_content)
    OnScrollListenerScrollView viContent;

    @InjectView(R.id.vi_action_detail_part1)
    View header;

    private ActionDetailPresenter actionDetailPresenter;

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_action_detail_main;
    }


    @Override
    protected void initViewsAndEvents() {
        //初始化  分享控件
        ShareUtil.init(this);
        //绑定控件

        actionDetailPresenter = new ActionDetailPresenter(this, ActionDetailActivity.this, this.getIntent());


        tar_title.getTitle_text().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viContent.smoothScrollTo(0, 0);
            }
        });
        //分享 点击
        tar_title.setListener(new TitleView.ClickListener() {
            @Override
            public void clickLeft() {
                onBackPressed();
            }

            @Override
            public void clickRight() {
                if (StringUtil.isNotEmpty(actionDetailPresenter.address))
                    actionDetailPresenter.showShareDialog();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        actionDetailPresenter.requestData();
    }

    /**
     * 编辑活动成功
     *
     * @param event
     */
    public void onEventMainThread(ActionEditRefreshEvent event) {
        actionDetailPresenter.getDataFromNet();
    }


    @Override
    protected View getLoadingTargetView() {
        return viContent;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }


    /**
     * @param response 监听关注的网络请求
     */
    public void onEventMainThread(AccountUserFollowEvent response) {
        actionDetailPresenter.setFollowUserResponse(response);
    }

    /**
     * 点赞 网络监听
     *
     * @param event
     */
    public void onEventMainThread(NodesLikeEvent event) {
        actionDetailPresenter.setLikeResponse(event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ActionDetailPresenter.INTENT_EXTRA_NAME_EDIT) {
            if (null != data && data.getBooleanExtra("delete", false))
                ActionDetailActivity.this.finish();
        }

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode != 0 && requestCode != ActionDetailPresenter.REQUEST_CODE_TO_COMMENT) {
            ShareUtil.onActivityResult(requestCode, resultCode, data);
        }
    }


    public TitleView getTar_title() {
        return tar_title;
    }


    @Override
    public View getHeader() {
        return header;
    }


    @Override
    public View getBottomView() {
        return rl_action_detail_bottom;
    }

    @Override
    public OnScrollListenerScrollView getContentView() {
        return viContent;
    }


    @Override
    public void onLoadStart() {
        updateView(UiState.LOADING, null);
    }

    @Override
    public void onLoadSuccess() {
        updateView(UiState.NORMAL);
    }

    @Override
    public void onLoadError(int code) {
        updateView(UiState.ERROR.setMsg(getString(R.string.common_error_msg),
                getString(R.string.try_to_click_refresh)), refreshListener);
        if (code == 1401001)
            TipUtil.alert(mContext, "原内容已删除", new OnBtnClickL() {
                @Override
                public void onBtnClick() {
                    finish();
                }
            });

    }

    /**
     * 刷新按钮
     */
    private View.OnClickListener refreshListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            updateView(UiState.LOADING, null);
            actionDetailPresenter.requestData();
        }
    };

}
