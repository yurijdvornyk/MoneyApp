package com.example.yuriidvornyk.moneyapp.data.usecase.projects;

import android.support.v4.util.Pair;

import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.data.repository.OperationRepository;
import com.example.yuriidvornyk.moneyapp.data.usecase.base.UseCase;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetRate;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by yurii.dvornyk on 2017-11-24.
 */

public class GetBalance extends UseCase<Pair<Project, Double>> {

    private OperationRepository repository;
    private GetRate getRate;
    private Project project;

    public GetBalance(OperationRepository repository, GetRate getRate, Scheduler callerThread, Scheduler resultThread) {
        super(callerThread, resultThread);
        this.repository = repository;
        this.getRate = getRate;
    }

    public GetBalance project(Project project) {
        this.project = project;
        return this;
    }

    @Override
    protected Flowable<Pair<Project, Double>> buildUseCaseFlowable() {
        return repository.getOperationsForProject(project.getId())
                .flatMapIterable(list -> list)
                .flatMap(operation -> getRate.
                        currencies(operation.getCurrency(), project.getDefaultCurrency())
                        .amount(operation.getAmount())
                        .getRawFlowable())
                .toList()
                .map(doubles -> {
                    double result = 0.0;
                    for (Double amount : doubles) {
                        result += amount;
                    }
                    return Pair.create(project, result);
                })
                .toFlowable();
    }
}
