
package net.doyouhike.app.bbs.biz.newnetwork.service;

import android.support.annotation.NonNull;

import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;

/**
 * 功能：网络请求接口集合
 * 作者：曾江
 * 日期：16-1-20.
 */
public abstract class RequestHelper {

    /**
     * get方法与post方法区别,get配置了缓存,post没有
     * @param request 请求参数
     */
    public void get(
            @NonNull final BaseGetRequest request,
            IOnResponseListener onResponseListener
    ) {
        onGetRequest( request,  onResponseListener);
    }

    protected abstract void onGetRequest(BaseGetRequest request, IOnResponseListener onResponseListener);


    /**
     * get方法与post方法区别,get配置了缓存,post没有
     * @param request 请求参数
     */
    public void post(
            @NonNull final BasePostRequest request,
            IOnResponseListener onResponseListener
    ) {
        onPostRequest( request, onResponseListener);
    }

    protected abstract void onPostRequest(
                                          BasePostRequest request,
                                          IOnResponseListener onResponseListener);
}