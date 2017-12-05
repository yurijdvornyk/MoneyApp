package com.example.yuriidvornyk.moneyapp.data.datasource;

import com.example.yuriidvornyk.moneyapp.data.local.DbOperation;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by yurii.dvornyk on 2017-12-01.
 */

public class OperationDataSource {

    private MoneyDatabase database;

    public OperationDataSource(MoneyDatabase database) {
        this.database = database;
    }

    public Flowable<List<DbOperation>> getOperationsByProjectId(int projectId) {
        return Flowable.fromCallable(() -> database.moneyDao().loadOperationsByProjectId(projectId));
    }

    public void saveOperation(DbOperation dbOperation) {
        database.moneyDao().saveOperation(dbOperation);
    }
}
