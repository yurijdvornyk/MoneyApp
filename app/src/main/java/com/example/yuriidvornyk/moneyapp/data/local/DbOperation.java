package com.example.yuriidvornyk.moneyapp.data.local;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import org.threeten.bp.LocalDateTime;

/**
 * Created by yurii.dvornyk on 2017-11-28.
 */
@Entity(foreignKeys = @ForeignKey(entity = DbProject.class, parentColumns = "id", childColumns = "projectId"))
public class DbOperation {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "projectId")
    private int projectId;

    @ColumnInfo(name = "time")
    private LocalDateTime time;

    @ColumnInfo(name = "currency")
    private String currency;

    @ColumnInfo(name = "amount")
    private double amount;

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
