package com.example.yuriidvornyk.moneyapp.presentation.base;

import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by yurii.dvornyk on 2017-11-28.
 */

public class BaseFragmentDialog<P extends BaseContract.Presenter> extends DialogFragment implements BaseContract.View<P> {

    protected P presenter;

    @Override
    public void onStart() {
        super.onStart();
        presenter.attach(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.detach();
    }

    @Override
    public void attachPresenter(P presenter) {
        this.presenter = presenter;
    }

    protected AppCompatActivity getAppCompatActivity() {
        return (AppCompatActivity) getActivity();
    }
}
