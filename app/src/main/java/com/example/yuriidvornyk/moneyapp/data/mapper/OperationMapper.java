package com.example.yuriidvornyk.moneyapp.data.mapper;

import com.example.yuriidvornyk.moneyapp.data.local.DbOperation;
import com.example.yuriidvornyk.moneyapp.data.model.Operation;

/**
 * Created by yurii.dvornyk on 2017-11-29.
 */

public class OperationMapper implements Mapper<DbOperation, Operation> {
    @Override
    public Operation fromLocal(DbOperation local) {
        final Operation operation = new Operation();
        operation.setId(local.getId());
        operation.setProjectId(local.getProjectId());
        operation.setName(local.getName());
        operation.setAmount(local.getAmount());
        operation.setCurrency(local.getCurrency());
        operation.setTime(local.getTime());
        return operation;
    }

    @Override
    public DbOperation toLocal(Operation remote) {
        final DbOperation operation = new DbOperation();
        if (remote.getId() != null) {
            operation.setId(remote.getId());
        }
        operation.setProjectId(remote.getProjectId());
        operation.setName(remote.getName());
        operation.setAmount(remote.getAmount());
        operation.setCurrency(remote.getCurrency());
        operation.setTime(remote.getTime());
        return operation;
    }
}
