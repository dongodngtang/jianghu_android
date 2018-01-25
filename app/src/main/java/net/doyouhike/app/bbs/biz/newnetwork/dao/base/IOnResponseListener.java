package net.doyouhike.app.bbs.biz.newnetwork.dao.base;


import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;

/**
 * 主要实现功能：网络请求响应回调
 * 作者：zaitu
 * 日期：15-12-25.
 */
public interface IOnResponseListener<T extends Response> {
    void onSuccess(T response);
    void onError(Response response);
}
