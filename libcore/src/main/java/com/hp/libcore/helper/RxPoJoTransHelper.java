package com.hp.libcore.helper;

import com.hp.libcore.base.BasePoJo;
import com.hp.libcore.tools.Utils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * 通过compose操作符将Observable<BasePoJo<T>>
 * 转换成Observable<T> 若错误统一toast信息
 * @param <T>
 */
public class RxPoJoTransHelper<T>{
    private volatile static RxPoJoTransHelper instance;

    private RxPoJoTransHelper(){ }

    public static RxPoJoTransHelper getInstance(){
        if (instance == null) {
            synchronized (RxPoJoTransHelper.class) {
                if (instance == null) {
                    instance = new RxPoJoTransHelper();
                }
            }
        }
        return instance;
    }

    private final ObservableTransformer poJoTransformer =
            (ObservableTransformer<BasePoJo<T>, T>) upstream -> upstream.filter(basePoJo -> {
                //全局在这里显示错误信息
                if (!basePoJo.isSuccess()) Utils.toast(basePoJo.getMsg());
                return basePoJo.isSuccess();
            }).flatMap((Function<BasePoJo<T>, ObservableSource<T>>) basePoJo -> {
                return Observable.just(basePoJo.getData()); });

    public <R> ObservableTransformer<R, R> transformPoJo() {
        return (ObservableTransformer<R, R>) poJoTransformer;
    }

}
