package net.doyouhike.app.bbs.ui.user.other;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.biz.db.green.entities.Follow;
import net.doyouhike.app.bbs.biz.entity.CurrentUserDetails;
import net.doyouhike.app.bbs.biz.openapi.presenter.UsersHelper;
import net.doyouhike.app.bbs.ui.widget.common.TitleView;
import net.doyouhike.app.bbs.ui.widget.me.ViUserContent;
import net.doyouhike.app.bbs.util.UIUtils;

import butterknife.InjectView;

/**
 * 功能：
 *
 * @author：曾江 日期：16-3-23.
 */
public class ViOtherContent extends ViUserContent {

    /**
     * 标题
     */
    @InjectView(R.id.title_other)
    protected TitleView mTitleView;
    /**
     * 还没关注该用户
     */
    private boolean notAttend = true;

    public ViOtherContent(Context context) {
        super(context);
    }

    public ViOtherContent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void initAddView() {

        mViFavorite.setVisibility(GONE);
    }

    @Override
    public void updateProfile(CurrentUserDetails profile) {
        super.updateProfile(profile);

        if (null != profile) {
            Follow follow = UsersHelper.getSingleTon().getFollowByUserId(profile.getUser_id());

            // 关注图标（右上角）
            if (follow == null) {

                tvUserInfo.setText("关注");

//                tvUserInfo.setImageResource(R.drawable.selector_btn_follow);
            } else if (!follow.getFollow_each()) {
//                btnUserOtherFollow.setImageResource(R.drawable.selector_btn_followed);
                tvUserInfo.setText("取消关注");
            } else if (follow.getFollow_each()) {
                tvUserInfo.setText("相互关注");
//                btnUserOtherFollow
//                        .setImageResource(R.drawable.selector_btn_both_follow);
            }
            tvUserInfo.setOnClickListener(this);
        }
    }

    /**
     * @return 需要隐藏的视图
     */
    @Override
    protected View getHideView() {
        return View.inflate(getContext(), R.layout.layout_user_other_head, null);
    }

    @Override
    protected int getHideViewHeight() {
        return UIUtils.getIntFromDimens(getContext(), R.dimen.dimen_154dp);
    }

    @Override
    protected void initialize() {
        super.initialize();
    }

    @Override
    protected boolean isTranslucentStatus() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    @Override
    protected int getPaddingHeight() {
        return 0;
    }


    @Override
    public boolean isRefreshing() {
        return false;
    }

    public TitleView getTitleView() {
        return mTitleView;
    }
}
