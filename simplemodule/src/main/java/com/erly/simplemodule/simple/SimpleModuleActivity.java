package com.erly.simplemodule.simple;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.erly.simplemodule.R;
import com.erly.simplemodule.R2;
import com.erly.simplemodule.pojo.JokeBean;
import com.hp.libcore.base.BaseActivity;

import butterknife.BindView;

/**
 * 业务模块的主页面
 */
@Route(path = "/simplemodule/activity")
public class SimpleModuleActivity extends BaseActivity<SimplePresenter> implements SimpleContract.View {
    @BindView(R2.id.text)
    TextView textView;

    private ProgressDialog progressDialog;
    //默认请求第一页数据
    private int page = 0;

    @Override
    protected int layoutID() {
        return R.layout.activity_simple_module;
    }

    @Override
    protected SimplePresenter createPresenter() {
        return new SimplePresenter(this);
    }

    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {
        mPresenter.getJokeInfo();
    }

    @Override
    public int returnPage() {
        return page;
    }

    @Override
    public void showProgress() {
        if (progressDialog == null)
            progressDialog = new ProgressDialog(this);
        progressDialog.show();
    }

    @Override
    public void cancelProgress() {
        if (progressDialog != null)
            progressDialog.cancel();
    }

    @Override
    public void callbackResultData(JokeBean jokeBean) {
        textView.setText(jokeBean.toString());
    }
}
