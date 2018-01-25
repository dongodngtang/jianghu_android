package net.doyouhike.app.bbs.biz.newnetwork.Event;

import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;

/**
 * 网络响应事件
 *
 * EventBus传递
 *
 * 定义网络请求时,可以添加该类注解,有该注解时,网络响应将会发送EventBus事件
 * 为了不混淆事件,每个网络响应事件都应以此类为基类
 * 例子{@link net.doyouhike.app.bbs.biz.newnetwork.model.request.get.DoLikeTopicGetParam}
 *
 * 事件解析
 * @see net.doyouhike.app.bbs.biz.newnetwork.net.ParserParamUtil#getEvent(BaseRequest)
 *
 * Created by zengjiang on 16/7/7.
 */
public class BaseResponseEvent<DATA> {

    Response<DATA> response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response<DATA> response) {
        this.response = response;
    }

    public boolean isSuccess(){
        return response.isSuccess();
    }

    public int getCode(){
        return response.getCode();
    }



    public  DATA getData(){
        return response.getData();
    }

    public String getMsg() {
        return response.getMsg();
    }

    public Object getExtraTag(){

        return response.getExtraTag();

    }
}
