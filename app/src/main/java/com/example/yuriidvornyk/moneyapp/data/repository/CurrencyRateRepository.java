package com.example.yuriidvornyk.moneyapp.data.repository;

import com.example.yuriidvornyk.moneyapp.data.datasource.CurrencyDataSource;
import com.example.yuriidvornyk.moneyapp.data.local.DbCurrencyRate;
import com.example.yuriidvornyk.moneyapp.data.mapper.CurrencyRateMapper;
import com.example.yuriidvornyk.moneyapp.data.model.CurrencyRate;

import org.threeten.bp.LocalDateTime;

import java.util.List;

import io.reactivex.Flowable;

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
        return Flowable.zip(
                dataSource.getCurrencyRate(from),
                dataSource.getCurrencyRate(to),
                (rateFrom, rateTo) -> convertRates(from, to, rateFrom, rateTo));
    }

    public Flowable<List<CurrencyRate>> updateCurrencyRates() {
        return dataSource.loadCurrencyRates()
                .map(currencyRates -> {
                    dataSource.clearRates();
                    dataSource.saveRates(currencyRates);
                    return currencyRates;
                })
                .flatMapIterable(list -> list)
                .map(rate -> mapper.fromLocal(rate))
                .toList()
                .toFlowable();
    }

    public Flowable<LocalDateTime> getCurrencyRateUpdateTime() {
        return dataSource.getCurrencyRateUpdateTimes()
                .map(times -> {
                    final LocalDateTime now = LocalDateTime.now();
                    LocalDateTime time = now;
                    for (LocalDateTime updateTime : times) {
                        if (time.isAfter(updateTime)) {
                            time = updateTime;
                        }
                    }
                    return time.isEqual(now) ? null : time;
                });
    }

    private CurrencyRate convertRates(String from, String to, DbCurrencyRate rateFrom, DbCurrencyRate rateTo) {
        if (rateFrom != null && rateTo != null) {
            final CurrencyRate rate = new CurrencyRate();
            rate.setCurrencyFrom(from);
            rate.setCurrencyTo(to);
            rate.setRate(rateTo.getRate() / rateFrom.getRate());
            return rate;
        } else if (rateFrom != null || rateTo != null) {
            final CurrencyRate rate = new CurrencyRate();
            rate.setCurrencyFrom(from);
            rate.setCurrencyTo(to);
            rate.setRate(1.0);
            return rate;
        } else {
            return null;
        }
    }
}