package net.doyouhike.app.bbs.biz.newnetwork.model.base;

import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.CacheMode;

import net.doyouhike.app.bbs.biz.newnetwork.Event.BaseResponseEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.dao.net.DataJsonParser;
import net.doyouhike.app.bbs.biz.newnetwork.net.ParserParamUtil;

/**
 * 主要实现功能：网络请求的基类
 * <p/>
 * 主要方法:
 *
 * @see BaseRequest#getProcess() 网络响应结果解析,继承此基类时,需指定解析器
 * @see net.doyouhike.app.bbs.biz.newnetwork.net.RequestUrlAnnotation 请求地址注解,继承此基类时,可以使用注解方式指定网络请求地址
 * 也可以重写getUrl方法
 * @see BaseRequest#getSubUrl()
 * @see BaseRequest#getExtraInfo() 额外信息,如果有设置,则网络响应的额外信息等于此值{@link Response#extraTag}
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * <p/>
 * 作者：zaitu
 * 日期：15-12-26.
 */
public class BaseRequest {

    /**
     * 额外信息
     */
    private Object extraInfo;

    /**
     * 取消标志
     */
    private Object cancelSign;

    /**
     * 缓存类型
     */
    private CacheMode cacheMode = CacheMode.REQUEST_NETWORK_FAILED_READ_CACHE;

    /**
     * 请求方法
     */
    private RequestMethod requestMethod = RequestMethod.POST;

    private int time_out = 15 * 1000;

    private String contentTyep = Headers.HEAD_VALUE_ACCEPT_APPLICATION_JSON;

    public Object getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Object tag) {
        this.extraInfo = tag;
    }

    /**
     * @return 响应解析器, 将响应json 解析成响应对象
     */
    public IResponseProcess getProcess() {
        return new DataJsonParser();
    }


    /**
     * @return 接口地址
     */
    public String getSubUrl() {
        return ParserParamUtil.getSubUrl(this);
    }

    public String getRequestTag() {
        return getSubUrl();
    }

    public BaseResponseEvent getResponseEvent() {
        return ParserParamUtil.getEvent(this);
    }

    public CacheMode getCacheMode() {
        return cacheMode;
    }

    public void setCacheMode(CacheMode cacheMode) {
        this.cacheMode = cacheMode;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }

    public Object getCancelSign() {
        return cancelSign;
    }

    public void setCancelSign(Object cancelSign) {
        this.cancelSign = cancelSign;
    }

    public String getContentTyep() {
        return contentTyep;
    }

    public void setContentTyep(String contentTyep) {
        this.contentTyep = contentTyep;
    }

    public int getTime_out() {
        return time_out;
    }

    public void setTime_out(int time_out) {
        this.time_out = time_out;
    }
}
