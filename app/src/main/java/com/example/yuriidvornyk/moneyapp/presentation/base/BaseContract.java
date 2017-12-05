package com.example.yuriidvornyk.moneyapp.presentation.base;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

public interface BaseContract {

    interface Presenter<V extends View> {

        void attach(V view);

        void detach();
    }

    interface View<P extends Presenter> {

        void attachPresenter(P presenter);
    }
}
