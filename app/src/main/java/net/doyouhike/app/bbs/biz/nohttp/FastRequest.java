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

import com.yolanda.nohttp.Headers;
import com.yolanda.nohttp.NoHttp;
import com.yolanda.nohttp.RequestMethod;
import com.yolanda.nohttp.rest.RestRequest;
import com.yolanda.nohttp.rest.StringRequest;

import net.doyouhike.app.bbs.BuildConfig;
import net.doyouhike.app.bbs.util.StringUtil;
import net.doyouhike.app.bbs.util.UserInfoUtil;

/**
 * <p>自定义请求对象.</p>
 * Created in Feb 1, 2016 8:53:17 AM.
 *
 * @author Yan Zhenjie.
 */
public class FastRequest extends RestRequest<String> {

    public FastRequest(String url) {
        this(url, RequestMethod.GET);
    }

    public FastRequest(String url, RequestMethod requestMethod) {
        super(url, requestMethod);
        setAccept(Headers.HEAD_VALUE_ACCEPT_APPLICATION_JSON);
        addHeader("X-ZAITU-APP-KEY", BuildConfig.X_ZAITU_APP_KEY);

        if (StringUtil.isNotEmpty(StringUtil.getHostIp()))
            addHeader("X-ZAITU-CLIENT-IP", StringUtil.getHostIp());
        if (StringUtil.isNotEmpty(UserInfoUtil.getInstance().getToken()))
            addHeader("X-ZAITU-OPENAPI-TOKEN", UserInfoUtil.getInstance().getToken());
    }

    @Override
    public String parseResponse(Headers responseHeaders, byte[] responseBody) {

        String result = StringRequest.parseResponseString(responseHeaders, responseBody);

        return result;
    }
}
