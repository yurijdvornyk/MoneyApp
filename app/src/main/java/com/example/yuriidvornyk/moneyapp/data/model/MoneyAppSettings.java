package com.example.yuriidvornyk.moneyapp.data.model;

/**
 * Created by yurii.dvornyk on 2017-12-13.
 */
public class MoneyAppSettings {

    private String defaultCurrency;

    private boolean autoSetCurrency;

    public String getDefaultCurrency() {
        return defaultCurrency;
    }

    public void setDefaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
    }

    public boolean isAutoSetCurrency() {
        return autoSetCurrency;
    }

    public void setAutoSetCurrency(boolean autoSetCurrency) {
        this.autoSetCurrency = autoSetCurrency;
    }
}
