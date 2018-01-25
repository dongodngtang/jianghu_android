package net.doyouhike.app.bbs.biz.newnetwork.dao.base;


import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;

/**
 * Created by zaitu on 15-11-18.
 */
public interface IResponseProcess<T extends Response>{
    /**
     * @param strJson 网络响应json
     * @return 网络响应对象 继承{@link Response}
     */
    T getResponse(String strJson);

    /**
     * @param error 网络错误
     * @return 将网络错误转为通用响应格式
     */
    Response getErrorResponse(Exception error);

    /**
     * @param tag 设置额外信息,可由请求带过来
     */
    void setExtraTag(Object tag);

    /**
     * @return 获取额外信息
     */
    Object getExtraTag();
}
