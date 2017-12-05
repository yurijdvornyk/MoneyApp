package com.example.yuriidvornyk.moneyapp.presentation.root;

import com.example.yuriidvornyk.moneyapp.data.usecase.currency.LastRatesUpdateTime;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.LoadCurrencyRates;
import com.example.yuriidvornyk.moneyapp.presentation.base.BasePresenter;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.temporal.ChronoUnit;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

class RootPresenter extends BasePresenter<RootContract.View> implements RootContract.Presenter {

    private static final int UPDATE_PERIOD_HOURS = 24;
    private LoadCurrencyRates loadCurrencyRates;
    private LastRatesUpdateTime lastRatesUpdateTime;

    RootPresenter(LastRatesUpdateTime lastRatesUpdateTime, LoadCurrencyRates loadCurrencyRates) {
        this.lastRatesUpdateTime = lastRatesUpdateTime;
        this.loadCurrencyRates = loadCurrencyRates;
    }

    @Override
    public void detach() {
        loadCurrencyRates.unsubscribe();
        lastRatesUpdateTime.unsubscribe();
    }

    @Override
    public void loadRatesIfNeeded() {
        lastRatesUpdateTime.execute(time -> {
            if (ChronoUnit.HOURS.between(time, LocalDateTime.now()) > UPDATE_PERIOD_HOURS) {
                loadRates();
            }
        }, throwable -> loadRates());
    }

    private void loadRates() {
        loadCurrencyRates.execute(
                rates -> view.showCurrencyRatesUpdateSuccess(),
                throwable -> view.showCurrencyRatesUpdateError());
    }
}
