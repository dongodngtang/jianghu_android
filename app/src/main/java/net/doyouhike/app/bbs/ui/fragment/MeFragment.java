package net.doyouhike.app.bbs.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.yolanda.nohttp.RequestMethod;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.fragment.BaseFragment;
import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.event.PostLiveEvent;
import net.doyouhike.app.bbs.biz.event.open.NodesFavoriteEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.service.BaseApiReq;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.IPage;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.user.PageCollect;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.user.PageEvent;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.user.PageLive;
import net.doyouhike.app.bbs.biz.openapi.presenter.page.user.PageManager;
import net.doyouhike.app.bbs.biz.openapi.request.nodes.NodesFavoritesPost;
import net.doyouhike.app.bbs.biz.openapi.request.users.UserEventsGet;
import net.doyouhike.app.bbs.biz.openapi.response.AppVersionResp;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventsListResp;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.NodeTimeline;
import net.doyouhike.app.bbs.biz.presenter.me.MePresenter;
import net.doyouhike.app.bbs.ui.activity.me.ChooseImageActivity;
import net.doyouhike.app.bbs.ui.activity.me.SettingMainActivity;
import net.doyouhike.app.bbs.ui.activity.me.SettingPersonalMassageActivity;
import net.doyouhike.app.bbs.ui.activity.me.TailorImageActivity;
import net.doyouhike.app.bbs.ui.release.yueban.DeleteEvent;
import net.doyouhike.app.bbs.ui.widget.me.IViewMeFrag;
import net.doyouhike.app.bbs.ui.widget.me.ViMeContent;
import net.doyouhike.app.bbs.ui.widget.me.ViUserContent;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.ImageUtil;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SaveFileUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.eventbus.EventCenter;
import net.doyouhike.app.library.ui.uistate.UiState;
import net.doyouhike.app.library.ui.widgets.XSwipeRefreshLayout;

import java.io.File;
import java.util.List;

import butterknife.InjectView;

/**
 * 功能：我的 页面
 * 作者：曾江
 * 日期：16-1-8.
 */
public class MeFragment extends BaseFragment implements IViewMeFrag, ViUserContent.IViContentListener,
        PageManager.StateListener {


    @InjectView(R.id.vc_frag_me_content)
    ViMeContent vcFragMeContent;
    @InjectView(R.id.ptr_frag_me_refresh)
    XSwipeRefreshLayout ptrFragMeRefresh;

    PullToRefreshListView plvContent;

    /**
     * 头像选择对话框
     */
    ActionSheetDialog showGetImageDialog;

    private MePresenter mPresenterMeFragment;

    PageLive mStateLive;
    PageCollect mStateCollect;
    PageEvent mStateEvent;

    String savePicUri;

    PageManager mPageManager;


    private void refreshData() {

        setRefreshView();
        mPresenterMeFragment.getMyProfile();
        mPageManager.refreshData();
    }


    private void updateItemsAndVi(List items, boolean isRefreash, IPage page) {


        page.updateItem(items, isRefreash);

        if (isRefreash) {
            ptrFragMeRefresh.setRefreshing(false);
        } else {
            plvContent.onRefreshComplete();
        }


        if (page == mPageManager.getmStateImp()) {
            //如果状态不一致，不需要更新视图

            if (isRefreash) {
                mPageManager.updateSate();
                vcFragMeContent.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (null != vcFragMeContent)
                            vcFragMeContent.showView();
                    }
                }, 300);
            } else {
                mPageManager.notifyDataChange();
            }
        }
    }


    /**
     * 设置加载刷新视图
     */
    private void setRefreshView() {

//        if (plvContent.isLoadingMore()) {
//            //如果是正在加载更多，则加载完成
//            plvContent.onLoadMoreComplete();
//        }


        ptrFragMeRefresh.setRefreshing(true);
    }

    /**
     * 设置加载更多视图
     */
    private void setLoadMoreView() {
        if (ptrFragMeRefresh.isRefreshing()) {
            //如果正在刷新视图，停止正在刷新
            ptrFragMeRefresh.setRefreshing(false);
        }
        ptrFragMeRefresh.setRefreshing(false);
    }


    /**
     * 跳转编辑头像页面
     *
     * @param path
     */
    private void gotoTailorImageActivity(String path) {

        Intent intent = new Intent();
        intent.setClass(mContext,
                TailorImageActivity.class);
        intent.putExtra(TailorImageActivity.IMAGE_PATH,
                path);
        startActivity(intent);
    }


    @Override
    protected void onFirstUserVisible() {
        updateView(UiState.LOADING);
        //默认选中为直播页
        mPageManager.checkoutSate(mStateLive);

        //获取最新的直播列表
        getListDate(mStateLive);

        //获取本地用户资料
        CurrentUserDetails userDetails = SharedPreferencesManager.getUserDetailsInfo(mContext);
        updateProfile(userDetails);

        //展开状态
        vcFragMeContent.smoothShowView();


    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    protected void initViewsAndEvents() {

        vcFragMeContent.setListener(this);
        plvContent = vcFragMeContent.getListView();
        String[] itemStrList = {"拍照", "从手机相册选择"};
        showGetImageDialog = new ActionSheetDialog(mContext, itemStrList, null);
        showGetImageDialog.title("更换头像");
        showGetImageDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                showGetImageDialog.dismiss();
                switch (position) {
                    case 0:

                        // 相机项
                        savePicUri = SaveFileUtil.getSaveImagePath(
                                mContext, false);

                        File out = new File(savePicUri);
                        Uri uri = Uri.fromFile(out);

                        System.out.println("uri.getPath() = " + uri.getPath());

                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent, 1);
                        break;

                    case 1:

                        intent = new Intent();
                        intent.setClass(getActivity(), ChooseImageActivity.class);
                        intent.putExtra(ChooseImageActivity.CHOOSE_TYPE,
                                ChooseImageActivity.CHOOSE_TYPE_HEAD);
                        startActivity(intent);
                        break;
                }
            }
        });

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
                    refreshView.post(new Runnable() {
                        @Override
                        public void run() {
                            refreshView.onRefreshComplete();
                        }
                    });

//                    StringUtil.showSnack(getActivity(),"已经是最后一页");
                }

            }
        });

        //下拉刷新
        ptrFragMeRefresh.setColorSchemeColors(getResources().getColor(R.color.color_theme));
        ptrFragMeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });

        mStateLive = new PageLive(mContext, UserInfoUtil.getInstance().getUserId());
        mStateCollect = new PageCollect(mContext, UserInfoUtil.getInstance().getUserId());
        mStateEvent = new PageEvent(mContext, UserInfoUtil.getInstance().getUserId());

        mPresenterMeFragment = new MePresenter(this);
        //初始化状态管理器
        mPageManager = new PageManager(plvContent);
        mPageManager.setStateListener(this);

        //是否显示更新小红点
        AppVersionResp appVersion = SharedPreferencesManager.getAppUpdateInfo();
        int feedbackNum = SharedPreferencesManager.getFeedBackNum();
        boolean isIndicate = (appVersion != null && appVersion.getUpgrade() != 0) || feedbackNum != 0;
        vcFragMeContent.showUpdateIndicate(isIndicate);


    }

    @Override
    protected void onUserVisible() {
        vcFragMeContent.showView();
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取个人信息
        mPresenterMeFragment.getMyProfile();
        mStateLive.getRequestParam().setUser_id(UserInfoUtil.getInstance().getUserId());
        mStateCollect.getRequestParam().setUser_id(UserInfoUtil.getInstance().getUserId());
        mStateEvent.getRequestParam().setUser_id(UserInfoUtil.getInstance().getUserId());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }


    @Override
    public boolean isNeedPaddingTop() {
        return true;
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_me_new;
    }

    @Override
    protected View getLoadingTargetView() {
        return plvContent;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == Activity.RESULT_OK) {

            if (TextUtils.isEmpty(savePicUri)) {
                return;
            }

            // TODO 低版本可能会空指针
            Bitmap bitmap = ImageUtil.getSmallBitmap(savePicUri, 1000, 1000);
            if (bitmap == null) {
                return;
            }
            // 旋转
            Matrix matrix = new Matrix();
            matrix.postRotate(SaveFileUtil.readPictureDegree(savePicUri));
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);


            SaveFileUtil.saveBitmapToJPG(mContext, bitmap, savePicUri);

            if (bitmap != null) {
                bitmap.recycle();
            }
            // 编辑头像
            gotoTailorImageActivity("file://" + savePicUri);
        }


    }

    @Override
    public void onDestroyView() {
        vcFragMeContent.onDestroyView();
        mPresenterMeFragment.onDestroy();
        super.onDestroyView();
    }


    /**
     * overrider parent method
     * #######结束######
     */


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
     * @param items      收藏列表
     * @param isRefreash 是否刷新，true 为刷新，false为加载更多
     */
    @Override
    public void updateCollects(List<NodeTimeline.ItemsBean> items, boolean isRefreash) {

        updateItemsAndVi(items, isRefreash, mStateCollect);
    }

    @Override
    public void updateProfile(CurrentUserDetails profile) {
        vcFragMeContent.updateProfile(profile);
    }

    @Override
    public void getItemsErr(int symbol, int errCode, String msg, boolean isRefreash) {


        if (isRefreash) {
            ptrFragMeRefresh.setRefreshing(false);
        } else {
            plvContent.onRefreshComplete();
        }


        String tip = getString(R.string.common_error_msg);

        if (errCode == BaseApiReq.ERROR_CODE_SERVICE) {
            msg = getString(R.string.try_to_click_refresh);
        }

        switch (symbol) {
            case ERR_COLLECT:
                mStateCollect.getDataErr(tip, msg);
                break;
            case ERR_EVENT:
                mStateEvent.getDataErr(tip, msg);
                break;
            case ERR_LIVE:
                mStateLive.getDataErr(tip, msg);
                break;
        }

        mPageManager.updateSate();
    }

    @Override
    public void onRequestErr(int symbol, String msg) {

    }


    @Override
    public void onClick(View view) {

        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.tv_user_other_button:
                //用户信息按钮
                if (UserInfoUtil.getInstance().isLogin()) {
                    intent.setClass(getContext(),
                            SettingPersonalMassageActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.iv_setting:
                //设置按钮
                intent.setClass(getActivity(), SettingMainActivity.class);
                startActivity(intent);

                break;
            case R.id.iv_portrait:
                //用户头像按钮
                showGetImageDialog.show();
                break;
            case R.id.llyt_follow:
                //关注按钮
                ActivityRouter.openFollowFansActivity(mContext, UserInfoUtil.getInstance().getUserId(), "follow");
                break;
            case R.id.llyt_follower:
                //粉丝按钮
                vcFragMeContent.setIvMarkNewMsgGone();
                ActivityRouter.openFollowFansActivity(mContext, UserInfoUtil.getInstance().getUserId(), "fans");
                break;
            case R.id.llyt_me_navigation_post:
                // 直播
                checkoutPage(mStateLive);
                break;
            case R.id.llyt_me_navigation_action:
                // 活动
                checkoutPage(mStateEvent);
                break;
            case R.id.llyt_me_navigation_favorite:
                // 收藏
                checkoutPage(mStateCollect);
                break;

            default:
                break;
        }
    }

    private void checkoutPage(final IPage state) {


        vcFragMeContent.post(new Runnable() {
            @Override
            public void run() {

                if (null != vcFragMeContent) {

                    mPageManager.checkoutSate(state);
                    mPageManager.updateSate();
                    vcFragMeContent.smoothShowView();
                }
            }
        });
    }

    @Override
    public void isListViewTop(boolean isTop) {
        ptrFragMeRefresh.setEnabled(isTop);
    }


    @Override
    public void getListDate(IPage state) {

        if (state instanceof PageLive) {
            //获取直播数据
            mPresenterMeFragment.getTimeLine(((PageLive) state).getRequestParam());
        } else if (state instanceof PageCollect) {
            //获取收藏列表
            mPresenterMeFragment.getMyCollectList(((PageCollect) state).getRequestParam());
        } else if (state instanceof PageEvent) {
            //获取我的活动列表
            mPresenterMeFragment.getUserEvent(((PageEvent) state).getRequestParam());
        }
    }

    @Override
    public void setViewState(IPage page, UiState state) {
        vcFragMeContent.setContentView(page.getItems().isEmpty());


        switch (page.getState()) {
            case ERROR:
                //错误状态添加,刷新按钮
                updateView(state, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        updateView(UiState.LOADING);
                        refreshData();
                        ptrFragMeRefresh.setRefreshing(false);
                    }
                });

                break;
            default:
                updateView(state);
        }

    }


    /**
     * 活动页条件筛选
     */
    /**
     * @param position 条件选择索引
     */
    @Override
    public void onItemSelected(int position) {

        //重新从首页开始搜索
        mStateEvent.getRequestParam().setPage_index(1);
        mStateEvent.getRequestParam().setParticipate_type(UserEventsGet.getParticipateType(position));
        getListDate(mStateEvent);
        setRefreshView();
        mStateEvent.getAdapter().setActionType(position + 1);
    }


    /**
     * 取消收藏
     *
     * @param response
     */
    public void onEventMainThread(NodesFavoriteEvent response) {
        if (response.getCode() == 0 && this != null) {
            try {
                LogUtil.d("MeFragment", this.hashCode() + " NodesFavoriteEvent");
                NodesFavoritesPost post = (NodesFavoritesPost) response.getExtraTag();
                if (post.getRequestMethod() == RequestMethod.DELETE) {
                    String nodeId = post.getNode_id();
                    remove(nodeId);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void remove(String nodeId) {
        NodeTimeline.ItemsBean deleteNode = new NodeTimeline.ItemsBean();
        deleteNode.setNode(new NodeTimeline.ItemsBean.NodeBean(nodeId));
        mStateCollect.getAdapter().getDatas().remove(deleteNode);
        mStateCollect.getAdapter().notifyDataSetChanged();
        mPresenterMeFragment.getMyProfile();
    }

    /**
     * 新粉丝 推送
     */
    public void onEventMainThread(EventCenter eventCenter) {
        if (eventCenter.getEventCode() == Content.NEW_FANS_TAG) {
            LogUtil.d(TAG_LOG, Content.NEW_FANS_TAG + "");
            vcFragMeContent.setIvMarkNewMsgVisible();

            //刷新我的资料
            if (null != mPresenterMeFragment) {
                mPresenterMeFragment.getMyProfile();
            }
        }
    }


    /**
     * 约伴活动刷新
     *
     * @param deleteEvent
     */
    public void onEventMainThread(DeleteEvent deleteEvent) {
//        CurrentUserDetails userDetails = SharedPreferencesManager.getUserDetailsInfo(mContext);
//
//        //刷新用户消息
//        if (null != userDetails) {
//            userDetails.setActivityCount(userDetails.getActivityCount() - 1);
//            SharedPreferencesManager.setUserDetailsInfo(mContext, userDetails);
//        }
//        //刷新活动列表
//        getListDate(mStateEvent);
//
////        刷新直播
//        mStateLive.getRequestParam().setPage_index(1);
//        getListDate(mStateLive);
    }

    // 发直播的响应回调
    public void onEventMainThread(PostLiveEvent event) {
        // 重新获取用户详细信息
//        if (event.getEventCode() == Content.RELEASE_LIVE_SUCCUSS) {
//
//            CurrentUserDetails userDetails = SharedPreferencesManager.getUserDetailsInfo(mContext);
//
//            switch (event.getData().getMinilog_type()) {
//                case NewLiveAdapter.LIVE_TYPE_ACTION:
//                    //刷新用户消息
//                    if (null != userDetails) {
//                        userDetails.setActivityCount(userDetails.getActivityCount() + 1);
//                        SharedPreferencesManager.setUserDetailsInfo(mContext, userDetails);
//                    }
//                    //刷新活动列表
//                    getListDate(mStateEvent);
//
//                    break;
//                case NewLiveAdapter.LIVE_TYPE_TEXT_IMAGE:
//                case NewLiveAdapter.LIVE_TYPE_TEXT:
//                    //刷新用户消息
//                    if (null != userDetails) {
//                        userDetails.setNodeCount(userDetails.getNodeCount() + 1);
//                        SharedPreferencesManager.setUserDetailsInfo(mContext, userDetails);
//                    }
//
//                    mStateLive.getItems().add(0, event.getData());
//                    mStateLive.getAdapter().notifyDataSetChanged();
//
//                    break;
//
//            }
//
//            updateProfile(userDetails);


//        }
    }


}
