package net.doyouhike.app.bbs.biz.newnetwork.net;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 网络请求注解
 * Created by zengjiang on 16/6/27.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestUrlAnnotation {

    /**
     * @return 接口地址
     */
    String value() default "";



}
