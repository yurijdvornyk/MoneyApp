package com.example.yuriidvornyk.moneyapp.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.yuriidvornyk.moneyapp.data.local.DbCurrencyRate;
import com.example.yuriidvornyk.moneyapp.data.local.DbOperation;
import com.example.yuriidvornyk.moneyapp.data.local.DbProject;

import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-11-28.
 */
@Dao
public interface DatabaseDao {

    @Query("SELECT * FROM DbProject")
    List<DbProject> loadProjects();

    @Query("SELECT * FROM DbOperation WHERE projectId LIKE :projectId")
    List<DbOperation> loadOperationsByProjectId(int projectId);

    @Query("SELECT * FROM DbCurrencyRate WHERE (currencyFrom LIKE :from) AND (currencyTo LIKE :to)")
    DbCurrencyRate getCurrencyRate(String from, String to);

    @Insert
    void saveProjects(DbProject... projects);

    @Insert
    void saveOperation(DbOperation... dbOperation);

    @Insert
    void saveRates(DbCurrencyRate... currencyRates);

    @Query("DELETE FROM DbCurrencyRate")
    void clearRates();

    @Delete
    void deleteProjects(DbProject... projects);

    @Delete
    void deleteOperations(DbOperation... operations);
}