package com.hp.yujian.main;

import com.hp.libcore.base.BaseResponse;
import com.hp.libcore.helper.ErrorHandleHelper;
import com.hp.libcore.helper.ErrorObserverHelper;
import com.hp.libcore.helper.RetryWithDelay;
import com.hp.libcore.helper.RxLifecycleHelper;
import com.hp.libcore.mvp.BasePresenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainContract.Model,MainContract.View> {

    public MainPresenter(MainContract.View mView) {
        super(new MainModel(), mView);
    }

    /**
     * 获取主页数据
     */
    public void getHomeData(){
        mModel.getHomeInfo()
                .subscribeOn(Schedulers.io())
                .retryWhen(new RetryWithDelay(3, 2))
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        mView.showProgress();
                    }
                }).subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        mView.cancelProgress();
                    }
                })
                .compose(RxLifecycleHelper.bindToLifecycle(mView))
                .subscribe(new ErrorObserverHelper<BaseResponse<Object>>(ErrorHandleHelper.getInstance()){
                    @Override
                    public void onNext(BaseResponse<Object> objectBaseResponse) {
                        String s = objectBaseResponse.toString();
                        mView.callbackResult(objectBaseResponse.toString());
                    }
                });

    }
}
