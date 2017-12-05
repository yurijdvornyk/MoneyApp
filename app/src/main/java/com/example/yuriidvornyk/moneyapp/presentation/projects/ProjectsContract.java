package com.example.yuriidvornyk.moneyapp.presentation.projects;

import android.support.v4.util.Pair;

import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseContract;

import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

interface ProjectsContract {

    interface Presenter extends BaseContract.Presenter<View> {

        void loadProjects();

        void onNewProjectSaved(String name, Currency currency);

        void onAddProjectClicked();
    }

    interface View extends BaseContract.View<Presenter> {

        void setProjects(List<Project> projects);

        void showAddProjectDialog(List<Currency> currencies);

        void setProjectBalance(Pair<Project, Double> projectBalance);
    }
}
