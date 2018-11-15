package com.hp.libcore.base;

/**
 * Presenter父接口
 * @param <V>页面回调可以为空
 */
public interface BasePresenter<V> {
    /**
     * 页面的绑定resumed
     * 和解除destroyed
     * @param view
     */
    void attachView(V view);
    void detachView();
}
