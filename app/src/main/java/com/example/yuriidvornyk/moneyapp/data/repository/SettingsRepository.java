package com.example.yuriidvornyk.moneyapp.data.repository;

import com.example.yuriidvornyk.moneyapp.MoneyApplication;
import com.example.yuriidvornyk.moneyapp.data.model.MoneyAppSettings;

import io.reactivex.Flowable;

/**
 * Created by yurii.dvornyk on 2017-12-13.
 */

public class SettingsRepository {

    private static final String DEFAULT_CURRENCY = "DEFAULT_CURRENCY";
    private static final String AUTO_SET_CURRENCY = "AUTO_SET_CURRENCY";

    public String getDefaultCurrency() {
        return MoneyApplication.getDefaultSharedPreferences().getString(DEFAULT_CURRENCY, "");
    }

    public void saveDefaultCurrency(String currencyCode) {
        MoneyApplication.getDefaultSharedPreferences().edit().putString(DEFAULT_CURRENCY, currencyCode).apply();
    }

    public boolean isAutoSetCurrency() {
        return MoneyApplication.getDefaultSharedPreferences().getBoolean(AUTO_SET_CURRENCY, false);
    }

    public void saveAutoSetCurrency(boolean autoSetCurrency) {
        MoneyApplication.getDefaultSharedPreferences().edit().putBoolean(AUTO_SET_CURRENCY, autoSetCurrency).apply();
    }

    public Flowable<MoneyAppSettings> getSettings() {
        final MoneyAppSettings settings = new MoneyAppSettings();
        settings.setDefaultCurrency(getDefaultCurrency());
        settings.setAutoSetCurrency(isAutoSetCurrency());
        return Flowable.just(settings);
    }
}
