package com.example.yuriidvornyk.moneyapp.data.mapper;

import com.example.yuriidvornyk.moneyapp.data.local.DbCurrencyRate;
import com.example.yuriidvornyk.moneyapp.data.model.CurrencyRate;

/**
 * Created by yurii.dvornyk on 2017-12-01.
 */

public class CurrencyRateMapper implements Mapper<DbCurrencyRate, CurrencyRate> {
    @Override
    public CurrencyRate fromLocal(DbCurrencyRate local) {
        final CurrencyRate currencyRate = new CurrencyRate();
        currencyRate.setId(local.getId());
        currencyRate.setLastSyncTime(local.getLastSyncTime());
        currencyRate.setRate(local.getRate());
        currencyRate.setCurrencyFrom(local.getCurrencyFrom());
        currencyRate.setCurrencyTo(local.getCurrencyTo());
        return currencyRate;
    }

    @Override
    public DbCurrencyRate toLocal(CurrencyRate remote) {
        final DbCurrencyRate currencyRate = new DbCurrencyRate();
        currencyRate.setId(remote.getId());
        currencyRate.setLastSyncTime(remote.getLastSyncTime());
        currencyRate.setRate(remote.getRate());
        currencyRate.setCurrencyFrom(remote.getCurrencyFrom());
        currencyRate.setCurrencyTo(remote.getCurrencyTo());
        return currencyRate;
    }
}
