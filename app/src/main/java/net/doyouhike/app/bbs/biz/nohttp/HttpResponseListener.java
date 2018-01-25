/*
 * Copyright 2015 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.doyouhike.app.bbs.biz.nohttp;

import com.yolanda.nohttp.Logger;
import com.yolanda.nohttp.error.NetworkError;
import com.yolanda.nohttp.error.NotFoundCacheError;
import com.yolanda.nohttp.error.ParseError;
import com.yolanda.nohttp.error.TimeoutError;
import com.yolanda.nohttp.error.URLError;
import com.yolanda.nohttp.error.UnKnownHostError;
import com.yolanda.nohttp.rest.OnResponseListener;
import com.yolanda.nohttp.rest.Request;
import com.yolanda.nohttp.rest.Response;

import net.doyouhike.app.bbs.biz.event.GlobalDialogEvent;
import net.doyouhike.app.bbs.biz.event.LogoutEvent;
import net.doyouhike.app.bbs.biz.helper.userinfo.UserInfoClearUtil;
import net.doyouhike.app.bbs.ui.widget.common.dialog.PasswordChangeDialog;
import net.doyouhike.app.bbs.util.ActivityRouter;
import net.doyouhike.app.bbs.util.LogUtil;

import java.net.ProtocolException;

import de.greenrobot.event.EventBus;

/**
 * Created in Nov 4, 2015 12:02:55 PM.
 *
 * @author Yan Zhenjie.
 */
public class HttpResponseListener<T> implements OnResponseListener<T> {
    private static final String TAG = "NET_HTTP";

    /**
     * Request.
     */
    private Request<?> mRequest;
    /**
     * 结果回调.
     */
    private HttpListener<T> callback;

    /**
     * @param request      请求对象.
     * @param httpCallback 回调对象.
     */
    public HttpResponseListener(Request<?> request, HttpListener<T> httpCallback) {
        this.mRequest = request;
        this.callback = httpCallback;
    }


    @Override
    public void onStart(int what) {

    }

    /**
     * 成功回调.
     */
    @Override
    public void onSucceed(int what, final Response<T> response) {
        int responseCode = response.getHeaders().getResponseCode();

        LogUtil.d(TAG + " response", "" + response.responseCode());
        LogUtil.d(TAG, "" + response.getNetworkMillis() + "毫秒");
        if (responseCode == 804) {//密码已更改,请重新登陆
            //显示全局对话框
            GlobalDialogEvent event = new GlobalDialogEvent().setmDialogCreater(PasswordChangeDialog.getInstance());
            EventBus.getDefault().post(event);
            return;

        } else if (responseCode == 805) {//OpenAPI token已失效.
            EventBus.getDefault().post(new LogoutEvent());
//                //清理一些登陆信息
            new UserInfoClearUtil().logout();
            ActivityRouter.tokenExpiry();
            return;

        }

        if (responseCode > 400) {
            if (responseCode == 405) {// 405表示服务器不支持这种请求方法，比如GET、POST、TRACE中的TRACE就很少有服务器支持。

            } else {// 但是其它400+的响应码服务器一般会有流输出。

            }
        }
        if (callback != null) {
            callback.onSucceed(what, response);
        }
    }

    /**
     * 失败回调.
     */
    @Override
    public void onFailed(int what, Response<T> response) {

        LogUtil.d(TAG + " response", "" + response.responseCode());
        LogUtil.d(TAG, "" + response.getNetworkMillis() + "毫秒");

        Exception exception = response.getException();
        if (exception instanceof NetworkError) {// 网络不好

        } else if (exception instanceof TimeoutError) {// 请求超时

        } else if (exception instanceof UnKnownHostError) {// 找不到服务器

        } else if (exception instanceof URLError) {// URL是错的

        } else if (exception instanceof NotFoundCacheError) {
            // 这个异常只会在仅仅查找缓存时没有找到缓存时返回

        } else if (exception instanceof ProtocolException) {

        } else if (exception instanceof ParseError) {

        } else {

        }

        LogUtil.d("错误：" + exception.getMessage());
        if (callback != null)
            callback.onFailed(what, response);
    }

    @Override
    public void onFinish(int what) {

    }

}
