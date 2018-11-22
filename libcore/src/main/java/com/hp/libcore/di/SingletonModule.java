package com.hp.libcore.di;

import android.app.Application;
import android.content.Context;
import androidx.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hp.libcore.http.HttpHandler;
import com.hp.libcore.http.IRepositoryManager;
import com.hp.libcore.http.RepositoryManager;
import com.hp.libcore.http.log.LoggerInterceptor;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import okhttp3.Dispatcher;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 提供项目全局单例
 */
@Module
public abstract class SingletonModule {
    private static final int TIME_OUT = 10;

    @Binds
    abstract IRepositoryManager repositoryManager(RepositoryManager repository);

    @Binds
    abstract Interceptor interceptor(LoggerInterceptor interceptor);

    @Singleton
    @Provides
    static Retrofit.Builder provideRetrofitBuilder() {
        return new Retrofit.Builder();
    }

    @Singleton
    @Provides
    static OkHttpClient.Builder provideClientBuilder() {
        return new OkHttpClient.Builder();
    }

    @Singleton
    @Provides
    static Gson provideGson() {
        return new GsonBuilder().create();
    }

    /**
     * 提供 {@link Retrofit}
     * @param application
     * @param configuration
     * @param builder
     * @param client
     * @param httpUrl
     * @param gson
     * @return {@link Retrofit}
     */
    @Singleton
    @Provides
    static Retrofit provideRetrofit(Application application, @Nullable RetrofitConfiguration configuration,
                                    Retrofit.Builder builder, OkHttpClient client, HttpUrl httpUrl, Gson gson) {
        builder.baseUrl(httpUrl)//域名
                .client(client);//设置okHttp

        if (configuration != null)
            configuration.configRetrofit(application, builder);

        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用 Rxjava
                .addConverterFactory(GsonConverterFactory.create(gson));//使用 Gson
        return builder.build();
    }

    /**
     * 提供 {@link OkHttpClient}
     * @param application
     * @param configuration
     * @param builder
     * @param intercept
     * @param interceptors
     * @param handler
     * @return {@link OkHttpClient}
     */
    @Singleton
    @Provides
    static OkHttpClient provideClient(Application application, @Nullable OkHttpConfiguration configuration, OkHttpClient.Builder builder, Interceptor intercept
            , @Nullable List<Interceptor> interceptors, @Nullable HttpHandler handler, ExecutorService executorService) {
        builder
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .readTimeout(TIME_OUT, TimeUnit.SECONDS)
                .addNetworkInterceptor(intercept);

        if (handler != null)
            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    return chain.proceed(handler.onHttpRequestBefore(chain, chain.request()));
                }
            });

        if (interceptors != null) {//如果外部提供了interceptor的集合则遍历添加
            for (Interceptor interceptor : interceptors) {
                builder.addInterceptor(interceptor);
            }
        }

        // 为 OkHttp 设置默认的线程池。
        builder.dispatcher(new Dispatcher(executorService));

        if (configuration != null)
            configuration.configOkHttp(application, builder);
        return builder.build();
    }

    public interface RetrofitConfiguration {
        void configRetrofit(Context context, Retrofit.Builder builder);
    }

    public interface OkHttpConfiguration {
        void configOkHttp(Context context, OkHttpClient.Builder builder);
    }

}
