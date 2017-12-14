package com.example.yuriidvornyk.moneyapp.data.usecase.settings;

import com.example.yuriidvornyk.moneyapp.data.model.MoneyAppSettings;
import com.example.yuriidvornyk.moneyapp.data.repository.SettingsRepository;
import com.example.yuriidvornyk.moneyapp.data.usecase.base.UseCase;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by yurii.dvornyk on 2017-12-13.
 */

public class GetSettings extends UseCase<MoneyAppSettings> {

    private SettingsRepository repository;

    public GetSettings(SettingsRepository repository, Scheduler callerThread, Scheduler resultThread) {
        super(callerThread, resultThread);
        this.repository = repository;
    }

    @Override
    protected Flowable<MoneyAppSettings> buildUseCaseFlowable() {
        return repository.getSettings();
    }
}
