package com.hp.libcore.base;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hp.libcore.mvp.IPresenter;
import com.trello.rxlifecycle2.components.support.RxFragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends IPresenter> extends RxFragment {
    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder unbinder;
    @Nullable
    protected P mPresenter;//当前页面逻辑简单,Presenter 可以为 null

    /**
     * 返回当前页面布局
     * @return
     */
    protected abstract int layoutID();

    /**
     * 初始化
     * @param savedInstanceState
     */
    protected abstract void initialize(@Nullable Bundle savedInstanceState);

    /**
     * 子页面覆盖该方法创建Presenter
     * 如果页面简单不需要Presenter可以不覆盖
     * @return
     */
    protected P createPresenter(){
        return null;
    };

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(layoutID(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        mPresenter = createPresenter();
        initialize(savedInstanceState);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        if (mPresenter != null){
            mPresenter.onDestroy();//释放资源
        }
        this.mPresenter = null;
    }

    /**
     * 同意权限
     * 在Fragment中请求权限时
     * 在宿主Activity中回调该方法
     * @param permissions
     */
    protected void onPermissionsGranted(int reqCode, String[] permissions) { }
}
