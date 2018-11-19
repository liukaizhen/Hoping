package com.hp.yujian.main;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.hp.libcore.base.BaseActivity;
import com.hp.yujian.R;
import butterknife.BindView;

@Route(path = "/test/activity")
public class MainActivity extends BaseActivity<MainPresenter> implements MainContract.View {
    @BindView(R.id.text_view)
    TextView textView;

    @Override
    protected int layoutID() {
        return R.layout.activity_main;
    }

    @Override
    protected MainPresenter createPresenter() {
        return new MainPresenter(this);
    }
    @Override
    protected void initialize(@Nullable Bundle savedInstanceState) {
        mPresenter.getHomeData();
    }


    ProgressDialog progressDialog;
    @Override
    public void showProgress() {
        if (progressDialog == null){
            progressDialog = new ProgressDialog(this);
        }
        progressDialog.show();
    }

    @Override
    public void cancelProgress() {
        if (progressDialog != null){
            progressDialog.cancel();
        }
    }

    @Override
    public void callbackResult(String result) {
        textView.setText(result);
    }
}
