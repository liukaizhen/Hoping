package com.hp.libcore.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.rxlifecycle2.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends RxFragment {
    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(layoutID(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    /**
     * 返回当前页面布局
     * @return
     */
    protected abstract int layoutID();

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
