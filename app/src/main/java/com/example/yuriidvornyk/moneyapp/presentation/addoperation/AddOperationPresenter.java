package com.example.yuriidvornyk.moneyapp.presentation.addoperation;

import android.app.Activity;

import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.data.model.Operation;
import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetCurrencies;
import com.example.yuriidvornyk.moneyapp.data.usecase.operation.AddOperation;
import com.example.yuriidvornyk.moneyapp.presentation.base.BasePresenter;

import org.threeten.bp.LocalDateTime;

/**
 * Created by yurii.dvornyk on 2017-12-06.
 */

class AddOperationPresenter extends BasePresenter<AddOperationContract.View> implements AddOperationContract.Presenter {

    private Project project;
    private GetCurrencies getCurrencies;
    private AddOperation addOperation;

    AddOperationPresenter(Project project, GetCurrencies getCurrencies, AddOperation addOperation) {
        this.project = project;
        this.getCurrencies = getCurrencies;
        this.addOperation = addOperation;
    }

    @Override
    public void loadCurrencies() {
        getCurrencies.execute(currencies -> view.setCurrencies(currencies, project.getDefaultCurrency()));
    }

    @Override
    public void detach() {
        getCurrencies.unsubscribe();
        addOperation.unsubscribe();
    }

    @Override
    public void onNewOperationSaved(String name, Currency currency, String amount) {
        final Operation operation = new Operation();
        operation.setName(name);
        operation.setCurrency(currency.getCode());
        operation.setAmount(Double.parseDouble(amount));
        operation.setProjectId(project.getId());
        operation.setTime(LocalDateTime.now());
        addOperation.operation(operation).execute(result -> view.close(Activity.RESULT_OK, project));
    }
}
