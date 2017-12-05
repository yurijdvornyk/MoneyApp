package com.example.yuriidvornyk.moneyapp.data.local.typeconverter;

import android.arch.persistence.room.TypeConverter;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by yurii.dvornyk on 2017-11-29.
 */

public class LocalDateTimeTypeConverter {

    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_DATE_TIME;

    @TypeConverter
    public static LocalDateTime toLocalDateTime(String value) {
        return LocalDateTime.parse(value, FORMATTER);
    }

    @TypeConverter
    public static String fromLocalDateTime(LocalDateTime value) {
        return FORMATTER.format(value);
    }
}
