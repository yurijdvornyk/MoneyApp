package com.example.yuriidvornyk.moneyapp.data.repository;

import android.support.annotation.Nullable;

import com.example.yuriidvornyk.moneyapp.data.datasource.CurrencyDataSource;
import com.example.yuriidvornyk.moneyapp.data.local.DbCurrencyRate;
import com.example.yuriidvornyk.moneyapp.data.mapper.CurrencyRateMapper;
import com.example.yuriidvornyk.moneyapp.data.model.CurrencyRate;

import java.util.List;

import io.reactivex.Flowable;

import static com.example.yuriidvornyk.moneyapp.data.datasource.CurrencyDataSource.DEFAULT_CURRENCY_CODE;

/**
 * Created by yurii.dvornyk on 2017-11-30.
 */

public class CurrencyRateRepository {

    private CurrencyDataSource dataSource;
    private CurrencyRateMapper mapper;

    public CurrencyRateRepository(CurrencyDataSource dataSource, CurrencyRateMapper mapper) {
        this.dataSource = dataSource;
        this.mapper = mapper;
    }

    public Flowable<CurrencyRate> getRate(String from, String to) {
        return dataSource.loadCurrencyRates()
                .map(currencyRates -> {
                    dataSource.clearRates();
                    dataSource.saveRates(currencyRates);
                    return currencyRates;
                })
                .flatMapIterable(list -> list)
                .filter(rate -> (rate.getCurrencyFrom().equals(DEFAULT_CURRENCY_CODE) && rate.getCurrencyTo().equals(from) ||
                        (rate.getCurrencyFrom().equals(DEFAULT_CURRENCY_CODE) && rate.getCurrencyTo().equals(to))))
                .toList()
                .map(dbCurrencyRates -> convertRates(from, to, dbCurrencyRates))
                .toFlowable();
    }

    @Nullable
    private CurrencyRate convertRates(String from, String to, List<DbCurrencyRate> dbCurrencyRates) {
        if (dbCurrencyRates.size() == 2) {
            final CurrencyRate rate = new CurrencyRate();
            rate.setCurrencyFrom(from);
            rate.setCurrencyTo(to);
            rate.setRate(dbCurrencyRates.get(1).getRate() / dbCurrencyRates.get(0).getRate());
            return rate;
        } else if (dbCurrencyRates.size() == 1) {
            final CurrencyRate rate = new CurrencyRate();
            rate.setCurrencyFrom(from);
            rate.setCurrencyTo(to);
            rate.setRate(1.0);
            return rate;
        } else {
            return null;
        }
    }

    private Flowable<List<DbCurrencyRate>> updateCurrencyRates() {
        return dataSource.loadCurrencyRates()
                .map(currencyRates -> {
                    dataSource.clearRates();
                    dataSource.saveRates(currencyRates);
                    return currencyRates;
                })
                .flatMapIterable(list -> list)
                .toList()
                .toFlowable();
    }
}
