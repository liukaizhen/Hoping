package com.hp.libcore.config;

import android.content.Context;

/**
 * 可以配置一些参数,需要实现 {@link IConfig} 后
 * 在 AndroidManifest Meta中声明该实现类
 */
public interface IConfig {
    void applyOptions(Context context, GlobalConfigModule.Builder builder);
}
