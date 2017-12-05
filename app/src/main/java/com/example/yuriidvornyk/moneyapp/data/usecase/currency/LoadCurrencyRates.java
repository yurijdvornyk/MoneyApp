package com.example.yuriidvornyk.moneyapp.data.usecase.currency;

import com.example.yuriidvornyk.moneyapp.data.model.CurrencyRate;
import com.example.yuriidvornyk.moneyapp.data.repository.CurrencyRateRepository;
import com.example.yuriidvornyk.moneyapp.data.usecase.base.UseCase;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by yurii.dvornyk on 2017-12-05.
 */

public class LoadCurrencyRates extends UseCase<List<CurrencyRate>> {

    private CurrencyRateRepository repository;

    public LoadCurrencyRates(CurrencyRateRepository repository, Scheduler callerThread, Scheduler resultThread) {
        super(callerThread, resultThread);
        this.repository = repository;
    }

    @Override
    protected Flowable<List<CurrencyRate>> buildUseCaseFlowable() {
        return repository.updateCurrencyRates();
    }
}
