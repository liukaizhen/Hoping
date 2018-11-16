package com.hp.libcore.base;

import android.support.annotation.NonNull;

import com.hp.libcore.di.AppComponent;

/**
 * 统一规范
 */
public interface IApp {
    @NonNull
    AppComponent obtainAppComponent();
}
