package com.erly.simplemodule.simple;

import com.erly.simplemodule.pojo.JokeBean;
import com.hp.libcore.base.BasePoJo;
import com.hp.libcore.helper.RetryWithDelay;
import com.hp.libcore.helper.RxLifecycleHelper;
import com.hp.libcore.mvp.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class SimplePresenter extends BasePresenter<SimpleContract.Model,SimpleContract.View> {

    public SimplePresenter(SimpleContract.View mView) {
        super(new SimpleModel(), mView);
    }

    public void getJokeInfo(){
        mModel.getJokeInfo(mView.returnPage())
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3,2))
//                .filter(new Predicate<BasePoJo<JokeBean>>() {
//                    @Override
//                    public boolean test(BasePoJo<JokeBean> jokeBeanBasePoJo) throws Exception {
//                        return jokeBeanBasePoJo.isSuccess();
//                    }
//                })
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())//切换到主线程显示进度条
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.cancelProgress();
                    }
                }).compose(RxLifecycleHelper.bindToLifecycle(mView))
                .subscribe(new Consumer<JokeBean>() {
                    @Override
                    public void accept(JokeBean jokeBean) throws Exception {
                        mView.callbackResultData(jokeBean);
                    }
                });
//                .subscribe(new Consumer<BasePoJo<JokeBean>>() {
//                    @Override
//                    public void accept(BasePoJo<JokeBean> jokeBeanBasePoJo) throws Exception {
//                        mView.callbackResultData(jokeBeanBasePoJo.getData());
//                    }
//                });

    }
}
