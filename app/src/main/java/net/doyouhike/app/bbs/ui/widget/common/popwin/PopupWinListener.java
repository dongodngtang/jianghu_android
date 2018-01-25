package net.doyouhike.app.bbs.ui.widget.common.popwin;

/**
 * 功能：
 * 作者：曾江
 * 日期：15-12-29.
 */
public interface PopupWinListener {
    static final int ALL_SELECT = -1;

    /**
     * @param position 列表的索引号
     */
    void onItemSelected(int position);

    /**
     * popupWin消失调用
     */
    void onDismiss();
}
