package com.hp.libcore.helper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 处理RxJava异常的观察者
 * @param <T>
 */
public abstract class ErrorObserverHelper <T> implements Observer<T> {
    private IErrorHandler errorHandler;

    public ErrorObserverHelper(IErrorHandler errorHandler) {
        this.errorHandler = errorHandler;
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T t) {
    }

    @Override
    public void onError(Throwable e) {
        errorHandler.handleError(e);
    }

    @Override
    public void onComplete() {

    }
}
