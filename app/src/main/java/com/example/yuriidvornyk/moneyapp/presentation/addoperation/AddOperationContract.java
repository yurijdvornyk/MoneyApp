package com.example.yuriidvornyk.moneyapp.presentation.addoperation;

import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseContract;

import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-12-06.
 */

interface AddOperationContract {

    interface Presenter extends BaseContract.Presenter<View> {

        void loadCurrencies();

        void onNewOperationSaved(String name, Currency currency, String amount);
    }

    interface View extends BaseContract.View<Presenter> {

        void close(int result, Project project);

        void setCurrencies(List<Currency> currencies, String defaultCurrency);
    }
}
