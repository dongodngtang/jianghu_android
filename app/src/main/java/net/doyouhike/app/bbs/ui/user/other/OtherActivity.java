package net.doyouhike.app.bbs.ui.user.other;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.db.dao.UserInfoDbUtil;
import net.doyouhike.app.bbs.biz.db.green.entities.ChatUserInfo;
import net.doyouhike.app.bbs.biz.db.green.entities.Follow;
import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.event.GlobalDialogEvent;
import net.doyouhike.app.bbs.biz.event.im.GetOtherHxIdEvent;
import net.doyouhike.app.bbs.biz.event.im.HxidEvent;
import net.doyouhike.app.bbs.biz.event.open.AccountUserFollowEvent;
import net.doyouhike.app.bbs.biz.helper.im.action.IHxIdAction;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.service.BaseApiReq;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.IPage;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.State;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.user.PageEvent;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.user.PageLive;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.user.PageManager;
import net.doyouhike.app.bbs.biz.openapi.request.users.UserEventsGet;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.ui.widget.me.ViUserContent;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.uistate.UiState;
import net.doyouhike.app.library.ui.widgets.XSwipeRefreshLayout;

import java.util.List;

import butterknife.InjectView;
import de.greenrobot.event.EventBus;


public class OtherActivity extends net.doyouhike.app.bbs.base.activity.BaseActivity implements IViewOhter,
        PageManager.StateListener, ViUserContent.IViContentListener {


    public static final int TYPE_POST = 0;
    public static final int TYPE_ACTION = 1;
    public static final String OTHER_USER_ID = "other_user_id";

    @InjectView(R.id.vc_act_other_content)
    ViOtherContent vcActOtherContent;
    @InjectView(R.id.ptr_act_other_refresh)
    XSwipeRefreshLayout ptrActOtherRefresh;

    /**
     * 上拉加载更多列表
     */
    PullToRefreshListView plvContent;

    /**
     * 直播页面
     */
    OtherPageLive mStateLive;
    /**
     * 活动页面
     */
    PageEvent mStateEvent;

    /**
     * 不同页面的切换管理
     */
    PageManager mPageManager;

    /**
     * 用于获取数据的接口
     */
    private OtherNetPresenter mPresenterOther;

    /**
     * 用户ID
     */
    private String otherUserId;

    /**
     * 用户信息
     */
    CurrentUserDetails profile;
    /**
     * 标题
     */
    @InjectView(R.id.title_other)
    protected TitleView mTitleView;


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.activity_user_other;
    }

    @Override
    protected View getLoadingTargetView() {
        return vcActOtherContent.getListView();
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void initViewsAndEvents() {
        otherUserId = getIntent().getStringExtra(OTHER_USER_ID);

        initViews();

        initStateParams();

        initData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mStateLive.getAdapter().setCurrentUserId(UserInfoUtil.getInstance().getUserId());
    }

    @Override
    protected void onDestroy() {
        vcActOtherContent.onDestroyView();
        mPresenterOther.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_user_other_button:
                // 关注
                if (!UserInfoUtil.getInstance().isLogin()) {
                    //没登陆调到登陆界面
                    ActivityRouter.jumpToLogin();
                    return;
                }

                Follow follow = UsersHelper.getSingleTon().getFollowByUserId(otherUserId);

                if (follow == null) {
                    // 去执行 关注
                    UsersHelper.getSingleTon().follow(otherUserId, new IOnResponseListener() {
                        @Override
                        public void onSuccess(Response response) {
                            String user_id = (String) response.getExtraTag();
                            if (user_id.equals(otherUserId)) {
                                Follow follow = new Follow();
                                follow.setUser_id(user_id);
                                UsersHelper.getSingleTon().addFollow(follow);
                                AccountUserFollowEvent event = new AccountUserFollowEvent();
                                event.setResponse(response);
                                EventBus.getDefault().post(event);
                                if (vcActOtherContent.getTvUserInfo() != null) {
                                    vcActOtherContent.getTvUserInfo().setText("已关注");
                                }

                            }
                        }

                        @Override
                        public void onError(Response response) {

                        }
                    });
                } else {
                    // 取消关注

                    UsersHelper.getSingleTon().unFollow(otherUserId, new IOnResponseListener() {
                        @Override
                        public void onSuccess(Response response) {
                            String user_id = (String) response.getExtraTag();
                            if (user_id.equals(otherUserId)) {
                                UsersHelper.getSingleTon().deleteFollow(user_id);
                                AccountUserFollowEvent event = new AccountUserFollowEvent();
                                event.setResponse(response);
                                EventBus.getDefault().post(event);
                                if (vcActOtherContent.getTvUserInfo() != null)
                                    vcActOtherContent.getTvUserInfo().setText("关注");


                            }
                        }

                        @Override
                        public void onError(Response response) {

                        }
                    });
                }
                break;
            case R.id.llyt_follow:
                // 打开关注
                ActivityRouter.openFollowFansActivity(mContext, profile.getUser_id(), "follow");

                break;
            case R.id.llyt_follower:
                // 打开粉丝
                ActivityRouter.openFollowFansActivity(mContext, profile.getUser_id(), "fans");
                break;
            case R.id.llyt_me_navigation_post:
                // 直播
                mPageManager.checkoutSate(mStateLive);
                mPageManager.updateSate();
                break;
            case R.id.llyt_me_navigation_action:
                // 活动
                mPageManager.checkoutSate(mStateEvent);
                mPageManager.updateSate();
                break;
            default:
                break;
        }
    }

    @Override
    public void isListViewTop(boolean isTop) {
        ptrActOtherRefresh.setEnabled(isTop);
    }

    @Override
    public void onItemSelected(int position) {

        //重新从首页开始搜索
        mStateEvent.getRequestParam().setPage_index(1);
        mStateEvent.getRequestParam().setParticipate_type(UserEventsGet.getParticipateType(position));
        getListDate(mStateEvent);
        if (mStateEvent.getItems().isEmpty()) {
            updateView(UiState.LOADING);
        } else {
            ptrActOtherRefresh.setRefreshing(true);
        }

        mStateEvent.getAdapter().setActionType(position + 1);
    }
/**
 * overrider listener method
 * ######开始#######
 */

    /**
     * @param events     活动内容
     * @param isRefreash 是否刷新，true 为刷新，false为加载更多
     */
    @Override
    public void updateUserEvent(List<EventsListResp.ItemsBean> events, boolean isRefreash) {
        updateItemsAndVi(events, isRefreash, mStateEvent);

    }

    /**
     * @param items      直播列表
     * @param isRefreash 是否刷新，true 为刷新，false为加载更多
     */
    @Override
    public void
    updateTmLine(List<NodeTimeline.ItemsBean> items, boolean isRefreash) {
        updateItemsAndVi(items, isRefreash, mStateLive);

    }

    /**
     * @param profile 用户信息
     */
    @Override
    public void updateProfile(CurrentUserDetails profile) {
        vcActOtherContent.updateProfile(profile);

        this.profile = profile;
        //获取环信信息
        EventBus.getDefault().post(new GetOtherHxIdEvent(profile.getUser_id()));
    }

    /**
     * @param symbol     错误回调标志，用以区分接口
     * @param errCode    错误编码
     * @param msg        错误信息
     * @param isRefreash 是否刷新，true 为刷新，false为加载更多
     */
    @Override
    public void getItemsErr(int symbol, int errCode, String msg, boolean isRefreash) {


        if (isRefreash) {
            ptrActOtherRefresh.setRefreshing(false);
        } else {
            plvContent.onRefreshComplete();
        }


        String tip = getString(R.string.common_error_msg);

        if (errCode == BaseApiReq.ERROR_CODE_SERVICE) {
            msg = getString(R.string.try_to_click_refresh);
        }

        switch (symbol) {
            case ERR_EVENT:
                mStateEvent.getDataErr(tip, msg);
                break;
            case ERR_LIVE:
                mStateLive.getDataErr(tip, msg);
                break;
        }

        mPageManager.updateSate();
    }

    /**
     * @param symbol 错误回调标志，用以区分接口
     * @param msg    错误信息
     */
    @Override
    public void onRequestErr(int symbol, String msg) {

    }


    @Override
    public Context getContext() {
        return this;
    }


    /**
     * @param state 获取列表数据
     */
    @Override
    public void getListDate(IPage state) {

        if (state instanceof PageLive) {
            //获取直播数据
            mPresenterOther.getTimeLine(((PageLive) state).getRequestParam());
        } else if (state instanceof PageEvent) {
            //获取我的活动列表
            mPresenterOther.getUserEvent(((PageEvent) state).getRequestParam());
        }
    }

    @Override
    public void setViewState(IPage page, UiState state) {
        vcActOtherContent.setContentView(page.getItems().isEmpty());

        if (page.getState() == State.ERROR) {
            updateView(state, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateView(UiState.LOADING);
                    refreshData();
                }
            });
        } else {
            updateView(state);
        }
    }


    private void initViews() {

        plvContent = vcActOtherContent.getListView();


        plvContent.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {

            }

            @Override
            public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView) {

                if (mPageManager.getmStateImp().hasMore()) {
                    setLoadMoreView();
                    mPageManager.getMoreData();
                } else {

                    plvContent.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (null != plvContent) {
                                plvContent.onRefreshComplete();
//                                StringUtil.showSnack(OtherActivity.this, "已经是最后一页");
                            }
                        }
                    }, 100);

                }
            }
        });

        //  标题
        mTitleView = vcActOtherContent.getTitleView();
        UIUtils.showView(mTitleView.getRight_image(), UserInfoDbUtil.getInstance().isEixtEmId(otherUserId));
        mTitleView.setListener(new TitleView.ClickListener() {
            @Override
            public void clickLeft() {
                //返回按钮
                OtherActivity.this.finish();
            }

            @Override
            public void clickRight() {
                //聊天按钮
                chatToOther();

            }
        });


        //下拉刷新
        ptrActOtherRefresh.setColorSchemeColors(
                getResources().getColor(R.color.color_theme));
        ptrActOtherRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });


        mPresenterOther = new OtherNetPresenter(this);
        //初始化状态管理器
        mPageManager = new PageManager(plvContent);
        mPageManager.setStateListener(this);


        //条件筛选框
        vcActOtherContent.setListener(this);
        //展开状态
        vcActOtherContent.showView();
    }

    /**
     * 点击聊天按钮
     */
    private void chatToOther() {
        if (null == profile) {
            return;
        }
        //聊天按钮
        ChatUserInfo userInfo = UserInfoDbUtil.getInstance().getUser(profile.getUser_id());

        if (userInfo.getIm_enabled()) {
            if (!TextUtils.isEmpty(userInfo.getIm_id())) {
                //打开聊天窗口
                ActivityRouter.openChatActvity(OtherActivity.this, userInfo.getIm_id(), userInfo.getUser_id());
            } else {
                //获取环信信息
                EventBus.getDefault().post(new GetOtherHxIdEvent(profile.getUser_id()));
            }

        } else {
            EventBus.getDefault().post(GlobalDialogEvent.getGlobalDialogEvent("无法发送", "账号被冻结，有疑问请联系磨房网"));
        }
    }

    /**
     * 初始化网络请求参数
     */
    private void initStateParams() {


        //初始化直播参数
        mStateLive = new OtherPageLive(mContext, otherUserId);

        //初始化活动请求参数
        mStateEvent = new PageEvent(mContext,otherUserId);

    }


    private void initData() {

        //默认选中为直播页
        mPageManager.checkoutSate(mStateLive);

        updateView(UiState.LOADING);

        //获取最新的直播列表
        getListDate(mStateLive);

        //获取网络用户资料
        mPresenterOther.getProfile(otherUserId);


        ptrActOtherRefresh.setRefreshing(true);

    }

    /**
     * 刷新数据
     */
    private void refreshData() {

        setRefreshView();
        mPresenterOther.getProfile(otherUserId);
        mPageManager.refreshData();
    }




    /**
     * 更新数据和视图
     *
     * @param items
     * @param isRefreash
     * @param page
     */
    private void updateItemsAndVi(List items, boolean isRefreash, IPage page) {

        page.updateItem(items, isRefreash);

        if (isRefreash) {
            ptrActOtherRefresh.setRefreshing(false);
        } else {
            plvContent.onRefreshComplete();
        }


        if (page == mPageManager.getmStateImp()) {
            //如果状态不一致，不需要更新视图

            if (isRefreash) {
                mPageManager.updateSate();

                vcActOtherContent.post(new Runnable() {
                    @Override
                    public void run() {
                        vcActOtherContent.showView();
                    }
                });
            } else {
                mPageManager.notifyDataChange();
            }
        }
    }


    /**
     * 设置加载刷新视图
     */
    private void setRefreshView() {

        plvContent.onRefreshComplete();


        ptrActOtherRefresh.setRefreshing(false);
    }

    /**
     * 设置加载更多视图
     */
    private void setLoadMoreView() {
        if (ptrActOtherRefresh.isRefreshing()) {
            //如果正在刷新视图，停止正在刷新
            ptrActOtherRefresh.setRefreshing(false);
        }
    }

    // -------------------------------响应回调---------------------------------//



    /**
     * 获取IM用户信息 回调
     *
     * @param hxidEvent
     */
    public void onEventMainThread(HxidEvent hxidEvent) {

        if (TextUtils.isEmpty(otherUserId) || null == mTitleView) {
            return;
        }

        if (hxidEvent.getAction().equals(IHxIdAction.REQUEST_ID)) {


            if (hxidEvent.getUserId().equals(otherUserId)) {
                if (hxidEvent.isSuccess() && hxidEvent.getInfo() != null && !TextUtils.isEmpty(hxidEvent.getInfo().getIm_id())) {

                    //获取到环信ID,更新UI
                    UIUtils.showView(mTitleView.getRight_image(), true);

                }
            }
        }


    }
}
