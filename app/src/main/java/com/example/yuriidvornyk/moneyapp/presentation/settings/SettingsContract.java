package com.example.yuriidvornyk.moneyapp.presentation.settings;

import com.example.yuriidvornyk.moneyapp.presentation.base.BaseContract;

/**
 * Created by yurii.dvornyk on 2017-12-12.
 */

interface SettingsContract {

    interface Presenter extends BaseContract.Presenter<View> {
    }

    interface View extends BaseContract.View<Presenter> {
    }
}
