package com.hp.libcore.helper;

import com.hp.libcore.tools.Utils;

/**
 * RxJava异常统一处理
 */
public class GlobalErrorHandler implements IErrorHandler {
    private volatile static GlobalErrorHandler instance;

    private GlobalErrorHandler(){ }

    public static GlobalErrorHandler getInstance(){
        if (instance == null) {
            synchronized (GlobalErrorHandler.class) {
                if (instance == null) {
                    instance = new GlobalErrorHandler();
                }
            }
        }
        return instance;
    }

    @Override
    public void handleError(Throwable throwable) {
        // TODO: 2018/11/19 统一处理异常
        Utils.toast(throwable.getMessage());
    }
}
