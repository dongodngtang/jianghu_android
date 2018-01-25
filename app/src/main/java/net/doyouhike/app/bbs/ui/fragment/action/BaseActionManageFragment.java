package net.doyouhike.app.bbs.ui.fragment.action;

import android.view.View;

import net.doyouhike.app.bbs.biz.openapi.presenter.page.base.State;
import net.doyouhike.app.bbs.biz.event.open.AccountUserFollowEvent;
import net.doyouhike.app.bbs.biz.openapi.response.events.EventMembersResp;
import net.doyouhike.app.bbs.ui.fragment.ListFragment;
import net.doyouhike.app.bbs.biz.openapi.presenter.AttendState;
import net.doyouhike.app.library.ui.uistate.UiState;

/**
 * Created by zengjiang on 16/7/19.
 */
public abstract class BaseActionManageFragment extends ListFragment<EventMembersResp.ItemsBean> {

    @Override
    protected void onFirstUserVisible() {
        super.onFirstUserVisible();
        simpleListHelper.getData(true);
    }

    @Override
    protected boolean isBindEventBusHere() {
        return true;
    }


    /**
     * 是否已显示过normalView
     */
    boolean isFirstNormalView =false;
    @Override
    public void updateView(UiState state, View.OnClickListener onClickListener) {

        if ((state == UiState.ERROR || state == UiState.LOADING )&&!isFirstNormalView){
            super.updateView(state, onClickListener);
            return;
        }

        if (state != UiState.NORMAL && simpleListHelper.getPage().getState() != State.NULL) {
            isFirstNormalView =true;
            updateView(UiState.NORMAL);
        } else {
            super.updateView(state, onClickListener);
        }

    }


    /**
     * 关注回调
     */
    public void onEventMainThread(AccountUserFollowEvent response) {
        updateView(UiState.NORMAL);
        if (response.getCode() == 0) {

            String user_id = (String) response.getExtraTag();
            for (EventMembersResp.ItemsBean merbers : simpleListHelper.getItems()) {
                if (user_id.equals(merbers.getUser().getUser_id())) {
                    if (merbers.getIsFollow() == AttendState.NOT_ATTEND) {
                        merbers.setIsFollow(AttendState.ATTENDING);
                    } else {
                        merbers.setIsFollow(AttendState.NOT_ATTEND);
                    }
                    simpleListHelper.getAdapter().notifyDataSetChanged();
                    return;
                }
            }


        }
    }
}
