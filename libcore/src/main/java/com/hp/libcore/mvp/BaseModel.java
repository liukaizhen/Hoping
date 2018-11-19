package com.hp.libcore.mvp;

import com.hp.libcore.http.IRepositoryManager;
import com.hp.libcore.tools.Utils;

/**
 * M层基类
 * 具体Model层继承该父类
 * 实现自己定义的接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
 */
public class BaseModel implements IModel{
    protected IRepositoryManager mRepository;

    public BaseModel() {
        this.mRepository = Utils.obtainAppComponent().repository();
    }

    @Override
    public void onDestroy() {
        mRepository = null;
    }
}
