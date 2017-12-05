package com.example.yuriidvornyk.moneyapp.data.usecase.currency;

import com.example.yuriidvornyk.moneyapp.data.repository.CurrencyRateRepository;
import com.example.yuriidvornyk.moneyapp.data.usecase.base.UseCase;

import org.threeten.bp.LocalDateTime;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by yurii.dvornyk on 2017-12-05.
 */

public class LastRatesUpdateTime extends UseCase<LocalDateTime> {

    private CurrencyRateRepository repository;

    public LastRatesUpdateTime(CurrencyRateRepository repository, Scheduler callerThread, Scheduler resultThread) {
        super(callerThread, resultThread);
        this.repository = repository;
    }

    @Override
    protected Flowable<LocalDateTime> buildUseCaseFlowable() {
        return repository.getCurrencyRateUpdateTime();
    }
}
