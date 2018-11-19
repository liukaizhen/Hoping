package com.hp.yujian;

import android.content.Context;

import com.hp.libcore.config.GlobalConfigModule;
import com.hp.libcore.config.IConfig;
import com.hp.libcore.http.HttpHandler;
import com.hp.libcore.http.RequestInterceptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class YjConfig implements IConfig {
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        builder.baseUrl("http://apitrial.xiyuan.tv/XIYUAN/")
                .printHttpLogLevel(RequestInterceptor.Level.ALL)
                .httpHandler(new HttpHandler() {
                    @Override
                    public Request onHttpRequestBefore(Interceptor.Chain chain, Request request) {
                        Request newRequest = chain.request().newBuilder()
                                .header("clientID", "Android")
                                .header("clientKey", "D8dHEm5Y6A2x/tCOi2sfB5fnxOP25mUTv0W2TeSYKSWMgbIN1ZrEEQ==")
                                .header("Content-Type", "text/plain;charset=utf-8")
                                .header("lang", "zh_CN")
                                .header("channel", "2")
                                .header("version", "560")
                                .build();
                        return newRequest;
                    }

                    @Override
                    public Response onHttpResponseBefore(String httpResult, Interceptor.Chain chain, Response response) {
                        return response;
                    }
                });
    }
}
