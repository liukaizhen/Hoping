package com.hp.libcore.http;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 处理 Http 请求和响应结果的处理类
 */
public interface HttpHandler {
    Request onHttpRequestBefore(Interceptor.Chain chain, Request request);

    Response onHttpResponseBefore(String httpResult, Interceptor.Chain chain, Response response);
}
