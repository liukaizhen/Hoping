package com.hp.yujian.main;

import com.hp.libcore.base.BaseResponse;
import com.hp.libcore.mvp.IModel;
import com.hp.libcore.mvp.IView;

import io.reactivex.Observable;

public interface MainContract {
    interface View extends IView{
        void showProgress();
        void cancelProgress();
        void callbackResult(String result);
    }

    interface Model extends IModel {
        Observable<BaseResponse<Object>> getHomeInfo();
    }

}
