package com.example.yuriidvornyk.moneyapp.presentation.projectdetails;

import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.data.model.Operation;
import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseContract;

import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-11-24.
 */

interface ProjectDetailsContract {

    interface Presenter extends BaseContract.Presenter<View> {

        void start();

        void onAddOperationClicked();

        void onNewOperationSaved(String name, Currency currency, String amount);
    }

    interface View extends BaseContract.View<Presenter> {

        void showProjectData(Project project);

        void showMoneyBalance(double balance, String currency);

        void showOperations(List<Operation> operations);

        void showAddOperationDialog(Project project);
    }
}
