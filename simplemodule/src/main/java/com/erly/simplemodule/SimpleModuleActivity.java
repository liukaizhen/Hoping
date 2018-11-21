package com.erly.simplemodule;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hp.libcore.base.BaseActivity;

/**
 * 业务模块的主页面
 */
@Route(path = "/simplemodule/activity")
public class SimpleModuleActivity extends BaseActivity {

    @Override
    protected int layoutID() {
        return R.layout.activity_simple_module;
    }

    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {
        // TODO: 2018/11/21
    }

}
