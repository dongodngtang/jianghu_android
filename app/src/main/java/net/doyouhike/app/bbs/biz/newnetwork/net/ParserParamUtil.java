package net.doyouhike.app.bbs.biz.newnetwork.net;

import net.doyouhike.app.bbs.biz.newnetwork.Event.BaseResponseEvent;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseRequest;

/**
 * 解析注解参数
 * Created by zengjiang on 16/6/27.
 */
public class ParserParamUtil {

    /**
     * @param request
     * @return
     */
    public static String getSubUrl(BaseRequest request){

        Class<?> clazz=request.getClass();

        boolean isExist=clazz.isAnnotationPresent(RequestUrlAnnotation.class);//是否被RequestUrlAnnotation注解

        if (!isExist){
            return "";
        }

        RequestUrlAnnotation urlAnnotation=clazz.getAnnotation(RequestUrlAnnotation.class);

        return urlAnnotation.value();
    }

    public static BaseResponseEvent getEvent(BaseRequest request){


        Class<?> clazz=request.getClass();

        boolean isExist=clazz.isAnnotationPresent(ResponseEventAnnotation.class);//是否被RequestUrlAnnotation注解

        if (!isExist){
            return null;
        }


        ResponseEventAnnotation annotation=  clazz.getAnnotation(ResponseEventAnnotation.class);
        BaseResponseEvent event=null;

        try {

            Class<?> eventClazz=annotation.value();
            event = ((BaseResponseEvent)eventClazz.newInstance());

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return event;
    }
}
