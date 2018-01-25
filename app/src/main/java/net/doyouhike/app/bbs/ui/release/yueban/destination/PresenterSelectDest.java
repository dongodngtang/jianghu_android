package net.doyouhike.app.bbs.ui.release.yueban.destination;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.openapi.presenter.NodesHelper;
import net.doyouhike.app.bbs.biz.openapi.response.nodes.BaseMfDestResp;
import net.doyouhike.app.bbs.util.LogUtil;

/**
 * Filework:
 * Author: luochangdong
 * Date:16-3-22
 */
public class PresenterSelectDest {

    private SelectDestActivity selectDestActivity;

    public PresenterSelectDest(SelectDestActivity activity) {
        selectDestActivity = activity;
    }

    public void getDestByKeyword(String keyword) {
        NodesHelper.getInstance().searchBaseMfDest(selectDestActivity,keyword,listener);
    }

    private IOnResponseListener listener = new IOnResponseListener<Response<BaseMfDestResp>>() {
        @Override
        public void onSuccess(Response<BaseMfDestResp> response) {
            LogUtil.d("SelectDestSuccess");
            selectDestActivity.setSelectDatas(response.getData().getItems());
        }

        @Override
        public void onError(Response response) {
            LogUtil.d("SelectDestonError" + response.getMsg());
            selectDestActivity.loadError(response.getMsg());
        }
    };

    public void cancelPost() {
    }
}
