package com.hp.libcore.helper;

import timber.log.Timber;

/**
 * RxJava异常处理默认实现帮助类
 */
public class ErrorHandleHelper implements IErrorHandler {
    private volatile static ErrorHandleHelper instance;

    private ErrorHandleHelper(){ }

    public static ErrorHandleHelper getInstance(){
        if (instance == null) {
            synchronized (ErrorHandleHelper.class) {
                if (instance == null) {
                    instance = new ErrorHandleHelper();
                }
            }
        }
        return instance;
    }

    @Override
    public void handleError(Throwable throwable) {
        // TODO: 2018/11/19 统一处理异常
        Timber.e(throwable.getMessage());
    }
}
