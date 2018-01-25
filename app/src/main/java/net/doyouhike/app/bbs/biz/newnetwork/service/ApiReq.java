package net.doyouhike.app.bbs.biz.newnetwork.service;

import android.support.annotation.NonNull;


import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.nohttp.HttpInterface;

/**
 * 获取网络数据
 * Created by zaitu on 15-11-25.
 */
public class ApiReq extends RequestHelper {

    private static ApiReq instance = new ApiReq();

    private ApiReq() {
    }

    /**
     * @param request 请求参数
     */
    public static void doGet(
            @NonNull final BaseGetRequest request
    ) {
        doGet(request, null);
    }



    /**
     * @param request 请求参数
     */
    public static void doGet(
            @NonNull final BaseGetRequest request,
            IOnResponseListener onResponseListener
    ) {
        instance.get(request, onResponseListener);
    }


    /**
     * @param request 请求参数
     */
    public static void doPost(
            @NonNull final BasePostRequest request
    ) {
        doPost(request, null);
    }

    /**
     * @param request 请求参数
     */
    public static void doPost(
            @NonNull final BasePostRequest request,
            IOnResponseListener onResponseListener
    ) {
        instance.post(request, onResponseListener);
    }

    @Override
    protected void onGetRequest(BaseGetRequest request, IOnResponseListener onResponseListener) {

        String url = HttpInterface.getInterface(request.getSubUrl());
        BaseApiReq.baseGetFast(url,request, onResponseListener);
    }

    @Override
    protected void onPostRequest(BasePostRequest request,
                                 IOnResponseListener onResponseListener) {

        String url = HttpInterface.getInterface(request.getSubUrl());
        BaseApiReq.baesPostFast(url,request, onResponseListener);
    }


}