package net.doyouhike.app.bbs.ui.widget.action;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.doyouhike.app.bbs.R;
import net.doyouhike.app.library.ui.uistate.SimpleUiHandler;
import net.doyouhike.app.library.ui.uistate.VaryViewHelper;
import net.doyouhike.app.library.ui.utils.BaseBitmapUtil;

/**
 * 功能：
 * 作者：曾江
 * 日期：16-1-12.
 */
public class ActionUiHandle extends SimpleUiHandler {

    public ActionUiHandle(View view) {
        setHelper(new VaryViewHelper(view));
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
    public void onCustomView(int imgId, View.OnClickListener onClickListener, String... msgs) {
        if (null == tipView) {
            tipView = initTipView();
        }
        setTipViewContent(tipView, onClickListener, imgId, msgs);
        getHelper().showLayout(tipView);
    }

    private View initTipView() {
        return getHelper().inflate(R.layout.layout_frg_action_tip);
    }


    private void showView(View tipView, View.OnClickListener onClickListener, boolean isErrTip, String... msgs) {
        setTipViewContent(tipView, onClickListener,
                isErrTip ?
                        net.doyouhike.app.library.ui.R.drawable.ic_search_404_3x :
                        net.doyouhike.app.library.ui.R.drawable.ic_no_content_3x, msgs);
        getHelper().showLayout(tipView);
    }


    private void setTipViewContent(View tipView, View.OnClickListener onClickListener, int imgId, String... msgs) {

        //设置提示图标，是错误提示还是普通提示
        ImageView imageView = (ImageView) tipView.findViewById(net.doyouhike.app.library.ui.R.id.iv_tip_icon);

        if (imgId > 0) {
            imageView.setBackgroundDrawable(BaseBitmapUtil.getDrawable(getHelper().getContext(),imgId));
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }


        TextView tvFirstTip = (TextView) tipView.findViewById(net.doyouhike.app.library.ui.R.id.tv_tip_first_word);
        TextView tvSecondTip = (TextView) tipView.findViewById(net.doyouhike.app.library.ui.R.id.tv_tip_second_word);
        TextView btnRetry = (TextView) tipView.findViewById(net.doyouhike.app.library.ui.R.id.btn_tip_retry);


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
            }
            if (msgs.length >= 3) {
                btnRetry.setVisibility(View.VISIBLE);
                btnRetry.setText(msgs[2]);
            }

        }


    }

}
