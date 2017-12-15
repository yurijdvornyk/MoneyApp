package com.example.yuriidvornyk.moneyapp.presentation.root;

import com.example.yuriidvornyk.moneyapp.data.usecase.currency.LastRatesUpdateTime;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.LoadCurrencyRates;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.SaveSettings;
import com.example.yuriidvornyk.moneyapp.data.usecase.settings.GetSettings;
import com.example.yuriidvornyk.moneyapp.presentation.base.BasePresenter;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.Currency;
import java.util.Locale;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

class RootPresenter extends BasePresenter<RootContract.View> implements RootContract.Presenter {

    private static final int UPDATE_PERIOD_HOURS = 24;
    private LoadCurrencyRates loadCurrencyRates;
    private LastRatesUpdateTime lastRatesUpdateTime;
    private SaveSettings saveSettings;
    private GetSettings getSettings;

    RootPresenter(LastRatesUpdateTime lastRatesUpdateTime, LoadCurrencyRates loadCurrencyRates,
                  SaveSettings saveSettings, GetSettings getSettings) {
        this.lastRatesUpdateTime = lastRatesUpdateTime;
        this.loadCurrencyRates = loadCurrencyRates;
        this.saveSettings = saveSettings;
        this.getSettings = getSettings;
    }

    @Override
    public void detach() {
        loadCurrencyRates.unsubscribe();
        lastRatesUpdateTime.unsubscribe();
        getSettings.unsubscribe();
        saveSettings.unsubscribe();
    }

    @Override
    public void start() {
        getSettings.execute(settings -> {
            if (settings.isAutoSetCurrency()) {
                view.updateCurrency();
            } else if (settings.getDefaultCurrency().isEmpty()) {
                saveSettings
                        .defaultCurrency(Currency.getInstance(Locale.getDefault()).getCurrencyCode())
                        .execute(() -> {});
            }
        });
        lastRatesUpdateTime.execute(time -> {
            if (ChronoUnit.HOURS.between(time, LocalDateTime.now()) > UPDATE_PERIOD_HOURS) {
                loadRates();
            }
        }, throwable -> loadRates());
    }

    @Override
    public void onLocaleUpdated(Locale locale) {
        if (locale != null) {
            saveSettings.defaultCurrency(Currency.getInstance(locale).getCurrencyCode()).execute(() -> {});
        }
    }

    private void loadRates() {
        loadCurrencyRates.execute(
                rates -> view.showCurrencyRatesUpdateSuccess(),
                throwable -> view.showCurrencyRatesUpdateError());
    }
}
