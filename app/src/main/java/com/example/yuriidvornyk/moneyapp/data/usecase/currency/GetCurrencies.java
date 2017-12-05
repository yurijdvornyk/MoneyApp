package com.example.yuriidvornyk.moneyapp.data.usecase.currency;

import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.data.repository.CurrencyRepository;
import com.example.yuriidvornyk.moneyapp.data.usecase.base.UseCase;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by yurii.dvornyk on 2017-11-28.
 */

public class GetCurrencies extends UseCase<List<Currency>> {

    private CurrencyRepository repository;

    public GetCurrencies(CurrencyRepository repository, Scheduler callerThread, Scheduler resultThread) {
        super(callerThread, resultThread);
        this.repository = repository;
    }

    @Override
    protected Flowable<List<Currency>> buildUseCaseFlowable() {
        return repository.getAllCurrencies();
    }
}
