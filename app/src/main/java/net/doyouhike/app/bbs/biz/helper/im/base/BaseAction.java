package net.doyouhike.app.bbs.biz.helper.im.base;

import net.doyouhike.app.bbs.biz.helper.im.HxidDispatcher;
import net.doyouhike.app.bbs.biz.helper.im.action.IHxIdAction;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;

/**
 * Created by zengjiang on 16/7/30.
 */

public abstract class BaseAction<T,DATA> {
    private String action;
    protected T params;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public T getParams() {
        return params;
    }

    public void setParams(T params) {
        this.params = params;
    }

    public abstract void processRequest(HxidDispatcher dispatcher);

    public void processResponse(Response<DATA> response) {
    }

    public void processError() {
        setAction(IHxIdAction.REQUEST_COMPLETE);
    }

    public abstract BaseGetRequest getRequestParam();

    public void callBackSuccess(HxidDispatcher dispatcher) {

    }

    public void onCallBackError(HxidDispatcher dispatcher, Response response) {

    }
}
