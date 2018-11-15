package com.hp.yujian;

import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hp.libcore.base.BaseActivity;

import butterknife.BindView;

@Route(path = "/test/activity")
public class MainActivity extends BaseActivity {
    @BindView(R.id.text_view)
    TextView textView;

    @Override
    protected int layoutID() {
        return R.layout.activity_main;
    }
}
