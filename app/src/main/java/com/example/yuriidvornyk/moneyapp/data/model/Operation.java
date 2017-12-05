package com.example.yuriidvornyk.moneyapp.data.model;

import org.parceler.Parcel;
import org.threeten.bp.LocalDateTime;

/**
 * Created by yurii.dvornyk on 2017-11-23.
 */
@Parcel(Parcel.Serialization.BEAN)
public class Operation {

    private Integer id;
    private String name;
    private LocalDateTime time;
    private String currency;
    private double amount;
    private int projectId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getProjectId() {
        return projectId;
    }
}
