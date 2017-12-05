package com.example.yuriidvornyk.moneyapp.presentation.root;

import com.example.yuriidvornyk.moneyapp.data.usecase.currency.LoadCurrencyRates;
import com.example.yuriidvornyk.moneyapp.presentation.base.BasePresenter;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

class RootPresenter extends BasePresenter<RootContract.View> implements RootContract.Presenter {

    private LoadCurrencyRates loadCurrencyRates;

    RootPresenter(LoadCurrencyRates loadCurrencyRates) {
        this.loadCurrencyRates = loadCurrencyRates;
    }

    @Override
    public void detach() {
        loadCurrencyRates.unsubscribe();
    }

    @Override
    public void loadCurrencyRates() {
        loadCurrencyRates.execute(rates -> {
            view.showCurrencyRatesUpdateSuccess();
        }, throwable -> view.showCurrencyRatesUpdateError());
    }
}
