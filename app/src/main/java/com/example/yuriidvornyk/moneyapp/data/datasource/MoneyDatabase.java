package com.example.yuriidvornyk.moneyapp.data.datasource;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.yuriidvornyk.moneyapp.data.dao.DatabaseDao;
import com.example.yuriidvornyk.moneyapp.data.local.DbCurrencyRate;
import com.example.yuriidvornyk.moneyapp.data.local.DbOperation;
import com.example.yuriidvornyk.moneyapp.data.local.DbProject;
import com.example.yuriidvornyk.moneyapp.data.local.typeconverter.LocalDateTimeTypeConverter;

/**
 * Created by yurii.dvornyk on 2017-11-28.
 */
@Database(
        entities = {DbCurrencyRate.class, DbOperation.class, DbProject.class},
        version = 1)
@TypeConverters({LocalDateTimeTypeConverter.class})
public abstract class MoneyDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "money-database";
    private static MoneyDatabase INSTANCE;

    public abstract DatabaseDao moneyDao();

    public static MoneyDatabase getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), MoneyDatabase.class, DATABASE_NAME)
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}
