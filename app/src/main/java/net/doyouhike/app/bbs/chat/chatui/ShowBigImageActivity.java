package net.doyouhike.app.bbs.chat.chatui;

import android.os.Bundle;
import android.view.View;

import com.hyphenate.easeui.ui.EaseShowBigImageActivity;

import net.doyouhike.app.bbs.biz.presenter.live.SaveImageTask;
import net.doyouhike.app.bbs.ui.widget.me.MeUiHandle;
import net.doyouhike.app.library.ui.uistate.UiState;
import net.doyouhike.app.library.ui.uistate.UiStateController;

/**
 * 作者：luochangdong on 16/8/27
 * 描述：
 */
public class ShowBigImageActivity extends EaseShowBigImageActivity implements EaseShowBigImageActivity.ShowBigImageHelper {
    /**
     * ui状态控制
     */
    protected UiStateController uiStateController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setShowBigImageHelper(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void saveImageToLocal(String url) {
        new SaveImageTask(this).execute(url);
    }

    @Override
    public void loading(String thumbPath) {
        uiStateController = new UiStateController(image);
        MeUiHandle meUiHandle = new MeUiHandle(image);
        meUiHandle.setTempUrl(thumbPath);
        uiStateController.setUiStateHandle(meUiHandle);
        updateView(UiState.LOADING, null);
    }

    @Override
    public void loadingNomal() {
        updateView(UiState.NORMAL, null);
    }

    /**
     * 更新ui，根据ui状态更新界面
     */
    public void updateView(UiState state, View.OnClickListener onClickListener) {
        if (null == uiStateController) {
            throw new IllegalArgumentException("You must return a right target view for ui state");
        }

        uiStateController.updateView(state, onClickListener);
    }
}
