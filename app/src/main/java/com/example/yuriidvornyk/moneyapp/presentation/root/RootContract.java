package com.example.yuriidvornyk.moneyapp.presentation.root;

import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseContract;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

public interface RootContract {

    interface Presenter extends BaseContract.Presenter<View>{

        void start();
    }

    interface View extends BaseContract.View<Presenter> {

        void navigateToProjectDetails(Project project);

        void showCurrencyRatesUpdateError();

        void showCurrencyRatesUpdateSuccess();

        void checkLocationPermission();
    }
}
