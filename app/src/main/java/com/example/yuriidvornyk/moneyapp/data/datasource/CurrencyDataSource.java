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

    public static final String DEFAULT_CURRENCY_CODE = "EUR";

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
        return service.getAPI().getCurrencyRates(CurrencyRateService.DOCUMENT_ID).map(responseBody -> {
            final String body = responseBody.string();
            final int startIndex = body.indexOf("(");
            final int endIndex = body.lastIndexOf(")");
            final List<DbCurrencyRate> rates = new ArrayList<>();
            final String jsonContent = body.substring(startIndex + 1, endIndex);
            final JSONArray entries = new JSONObject(jsonContent).getJSONObject("table").getJSONArray("rows");
            for (int i = 0; i < entries.length(); i++) {
                final DbCurrencyRate rate = new DbCurrencyRate();
                final JSONArray entry = entries.getJSONObject(i).getJSONArray("c");
                rate.setCurrencyFrom(DEFAULT_CURRENCY_CODE);
                rate.setCurrencyTo(String.valueOf(entry.getJSONObject(0).get("v")));
                rate.setRate(Double.valueOf(entry.getJSONObject(1).get("f").toString()));
                rates.add(rate);
            }
            return rates;
        });
    }

    public void clearRates() {
        database.moneyDao().clearRates();
    }

    public void saveRates(List<DbCurrencyRate> currencyRates) {
        database.moneyDao().saveRates(currencyRates.toArray(new DbCurrencyRate[currencyRates.size()]));
    }
}
