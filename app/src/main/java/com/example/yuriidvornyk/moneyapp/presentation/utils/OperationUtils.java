package com.example.yuriidvornyk.moneyapp.presentation.utils;

import android.content.Context;

import com.example.yuriidvornyk.moneyapp.R;

/**
 * Created by yurii.dvornyk on 2017-11-27.
 */

public class OperationUtils {

    public static String formatBalance(Context context, double amount, String currency) {
        return context.getString(R.string.balance_arg, amount > 0 ? "+" : "", amount, currency);
    }
}
