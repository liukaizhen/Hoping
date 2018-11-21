package com.hp.libcore.helper;

import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;

/**
 * RxJava错误时重试
 * 被返回的Observable<?>所要发送的事件决定了重订阅是否会发生。
 * 如果发送的是onCompleted或者onError事件，将不会触发重订阅
 * 如果这个Observable发射了一项数据，它就重新订阅原来的数据源
 *
 * 而 onErrorResumeNext是重发了一个新的数据源(doOnError不能变换)
 */
public class RetryWithDelay implements Function<Observable<Throwable>, ObservableSource<?>> {
    private final int maxRetries;
    private final int retryDelaySecond;
    private int retryCount = 0;

    /**
     * 第一个参数为重试几次,
     * 第二个参数为重试的间隔(秒)
     * @param maxRetries
     * @param retryDelaySecond
     */
    public RetryWithDelay(int maxRetries, int retryDelaySecond) {
        this.maxRetries = maxRetries;
        this.retryDelaySecond = retryDelaySecond;
    }

    @Override
    public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
        return throwableObservable
                .flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {
                        if (++retryCount <= maxRetries) {
                            return Observable.timer(retryDelaySecond,
                                    TimeUnit.SECONDS);
                        }
                        return Observable.error(throwable);
                    }
                });
    }
}
