package com.hp.libcore.base;

import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hp.libcore.BuildConfig;
import com.hp.libcore.tools.Util;

public class BaseApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Util.init(this);
        initTodo();
    }

    /**
     * 初始化相关配置
     */
    private void initTodo() {
        //ARouter初始化
        if (BuildConfig.DEBUG){
            ARouter.openDebug();
            ARouter.openLog();
        }
        ARouter.init(this);
    }
}
