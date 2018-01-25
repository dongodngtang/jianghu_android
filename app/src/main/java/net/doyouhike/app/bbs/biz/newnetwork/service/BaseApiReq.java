package net.doyouhike.app.bbs.biz.newnetwork.service;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yolanda.nohttp.NoHttp;

import net.doyouhike.app.bbs.base.application.MyApplication;
import net.doyouhike.app.bbs.biz.newnetwork.Event.BaseResponseEvent;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IOnResponseListener;
import net.doyouhike.app.bbs.biz.newnetwork.dao.base.IResponseProcess;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BaseGetRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.BasePostRequest;
import net.doyouhike.app.bbs.biz.newnetwork.model.base.Response;
import net.doyouhike.app.bbs.biz.nohttp.CallServer;
import net.doyouhike.app.bbs.biz.nohttp.FastRequest;
import net.doyouhike.app.bbs.biz.nohttp.HeaderException;
import net.doyouhike.app.bbs.biz.nohttp.HttpListener;
import net.doyouhike.app.bbs.util.LogUtil;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.log.LocalLogUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import de.greenrobot.event.EventBus;

/**
 * volley的调用及回调处理
 * Created by zaitu on 15-11-26.
 */
public class BaseApiReq {
    private static final String TAG = "NET_REQUEST";


    public static final int ERROR_CODE_DEFAULT = -1000;
    public static final int ERROR_CODE_TIMEOUT = -1001;
    public static final int ERROR_CODE_NET_CONNECT = -1002;
    public static final int ERROR_CODE_SERVICE = -1003;

    public static final String ERR_MSG_TIMEOUT = "连接超时";
    public static final String ERR_MSG_COMMON = "请检查网络后重试";
    public static final String ERR_MSG_SERVER = "请检查网络后重试";
    public static final String ERR_MSG_NET = "无法连接到网络,请检查网络设置";


    public static final int TIME_OUT = 30000;
    public static final int RETRY_COUNT = 0;
    /**
     * The default backoff multiplier
     */
    public static final float DEFAULT_BACKOFF_MULT = 1f;


    public static void baseGetFast(
            @NonNull final String url,
            final BaseGetRequest request,
            final IOnResponseListener onResponseListener
    ) {
        Map<String, String> map = request.toHashMap();

        final IResponseProcess process = request.getProcess();
        process.setExtraTag(request.getExtraInfo());
        //EventBus事件
        final BaseResponseEvent event = request.getResponseEvent();


        LogUtil.d(TAG, "URL:" + url);
        LogUtil.d(TAG, "PARAMS:" + map.toString());

        final String urlParam = encodeParameters(map, NoHttp.CHARSET_UTF8);

        FastRequest fastRequest = new FastRequest(url + "?" + urlParam);
        fastRequest.setCacheMode(request.getCacheMode());
        fastRequest.setCancelSign(request.getCancelSign());
        fastRequest.setContentType(request.getContentTyep());
        fastRequest.setConnectTimeout(request.getTime_out());

        CallServer.getRequestInstance().add(0, fastRequest, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, com.yolanda.nohttp.rest.Response<String> response) {
                //本地日志打印
                LocalLogUtil.netLog_d("NET",
                        "\nURL: ", url,
                        "\nPARAMS: ", urlParam,
                        "\nresponse: ", response.get());
                handleSucceed(response, process, onResponseListener, event);
            }

            @Override
            public void onFailed(int what, com.yolanda.nohttp.rest.Response<String> response) {
                deliveryError(response.getException(), process, onResponseListener, event);
            }
        });

    }

    private static void handleSucceed(com.yolanda.nohttp.rest.Response<String> response, IResponseProcess process, IOnResponseListener onResponseListener, BaseResponseEvent event) {
        if (response.getHeaders().getResponseCode() == 200 ||
                response.getHeaders().getResponseCode() == 304)
            deliveryResponse(response.get(), process, onResponseListener, event);
        else {
            if (response.getHeaders().containsKey("X-Zaitu-Msg")) {
                List<String> msgs = response.getHeaders().getValues("X-Zaitu-Msg");
                if (msgs != null || msgs.get(0) != null) {
                    HeaderException exception = new HeaderException(msgs.get(0),
                            response.getHeaders().getResponseCode());
                    deliveryError(exception, process, onResponseListener, event);
                } else {
                    HeaderException exception = new HeaderException(getErrMsg(response.getHeaders().getResponseMessage()),
                            response.getHeaders().getResponseCode());
                    deliveryError(exception, process, onResponseListener, event);
                }
            } else {
                HeaderException exception = new HeaderException(getErrMsg(response.getHeaders().getResponseMessage()),
                        response.getHeaders().getResponseCode());
                deliveryError(exception, process, onResponseListener, event);
            }
        }
    }

    private static String getErrMsg(String responseMessage) {
        String err = "未知异常";
        if (StringUtil.isNotEmpty(responseMessage)) {
            err = responseMessage;
        }
        return err;
    }

    private static String encodeParameters(Map<String, String> params, String paramsEncoding) {
        StringBuilder encodedParams = new StringBuilder();
        try {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String value = entry.getValue();
                if (null == value) {
                    continue;
                }

                encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), paramsEncoding));
                encodedParams.append('&');
            }
            LogUtil.d(encodedParams.toString());
            return encodedParams.toString();
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + paramsEncoding, uee);
        }
    }


    public static void baesPostFast(
            @NonNull final String url,
            @NonNull final BasePostRequest request,
            @NonNull final IOnResponseListener onResponseListener
    ) {
        //将对象转为json格式字符串
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        final String json = gson.toJson(request);
        final BaseResponseEvent event = request.getResponseEvent();

        LogUtil.d(TAG, "URL:" + url);
        LogUtil.d(TAG, "PARAMS_JSON:" + json);


        //Process 针对返回json的处理工具
        final IResponseProcess process = request.getProcess();
        process.setExtraTag(request.getExtraInfo());

        FastRequest fastRequest = new FastRequest(url, request.getRequestMethod());
        fastRequest.setDefineRequestBodyForJson(json);
        fastRequest.setCacheMode(request.getCacheMode());
        fastRequest.setCancelSign(request.getCancelSign());
        fastRequest.setContentType(request.getContentTyep());

        CallServer.getRequestInstance().add(0, fastRequest, new HttpListener<String>() {
            @Override
            public void onSucceed(int what, com.yolanda.nohttp.rest.Response<String> response) {
                //本地日志打印
                LocalLogUtil.netLog_d("NET",
                        "\nURL: ", url,
                        "\nPARAMS: ", json,
                        "\nresponse: ", response.get());
                handleSucceed(response, process, onResponseListener, event);

            }

            @Override
            public void onFailed(int what, com.yolanda.nohttp.rest.Response<String> response) {
                deliveryError(response.getException(), process, onResponseListener, event);
            }
        });
    }


    private static void deliveryError(Exception error,
                                      final IResponseProcess process,
                                      final IOnResponseListener onResponseListener,
                                      final BaseResponseEvent event) {

        Response response = process.getErrorResponse(error);

        if (null != event) {
            event.setResponse(response);
            EventBus.getDefault().post(event);
        }

        if (null != onResponseListener) {
            onResponseListener.onError(response);
        }
    }

    /**
     * @param result             网络响应结果
     * @param process            网络响应处理器
     * @param onResponseListener 响应监听
     * @param event
     */
    private static void deliveryResponse(final String result,
                                         final IResponseProcess process,
                                         final IOnResponseListener onResponseListener,
                                         final BaseResponseEvent event) {


        MyApplication.getInstance().getExecutorService().execute(new Runnable() {
            @Override
            public void run() {

                final Response response = process.getResponse(result);

                if (null != event) {
                    event.setResponse(response);
                    EventBus.getDefault().post(event);
                }

                Handler handler = new Handler(Looper.getMainLooper());

                if (null != onResponseListener && handler != null) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (response.isSuccess()) {
                                onResponseListener.onSuccess(response);
                            } else {
                                onResponseListener.onError(response);
                            }
                        }
                    });
                }


            }
        });

    }


}
