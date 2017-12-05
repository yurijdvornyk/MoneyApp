package com.example.yuriidvornyk.moneyapp.data.usecase.currency;

import com.example.yuriidvornyk.moneyapp.data.repository.CurrencyRateRepository;
import com.example.yuriidvornyk.moneyapp.data.usecase.base.UseCase;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by yurii.dvornyk on 2017-11-30.
 */

public class GetRate extends UseCase<Double> {

    private CurrencyRateRepository repository;
    private String from;
    private String to;
    private double amount;

    public GetRate(CurrencyRateRepository repository, Scheduler callerThread, Scheduler resultThread) {
        super(callerThread, resultThread);
        this.repository = repository;
        amount = 1.0;
    }

    public GetRate currencies(String from, String to) {
        this.from = from;
        this.to = to;
        return this;
    }

    public GetRate amount(double amount) {
        this.amount = amount;
        return this;
    }

    @Override
    protected Flowable<Double> buildUseCaseFlowable() {
        return repository.getRate(from, to).map(currencyRate -> {
            return amount * currencyRate.getRate();
        });
    }
}
