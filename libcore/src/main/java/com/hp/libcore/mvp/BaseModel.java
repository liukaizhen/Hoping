package com.hp.libcore.mvp;

import com.hp.libcore.http.IRepositoryManager;

/**
 * M层基类
 * 具体Model层的父类
 */
public class BaseModel implements IModel{
    protected IRepositoryManager mRepository;

    public BaseModel(IRepositoryManager mRepository) {
        this.mRepository = mRepository;
    }

    @Override
    public void onDestroy() {
        mRepository = null;
    }
}
