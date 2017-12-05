package com.example.yuriidvornyk.moneyapp.presentation.projects;

import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetCurrencies;
import com.example.yuriidvornyk.moneyapp.data.usecase.projects.AddProject;
import com.example.yuriidvornyk.moneyapp.data.usecase.projects.GetProjectsWithBalance;
import com.example.yuriidvornyk.moneyapp.presentation.base.BasePresenter;

import org.threeten.bp.LocalDateTime;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

class ProjectsPresenter extends BasePresenter<ProjectsContract.View> implements ProjectsContract.Presenter {

    private GetProjectsWithBalance getProjectsWithBalance;
    private GetCurrencies getCurrencies;
    private AddProject addProject;

    ProjectsPresenter(GetProjectsWithBalance getProjectsWithBalance, AddProject addProject, GetCurrencies getCurrencies) {
        this.getProjectsWithBalance = getProjectsWithBalance;
        this.addProject = addProject;
        this.getCurrencies = getCurrencies;
    }

    @Override
    public void loadProjects() {
        getProjectsWithBalance.execute(view::setProjects);
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
        getCurrencies.execute(view::showAddProjectDialog);
    }

    @Override
    public void detach() {
        getProjectsWithBalance.unsubscribe();
    }
}
