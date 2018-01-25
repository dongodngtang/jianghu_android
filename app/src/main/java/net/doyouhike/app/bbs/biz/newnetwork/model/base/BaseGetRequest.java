package net.doyouhike.app.bbs.biz.newnetwork.model.base;

import android.text.TextUtils;

import java.util.HashMap;

/**
 * 每一个请求接口都必须继承此接口
 * Created by zaitu on 15-11-18.
 */
public abstract class BaseGetRequest extends BaseRequest{
    protected HashMap<String,String> map =new HashMap<>();
    public HashMap<String,String> toHashMap(){
        map.clear();
        setMapValue();
        return map;
    }

    /**
     * 设置具体的参数值
     */
    protected abstract void setMapValue();

    protected void putValue(String key,Object value){
        if (null!=value){
            putValue(key,String.valueOf(value));
        }
    }
    protected void putValue(String key,String value){
        if (!TextUtils.isEmpty(value)){
            map.put(key,value);
        }
    }
}
