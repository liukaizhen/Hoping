package com.erly.simpleapp;

import android.content.Context;

import com.hp.libcore.config.GlobalConfigModule;
import com.hp.libcore.config.IConfig;
import com.hp.libcore.http.log.LoggerInterceptor;

/**
 * SimpleApplication下的全局配置
 * 需要在AndroidManifest.xml Meta标签中加入该类的全引用
 * <meta-data android:name="com.erly.simpleapp.SimpleConfig"
 *             android:value="globalConfig" />
 */
public class SimpleConfig implements IConfig {
    @Override
    public void applyOptions(Context context, GlobalConfigModule.Builder builder) {
        builder.baseUrl("http://v.juhe.cn/")
                .printHttpLogLevel(LoggerInterceptor.Level.ALL);
    }
}
