package com.example.yuriidvornyk.moneyapp.data.datasource;

import com.example.yuriidvornyk.moneyapp.data.local.DbCurrencyRate;
import com.example.yuriidvornyk.moneyapp.service.CurrencyRateService;

import org.json.JSONArray;
import org.json.JSONObject;
import org.threeten.bp.LocalDateTime;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

/**
 * Created by yurii.dvornyk on 2017-12-01.
 */

public class CurrencyDataSource {

    public static final String DEFAULT_CURRENCY_CODE = "USD";

    private MoneyDatabase database;
    private CurrencyRateService service;

    public CurrencyDataSource(MoneyDatabase database, CurrencyRateService service) {
        this.database = database;
        this.service = service;
    }

    public Flowable<DbCurrencyRate> getCurrencyRate(String to) {
        return Flowable.fromCallable(() -> database.moneyDao().getCurrencyRate(DEFAULT_CURRENCY_CODE, to));
    }

    public Flowable<List<LocalDateTime>> getCurrencyRateUpdateTimes() {
        return Flowable.fromCallable(() -> database.moneyDao().getCurrencyRateUpdateTimes());
    }

    public Flowable<List<DbCurrencyRate>> loadCurrencyRates() {
        return service.getAPI().getCurrencyRates("json").map(responseBody -> {
            final List<DbCurrencyRate> result = new ArrayList<>();
            final JSONArray entries = new JSONObject(responseBody.string())
                    .getJSONObject("feed")
                    .getJSONArray("entry");
            for (int i = 0; i < entries.length(); i++) {
                final JSONObject entry = entries.getJSONObject(i);
                final DbCurrencyRate rate = new DbCurrencyRate();
                rate.setCurrencyFrom(DEFAULT_CURRENCY_CODE);
                rate.setCurrencyTo(entry.getJSONObject("title").getString("$t"));
                final String[] rateRaw = entry.getJSONObject("content").getString("$t").split(" ");
                rate.setRate(Double.parseDouble(rateRaw[rateRaw.length - 1]));
                rate.setLastSyncTime(LocalDateTime.now());
                result.add(rate);
            }
            return result;
        });
    }

    public void clearRates() {
        database.moneyDao().clearRates();
    }

    public void saveRates(List<DbCurrencyRate> currencyRates) {
        database.moneyDao().saveRates(currencyRates.toArray(new DbCurrencyRate[currencyRates.size()]));
    }
}
