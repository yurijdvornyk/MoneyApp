package com.example.yuriidvornyk.moneyapp.data.datasource;

import com.example.yuriidvornyk.moneyapp.data.local.DbProject;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by yurii.dvornyk on 2017-11-28.
 */

public class ProjectDataSource {

    private MoneyDatabase database;

    public ProjectDataSource(MoneyDatabase database) {
        this.database = database;
    }

    public Flowable<List<DbProject>> getAllProjects() {
        return Flowable.fromCallable(() -> database.moneyDao().loadProjects());
    }
}
