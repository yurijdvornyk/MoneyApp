package com.example.yuriidvornyk.moneyapp.data.usecase.projects;

import android.support.v4.util.Pair;

import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.data.repository.ProjectRepository;
import com.example.yuriidvornyk.moneyapp.data.usecase.base.UseCase;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Scheduler;

/**
 * Created by yurii.dvornyk on 2017-11-23.
 */

public class GetProjectsWithBalance extends UseCase<List<Pair<Project, Double>>> {

    private ProjectRepository projectRepository;
    private GetBalance getBalance;

    public GetProjectsWithBalance(ProjectRepository projectRepository, GetBalance getBalance,
                                  Scheduler callerThread, Scheduler resultThread) {
        super(callerThread, resultThread);
        this.projectRepository = projectRepository;
        this.getBalance = getBalance;
    }

    @Override
    protected Flowable<List<Pair<Project, Double>>> buildUseCaseFlowable() {
        return projectRepository.getAllProjects()
                .flatMapIterable(list -> list)
                .flatMap(project -> getBalance.project(project).getRawFlowable())
                .toList()
                .toFlowable();
    }
}