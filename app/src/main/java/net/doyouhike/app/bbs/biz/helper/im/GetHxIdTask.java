package net.doyouhike.app.bbs.biz.helper.im;

import net.doyouhike.app.bbs.biz.helper.im.base.BaseAction;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.model.response.GetUserImResponse;
import net.doyouhike.app.bbs.biz.newnetwork.service.ApiReq;
import net.doyouhike.app.bbs.util.LogUtil;

/**
 * Created by zengjiang on 16/8/4.
 */

public class GetHxIdTask {


    int retryCount=5;
    BaseAction action;
    HxIdTaskListener listener;

    public GetHxIdTask(BaseAction action) {
        this.action = action;
    }

    public void setListener(HxIdTaskListener listener) {
        this.listener = listener;
    }

    public void getData(){

        ApiReq.doGet(action.getRequestParam(), new IOnResponseListener<Response>() {
            @Override
            public void onSuccess(Response response) {
                //网络请求成功处理
                action.processResponse(response);
                //转发下一步处理
                listener.onSuccess(action);
            }

            @Override
            public void onError(Response response) {
                retry(response);
            }
        });
    }

    private void retry(Response response){

        LogUtil.d("GetHxIdTask","retry"+retryCount);

        if (retryCount>0){
            retryCount--;
            getData();
        }else {
            //网络请求出错处理
            action.processError();
            //转发下一步处理
            listener.onError(action,response);
        }
    }


    public interface HxIdTaskListener{
        void onSuccess(BaseAction action);
        void onError(BaseAction action,Response response);
    }


}
