package com.example.yuriidvornyk.moneyapp.utils;

import com.example.yuriidvornyk.moneyapp.data.model.Currency;

/**
 * Created by yurii.dvornyk on 2017-11-28.
 */

public class CsvUtils {

    private static final String CSV_SEPARATOR = ",";

    public static Currency getCurrencyFromCsv(String csv) {
        final Currency currency = new Currency();
        final String[] data = csv.split(CSV_SEPARATOR);
        currency.setCode(data[0]);
        currency.setName(data[1]);
        return currency;
    }
}
