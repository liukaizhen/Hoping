package com.hp.libcore.helper;

import com.hp.libcore.mvp.IView;
import com.hp.libcore.tools.PredictUtil;
import com.trello.rxlifecycle2.LifecycleTransformer;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle2.components.support.RxFragment;
import io.reactivex.annotations.NonNull;

/**
 * RxLifecycle绑定帮助类
 * 在subscribeOn之后使用
 */
public class RxLifecycleHelper {
    private RxLifecycleHelper() {
        throw new IllegalStateException("u can't instantiate me!");
    }

    /**
     * 绑定到Activity/Fragment
     * 整个生命周期
     * @param view
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindToLifecycle(@NonNull IView view) {
        PredictUtil.checkNotNull(view, "view == null");
        if (view instanceof RxAppCompatActivity) {
            return ((RxAppCompatActivity) view).bindToLifecycle();
        } else if (view instanceof RxFragment) {
            return ((RxFragment) view).bindToLifecycle();
        } else {
            throw new IllegalArgumentException("view isn't RxAppCompatActivity or RxFragment");
        }
    }

    /**
     * 绑定Activity指定生命周期
     * @param view
     * @param event
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindActivityEvent(@NonNull IView view,final ActivityEvent event) {
        PredictUtil.checkNotNull(view, "view == null");
        if (view instanceof RxAppCompatActivity) {
            return ((RxAppCompatActivity) view).bindUntilEvent(event);
        } else {
            throw new IllegalArgumentException("view isn't RxAppCompatActivity");
        }
    }

    /**
     * 绑定Fragment指定生命周期
     * @param view
     * @param event
     * @param <T>
     * @return
     */
    public static <T> LifecycleTransformer<T> bindFragmentEvent(@NonNull IView view,final FragmentEvent event) {
        PredictUtil.checkNotNull(view, "view == null");
        if (view instanceof RxFragment) {
            return ((RxFragment) view).bindUntilEvent(event);
        } else {
            throw new IllegalArgumentException("view isn't RxFragment");
        }
    }

}
