package com.example.yuriidvornyk.moneyapp.data.repository;

import android.util.Log;

import com.example.yuriidvornyk.moneyapp.MoneyApplication;
import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.utils.CsvUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import io.reactivex.Flowable;

/**
 * Created by yurii.dvornyk on 2017-11-24.
 */

public class CurrencyRepository {

    private static String TAG = CurrencyRepository.class.getSimpleName();

    private List<Currency> currencies;

    public CurrencyRepository() {
        currencies = new ArrayList<>();
    }

    public Flowable<List<Currency>> getAllCurrencies() {
        if (currencies.isEmpty()) {
            readCurrencies();
        }
        return Flowable.just(currencies);
    }

    private void readCurrencies() {
        try {
            final BufferedReader reader = new BufferedReader(new InputStreamReader(
                    MoneyApplication.getAppContext().getAssets().open("currencies.csv")), 8192);
            String line;
            while ((line = reader.readLine()) != null) {
                currencies.add(CsvUtils.getCurrencyFromCsv(line));
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
