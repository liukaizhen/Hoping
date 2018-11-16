package com.hp.libcore.mvp;


import com.hp.libcore.tools.PredictUtil;

/**
 * Presenter基类
 * @param <M>
 * @param <V>
 */
public class BasePresenter<M extends IModel, V extends IView> implements IPresenter{
    protected final String TAG = this.getClass().getSimpleName();
    protected M mModel;
    protected V mView;

    /**
     * 如果当前页面同时需要 Model 层和 View 层,则使用此构造函数(默认)
     * @param model
     * @param mView
     */
    public BasePresenter(M model, V mView) {
        PredictUtil.checkNotNull(model, "%s cannot be null", IModel.class.getName());
        PredictUtil.checkNotNull(mView, "%s cannot be null", IView.class.getName());
        this.mModel = model;
        this.mView = mView;
        onStart();
    }

    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
     * @param mView
     */
    public BasePresenter(V mView) {
        PredictUtil.checkNotNull(mView, "%s cannot be null", IView.class.getName());
        this.mView = mView;
        onStart();
    }

    /**
     * 页面特别简单不需要Model层和View层,则使用此构造函数
     */
    public BasePresenter() {
        onStart();
    }

    @Override
    public void onStart() {
        // TODO: 2018/11/16
    }

    @Override
    public void onDestroy() {
        if (mModel != null){
            mModel.onDestroy();
        }
        this.mModel = null;
        this.mView = null;
    }
}
