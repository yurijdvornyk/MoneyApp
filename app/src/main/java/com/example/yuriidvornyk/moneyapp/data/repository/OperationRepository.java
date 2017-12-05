package com.example.yuriidvornyk.moneyapp.data.repository;

import com.example.yuriidvornyk.moneyapp.data.datasource.OperationDataSource;
import com.example.yuriidvornyk.moneyapp.data.mapper.OperationMapper;
import com.example.yuriidvornyk.moneyapp.data.model.Operation;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by yurii.dvornyk on 2017-11-29.
 */

public class OperationRepository {

    private OperationDataSource dataSource;
    private OperationMapper mapper;

    public OperationRepository(OperationDataSource dataSource, OperationMapper mapper) {
        this.dataSource = dataSource;
        this.mapper = mapper;
    }

    public Flowable<List<Operation>> getOperationsForProject(int projectId) {
        return dataSource.getOperationsByProjectId(projectId)
                .flatMapIterable(list -> list)
                .map(dbOperation -> mapper.fromLocal(dbOperation))
                .toList()
                .toFlowable();
    }

    public Flowable<Operation> saveOperation(Operation operation) {
        return Flowable.fromCallable(() -> {
            dataSource.saveOperation(mapper.toLocal(operation));
            return operation;
        });
    }
}