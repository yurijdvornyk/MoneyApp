package com.example.yuriidvornyk.moneyapp.data.usecase.operation;

import com.example.yuriidvornyk.moneyapp.data.model.Operation;
import com.example.yuriidvornyk.moneyapp.data.repository.OperationRepository;
import com.example.yuriidvornyk.moneyapp.data.usecase.base.UseCase;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by yurii.dvornyk on 2017-11-29.
 */

public class GetOperations extends UseCase<List<Operation>> {

    private OperationRepository repository;
    private int projectId;

    public GetOperations(OperationRepository repository, Scheduler callerThread, Scheduler resultThread) {
        super(callerThread, resultThread);
        this.repository = repository;
    }

    public GetOperations projectId(int projectId) {
        this.projectId = projectId;
        return this;
    }

    @Override
    protected Flowable<List<Operation>> buildUseCaseFlowable() {
        return repository.getOperationsForProject(projectId);
    }
}
