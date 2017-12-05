package com.example.yuriidvornyk.moneyapp.data.usecase.operation;

import com.example.yuriidvornyk.moneyapp.data.model.Operation;
import com.example.yuriidvornyk.moneyapp.data.repository.OperationRepository;
import com.example.yuriidvornyk.moneyapp.data.usecase.base.UseCase;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by yurii.dvornyk on 2017-11-29.
 */

public class AddOperation extends UseCase<Operation> {

    private OperationRepository repository;
    private Operation operation;

    public AddOperation(OperationRepository repository, Scheduler callerThread, Scheduler resultThread) {
        super(callerThread, resultThread);
        this.repository = repository;
    }

    public AddOperation operation(Operation operation) {
        this.operation = operation;
        return this;
    }

    @Override
    protected Flowable<Operation> buildUseCaseFlowable() {
        return repository.saveOperation(operation);
    }
}
