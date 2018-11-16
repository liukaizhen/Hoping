package com.hp.libcore.http;

/**
 * 数据管理层
 */
public interface IRepositoryManager {

    /**
     * 根据传入的 Class 获取对应的 Retrofit Service
     * @param service
     * @param <T>
     * @return
     */
    <T> T obtainRetrofitService(Class<T> service);
}
