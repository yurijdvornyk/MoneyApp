package com.example.yuriidvornyk.moneyapp.presentation.settings;

import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.data.model.MoneyAppSettings;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseContract;

import java.util.List;

import io.reactivex.functions.Consumer;

/**
 * Created by yurii.dvornyk on 2017-12-12.
 */

interface SettingsContract {

    interface Presenter extends BaseContract.Presenter<View> {

        void start();

        void save(String defaultCurrency, boolean autoSetCurrency);
    }

    interface View extends BaseContract.View<Presenter> {

        void setSettings(MoneyAppSettings settings);

        void onSettingsSaved();

        void setCurrencies(List<Currency> currencies);
    }
}
