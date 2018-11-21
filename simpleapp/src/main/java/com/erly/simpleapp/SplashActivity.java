package com.erly.simpleapp;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hp.libcore.base.BaseActivity;

import butterknife.OnClick;

/**
 * 应用壳的启动页面
 * 展示跳转到业务模块的主页面
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected int layoutID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {

    }

    @OnClick({R.id.btn})
    public void click(View view){
        ARouter.getInstance().build("/simplemodule/activity").navigation();
    }
}
