package com.example.yuriidvornyk.moneyapp.presentation.projects;

import android.support.v4.util.Pair;
import android.util.Log;

import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetCurrencies;
import com.example.yuriidvornyk.moneyapp.data.usecase.projects.AddProject;
import com.example.yuriidvornyk.moneyapp.data.usecase.projects.GetBalance;
import com.example.yuriidvornyk.moneyapp.data.usecase.projects.GetProjects;
import com.example.yuriidvornyk.moneyapp.presentation.base.BasePresenter;

import org.threeten.bp.LocalDateTime;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

class ProjectsPresenter extends BasePresenter<ProjectsContract.View> implements ProjectsContract.Presenter {

    private GetProjects getProjects;
    private GetCurrencies getCurrencies;
    private GetBalance getBalance;
    private AddProject addProject;

    ProjectsPresenter(GetProjects getProjects, GetBalance getBalance, AddProject addProject, GetCurrencies getCurrencies) {
        this.getProjects = getProjects;
        this.getBalance = getBalance;
        this.addProject = addProject;
        this.getCurrencies = getCurrencies;
    }

    @Override
    public void loadProjects() {
        getProjects.execute(loadedProjects -> {
            view.setProjects(loadedProjects);
            for (Project project : loadedProjects) {
                getBalance.project(project).execute(balance -> {
                    view.setProjectBalance(Pair.create(project, balance.second));
                }, error -> {
                    Log.e("AAA", error.getMessage());
                });
            }
        });
    }

    @Override
    public void onNewProjectSaved(String name, Currency currency) {
        final Project project = new Project();
        project.setName(name);
        project.setDefaultCurrency(currency.getCode());
        project.setCreateTime(LocalDateTime.now());
        addProject.project(project).execute(aVoid -> loadProjects());
    }

    @Override
    public void onAddProjectClicked() {
        getCurrencies.execute(currencies -> view.showAddProjectDialog(currencies));
    }

    @Override
    public void detach() {
        getProjects.unsubscribe();
    }
}
