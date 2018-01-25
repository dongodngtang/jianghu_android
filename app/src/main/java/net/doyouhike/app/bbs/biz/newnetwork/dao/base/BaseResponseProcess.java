package net.doyouhike.app.bbs.biz.newnetwork.dao.base;


import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.ParseError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;

import net.doyouhike.app.bbs.biz.event.GlobalDialogEvent;
import net.doyouhike.app.bbs.biz.event.LogoutEvent;
import net.doyouhike.app.bbs.biz.helper.userinfo.UserInfoClearUtil;
import net.doyouhike.app.bbs.biz.newnetwork.dao.NetException;
import net.doyouhike.app.bbs.biz.newnetwork.model.Content;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.newnetwork.service.BaseApiReq;
import net.doyouhike.app.bbs.biz.nohttp.HeaderException;
import net.doyouhike.app.bbs.ui.widget.common.dialog.PasswordChangeDialog;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.ProtocolException;

import de.greenrobot.event.EventBus;

/**
 * Created by Administrator on 15-5-21.
 */
public abstract class BaseResponseProcess<RESULT extends Response> implements IResponseProcess<RESULT> {
    protected RESULT result;
    protected Gson gson = new GsonBuilder().serializeNulls().create();

    public BaseResponseProcess(RESULT t) {
        setResult(t);
    }

    /**
     * @param strJson 网络响应json
     * @throws JSONException
     * @throws NetException
     */
    protected abstract void parserDataJson(String strJson) throws JSONException, NetException;

    @Override
    public Response getErrorResponse(Exception error) {
        if (null != error) {
            error.printStackTrace();
        }

        Response response = new Response();
        response.setExtraTag(getExtraTag());

        if (error instanceof NetworkError) {// 网络不好
            response.setMsg(BaseApiReq.ERR_MSG_NET);
            response.setCode(BaseApiReq.ERROR_CODE_NET_CONNECT);
        } else if (error instanceof com.yolanda.nohttp.error.TimeoutError) {// 请求超时
            //超时
            response.setMsg(BaseApiReq.ERR_MSG_TIMEOUT);
            response.setCode(BaseApiReq.ERROR_CODE_TIMEOUT);
        } else if (error instanceof UnKnownHostError) {// 找不到服务器
            response.setCode(BaseApiReq.ERROR_CODE_SERVICE);
            response.setMsg(BaseApiReq.ERR_MSG_SERVER);

        } else if (error instanceof URLError) {// URL是错的

        } else if (error instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回

        } else if (error instanceof ProtocolException) {

        } else if (error instanceof ParseError) {

        } else if (error instanceof HeaderException) {
            HeaderException exception = (HeaderException) error;
            response.setMsg(error.getMessage());
            response.setCode(exception.getResponseCode());
        } else {
            String strMsg = BaseApiReq.ERR_MSG_COMMON;

            response.setMsg(strMsg);
            response.setCode(BaseApiReq.ERROR_CODE_DEFAULT);
        }

        return response;

    }

    @Override
    public RESULT getResponse(String response) {
        parserJSON(response);
        return result;
    }

    @Override
    public void setExtraTag(Object tag) {
        result.setExtraTag(tag);
    }

    @Override
    public Object getExtraTag() {
        return result.getExtraTag();
    }

    public void setResult(RESULT result) {
        this.result = result;
    }

    private void parserJSON(String response) {
        try {

            LogUtil.d("NET_RESPONSE", "reslut " + response);
            JSONObject jsonObject = new JSONObject(response);
            result.setStrJSON(jsonObject.toString());
            result.setCode(1);//设置网络响应的ret默认值为1
            if (jsonObject.has(Content.NET_RESP_CODE)) {
                result.setCode(jsonObject.getInt(Content.NET_RESP_CODE));
            }

            if (jsonObject.has(Content.NET_RESP_MSG)) {
                String msg = jsonObject.getString(Content.NET_RESP_MSG);
                result.setMsg(msg);
            }


            if (result.getCode() == 0) {
                result.setIsSuccess(true);
            } else if (result.getCode() == 804) {
                //password Error
                result.setIsSuccess(false);
                result.setMsg("密码已更改,请重新登陆");

                //显示全局对话框
                GlobalDialogEvent event = new GlobalDialogEvent().setmDialogCreater(PasswordChangeDialog.getInstance());
                EventBus.getDefault().post(event);

            } else if (result.getCode() == 805) {
                //token Error
                result.setIsSuccess(false);
                result.setMsg("账号权限过期,请重新登陆");


                EventBus.getDefault().post(new LogoutEvent());
//                //清理一些登陆信息
                new UserInfoClearUtil().logout();

                ActivityRouter.tokenExpiry();

            } else if (result.getCode() == 2000001) {
                result.setIsSuccess(false);
                result.setMsg("账号被冻结，有疑问请联系磨房网");
            }


            //解析“data”部分
            processData(jsonObject);

            doNext();
        } catch (Exception e) {
            e.printStackTrace();
            result.setIsSuccess(false);
            result.setMsg(e.getMessage());

        }
    }

    /**
     * 处理 data部分
     *
     * @param jsonObject 网络响应json
     * @throws JSONException
     * @throws NetException
     */
    protected void processData(JSONObject jsonObject) throws JSONException, NetException {
        if (jsonObject.has(Content.NET_RESP_DATA)) {
            result.setStrData(jsonObject.getString(Content.NET_RESP_DATA));


            if (result.getStrData() == null || result.getStrData().equals("null")) {

                if (result.isSuccess()) {
                    result.setIsSuccess(false);
                    result.setMsg("返回结果为null");
                }
            } else {
                parserDataJson(jsonObject.getString(Content.NET_RESP_DATA));
            }
        }
    }

    protected void doNext() {

    }


    protected String getJSONStr(JSONObject object, String key) throws JSONException, NetException {

        if (object.has(key)) {
            return object.getString(key);
        } else {
            throw new NetException("缺少参数：" + key);
        }
    }

    protected int getJsonInt(JSONObject object, String key) throws JSONException, NetException {

        if (object.has(key)) {
            String value = object.getString(key);

            if (!TextUtils.isEmpty(value)) {
                return object.getInt(key);
            }
        }
        return -1;
    }


}
