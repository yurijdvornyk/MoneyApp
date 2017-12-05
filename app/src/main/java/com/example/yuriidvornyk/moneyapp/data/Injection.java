package com.example.yuriidvornyk.moneyapp.data;

import com.example.yuriidvornyk.moneyapp.MoneyApplication;
import com.example.yuriidvornyk.moneyapp.data.datasource.CurrencyDataSource;
import com.example.yuriidvornyk.moneyapp.data.datasource.MoneyDatabase;
import com.example.yuriidvornyk.moneyapp.data.datasource.OperationDataSource;
import com.example.yuriidvornyk.moneyapp.data.datasource.ProjectDataSource;
import com.example.yuriidvornyk.moneyapp.data.mapper.CurrencyRateMapper;
import com.example.yuriidvornyk.moneyapp.data.mapper.OperationMapper;
import com.example.yuriidvornyk.moneyapp.data.mapper.ProjectMapper;
import com.example.yuriidvornyk.moneyapp.data.repository.CurrencyRateRepository;
import com.example.yuriidvornyk.moneyapp.data.repository.CurrencyRepository;
import com.example.yuriidvornyk.moneyapp.data.repository.OperationRepository;
import com.example.yuriidvornyk.moneyapp.data.repository.ProjectRepository;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetCurrencies;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetRate;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.LoadCurrencyRates;
import com.example.yuriidvornyk.moneyapp.data.usecase.operation.AddOperation;
import com.example.yuriidvornyk.moneyapp.data.usecase.operation.GetOperations;
import com.example.yuriidvornyk.moneyapp.data.usecase.projects.AddProject;
import com.example.yuriidvornyk.moneyapp.data.usecase.projects.GetBalance;
import com.example.yuriidvornyk.moneyapp.data.usecase.projects.GetProjects;
import com.example.yuriidvornyk.moneyapp.service.CurrencyRateService;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yurii.dvornyk on 2017-11-29.
 */

public class Injection {

    public static GetProjects provideGetProjects() {
        return new GetProjects(provideProjectRepository(), provideExecutionThread(), provideResultThread());
    }

    public static GetBalance provideGetBalance() {
        return new GetBalance(provideOperationRepository(), provideGetRate(), provideExecutionThread(), provideResultThread());
    }

    public static AddProject provideAddProject() {
        return new AddProject(provideProjectRepository(), provideExecutionThread(), provideResultThread());
    }

    public static GetCurrencies provideGetCurrencies() {
        return new GetCurrencies(new CurrencyRepository(), provideExecutionThread(), provideResultThread());
    }

    public static GetOperations provideGetOperations() {
        return new GetOperations(provideOperationRepository(), provideExecutionThread(), provideResultThread());
    }

    public static AddOperation provideAddOperation() {
        return new AddOperation(provideOperationRepository(), provideExecutionThread(), provideResultThread());
    }

    public static GetRate provideGetRate() {
        return new GetRate(provideCurrencyRateRepository(), provideExecutionThread(), provideResultThread());
    }

    public static LoadCurrencyRates provideLoadCurrencyRates() {
        return new LoadCurrencyRates(provideCurrencyRateRepository(), provideExecutionThread(), provideResultThread());
    }

    private static CurrencyRateRepository provideCurrencyRateRepository() {
        return new CurrencyRateRepository(
                new CurrencyDataSource(provideDatabase(),
                        new CurrencyRateService()),
                new CurrencyRateMapper());
    }

    private static OperationRepository provideOperationRepository() {
        return new OperationRepository(new OperationDataSource(provideDatabase()), new OperationMapper());
    }

    private static ProjectRepository provideProjectRepository() {
        return new ProjectRepository(new ProjectDataSource(provideDatabase()), new ProjectMapper(), provideDatabase());
    }

    private static MoneyDatabase provideDatabase() {
        return MoneyDatabase.getAppDatabase(MoneyApplication.getAppContext());
    }

    private static Scheduler provideExecutionThread() {
        return Schedulers.io();
    }

    private static Scheduler provideResultThread() {
        return AndroidSchedulers.mainThread();
    }
}
