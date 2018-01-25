package net.doyouhike.app.bbs.ui.home.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.fragment.BaseFragment;
import net.doyouhike.app.bbs.biz.event.login.LoginEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.ui.home.topic.TimelineView;
import net.doyouhike.app.bbs.ui.widget.LoadMoreListView;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.bbs.util.linktext.linkutil.LinkTextUtil;
import net.doyouhike.app.library.ui.uistate.UiState;
import net.doyouhike.app.library.ui.widgets.XSwipeRefreshLayout;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BaseLiveFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BaseLiveFragment extends BaseFragment {
    protected static final String TAG = "BaseLiveFragment";
    protected static final String ARG_TAG = "param1";

    View viTagExtraInfo;
    TextView tvTagExtraInfo;
    @InjectView(R.id.lv_home_list)
    LoadMoreListView lvHomeList;
    @InjectView(R.id.rf_drop_layout)
    XSwipeRefreshLayout rfDropLayout;


    protected BaseTag mTag;
    TimelineView timelineView;


    /**
     * onCreate时的登录状态
     */
    private boolean onCreateLoginState;

    public LoadMoreListView getLvHomeList(){
        return lvHomeList;
    }

    public BaseLiveFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param tag
     * @return A new instance of fragment BaseLiveFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BaseLiveFragment newInstance(BaseTag tag) {
        BaseLiveFragment fragment = new BaseLiveFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TAG, tag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (null != savedInstanceState) {

            if (null == mTag)
                mTag = (BaseTag) savedInstanceState.getSerializable(ARG_TAG);
        }
        if (getArguments() != null && null == mTag) {
            mTag = (BaseTag) getArguments().getSerializable(ARG_TAG);
        }
        super.onCreate(savedInstanceState);

        onCreateLoginState=UserInfoUtil.getInstance().isLogin();
    }

    @Override
    protected void onUserVisible() {


        if (UserInfoUtil.getInstance().isLogin()!=onCreateLoginState){
            onCreateLoginState=UserInfoUtil.getInstance().isLogin();
            //如果登录状态改变,则刷新数据,
            timelineView.refreshData();
        }
    }



    @Override
    public void onResume() {
        super.onResume();
        //更新用户userId,登录、登出,调整主页
        timelineView.getLiveAdapter().setCurrentUserId(UserInfoUtil.getInstance().getUserId());
    }


    @Override
    protected void onFirstUserVisible() {


        timelineView.setUiStateController(uiStateController);
        updateView(UiState.LOADING);
        timelineView.initData();
        setHeadVi(mTag.getDesc());

    }

    @Override
    protected void initViewsAndEvents() {
        viTagExtraInfo = LayoutInflater.from(mContext).inflate(R.layout.layout_linked_expendtext, null);
        tvTagExtraInfo= ButterKnife.findById(viTagExtraInfo,R.id.tv_live_fragment_tag_extra_info);

        timelineView = new TimelineView(mContext, rfDropLayout, lvHomeList
                , mTag);
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_base_live;
    }

    @Override
    protected View getLoadingTargetView() {
        return rfDropLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_TAG, mTag);
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    public void onDestroyView() {
        timelineView.onDestroyView();
        super.onDestroyView();

    }

    public void setTag(BaseTag tag) {

        if (tag != null && !tag.equals(mTag)) {
            this.mTag = tag;
            setHeadVi(mTag.getDesc());
            timelineView.setTag(mTag);
            timelineView.resetData();
            updateView(UiState.LOADING);
            timelineView.initData();
            timelineView.getListView().setSelection(0);
//            timelineView.getListView().scrollTo(0,0);
        }
    }

    public void setCurrentTag(BaseTag tag) {

        if (tag != null && !tag.equals(mTag)) {
            this.mTag = tag;
            if (null != timelineView) {
                timelineView.setTag(tag);
            }
        }
    }

    private void setHeadVi(String desc) {

        if (null == viTagExtraInfo || null == lvHomeList) {
            return;
        }

        if (TextUtils.isEmpty(desc)) {
            if (lvHomeList.getHeaderViewsCount() != 0) {
                lvHomeList.removeHeaderView(viTagExtraInfo);
            }

        } else {
            if (lvHomeList.getHeaderViewsCount() == 0) {
                lvHomeList.addHeaderView(viTagExtraInfo);
            }

            UIUtils.showView(viTagExtraInfo,!TextUtils.isEmpty(mTag.getDesc()));

            if (null==mTag.getDesc()){
                mTag.setDesc("");
            }
            tvTagExtraInfo.setMovementMethod(LinkMovementMethod.getInstance());
            SpannableStringBuilder spannable = new LinkTextUtil().getSpannableString(mTag.getDesc());
            tvTagExtraInfo.setText(spannable);
        }
    }

    /**
     * 登录回调
     *
     * @param loginEvent 登录事件
     */
    public void onEventMainThread(LoginEvent loginEvent) {
        //随便看看标识重置

    }

}
