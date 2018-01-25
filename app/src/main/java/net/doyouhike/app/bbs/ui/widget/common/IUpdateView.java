package net.doyouhike.app.bbs.ui.widget.common;

import android.view.View;

import net.doyouhike.app.library.ui.uistate.UiState;

/**
 * 功能：更新Ui
 *
 * @author：曾江 日期：16-4-28.
 */
public interface IUpdateView {
    void updateView(UiState uiState);
    void updateView(UiState state, View.OnClickListener onClickListener);
}
