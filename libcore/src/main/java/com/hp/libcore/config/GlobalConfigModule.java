package com.hp.libcore.config;

import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hp.libcore.di.SingletonModule;
import com.hp.libcore.http.HttpHandler;
import com.hp.libcore.http.DefaultHttpHandler;
import com.hp.libcore.http.log.LoggerInterceptor;
import com.hp.libcore.http.log.DefaultFormatPrinter;
import com.hp.libcore.http.log.FormatPrinter;
import com.hp.libcore.tools.PredictUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.internal.Util;

/**
 * 可注入外部配置的自定义参数
 */
@Module
public class GlobalConfigModule {
    private HttpUrl mBaseUrl;
    private List<Interceptor> mInterceptors;
    private HttpHandler mHandler;
    private SingletonModule.RetrofitConfiguration mRetrofitConfiguration;
    private SingletonModule.OkHttpConfiguration mOkHttpConfiguration;
    private LoggerInterceptor.Level mPrintHttpLogLevel;
    private FormatPrinter mFormatPrinter;
    private ExecutorService mExecutorService;

    private GlobalConfigModule(Builder builder) {
        this.mBaseUrl = builder.baseUrl;
        this.mHandler = builder.handler;
        this.mInterceptors = builder.interceptors;
        this.mRetrofitConfiguration = builder.retrofitConfiguration;
        this.mOkHttpConfiguration = builder.okHttpConfiguration;
        this.mPrintHttpLogLevel = builder.printHttpLogLevel;
        this.mFormatPrinter = builder.formatPrinter;
        this.mExecutorService = builder.executorService;
    }

    public static Builder builder() {
        return new Builder();
    }


    @Singleton
    @Provides
    @Nullable
    List<Interceptor> provideInterceptors() {
        return mInterceptors;
    }

    @Singleton
    @Provides
    HttpUrl provideBaseUrl() {
        return mBaseUrl == null ? HttpUrl.parse("https://api.github.com/") : mBaseUrl;
    }

    /**
     * 提供处理 Http 请求和响应结果的处理类
     *
     * @return
     */
    @Singleton
    @Provides
    @Nullable
    HttpHandler provideHttpHandler() {
        return mHandler == null ? new DefaultHttpHandler() : mHandler;
    }

    @Singleton
    @Provides
    @Nullable
    SingletonModule.RetrofitConfiguration provideRetrofitConfiguration() {
        return mRetrofitConfiguration;
    }

    @Singleton
    @Provides
    @Nullable
    SingletonModule.OkHttpConfiguration provideOkHttpConfiguration() {
        return mOkHttpConfiguration;
    }

    @Singleton
    @Provides
    LoggerInterceptor.Level providePrintHttpLogLevel() {
        return mPrintHttpLogLevel == null ? LoggerInterceptor.Level.ALL : mPrintHttpLogLevel;
    }

    @Singleton
    @Provides
    FormatPrinter provideFormatPrinter(){
        return mFormatPrinter == null ? new DefaultFormatPrinter() : mFormatPrinter;
    }

    /**
     * 返回一个全局公用的线程池,适用于大多数异步需求。
     * 避免多个线程池创建带来的资源消耗。
     *
     * @return {@link Executor}
     */
    @Singleton
    @Provides
    ExecutorService provideExecutorService() {
        return mExecutorService == null ? new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), Util.threadFactory("Config Executor", false)) : mExecutorService;
    }

    public static final class Builder {
        private HttpUrl baseUrl;
        private List<Interceptor> interceptors;
        private HttpHandler handler;
        private SingletonModule.RetrofitConfiguration retrofitConfiguration;
        private SingletonModule.OkHttpConfiguration okHttpConfiguration;
        private LoggerInterceptor.Level printHttpLogLevel;
        private FormatPrinter formatPrinter;
        private ExecutorService executorService;

        private Builder() {
        }

        public Builder baseUrl(String baseUrl) {//基础url
            if (TextUtils.isEmpty(baseUrl)) {
                throw new NullPointerException("BaseUrl can not be empty");
            }
            this.baseUrl = HttpUrl.parse(baseUrl);
            return this;
        }


        public Builder httpHandler(HttpHandler handler) {//用来处理http响应结果
            this.handler = handler;
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {//动态添加任意个interceptor
            if (interceptors == null)
                interceptors = new ArrayList<>();
            this.interceptors.add(interceptor);
            return this;
        }


        public Builder retrofitConfiguration(SingletonModule.RetrofitConfiguration retrofitConfiguration) {
            this.retrofitConfiguration = retrofitConfiguration;
            return this;
        }

        public Builder okHttpConfiguration(SingletonModule.OkHttpConfiguration okHttpConfiguration) {
            this.okHttpConfiguration = okHttpConfiguration;
            return this;
        }


        public Builder printHttpLogLevel(LoggerInterceptor.Level printHttpLogLevel) {//是否让框架打印 Http 的请求和响应信息
            this.printHttpLogLevel = PredictUtil.checkNotNull(printHttpLogLevel, "The printHttpLogLevel can not be null, use LoggerInterceptor.Level.NONE instead.");
            return this;
        }

        public Builder formatPrinter(FormatPrinter formatPrinter){
            this.formatPrinter = PredictUtil.checkNotNull(formatPrinter, FormatPrinter.class.getCanonicalName() + "can not be null.");
            return this;
        }

        public Builder executorService(ExecutorService executorService) {
            this.executorService = executorService;
            return this;
        }

        public GlobalConfigModule build() {
            return new GlobalConfigModule(this);
        }
    }
}
