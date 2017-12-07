package com.example.yuriidvornyk.moneyapp.presentation.projectdetails;

import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.data.model.Operation;
import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetRate;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetCurrencies;
import com.example.yuriidvornyk.moneyapp.data.usecase.operation.AddOperation;
import com.example.yuriidvornyk.moneyapp.data.usecase.operation.GetOperations;
import com.example.yuriidvornyk.moneyapp.data.usecase.projects.GetBalance;
import com.example.yuriidvornyk.moneyapp.presentation.base.BasePresenter;

import org.threeten.bp.LocalDateTime;

/**
 * Created by yurii.dvornyk on 2017-11-24.
 */

class ProjectDetailsPresenter extends BasePresenter<ProjectDetailsContract.View>
        implements ProjectDetailsContract.Presenter {

    private GetBalance getBalance;
    private GetOperations getOperations;
    private GetCurrencies getCurrencies;
    private AddOperation addOperation;
    private Project project;

    ProjectDetailsPresenter(Project project, GetBalance getBalance, GetOperations getOperations,
                            GetCurrencies getCurrencies, AddOperation addOperation) {
        this.project = project;
        this.getBalance = getBalance;
        this.getOperations = getOperations;
        this.getCurrencies = getCurrencies;
        this.addOperation = addOperation;
    }

    @Override
    public void start() {
        view.showProjectData(project);
        getBalance.project(project).execute(projectWithBalance ->
                view.showMoneyBalance(projectWithBalance.second, project.getDefaultCurrency()));
        loadOperations();
    }

    @Override
    public void onAddOperationClicked() {
        view.showAddOperationDialog(project);
    }

    @Override
    public void onNewOperationSaved(String name, Currency currency, String amount) {
        final Operation operation = new Operation();
        operation.setName(name);
        operation.setCurrency(currency.getCode());
        operation.setAmount(Double.parseDouble(amount));
        operation.setProjectId(project.getId());
        operation.setTime(LocalDateTime.now());
        addOperation.operation(operation).execute(result -> start());
    }

    @Override
    public void detach() {
        getBalance.unsubscribe();
    }

    private void loadOperations() {
        getOperations.projectId(project.getId()).execute(operations -> view.showOperations(operations));
    }
}
