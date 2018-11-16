package com.hp.libcore.mvp;

/**
 * P层父接口
 */
public interface IPresenter {
    /**
     * 做一些初始化操作
     * eg:eventBus的绑定
     */
    void onStart();

    /**
     * 做一些释放操作 会在页面onDestroy中调用
     * eg:eventBus的解绑
     */
    void onDestroy();
}
