package net.doyouhike.app.bbs.biz.newnetwork.dao.net;

import android.support.annotation.NonNull;

import net.doyouhike.app.bbs.biz.newnetwork.dao.NetException;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.BaseResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 *
 * 1.网络响应解析器,将网络响应数据解析成响应对象{@link Response}
 *
 * 2.泛型S对应json的data部分
 *
 * 可以重写parserDataJson,实现手动解析
 * @see DataJsonParser#parserDataJson(String)
 *
 *
 *
 * Created by zaitu on 15-11-27.
 */
public class DataJsonParser<DATA> extends BaseResponseProcess<Response<DATA>> {
    protected Type type;

    public DataJsonParser(@NonNull Type type) {
        super(new Response<DATA>());
        this.type = type;
    }

    public DataJsonParser() {
        super(new Response<DATA>());
    }

    @Override
    protected void parserDataJson(String strJson) throws JSONException, NetException {

        if (null==type){
            return;
        }
        //使用gson自动解析
        DATA data = gson.fromJson(strJson, type);
        //设置解析结果
        result.setData(data);
    }
}
