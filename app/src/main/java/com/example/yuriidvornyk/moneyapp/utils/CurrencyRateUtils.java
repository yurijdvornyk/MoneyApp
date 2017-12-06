package com.example.yuriidvornyk.moneyapp.utils;

import com.example.yuriidvornyk.moneyapp.data.local.DbCurrencyRate;

/**
 * Created by yurii.dvornyk on 2017-12-06.
 */

public class CurrencyRateUtils {

    public static DbCurrencyRate getSingleRate(String currency) {
        final DbCurrencyRate rate = new DbCurrencyRate();
        rate.setCurrencyFrom(currency);
        rate.setCurrencyTo(currency);
        rate.setRate(1.0);
        return rate;
    }
}
