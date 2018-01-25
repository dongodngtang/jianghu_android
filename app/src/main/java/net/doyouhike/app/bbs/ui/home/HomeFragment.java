package net.doyouhike.app.bbs.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.base.fragment.BaseFragment;
import net.doyouhike.app.bbs.biz.event.CheckoutEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.openapi.response.LoginUser;
import net.doyouhike.app.bbs.ui.activity.MainActivity;
import net.doyouhike.app.bbs.ui.activity.live.SearchUserActivity;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.activity.login.RegisterFirstActivity;
import net.doyouhike.app.bbs.ui.home.fragment.BaseLiveFragment;
import net.doyouhike.app.bbs.ui.home.fragment.LiveFragmentFactory;
import net.doyouhike.app.bbs.ui.home.popupmenu.fixedtag.TagFactory;
import net.doyouhike.app.bbs.ui.home.tag.HomeTagActivity;
import net.doyouhike.app.bbs.ui.home.topic.TimelineRequestType;
import net.doyouhike.app.bbs.ui.widget.common.dialog.BottomPopupDialog;
import net.doyouhike.app.bbs.ui.widget.live.tag.HomeTagDialog;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.SharedPreferencesManager;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;
import net.doyouhike.app.library.ui.widgets.XViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Filework:
 * Author: luochangdong
 * Date:15-12-26
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener, BottomPopupDialog.BottomDialogListener<BaseTag> {

    public static final int REQ_CODE_TAG = 1001;

    /**
     * 添加关注
     */
    @InjectView(R.id.iv_add_follow)
    RelativeLayout ivAddFollow;
    /**
     * 跳转登录
     */
    @InjectView(R.id.tv_to_login)
    TextView tvToLogin;
    /**
     * 跳转注册
     */
    @InjectView(R.id.tv_to_register)
    TextView tvToRegister;
    @InjectView(R.id.vp_home)
    XViewPager vpHome;

    @InjectView(R.id.tv_frag_home_condition)
    TextView tvFragHomeCondition;
    @InjectView(R.id.iv_frag_home_condition_indicate)
    ImageView ivFragHomeConditionIndicate;
    /**
     * 发布
     */
    @InjectView(R.id.iv_release_memu)
    RelativeLayout ivReleaseMemu;
    @InjectView(R.id.rl_home_top_bar)
    RelativeLayout rlTopBar;


    @OnClick(R.id.btn_frag_home_condition)
    public void clickCondition(View view) {

        if (!UserInfoUtil.getInstance().isLogin()){
            //没有登录,不弹出标签框
            return;
        }

        ivFragHomeConditionIndicate.setImageDrawable(getResources().getDrawable(
                R.drawable.ic_home_topic_up));
        mPopWin.showDialog();
    }

    HomeTagDialog mPopWin;

    BaseTag mCurrentTag;

    List<BaseLiveFragment> fragments;
    private BaseLiveFragment mCurrentFragment;

    /**
     * 双击事件、多击事件
     */
    //存储时间的数组
    long[] mHits = new long[2];


    @Override
    public void onCreate(Bundle savedInstanceState) {

        if (null != savedInstanceState) {
            mCurrentTag = (BaseTag) savedInstanceState.getSerializable("mCurrentTag");
        }
//        if(mCurrentTag == null){
//            mCurrentTag = SharedPreferencesManager.getMainTopicPage();
//        }
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    protected void initViewsAndEvents() {

        mPopWin = new HomeTagDialog(this);
        mPopWin.setListener(this);
        initViewPagerAdapter();

        if (null == mCurrentTag) {
            mCurrentTag = TagFactory.createTag(TimelineRequestType.HOT);
        }
        checkoutTopic(mCurrentTag);

        vpHome.setEnableScroll(false);

        tvToRegister.setOnClickListener(this);
        tvToLogin.setOnClickListener(this);
        ivAddFollow.setOnClickListener(this);
        ivReleaseMemu.setOnClickListener(this);
        rlTopBar.setOnClickListener(this);

    }


    @Override
    public void onResume() {
        super.onResume();


        //没有登录,隐藏箭头显示
        UIUtils.showView(ivFragHomeConditionIndicate,UserInfoUtil.getInstance().isLogin());
        if (getIsLogin()) {
            tvToLogin.setVisibility(View.GONE);
            tvToRegister.setVisibility(View.GONE);
            ivAddFollow.setVisibility(View.VISIBLE);
            ivReleaseMemu.setVisibility(View.VISIBLE);
        } else {
            tvToLogin.setVisibility(View.VISIBLE);
            tvToRegister.setVisibility(View.VISIBLE);
            ivAddFollow.setVisibility(View.GONE);
            ivReleaseMemu.setVisibility(View.GONE);
        }
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }

    @Override
    public boolean isNeedPaddingTop() {
        return true;
    }

    @Override
    public void onDestroyView() {
        SharedPreferencesManager.setMainTopicPage(mCurrentTag);
        mPopWin.onDestroy();
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * 此消息由mainActivity onActivityResult发出
     *
     * @param event 发布直播传递的直播信息
     */
    public void onEventMainThread(CheckoutEvent event) {
        LogUtil.d("SendReleaseUtil", "onEventMainThread");

        if (event.getTag().getLiveType() == TimelineRequestType.ATTEND) {
            checkoutAttend(event.getTag());
        }
    }

    private void initViewPagerAdapter() {

        fragments = new ArrayList<>();

        // ATTEND-- 0 HOT-- 1 TOPIC-- 2
        fragments.add(getFragemet(TagFactory.createTag(TimelineRequestType.ATTEND)));// 0
        fragments.add(getFragemet(TagFactory.createTag(TimelineRequestType.HOT)));// 1
        fragments.add(getFragemet(TagFactory.createTag(TimelineRequestType.TOPIC)));// 2

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public void setPrimaryItem(ViewGroup container, int position, Object object) {
                super.setPrimaryItem(container, position, object);
                mCurrentFragment = (BaseLiveFragment) object;
            }
        };

        vpHome.setAdapter(mAdapter);

        vpHome.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (null != mCurrentFragment && position == 2) {
                    mCurrentFragment.setTag(mCurrentTag);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
//        vpHome.setOffscreenPageLimit(2);
    }


    private BaseLiveFragment getFragemet(BaseTag tag) {
        return LiveFragmentFactory.createLiveFragemtn(tag);
    }

    private void selectTag(int position) {

        // ATTEND-- 0 HOT-- 1 TOPIC-- 2

        if (vpHome.getCurrentItem() == position && mCurrentFragment != null) {

            if (position == 2) {
                mCurrentFragment.setTag(mCurrentTag);
                return;
            }
        }

        if (position == 2) {
            fragments.get(2).setCurrentTag(mCurrentTag);
        }

        vpHome.setCurrentItem(position, false);

    }

    private void setSaveSubscribTagResult(ArrayList<BaseTag> tags) {

        if (null != tags) {
            mPopWin.onGetSubcribeTags(tags, false);

            if (null == mCurrentTag || !tags.contains(mCurrentTag)) {
                mCurrentTag = TagFactory.createTag(TimelineRequestType.HOT);
            }
            checkoutTopic(mCurrentTag);
        }
    }


    /**
     * 检查用户是否登录
     *
     * @return
     */
    private boolean getIsLogin() {
        LoginUser user = UserInfoUtil.getInstance().getCurrentUser();
        return user != null && user.getOpenapi_token() != null;
    }

    @Override
    protected int getContentViewLayoutID() {
        return R.layout.fragment_home;
    }


    @Override
    protected void onFirstUserVisible() {
    }

    @Override
    protected void onUserVisible() {

    }

    @Override
    protected void onUserInvisible() {

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && requestCode == REQ_CODE_TAG) {

            ArrayList<BaseTag> tags = (ArrayList<BaseTag>) data.getSerializableExtra(HomeTagActivity.I_SUBSCRIB_ITEMS);
            setSaveSubscribTagResult(tags);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_to_login:
                readyGo(LoginActivity.class);
                break;
            case R.id.tv_to_register:
                readyGo(RegisterFirstActivity.class);
                break;
            case R.id.iv_add_follow:
//                readyGo(TestOpenApiActivity.class);
                readyGo(SearchUserActivity.class);
                break;
            case R.id.iv_release_memu:
                if (getActivity() instanceof MainActivity) {
                    MainActivity mainActivity = (MainActivity) getActivity();
                    mainActivity.popupReleaseMenu.gravity(Gravity.BOTTOM)
                            .anchorView(ivReleaseMemu)
                            .triangleWidth(12)
                            .triangleHeight(6)
                            .showAnim(null)
                            .dismissAnim(null)
                            .show();
                }

                break;
            case R.id.rl_home_top_bar:

                //实现数组的移位操作，点击一次，左移一位，末尾补上当前开机时间（cpu的时间）
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                //双击事件的时间间隔500ms
                if (mHits[0] >= (SystemClock.uptimeMillis() - 500)) {
                    //双击后具体的操作
                    mCurrentFragment.getLvHomeList().smoothScrollToPositionFromTop(0,0);
                }

                break;


        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("mCurrentTag", mCurrentTag);
    }

    public void checkoutAttend(BaseTag tag) {
        if (UserInfoUtil.getInstance().isLogin()) {
            checkoutTopic(tag);
        } else {
            startActivity(new Intent(getActivity(), LoginActivity.class));
            showToast(getString(R.string.please_to_login));
        }
    }


    @Override
    public void onDismiss() {
        ivFragHomeConditionIndicate.setImageDrawable(getResources().getDrawable(
                R.drawable.nav_icon_indicator_expand));
    }

    @Override
    public void setSelectTopic(BaseTag tag) {
        mCurrentTag = tag;
        setTitle(mCurrentTag);

        // ATTEND-- 0 HOT-- 1 TOPIC-- 2
        switch (tag.getLiveType()) {
            case HOT:
                selectTag(1);
                break;
            case ATTEND:
                selectTag(0);
                break;
            default:
                selectTag(2);

        }
    }

    private void checkoutTopic(BaseTag tag) {
        setSelectTopic(tag);
        mPopWin.setSelectItem(tag);

    }

    private void setTitle(BaseTag tag) {

        tvFragHomeCondition.setText(tag.getTag_name());
    }

}


