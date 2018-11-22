package com.hp.libcore.base;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.multidex.MultiDexApplication;
import com.alibaba.android.arouter.launcher.ARouter;
import com.hp.libcore.AppDelegate;
import com.hp.libcore.BuildConfig;
import com.hp.libcore.di.AppComponent;
import com.hp.libcore.tools.PredictUtil;
import com.squareup.leakcanary.LeakCanary;

public class BaseApplication extends MultiDexApplication implements IApp{
    private AppDelegate mAppDelegate;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        if (mAppDelegate == null)
            this.mAppDelegate = new AppDelegate(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mAppDelegate != null)
            this.mAppDelegate.onCreate(this);
        initTodo();
    }

    /**
     * 初始化相关配置
     */
    private void initTodo() {
        //leakCanary初始化
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        //ARouter初始化
        if (BuildConfig.DEBUG){
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @NonNull
    @Override
    public AppComponent obtainAppComponent() {
        PredictUtil.checkNotNull(mAppDelegate, "%s cannot be null", AppDelegate.class.getName());
        PredictUtil.checkState(mAppDelegate instanceof IApp, "%s must be implements %s",
                mAppDelegate.getClass().getName(), IApp.class.getName());
        return  mAppDelegate.obtainAppComponent();
    }
}
