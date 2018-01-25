package net.doyouhike.app.library.ui.uistate;

import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.doyouhike.app.library.ui.R;
import net.doyouhike.app.library.ui.utils.BaseBitmapUtil;

/**
 * 主要实现功能： 简单的ui控制器 视图不同加载状态在此实现
 * 作者：zaitu
 * 日期：15-12-23.
 */
public class SimpleUiHandler extends BaseUiStateHandle implements BaseUiStateHandle.OnViewListener {

    private final String TAG = SimpleUiHandler.class.getSimpleName();

    protected IVaryViewHelper helper;

    public SimpleUiHandler() {

        setOnViewListener(this);
    }


    @Override
    public void onEmptyView(View.OnClickListener onClickListener, String... msgs) {
        if (null == tipView) {
            tipView = initTipView();
        }
        showView(tipView, onClickListener, false, msgs);
    }

    @Override
    public void onErrorView(View.OnClickListener onClickListener, String... msgs) {
        if (null == errorView) {
            errorView = initTipView();
        }
        showView(errorView, onClickListener, true, msgs);
    }

    @Override
    public void onTipView(View.OnClickListener onClickListener, String... msgs) {
        if (null == tipView) {
            tipView = initTipView();
        }
        showView(tipView, onClickListener, false, msgs);
    }

    @Override
    public void onLoadingView() {
        try{
            if (null == loadingView) {
                loadingView = helper.inflate(R.layout.layout_loading);
            }
            View loadingV = loadingView.findViewById(R.id.v_loading_big);
            loadingV.setBackgroundResource(R.drawable.anim_loading);
            // 加载动画
            AnimationDrawable anim = (AnimationDrawable) loadingV.getBackground();
            anim.start();
            helper.showLayout(loadingView);
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    @Override
    public void onNetErrView(View.OnClickListener onClickListener) {

        if (null == netErrView) {
            netErrView = initTipView();
        }

        showView(netErrView, null, true, "网络好像有点问题", "无法连接到网络,请检查网络设置");
    }

    @Override
    public void onNormalView() {

        if (null!=dialog&&dialog.isShowing()){
            dialog.dismiss();
        }
        helper.restoreView();
    }

    /**
     * 展示自定义VIEW
     * @param imgId           展示的图片ID
     * @param onClickListener 按钮点击时间
     * @param msgs            消息
     */
    @Override
    public void onCustomView(int imgId, View.OnClickListener onClickListener, String... msgs) {
        if (null == tipView) {
            tipView = initTipView();
        }
        setTipViewContent(tipView, onClickListener,imgId, msgs);
        helper.showLayout(tipView);
    }

    @Override
    public void onShowDialog() {
//        if (null==dialog){
//
//            dialog = new LoadingDialog(getParentView().getContext());
//        }
//
//        dialog.show();
    }

    private View initTipView() {
        return helper.inflate(R.layout.layout_tip);
    }

    private void setTipViewContent(View tipView, View.OnClickListener onClickListener,int imgId, String... msgs) {
        //设置提示图标，是错误提示还是普通提示
        ImageView imageView = (ImageView) tipView.findViewById(R.id.iv_tip_icon);

        if (imgId > 0) {
            imageView.setBackgroundDrawable(BaseBitmapUtil.getDrawable(helper.getContext(),imgId));
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }


        TextView tvFirstTip = (TextView) tipView.findViewById(R.id.tv_tip_first_word);
        TextView tvSecondTip = (TextView) tipView.findViewById(R.id.tv_tip_second_word);
        TextView btnRetry = (TextView) tipView.findViewById(R.id.btn_tip_retry);


        tvFirstTip.setVisibility(View.GONE);
        tvSecondTip.setVisibility(View.GONE);
        btnRetry.setVisibility(View.GONE);

        //如果点击事件回调为空，则不显示重试按钮
        if (null != onClickListener) {
            btnRetry.setVisibility(View.VISIBLE);
            btnRetry.setOnClickListener(onClickListener);
        }

        if (null != msgs) {
            //如果传入提示信息不为空

            if (msgs.length >= 1) {
                tvFirstTip.setVisibility(View.VISIBLE);
                tvFirstTip.setText(msgs[0]);

            }
            if (msgs.length >= 2) {
                tvSecondTip.setVisibility(View.VISIBLE);
                tvSecondTip.setText(msgs[1]);

                if (TextUtils.isEmpty(msgs[0])){
                    //隐藏第一块提示语
                    tvFirstTip.setVisibility(View.GONE);
                    //调整间隙
                    LinearLayout.LayoutParams layoutParams= (LinearLayout.LayoutParams )tvSecondTip.getLayoutParams();
                    layoutParams.setMargins(0,tvSecondTip.getContext().getResources().getDimensionPixelSize(R.dimen.tip_second_word_margin_top),0,0);
                    tvSecondTip.setLayoutParams(layoutParams);
                    //调整字体
                    tvSecondTip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                }


            }
            if (msgs.length >= 3) {
                btnRetry.setVisibility(View.VISIBLE);
                btnRetry.setText(msgs[2]);
            }

        }
        //如果 只显示一行，则居中显示
        /*if(tvSecondTip.getVisibility() == View.GONE && btnRetry.getVisibility() == View.GONE){
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tvFirstTip.getLayoutParams();
            lp.setMargins((tipView.getWidth() - tvFirstTip.getWidth())/2, (tipView.getHeight() - tvFirstTip.getHeight())/2, 0, 0);
            tvFirstTip.setLayoutParams(lp);
            tvFirstTip.requestLayout();
        }*/
    }

    private void showView(View tipView, View.OnClickListener onClickListener, boolean isErrTip, String... msgs) {
        setTipViewContent(tipView, onClickListener,
                        isErrTip ?
                                R.drawable.ic_search_404_3x :
                                R.drawable.ic_no_content_3x, msgs);
        helper.showLayout(tipView);
    }


    protected void setGoneView() {
        if (null != parentView)
            parentView.setVisibility(View.GONE);
        if (null != contentView)
            contentView.setVisibility(View.GONE);
        if (null != tipView)
            tipView.setVisibility(View.GONE);
        if (null != errorView)
            errorView.setVisibility(View.GONE);
        if (null != loadingView)
            loadingView.setVisibility(View.GONE);
    }

    @Override
    public ViewGroup getParentView() {
        if (null == parentView) {
            setParentView(helper.getParentView());
        }
        return parentView;
    }

    public IVaryViewHelper getHelper() {
        return helper;
    }

    public void setHelper(IVaryViewHelper helper) {
        this.helper = helper;
    }


    public static class Builder {
        SimpleUiHandler uiHandler = new SimpleUiHandler();


        public Builder setContentView(View contentView) {
            uiHandler.contentView = contentView;
            return this;
        }

        public Builder setRootView(ViewGroup rootView) {
            uiHandler.parentView = rootView;
            return this;
        }

        public Builder setErrorView(View errorView) {
            uiHandler.errorView = errorView;
            return this;
        }

        public Builder setTipView(View tipView) {
            uiHandler.tipView = tipView;
            return this;
        }

        public Builder setLoadingView(View loadingView) {
            uiHandler.loadingView = loadingView;
            return this;
        }

        public Builder setHelper(View view) {
            uiHandler.helper = new VaryViewHelper(view);
            return this;
        }

        public SimpleUiHandler getUiHandler() {
            return uiHandler;
        }
    }


}
