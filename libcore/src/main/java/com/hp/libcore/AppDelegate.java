package com.hp.libcore;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.hp.libcore.base.IApp;
import com.hp.libcore.config.GlobalConfigModule;
import com.hp.libcore.config.IConfig;
import com.hp.libcore.config.ManifestParser;
import com.hp.libcore.di.AppComponent;
import com.hp.libcore.di.DaggerAppComponent;
import com.hp.libcore.tools.PredictUtil;

import java.util.List;

/**
 * AppDelegate 可以代理 Application 的生命周期,在对应的生命周期,执行对应的逻辑,因为 Java 只能单继承
 * 所以当遇到某些三方库需要继承于它的 Application 的时候,就只有自定义 Application 并继承于三方库的 Application
 * 这时就不用再继承 BaseApplication,只用在自定义Application中对应的生命周期调用AppDelegate对应的方法
 * (Application一定要实现IApp接口),框架就能照常运行
 */
public final class AppDelegate implements IApp {
    private Application mApplication;
    private AppComponent mAppComponent;
    private List<IConfig> mConfigs;

    public AppDelegate(@NonNull Context context){
        this.mConfigs = new ManifestParser(context).parse();
    }

    public void onCreate(@NonNull Application application){
        this.mApplication = application;
        mAppComponent = DaggerAppComponent
                .builder()
                .application(mApplication)//提供application
                .globalConfigModule(getGlobalConfigModule(mApplication, mConfigs))//全局配置
                .build();
        mAppComponent.inject(this);
        this.mConfigs = null;
    }

    /**
     * 将app的全局配置信息封装进module(使用Dagger注入到需要配置信息的地方)
     * 需要在AndroidManifest中声明{@link IConfig}的实现类,和Glide的配置方式相似
     *
     * @return GlobalConfigModule
     */
    private GlobalConfigModule getGlobalConfigModule(Context context, List<IConfig> modules) {

        GlobalConfigModule.Builder builder = GlobalConfigModule
                .builder();

        //遍历 ConfigModule 集合, 给全局配置 GlobalConfigModule 添加参数
        for (IConfig module : modules) {
            module.applyOptions(context, builder);
        }

        return builder.build();
    }

    @NonNull
    @Override
    public AppComponent obtainAppComponent() {
        PredictUtil.checkNotNull(mAppComponent,
                "%s == null, first call %s#onCreate(Application) in %s#onCreate()",
                AppComponent.class.getName(), getClass().getName(), mApplication == null
                        ? Application.class.getName() : mApplication.getClass().getName());
        return mAppComponent;
    }
}
