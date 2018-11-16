package com.hp.libcore.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hp.libcore.mvp.IPresenter;
import com.trello.rxlifecycle2.components.support.RxFragment;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends IPresenter> extends RxFragment {
    protected final String TAG = this.getClass().getSimpleName();
    private Unbinder unbinder;
    @Inject @Nullable
    protected P mPresenter;//当前页面逻辑简单,Presenter 可以为 null

    /**
     * 返回当前页面布局
     * @return
     */
    protected abstract int layoutID();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(layoutID(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
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
