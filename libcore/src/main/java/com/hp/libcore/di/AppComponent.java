package com.hp.libcore.di;

import android.app.Application;

import com.google.gson.Gson;
import com.hp.libcore.AppDelegate;
import com.hp.libcore.config.GlobalConfigModule;
import com.hp.libcore.http.IRepositoryManager;

import java.util.concurrent.ExecutorService;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import okhttp3.OkHttpClient;

@Singleton
@Component(modules = {AppModule.class,GlobalConfigModule.class})
public interface AppComponent {

    /**
     * 数据管理
     * 包括网络和缓存
     * @return
     */
    IRepositoryManager repository();

    /**
     * 网络客户端
     * @return
     */
    OkHttpClient okHttpClient();

    /**
     * Json 序列化库
     * @return
     */
    Gson gson();

    void inject(AppDelegate delegate);

    @Component.Builder
    interface Builder {
        @BindsInstance
        AppComponent.Builder application(Application application);
        Builder globalConfigModule(GlobalConfigModule globalConfigModule);
        AppComponent build();
    }
}
