package com.example.yuriidvornyk.moneyapp.presentation.calculator;

import android.util.Log;

import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetCurrencies;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetRate;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.LastRatesUpdateTime;
import com.example.yuriidvornyk.moneyapp.presentation.base.BasePresenter;

/**
 * Created by yurii.dvornyk on 2017-12-07.
 */

class CalculatorPresenter extends BasePresenter<CalculatorContract.View> implements CalculatorContract.Presenter {

    private static final String TAG = CalculatorPresenter.class.getSimpleName();

    private GetRate getRate;
    private GetCurrencies getCurrencies;
    private LastRatesUpdateTime lastRatesUpdateTime;

    CalculatorPresenter(GetRate getRate, GetCurrencies getCurrencies, LastRatesUpdateTime lastRatesUpdateTime) {
        this.getRate = getRate;
        this.getCurrencies = getCurrencies;
        this.lastRatesUpdateTime = lastRatesUpdateTime;
    }

    @Override
    public void detach() {
        getRate.unsubscribe();
    }

    @Override
    public void loadCurrencies() {
        getCurrencies.execute(currencies -> view.showCurrencies(currencies));
    }

    @Override
    public void onContentChanged(String currencyFrom, String currencyTo, String content,
                                 CalculatorContract.WhichEditText editText) {
        try {
            getRate
                    .currencies(currencyFrom, currencyTo)
                    .amount(Double.parseDouble(content))
                    .execute(amount -> {
                        if (editText == CalculatorContract.WhichEditText.FROM) {
                            view.setResultTo(amount);
                        } else {
                            view.setResultFrom(amount);
                        }
                    });
        } catch (NumberFormatException e) {
            Log.e(TAG, e.getMessage());
        }
    }

    @Override
    public void loadLastUpdateTime() {
        lastRatesUpdateTime.execute(
                time -> view.showLastUpdateTime(time),
                throwable -> view.showLastUpdateTime(null)
        );
    }
}
