package net.doyouhike.app.bbs.ui.widget.me;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.chat.helper.Constant;
import net.doyouhike.app.bbs.ui.adapter.me.MeActionOptionsAdapter;
import net.doyouhike.app.bbs.ui.widget.BottomDialogList;
import net.doyouhike.app.bbs.ui.widget.common.BaseScrollListView;
import net.doyouhike.app.bbs.util.UIUtils;
import net.doyouhike.app.bbs.util.glide.GlideHelper;

import butterknife.InjectView;

/**
 * 功能：
 *
 * @author：曾江 日期：16-3-18.
 */
public abstract class ViUserContent extends BaseScrollListView implements View.OnClickListener,
        BottomDialogList.BottomDialogListener {


    private static final String GENDER_UNKNOW = "unknow";
    private static final String GENDER_MALE = "male";
    private static final String GENDER_FEMALE = "female";

    private static final int CHOOSE_EVENT_ALL = 0;
    private static final int CHOOSE_EVENT_CREATE = 1;
    private static final int CHOOSE_EVENT_JOIN = 2;

    private int chooseed_event = CHOOSE_EVENT_ALL;

    IViContentListener listener;

    /**
     * 用户右边按钮,用户信息或关注状态
     */
    @InjectView(R.id.tv_user_other_button)
    protected TextView tvUserInfo;
    /**
     * 用户名称
     */
    @InjectView(R.id.tv_nick_name)
    TextView tvNickName;
    /**
     * 用户性别
     */
    @InjectView(R.id.iv_user_sex)
    ImageView ivSex;
    /**
     * 头像
     */
    @InjectView(R.id.iv_portrait)
    ImageView ivPortrait;
    /**
     * 关注数量
     */
    @InjectView(R.id.tv_follow_count)
    TextView tvFollowCount;
    /**
     * 关注栏
     */
    @InjectView(R.id.llyt_follow)
    LinearLayout llytFollow;
    /**
     * 粉丝数量
     */
    @InjectView(R.id.tv_follower_count)
    TextView tvFollowerCount;
    /**
     * 新关注粉丝提示
     */
    @InjectView(R.id.iv_mark_new_msg)
    ImageView ivMarkNewMsg;
    /**
     * 粉丝栏
     */
    @InjectView(R.id.llyt_follower)
    LinearLayout llytFollower;
    /**
     * 个人签名
     */
    @InjectView(R.id.tv_autograph)
    TextView tvAutograph;
    /**
     * 直播数量
     */
    @InjectView(R.id.tv_me_navigation_post_count)
    TextView tvMeNavigationPostCount;
    /**
     * 活动数量
     */
    @InjectView(R.id.tv_me_navigation_action_count)
    TextView tvMeNavigationActionCount;
    /**
     * 收藏数量
     */
    @InjectView(R.id.tv_me_navigation_favorite_count)
    TextView tvMeNavigationFavoriteCount;
    /**
     * 收藏
     */
    protected LinearLayout mViFavorite;
    /**
     * 活动
     */
    LinearLayout mViEvent;
    /**
     * 直播
     */
    LinearLayout mViLive;
    /**
     * 活动筛选对话框
     */
    BottomDialogList eventCondition;
    /**
     * 直播选中指示
     */
    @InjectView(R.id.vi_me_navigation_post_index)
    View viMeNavigationPostIndex;
    /**
     * 活动选中指示
     */
    @InjectView(R.id.vi_me_navigation_action_index)
    View viMeNavigationActionIndex;
    /**
     * 收藏选中指示
     */
    @InjectView(R.id.vi_me_navigation_favorite_index)
    View viMeNavigationFavoriteIndex;
    /**
     * 活动内容适配器
     */
    private MeActionOptionsAdapter optionsAdapter;
    /**
     * 活动条件框的箭头
     */
    private ImageView ivEventArrowIndex;

    /**
     * 个人信息
     */
    private CurrentUserDetails userDetails;


    public interface IViContentListener {

        /**
         * @param view 点击事件 点击的View
         */
        void onClick(View view);

        void isListViewTop(boolean isTop);

        /**
         * @param position 列表的索引号
         */
        void onItemSelected(int position);
    }


    public ViUserContent(Context context) {
        super(context);
    }

    public ViUserContent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    /**
     * @param listener 与此视图交互的接口
     */
    public void setListener(IViContentListener listener) {
        this.listener = listener;
    }


    /**
     * @param isTop listview是否在第一条
     */
    @Override
    protected void onListScroll(boolean isTop) {
        if (null != listener) {
            listener.isListViewTop(isTop);
        }
    }


    @Override
    protected int getHideViewHeight() {
        return LinearLayout.LayoutParams.WRAP_CONTENT;
    }

    /**
     * @return 需要悬停的视图
     */
    @Override
    protected View getStickyView() {

        View view = View.inflate(getContext(), R.layout.layout_user_navigation_bar, null);

        mViLive = (LinearLayout) view.findViewById(R.id.llyt_me_navigation_post);
        mViEvent = (LinearLayout) view.findViewById(R.id.llyt_me_navigation_action);
        mViFavorite = (LinearLayout) view.findViewById(R.id.llyt_me_navigation_favorite);
        ivEventArrowIndex = (ImageView) view.findViewById(R.id.iv_me_navigation_action_down);


        mViLive.setOnClickListener(this);
        mViEvent.setOnClickListener(this);
        mViFavorite.setOnClickListener(this);

        return view;
    }

    @Override
    protected int getStickyViewHeight() {
        return UIUtils.getIntFromDimens(getContext(), R.dimen.dimen_50dp);
    }

    @Override
    protected boolean isTranslucentStatus() {
        return false;
    }

    public boolean isRefreshing() {
        return false;
    }


    public void setIvMarkNewMsgVisible() {
        ivMarkNewMsg.setVisibility(VISIBLE);
    }

    public void setIvMarkNewMsgGone() {
        ivMarkNewMsg.setVisibility(GONE);
    }

    /**
     * 更新用户信息
     *
     * @param profile 用户信息
     */
    public void updateProfile(CurrentUserDetails profile) {

        if (null == profile) {
            setButtonClickable(false);
            return;
        }

        userDetails = profile;

        Constant.PHOTO_DOMAIN_PATH = profile.getPhoto_domain_path();
        // 头像
        String faceUrl = Constant.PHOTO_DOMAIN_PATH + profile.getAvatar();
        GlideHelper.displayHeader(getContext(), ivPortrait, faceUrl);

        // 昵称、
        tvNickName.setText(profile.getNick_name());

        //性别图标
        if (profile.getSex().equals(Constant.MALE)) {
            ivSex.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_me_male_3x));
        } else if (profile.getSex().equals(Constant.FEMALE)) {
            ivSex.setBackgroundDrawable(getResources().getDrawable(R.drawable.ic_me_female_3x));
        } else {
            UIUtils.showView(ivSex, false);
        }


        if (profile.getUser_counter() != null) {
            // 关注、粉丝
            tvFollowCount.setText(getFollowCount(profile.getUser_counter().getFollow_num()));
            tvFollowerCount.setText(getFollowCount(profile.getUser_counter().getFan_num()));

            // 导航栏：直播、活动、收藏

            tvMeNavigationPostCount.setText(profile.getUser_counter().getLive_num() + "");
            setNavigationActionCount(chooseed_event);
            tvMeNavigationFavoriteCount.setText(profile.getUser_counter().getFavorite_num() + "");
        }


        // 个性签名
        tvAutograph.setText(profile.getUser_desc());
        UIUtils.showView(tvAutograph, !TextUtils.isEmpty(profile.getUser_desc()));

        // 刚打开时，按钮的因为userDetails为空而全部不可点
        setButtonClickable(true);
        UIUtils.showView(tvUserInfo, true);

    }

    public TextView getActionCountView() {
        return tvMeNavigationActionCount;
    }


    @Override
    public void onClick(View v) {
        listener.onClick(v);

        switch (v.getId()) {

            case R.id.llyt_me_navigation_post:
                // 直播
                setSelectedVi(true, false, false);
                break;
            case R.id.llyt_me_navigation_action:
                // 活动

                if (viMeNavigationActionIndex.isSelected()) {
                    ivEventArrowIndex.setImageResource(R.drawable.ic_action_list_condition_up);
//                    //已选活动页，滑动到顶部，显示菜单
//                    scrollTopAndShowMenu(v);
                    eventCondition.show();
                    return;
                }
                setSelectedVi(false, true, false);

                break;
            case R.id.llyt_me_navigation_favorite:
                // 收藏
                setSelectedVi(false, false, true);
                break;
            default:
                break;
        }
    }


    /**
     * @param position 列表的索引号
     */
    @Override
    public void onItemSelected(int position) {

        chooseed_event = position;
        setNavigationActionCount(position);


        optionsAdapter.select(position);
        listener.onItemSelected(position);
    }

    private void setNavigationActionCount(int position) {
        if (userDetails != null && userDetails.getUser_counter() != null) {
            switch (position) {
                case CHOOSE_EVENT_ALL:

                    tvMeNavigationActionCount.setText(userDetails.getUser_counter().getEvent_join_num() + "");
                    break;
                case CHOOSE_EVENT_CREATE:
                    tvMeNavigationActionCount.setText(userDetails.getUser_counter().getEvent_num() + "");
                    break;
                case CHOOSE_EVENT_JOIN:
                    int join = userDetails.getUser_counter().getEvent_join_num() -
                            userDetails.getUser_counter().getEvent_num();
                    tvMeNavigationActionCount.setText(join + "");
                    break;
            }
        }
    }

    @Override
    public void onDismiss() {
        setContentViewHeight(getContentViewHeight(isContentViEmpty));
        ivEventArrowIndex.setImageResource(R.drawable.ic_action_list_condition_down);
    }

    /**
     * 初始化
     */
    @Override
    protected void initialize() {
        //设置活动页条件筛选框
        optionsAdapter = new MeActionOptionsAdapter(getContext());
        eventCondition = new BottomDialogList(getContext(), this, optionsAdapter);


        ivPortrait.setOnClickListener(this);
        llytFollow.setOnClickListener(this);
        llytFollower.setOnClickListener(this);

        //设置选中效果
        setSelectedVi(true, false, false);
        //设置按钮不可点
        setButtonClickable(false);

        getListView().setMode(PullToRefreshBase.Mode.PULL_FROM_END);
    }

    @Override
    protected void initAddView() {

    }

    @Override
    public void onDestroyView() {
        eventCondition.dismiss();
        super.onDestroyView();

    }


    public TextView getTvUserInfo() {
        return tvUserInfo;
    }

    /**
     * 统一设置可点性
     */
    private void setButtonClickable(boolean isClickable) {

        ivPortrait.setClickable(isClickable);
        llytFollow.setClickable(isClickable);
        llytFollower.setClickable(isClickable);

    }

    /**
     * 设置条件筛选框的
     *
     * @param post     直播
     * @param action   活动
     * @param favorite 收藏
     */
    protected void setSelectedVi(boolean post, boolean action, boolean favorite) {

        viMeNavigationPostIndex.setSelected(post);
        viMeNavigationActionIndex.setSelected(action);
        viMeNavigationFavoriteIndex.setSelected(favorite);

        ivEventArrowIndex.setImageResource(action ? R.drawable.ic_action_list_condition_down :
                R.drawable.ic_action_list_condition_down);


    }


    /**
     * 滑动到顶部并显示菜单
     *
     * @param v 在此view下面显示菜单
     */
    private void scrollTopAndShowMenu(final View v) {
        if (isContentViEmpty) {
            setContentViewHeight(getContentViewHeight(false));

            postDelayed(new Runnable() {
                @Override
                public void run() {
                    showMenu(v);
                }
            }, 100l);

        } else {
            showMenu(v);
        }

    }

    private void showMenu(final View v) {
        //滑动到顶部
        scrollToTop();
        if (mHideViewVisibleHeight > 0) {
            //如果轮播广告的可视高度大于0,延时0.1s再显示条件选择框
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    showPopView(v);
                }
            }, 100l);
        } else {
            //如果轮播广告的可视高度小等于0,直接显示条件选择框
            showPopView(v);
        }
    }

    private void showPopView(View v) {
        eventCondition.show();
    }


    /**
     * @param count 关注数量
     * @return 格式化输出关注数量
     */
    private String getFollowCount(int count) {

        if (count > 0) {
            // 超过10万	应以K显示千位（例如10万=100k）
            if (count > 10 * 10000) {
                return (count / 1000) + "k";

            }

            return String.valueOf(count);
        }


        return "0";
    }


}
