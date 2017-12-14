package com.example.yuriidvornyk.moneyapp.data.usecase.currency;

import com.example.yuriidvornyk.moneyapp.data.repository.CurrencyRepository;
import com.example.yuriidvornyk.moneyapp.data.repository.SettingsRepository;
import com.example.yuriidvornyk.moneyapp.data.usecase.base.UseCase;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by yurii.dvornyk on 2017-12-12.
 */

public class SaveSettings extends UseCase<Integer> {

    private SettingsRepository repository;
    private String defaultCurrency;
    private Boolean autoSetCurrency;

    public SaveSettings(SettingsRepository repository, Scheduler callerThread, Scheduler resultThread) {
        super(callerThread, resultThread);
        this.repository = repository;
    }

    public SaveSettings defaultCurrency(String defaultCurrency) {
        this.defaultCurrency = defaultCurrency;
        return this;
    }

    public SaveSettings autoSetCurrency(Boolean autoSetCurrency) {
        this.autoSetCurrency = autoSetCurrency;
        return this;
    }

    @Override
    protected Flowable<Integer> buildUseCaseFlowable() {
        return Flowable.fromCallable(() -> {
            repository.saveDefaultCurrency(defaultCurrency != null ? defaultCurrency : repository.getDefaultCurrency());
            repository.saveAutoSetCurrency(autoSetCurrency != null ? autoSetCurrency : repository.isAutoSetCurrency());
            return 0;
        });
    }
}
