package net.doyouhike.app.bbs.base.util;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;

import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.library.ui.uistate.UiState;
import net.doyouhike.app.library.ui.uistate.UiStateController;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * 作者:luochangdong on 16/4/14 16:36
 * 描述:
 */
public abstract class BaseDialog extends AlertDialog {

    public BaseDialog(Context context) {
        super(context);
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(getContext()).inflate(getContentViewLayoutID(), null);
        ButterKnife.inject(this, contentView);
        setContentView(contentView);
        initViewsAndEvents();
        View view = getLoadingTargetView();
        if (null != view && uiStateController == null) {
            uiStateController = new UiStateController(view);
        }
        onFirstUserVisible();
        LogUtil.d("Dialog onCreate");
    }


    public void onDialogDestory() {
        ButterKnife.reset(this);
    }


    /**
     * get ui sta target view
     */
    protected abstract View getLoadingTargetView();

    /**
     * bind layout resource file
     *
     * @return id of layout resource
     */
    protected abstract int getContentViewLayoutID();

    /**
     * init all views and add events
     */
    protected abstract void initViewsAndEvents();


    /**
     * 初始数据加载
     */
    protected abstract void onFirstUserVisible();

    /**
     * ui状态控制
     */
    protected UiStateController uiStateController;

    /**
     * 更新ui，根据ui状态更新界面
     */
    protected void updateView(UiState state) {
        if (null == uiStateController) {
            throw new IllegalArgumentException("You must return a right target view for ui state");
        }

        uiStateController.updateView(state, null);
    }

    /**
     * 更新ui，根据ui状态更新界面
     */
    protected void updateView(UiState state, View.OnClickListener onClickListener) {
        if (null == uiStateController) {
            throw new IllegalArgumentException("You must return a right target view for ui state");
        }

        uiStateController.updateView(state, onClickListener);
    }

    public void postEvent(Object o) {
        EventBus.getDefault().post(o);
    }
}
