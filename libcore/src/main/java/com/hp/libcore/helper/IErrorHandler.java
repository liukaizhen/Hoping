package com.hp.libcore.helper;

/**
 * 统一处理RxJava异常
 */
public interface IErrorHandler {
    void handleError(Throwable throwable);
}
