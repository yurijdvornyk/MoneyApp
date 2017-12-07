package com.example.yuriidvornyk.moneyapp.presentation.calculator;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.presentation.projects.CurrencyAdapter;

import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-12-07.
 */

class CurrencyShortAdapter extends CurrencyAdapter {

    CurrencyShortAdapter(@NonNull Context context, int resource, @NonNull List<Currency> objects) {
        super(context, resource, objects);
    }

    @Override
    protected String formatItemContent(Currency currency) {
        return currency.getCode();
    }
}
