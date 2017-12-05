package com.example.yuriidvornyk.moneyapp.data.usecase.projects;

import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.data.repository.ProjectRepository;
import com.example.yuriidvornyk.moneyapp.data.usecase.base.UseCase;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by yurii.dvornyk on 2017-11-23.
 */

public class GetProjects extends UseCase<List<Project>> {

    private ProjectRepository projectRepository;

    public GetProjects(ProjectRepository projectRepository,
                       Scheduler callerThread, Scheduler resultThread) {
        super(callerThread, resultThread);
        this.projectRepository = projectRepository;
    }

    @Override
    protected Flowable<List<Project>> buildUseCaseFlowable() {
        return projectRepository.getAllProjects();
    }
}