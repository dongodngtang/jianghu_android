package net.doyouhike.app.bbs.ui.release;

import android.os.Handler;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;


/**
 * Filework:
 * Author: luochangdong
 * Date:16-2-19
 */
public class PresenterBlocklist {
    private Handler mHandler;
    BlockCallback callback;

    public PresenterBlocklist(BlockCallback callback) {
        this.callback = callback;
    }

    public void checkSensitiveWord(String content) {
//        ApiReq.doPost(new CheckSensitiveWordPostReq(content), checkSensitiveWordListener);
    }

    private IOnResponseListener<Response> checkSensitiveWordListener = new IOnResponseListener<Response>() {
        @Override
        public void onSuccess(Response response) {
            callback.notHaveBlock();

        }

        @Override
        public void onError(Response response) {
            if (response.getCode() == 1700001)
                callback.haveBlock();
            else
                callback.netError();

        }
    };

    public interface BlockCallback {
        void haveBlock();
        void notHaveBlock();
        void netError();

    }
}
