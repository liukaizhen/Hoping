package com.erly.simplemodule.simple;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.erly.simplemodule.R;
import com.erly.simplemodule.R2;
import com.erly.simplemodule.pojo.JokeBean;
import com.hp.libcore.base.BaseActivity;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import butterknife.BindView;

/**
 * 业务模块的主页面
 */
@Route(path = "/simplemodule/activity")
public class SimpleModuleActivity extends BaseActivity<SimplePresenter> implements SimpleContract.View {
    @BindView(R2.id.smart)
    SmartRefreshLayout refreshLayout;
    @BindView(R2.id.recycler)
    RecyclerView recyclerView;
    private ProgressDialog progressDialog;
    private SimpleAdapter adapter;
    //默认请求第一页数据
    private int page = 1;

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
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new SimpleAdapter(this);
        recyclerView.setAdapter(adapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                page = 1;
                mPresenter.getJokeInfo();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                page++;
                mPresenter.getJokeInfo();
            }
        });
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
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.cancel();
    }

    @Override
    public void callbackResultData(JokeBean jokeBean) {
        if (page == 1){
            adapter.setItems(jokeBean.getJokeList());
            refreshLayout.finishRefresh();
        }else {
            adapter.loadMore(jokeBean.getJokeList());
            if (jokeBean.getJokeList().size() < 10){
                refreshLayout.finishLoadMoreWithNoMoreData();
            }else {
                refreshLayout.finishLoadMore();
            }
        }
    }
}
