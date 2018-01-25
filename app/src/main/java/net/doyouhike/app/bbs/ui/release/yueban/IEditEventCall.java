package net.doyouhike.app.bbs.ui.release.yueban;

import net.doyouhike.app.bbs.ui.widget.action.EditData;

/**
 * 作者：luochangdong on 16/8/17
 * 描述：
 */
public interface IEditEventCall {

    void setImageTextTitle(String title, String limit, EditData content);

    void updateTitle(String title);
}
