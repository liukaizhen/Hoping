package com.erly.simplemodule.simple;

import com.erly.simplemodule.pojo.JokeBean;
import com.hp.libcore.helper.ErrorObserver;
import com.hp.libcore.helper.GlobalErrorHandler;
import com.hp.libcore.helper.RetryWithDelay;
import com.hp.libcore.helper.RxLifecycleHelper;
import com.hp.libcore.mvp.BasePresenter;
import com.hp.libcore.tools.Utils;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SimplePresenter extends BasePresenter<SimpleContract.Model,SimpleContract.View> {
    private int initStatus = -1;

    public SimplePresenter(SimpleContract.View mView) {
        super(new SimpleModel(), mView);
    }

    public void getJokeInfo(){
        mModel.getJokeInfo(mView.returnPage())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (Utils.isNetConnect()){
                            if (initStatus < 0)
                                mView.showProgress();
                        }else {
                            Utils.toast("网络无连接...");
                            disposable.dispose();
                        }
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())//切换到主线程显示进度条
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        initStatus = mView.returnPage();
                        mView.cancelProgress();
                    }
                }).compose(RxLifecycleHelper.bindToLifecycle(mView))
                .subscribe(new ErrorObserver<JokeBean>(GlobalErrorHandler.getInstance()) {
                    @Override
                    public void onNext(JokeBean jokeBean) {
                        mView.callbackResultData(jokeBean);
                    }
                });
    }
}
