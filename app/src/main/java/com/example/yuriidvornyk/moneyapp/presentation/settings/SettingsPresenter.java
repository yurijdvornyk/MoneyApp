package com.example.yuriidvornyk.moneyapp.presentation.settings;

import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetCurrencies;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.SaveSettings;
import com.example.yuriidvornyk.moneyapp.data.usecase.settings.GetSettings;
import com.example.yuriidvornyk.moneyapp.presentation.base.BasePresenter;

import io.reactivex.functions.Action;

/**
 * Created by yurii.dvornyk on 2017-12-12.
 */

class SettingsPresenter extends BasePresenter<SettingsContract.View> implements SettingsContract.Presenter {

    private GetSettings getSettings;
    private SaveSettings saveSettings;
    private GetCurrencies getCurrencies;

    SettingsPresenter(GetSettings getSettings, SaveSettings saveSettings, GetCurrencies getCurrencies) {
        this.getSettings = getSettings;
        this.saveSettings = saveSettings;
        this.getCurrencies = getCurrencies;
    }

    @Override
    public void detach() {
        getSettings.unsubscribe();
        saveSettings.unsubscribe();
    }

    @Override
    public void start() {
        getCurrencies.execute(currencies -> {
            view.setCurrencies(currencies);
            getSettings.execute(settings -> view.setSettings(settings));
        });
    }

    @Override
    public void save(String defaultCurrency, boolean autoSetCurrency) {
        saveSettings
                .defaultCurrency(defaultCurrency)
                .autoSetCurrency(autoSetCurrency)
                .execute(() -> view.onSettingsSaved());
    }
}
