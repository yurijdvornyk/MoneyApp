package com.example.yuriidvornyk.moneyapp.presentation.base;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

public abstract class BasePresenter<V extends BaseContract.View> implements BaseContract.Presenter<V> {

    protected V view;

    @Override
    public void attach(V view) {
        this.view = view;
        view.attachPresenter(this);
    }
}
