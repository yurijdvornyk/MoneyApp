package com.example.yuriidvornyk.moneyapp.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

/**
 * Created by yurii.dvornyk on 2017-12-13.
 */

public class PermissionUtils {

    public static boolean isLocationPermissionGranted(Context context) {
        return context.checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED;
    }
}
