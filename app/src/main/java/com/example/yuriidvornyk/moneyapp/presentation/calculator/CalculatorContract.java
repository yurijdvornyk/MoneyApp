package com.example.yuriidvornyk.moneyapp.presentation.calculator;

import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseContract;

import org.threeten.bp.LocalDateTime;

import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-12-07.
 */

interface CalculatorContract {

    interface Presenter extends BaseContract.Presenter<View> {

        void loadCurrencies();

        void onContentChanged(String currencyFrom, String currencyTo, String content, WhichEditText editText);

        void loadLastUpdateTime();
    }

    interface View extends BaseContract.View<Presenter> {

        void showCurrencies(List<Currency> currencies);

        void setResultTo(double amount);

        void setResultFrom(double amount);

        void showLastUpdateTime(LocalDateTime time);
    }

    enum WhichEditText {
        FROM,
        TO
    }
}
