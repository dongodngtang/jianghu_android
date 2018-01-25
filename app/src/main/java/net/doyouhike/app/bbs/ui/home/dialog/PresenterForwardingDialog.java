package net.doyouhike.app.bbs.ui.home.dialog;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;

/**
 * 作者:luochangdong on 16/5/25 10:01
 * 描述:
 */
public class PresenterForwardingDialog {
    ForwardingDialog forwardingDialog;


    /**
     * 分享成功回调
     */
    ForwardCallBack forwardCallBack;

    public PresenterForwardingDialog(ForwardingDialog forwardingDialog) {
        this.forwardingDialog = forwardingDialog;
    }

    public PresenterForwardingDialog(ForwardingDialog forwardingDialog,ForwardCallBack forwardCallBack) {
        this.forwardingDialog = forwardingDialog;
        this.forwardCallBack = forwardCallBack;
    }

    public void ForwardingComment(String topicId,String content){
        String token = UserInfoUtil.getInstance().getToken();

        if(StringUtil.isEmpty(topicId) ||
                StringUtil.isEmpty(token))
            return;

//        ForwardNodeParam param = new ForwardNodeParam();
//        param.setToken(token);
//        param.setContent(content);
//        param.setNode_id(topicId);
//
//        ApiReq.doPost(param, listener);
    }

    IOnResponseListener listener = new IOnResponseListener() {
        @Override
        public void onSuccess(Response response) {
            if(null!=forwardCallBack)
                forwardCallBack.onSuccess();
        }

        @Override
        public void onError(Response response) {
            if(null!=forwardCallBack)
                forwardCallBack.onError();
        }
    };


    public interface ForwardCallBack {
        public abstract void onSuccess();
        public abstract void onError();
    }


    public ForwardCallBack getForwardCallBack() {
        return forwardCallBack;
    }

    public void setForwardCallBack(ForwardCallBack forwardCallBack) {
        this.forwardCallBack = forwardCallBack;
    }


}
