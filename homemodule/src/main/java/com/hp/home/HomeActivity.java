package com.hp.home;

import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hp.libcore.base.BaseActivity;

import butterknife.OnClick;

public class HomeActivity extends BaseActivity {

    @Override
    protected int layoutID() {
        return R.layout.activity_home;
    }

    @OnClick({R2.id.btn})
    public void click(View view){
        ARouter.getInstance().build("/test/activity").navigation();
    }
}
