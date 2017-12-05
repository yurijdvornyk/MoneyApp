package com.example.yuriidvornyk.moneyapp.data.usecase.projects;

import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.data.repository.ProjectRepository;
import com.example.yuriidvornyk.moneyapp.data.usecase.base.UseCase;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by yurii.dvornyk on 2017-11-28.
 */

public class AddProject extends UseCase<Project> {

    private ProjectRepository repository;
    private Project project;

    public AddProject(ProjectRepository repository, Scheduler callerThread, Scheduler resultThread) {
        super(callerThread, resultThread);
        this.repository = repository;
    }

    public AddProject project(Project project) {
        this.project = project;
        return this;
    }

    @Override
    protected Flowable<Project> buildUseCaseFlowable() {
        return repository.saveProject(project);
    }
}
