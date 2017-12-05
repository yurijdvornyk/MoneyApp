package com.example.yuriidvornyk.moneyapp.presentation.base;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

public abstract class BaseActivity<P extends BaseContract.Presenter> extends AppCompatActivity implements BaseContract.View<P> {

    protected P presenter;

    @Override
    public void attachPresenter(P presenter) {
        this.presenter = presenter;
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detach();
    }
}
