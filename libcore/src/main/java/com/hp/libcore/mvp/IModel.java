package com.hp.libcore.mvp;

/**
 * M层接口
 */
public interface IModel {
    /**
     * 释放对数据操作类的引用
     */
    void onDestroy();
}
