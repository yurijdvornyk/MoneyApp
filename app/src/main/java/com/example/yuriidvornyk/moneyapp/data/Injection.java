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
import com.example.yuriidvornyk.moneyapp.data.repository.SettingsRepository;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetCurrencies;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.LastRatesUpdateTime;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.GetRate;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.LoadCurrencyRates;
import com.example.yuriidvornyk.moneyapp.data.usecase.currency.SaveSettings;
import com.example.yuriidvornyk.moneyapp.data.usecase.operation.AddOperation;
import com.example.yuriidvornyk.moneyapp.data.usecase.operation.GetOperations;
import com.example.yuriidvornyk.moneyapp.data.usecase.projects.AddProject;
import com.example.yuriidvornyk.moneyapp.data.usecase.projects.GetBalance;
import com.example.yuriidvornyk.moneyapp.data.usecase.projects.GetProjectsWithBalance;
import com.example.yuriidvornyk.moneyapp.data.usecase.settings.GetSettings;
import com.example.yuriidvornyk.moneyapp.data.service.CurrencyRateService;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by yurii.dvornyk on 2017-11-29.
 */

public class Injection {

    public static GetProjectsWithBalance provideGetProjects() {
        return new GetProjectsWithBalance(provideProjectRepository(), provideGetBalance(), provideExecutionThread(), provideResultThread());
    }

    public static GetBalance provideGetBalance() {
        return new GetBalance(provideOperationRepository(), provideGetRate(), provideExecutionThread(), provideResultThread());
    }

    public static AddProject provideAddProject() {
        return new AddProject(provideProjectRepository(), provideExecutionThread(), provideResultThread());
    }

    public static GetCurrencies provideGetCurrencies() {
        return new GetCurrencies(provideCurrencyRepository(), provideExecutionThread(), provideResultThread());
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

    public static LastRatesUpdateTime provideLastRateUpdateTime() {
        return new LastRatesUpdateTime(provideCurrencyRateRepository(), provideExecutionThread(), provideResultThread());
    }

    public static SaveSettings provideSaveSettings() {
        return new SaveSettings(provideSettingsRepository(), provideExecutionThread(), provideResultThread());
    }

    public static GetSettings provideGetSettings() {
        return new GetSettings(provideSettingsRepository(), provideExecutionThread(), provideResultThread());
    }

    private static CurrencyRepository provideCurrencyRepository() {
        return new CurrencyRepository();
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

    private static SettingsRepository provideSettingsRepository() {
        return new SettingsRepository();
    }
}
