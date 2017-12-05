package com.example.yuriidvornyk.moneyapp.data.repository;

import com.example.yuriidvornyk.moneyapp.data.datasource.MoneyDatabase;
import com.example.yuriidvornyk.moneyapp.data.datasource.ProjectDataSource;
import com.example.yuriidvornyk.moneyapp.data.mapper.ProjectMapper;
import com.example.yuriidvornyk.moneyapp.data.model.Project;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */
public class ProjectRepository {

    private ProjectDataSource dataStore;
    private ProjectMapper mapper;
    private MoneyDatabase database;

    public ProjectRepository(ProjectDataSource dataStore, ProjectMapper mapper, MoneyDatabase database) {
        this.dataStore = dataStore;
        this.mapper = mapper;
        this.database = database;
    }

    public Flowable<List<Project>> getAllProjects() {
        return dataStore.getAllProjects()
                .flatMapIterable(list -> list)
                .map(dbProject -> {
                    return mapper.fromLocal(dbProject);
                })
                .toList()
                .toFlowable();
    }

    public Flowable<Project> saveProject(Project project) {
        return Flowable.fromCallable(() -> {
            database.moneyDao().saveProjects(mapper.toLocal(project));
            return project;
        });
    }

    public Flowable<Project> deleteProject(Project project) {
        return Flowable.fromCallable(() -> {
            database.moneyDao().deleteProjects(mapper.toLocal(project));
            return project;
        });
    }
}
