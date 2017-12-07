package com.example.yuriidvornyk.moneyapp.presentation.calculator;

import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetCurrencies;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetRate;
import com.example.yuriidvornyk.moneyapp.presentation.base.BasePresenter;

/**
 * Created by yurii.dvornyk on 2017-12-07.
 */

class CalculatorPresenter extends BasePresenter<CalculatorContract.View> implements CalculatorContract.Presenter {

    private GetRate getRate;
    private GetCurrencies getCurrencies;

    CalculatorPresenter(GetRate getRate, GetCurrencies getCurrencies) {
        this.getRate = getRate;
        this.getCurrencies = getCurrencies;
    }

    @Override
    public void detach() {
        getRate.unsubscribe();
    }

    @Override
    public void loadCurrencies() {
        getCurrencies.execute(currencies -> view.showCurrencies(currencies));
    }
}
