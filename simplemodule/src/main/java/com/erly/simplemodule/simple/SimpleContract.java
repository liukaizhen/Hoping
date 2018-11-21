package com.erly.simplemodule.simple;

import com.erly.simplemodule.pojo.JokeBean;
import com.hp.libcore.base.BasePoJo;
import com.hp.libcore.mvp.IModel;
import com.hp.libcore.mvp.IView;

import io.reactivex.Observable;

public interface SimpleContract {
    interface View extends IView{
        int returnPage();
        void showProgress();
        void cancelProgress();
        void callbackResultData(JokeBean jokeBean);
    }

    interface Model extends IModel{
        Observable<JokeBean> getJokeInfo(int page);
    }
}
