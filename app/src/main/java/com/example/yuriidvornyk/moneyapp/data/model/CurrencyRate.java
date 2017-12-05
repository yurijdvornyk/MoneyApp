package com.example.yuriidvornyk.moneyapp.data.model;

import org.parceler.Parcel;
import org.threeten.bp.LocalDateTime;

/**
 * Created by yurii.dvornyk on 2017-11-28.
 */
@Parcel(Parcel.Serialization.BEAN)
public class CurrencyRate {

    private Integer id;

    private String currencyFrom;

    private String currencyTo;

    private double rate;

    private LocalDateTime lastSyncTime;

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getRate() {
        return rate;
    }

    public LocalDateTime getLastSyncTime() {
        return lastSyncTime;
    }

    public void setLastSyncTime(LocalDateTime lastSyncTime) {
        this.lastSyncTime = lastSyncTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCurrencyFrom() {
        return currencyFrom;
    }

    public void setCurrencyFrom(String currencyFrom) {
        this.currencyFrom = currencyFrom;
    }

    public String getCurrencyTo() {
        return currencyTo;
    }

    public void setCurrencyTo(String currencyTo) {
        this.currencyTo = currencyTo;
    }
}
