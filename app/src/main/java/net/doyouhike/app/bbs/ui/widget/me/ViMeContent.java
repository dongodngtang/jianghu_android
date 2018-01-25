package net.doyouhike.app.bbs.ui.widget.me;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.bbs.util.UIUtils;

import butterknife.InjectView;

/**
 * 功能：
 *
 * @author：曾江 日期：16-3-23.
 */
public class ViMeContent extends ViUserContent {

    /**
     * 图片设置
     */
    @InjectView(R.id.iv_setting)
    ImageView ivSetting;
    /**
     * 更新小红点
     */
    @InjectView(R.id.iv_app_update_indicate)
    View viUpdateIndicate;


    public ViMeContent(Context context) {
        super(context);
    }

    public ViMeContent(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    protected void initialize() {
        super.initialize();
        ivSetting.setOnClickListener(this);
        tvUserInfo.setOnClickListener(this);
        tvUserInfo.setText("个人信息");
    }

    @Override
    protected int getPaddingHeight() {
        //悬停栏滑倒顶部距离=titleView高度+沉浸状态栏高度
        return 0;
    }


    /**
     * @return 需要隐藏的视图
     */
    @Override
    protected View getHideView() {
        return View.inflate(getContext(), R.layout.layout_user_me_head, null);
    }

    @Override
    protected int getHideViewHeight() {
        return UIUtils.getIntFromDimens(getContext(), R.dimen.dimen_138dp);
    }

    @Override
    public boolean isRefreshing() {
//        return pbLayMeRefresh.isShown();
        return false;
    }


    public void showUpdateIndicate(boolean show){
        UIUtils.showView(viUpdateIndicate,show);
    }

}
