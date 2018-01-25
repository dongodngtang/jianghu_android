package net.doyouhike.app.bbs.ui.widget.action;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.newnetwork.model.bean.BaseTag;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventBannersResp;
import net.doyouhike.app.bbs.ui.activity.action.ActionAndRoadSearchActivity;
import net.doyouhike.app.bbs.ui.activity.login.LoginActivity;
import net.doyouhike.app.bbs.ui.widget.action.list.ActionMidConditionDialog;
import net.doyouhike.app.bbs.ui.widget.action.list.ActionTypeDialog;
import net.doyouhike.app.bbs.ui.widget.common.BaseScrollListView;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.UserInfoUtil;

import java.util.List;

import butterknife.InjectView;

/**
 * 功能：活动页的视图内容，继承scrollview，实现悬停效果，以及和listview的兼容问题
 * 作者：曾江
 * 日期：16-1-5.
 */
public class ViActionContent extends BaseScrollListView implements View.OnClickListener {


    public interface IViActionContentListener {

        void isListViewTop(boolean isTop);

        void onAdViewScroll(float present);

        /**
         * 请求活动类型数据
         */
        void requestEventTypeData();

        /**
         * 条件选择
         *
         * @param viewId 对应的文本框ID
         * @param id     时间.活动类型 id
         */
        void updateConditionSelected(int viewId, int id);
    }

    /**
     * 标题
     */
    View titleView;

    /**
     * 广告的ViewPagere
     */
    @InjectView(R.id.fragment_action_viewpager)
    InterceptScrollViewPager fragmentActionViewpager;
    @InjectView(R.id.iv_frag_action_ad)
    LinearLayout ivFragActionAd;
    /**
     * 活动类型文本
     */
    @InjectView(R.id.tv_action_research_type)
    TextView tvActionResearchType;
    /**
     * 搜索类型指示箭头
     */
    @InjectView(R.id.iv_action_research_type_arrow)
    ImageView ivActionResearchTypeArrow;
    /**
     * 活动类型按钮
     */
    @InjectView(R.id.ll_action_research_type)
    LinearLayout llActionResearchType;
    /**
     * 即将出发文本
     */
    @InjectView(R.id.tv_action_research_go_soon)
    TextView tvActionResearchGoSoon;
    /**
     * 即将出发指示
     */
    @InjectView(R.id.iv_action_research_go_soon)
    ImageView ivActionResearchGoSoon;
    /**
     * 即将出发
     */
    @InjectView(R.id.ll_action_research_go_soon)
    LinearLayout llActionResearchGoSoon;
    /**
     * 活动条件筛选框根布局
     */
    @InjectView(R.id.ll_action_research_bar_parent)
    RelativeLayout llActionResearchBar;

    /**
     * 好友参加
     */
    @InjectView(R.id.cb_action_research_has_fd)
    CheckBox cbActionResearchHasFd;
    /**
     * 好友参加
     */
    @InjectView(R.id.ll_action_research_has_fd)
    LinearLayout ll_action_research_has_fd;
    /**
     * 定位 搜索栏的背景
     */
    @InjectView(R.id.v_action_title_bg)
    View vActionTitleBg;
    /**
     * 位置
     */
    @InjectView(R.id.tv_action_title_location)
    TextView tvActionTitleLocation;
    /**
     * 搜索按下区域
     */
    @InjectView(R.id.fl_action_title_search)
    FrameLayout flActionTitleSearch;
    /**
     * 定位 搜索栏的区域
     */
    @InjectView(R.id.ll_action_title)
    RelativeLayout llActionTitle;
    /**
     * 定位图片
     */
    @InjectView(R.id.iv_action_title_location)
    ImageView ivActionTitleLocation;
    /**
     * 定位点击区域
     */
    @InjectView(R.id.rl_action_title_location)
    RelativeLayout rlActionTitleLocation;
    /**
     * 搜索图片
     */
    @InjectView(R.id.iv_action_title_search)
    ImageView ivActionTitleSearch;


    /**
     * 轮播广告控制器
     */
    AdDataPlayer adDataPlayer;


    private IViActionContentListener listener;

    /**
     * 活动类型列表
     */
    ActionTypeDialog mActionTypeDialog;

    /**
     * 点击前,条件筛选框里文本字体颜色,为了还原
     */
    private int selectColor;
    /**
     * 即将出发
     */
    ActionMidConditionDialog mDidConditionDialog;

    public ViActionContent(Context context) {
        super(context);
    }

    public ViActionContent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int getPaddingHeight() {
        //悬停栏滑倒顶部距离=titleView高度+沉浸状态栏高度
        return titleView.getHeight() + UIUtils.getTranslucentStatusHeight(getContext());
    }

    @Override
    protected void onListScroll(boolean isTop) {
        if (listener != null)
            listener.isListViewTop(isTop);
    }

    @Override
    protected View getHideView() {
        //广告布局
        return View.inflate(getContext(), R.layout.layout_action_advertisement, null);
    }

    @Override
    protected int getHideViewHeight() {

        int screeWidth=UIUtils.getValidWidth(getContext());

        int height=screeWidth*176/375;

        //滑动隐藏的视图高度--广告布局高度
//        return UIUtils.getIntFromDimens(getContext(), R.dimen.dimen_180dp) + UIUtils.getTranslucentStatusHeight(getContext());
        return height;
    }

    @Override
    protected View getStickyView() {
        //返回悬停布局,即条件筛选布局
        return View.inflate(getContext(), R.layout.fragment_action_research_bar, null);
    }

    @Override
    protected int getStickyViewHeight() {
        return UIUtils.getIntFromDimens(getContext(), R.dimen.dimen_45dp);
    }

    @Override
    protected void initAddView() {

        //添加标题视图

        titleView = View.inflate(getContext(), R.layout.layout_action_title, null);

        LayoutParams layoutParams = new LayoutParams
                (LayoutParams.MATCH_PARENT, UIUtils.getIntFromDimens(getContext(), R.dimen.dimen_44dp));
        layoutParams.setMargins(0, +UIUtils.getTranslucentStatusHeight(getContext()), 0, 0);
        viLayScrollListRoot.addView(titleView, layoutParams);
    }

    @Override
    protected void initialize() {
        adDataPlayer = new AdDataPlayer(ivFragActionAd);

        initEventType();
        initTimeSelecter();

        rlActionTitleLocation.setOnClickListener(this);
        flActionTitleSearch.setOnClickListener(this);
        ll_action_research_has_fd.setOnClickListener(this);
        cbActionResearchHasFd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked && !UserInfoUtil.getInstance().isLogin()) {
                    openLoginActivity();
                    cbActionResearchHasFd.setChecked(false);
                    return;
                }

                listener.updateConditionSelected(R.id.cb_action_research_has_fd, isChecked ? 1 : 0);
            }
        });
        getListView().setMode(PullToRefreshBase.Mode.BOTH);
    }

    @Override
    protected boolean isTranslucentStatus() {
        return true;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_action_title_location:
                //城市选择
                listener.updateConditionSelected(R.id.rl_action_title_location, 0);
                break;
            case R.id.fl_action_title_search:
                Intent intentSearch = new Intent(getContext(), ActionAndRoadSearchActivity.class);
                getContext().startActivity(intentSearch);
                break;
            case R.id.ll_action_research_has_fd:
                boolean preState=cbActionResearchHasFd.isChecked();
                cbActionResearchHasFd.setChecked(!preState);
                break;
            default:
                showSearchBar(v);

        }


    }

    public void setListener(IViActionContentListener listener) {
        this.listener = listener;
    }

    public AdDataPlayer getAdDataPlayer() {
        return adDataPlayer;
    }

    /**
     * 显示广告栏
     */
    public void showAd() {
        showView();
    }


    /**
     * 隐藏广告栏
     */
    public void hideAd() {
        scrollToTop();
    }

    /**
     * @param adsData 广告数据
     */
    public void updateAd(List<EventBannersResp.ItemsBean> adsData) {

        if (null != adsData && !adsData.isEmpty()) {
//            fragmentActionViewpager.setVisibility(VISIBLE);
//            setHideViewHeight(getHideViewHeight());
            adDataPlayer.setAdsData(adsData);
        } else {
//            fragmentActionViewpager.setVisibility(GONE);
//            setHideViewHeight(0);
        }
//        requestLayout();

    }

    /**
     * @param items 更新活动类型数据
     */
    public void updateEventData(List<BaseTag> items) {
        mActionTypeDialog.updateEventData(items);
    }


    public void setLocation(String location) {
        tvActionTitleLocation.setText(location);
    }


    /**
     * 活动类型 条件选中
     * @param title 标题
     * @param id 类型id
     */
    public void setActionTypeSelected(String title, int id) {
        //设置活动类型选中的选项
        tvActionResearchType.setText(title);
        //转发选中事件,进行活动搜索
        listener.updateConditionSelected(R.id.ll_action_research_type, id);
    }

    /**
     * 活动类型对话框消失调用
     * @param isFirst 是否第一项
     */
    public void onActionTypeDialogDismiss(boolean isFirst) {

        ivActionResearchTypeArrow.setImageDrawable(getResources().getDrawable(
                R.drawable.ic_action_list_condition_down));
        //设置选中颜色,第一项灰色,其他项,主题色
        setTextColor(tvActionResearchType,!isFirst);
    }
    /**
     * 活动 即将出发 条件选中
     * @param title 标题
     * @param id 类型id
     */
    public void setMidConditionSelected(String title, int id) {

        //设置已选选项
        tvActionResearchGoSoon.setText(title);
        //"search_type": 1/2 , 1、即将出发 2、离我最近（需传经纬度）
        listener.updateConditionSelected(R.id.ll_action_research_go_soon,id);

    }

    /**
     * 活动即将出发对话框消失调用
     * @param isFirst 是否第一项
     */
    public void onMidConditionDismiss(boolean isFirst) {

        ivActionResearchGoSoon.setImageDrawable(getResources().getDrawable(
                R.drawable.ic_action_list_condition_down));

        //设置选中颜色,第一项灰色,其他项,主题色
        setTextColor(tvActionResearchGoSoon,!isFirst);
    }

    @Override
    protected void onScrollYChanged(int scrollY) {
        if (mScrollY == scrollY) {
            return;
        }
        super.onScrollYChanged(scrollY);

        //标题款悬停
        llActionTitle.setTranslationY(scrollY);


        // 设置标题栏渐变
        float distance = (float) (stickHideView.getTop() - getPaddingHeight());

        float percentage = scrollY / distance;

        if (percentage > 1) {
            percentage = 1.0f;
        }

        if (percentage < 0) {
            percentage = 0;
        }

//        LogUtil.d(TAG, "移动百分比：" + percentage);

        if (null != listener) {
            listener.onAdViewScroll(percentage);
        }

        if (percentage <= 0.75f) {
            //从0-75%位置渐变100%
            vActionTitleBg.setAlpha(percentage / 0.75f);
            rlActionTitleLocation.setSelected(false);
            flActionTitleSearch.setSelected(false);

            ivActionTitleLocation.setImageResource(R.drawable.selector_action_list_title_location_small);
            ivActionTitleSearch.setImageResource(R.drawable.selector_search_icon);

        } else {

            vActionTitleBg.setAlpha(1f);
            rlActionTitleLocation.setSelected(true);
            flActionTitleSearch.setSelected(true);

            ivActionTitleSearch.setImageResource(R.drawable.selector_search_icon);
            ivActionTitleLocation.setImageResource(R.drawable.ic_action_list_title_location_big);
        }
    }


    /**
     * 初始化即将出发选择
     */
    private void initTimeSelecter() {

        llActionResearchGoSoon.setOnClickListener(this);
        mDidConditionDialog=new ActionMidConditionDialog(this);
    }

    /**
     * 初始化活动类型相关
     */
    private void initEventType() {

        llActionResearchType.setOnClickListener(this);

        mActionTypeDialog = new ActionTypeDialog(this);

    }

    /**
     * 隐藏广告栏再弹出条件筛选框
     *
     * @param v 在那个视图下面弹出
     */
    private void showSearchBar(final View v) {

        showSearchPopupwin(v);


//        if (isContentViEmpty) {
//            showSearchPopupwin(v);
//            return;
//        } else {
//            hideAd();
//        }

//        if (mHideViewVisibleHeight > 0) {
//            //如果轮播广告的可视高度大于0,延时0.1s再显示条件选择框
//            postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    showSearchPopupwin(v);
//                }
//            }, 100l);
//        } else {
//            //如果轮播广告的可视高度小等于0,直接显示条件选择框
//            showSearchPopupwin(v);
//        }

    }

    /**
     * 弹出条件popupWin
     *
     * @param v 在那个视图下面弹出
     */
    private void showSearchPopupwin(View v) {
        switch (v.getId()) {
            case R.id.ll_action_research_type:
                //活动类型
                ivActionResearchTypeArrow.setImageDrawable(getResources().getDrawable(
                        R.drawable.ic_action_list_condition_up));
                selectColor = tvActionResearchType.getCurrentTextColor();
                setTextColor(tvActionResearchType, true);
                mActionTypeDialog.show();
                if (mActionTypeDialog.isEmpty()) {
                    listener.requestEventTypeData();
                }
                break;
            case R.id.ll_action_research_go_soon:
                //即将出发
                ivActionResearchGoSoon.setImageDrawable(getResources().getDrawable(
                        R.drawable.ic_action_list_condition_up));
                selectColor = tvActionResearchGoSoon.getCurrentTextColor();
                setTextColor(tvActionResearchGoSoon, true);
                mDidConditionDialog.show();
                break;
        }
    }

    private void setTextColor(TextView textView, boolean isBlue) {


        textView.setTextColor(isBlue ? getResources()
                .getColor(R.color.color_theme) : getResources()
                .getColor(R.color.text_info));


    }

    /**
     * 特定的时机，未登录时调用
     */
    private void openLoginActivity() {
        Intent intent = new Intent();
        intent.setClass(getContext(), LoginActivity.class);
        getContext().startActivity(intent);

        StringUtil.showSnack(getContext(),
                getResources().getString(R.string.please_to_login));
    }

}
