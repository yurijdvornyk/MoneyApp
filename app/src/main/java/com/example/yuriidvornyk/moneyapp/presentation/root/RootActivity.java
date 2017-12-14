package com.example.yuriidvornyk.moneyapp.presentation.root;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.yuriidvornyk.moneyapp.R;
import com.example.yuriidvornyk.moneyapp.data.Injection;
import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.databinding.ActivityRootBinding;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseActivity;
import com.example.yuriidvornyk.moneyapp.utils.PermissionUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

public class RootActivity extends BaseActivity<RootContract.Presenter> implements RootContract.View {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 11123;

    private Navigator navigator;

    private ActivityRootBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_root);
        navigator = Navigator.getInstance();
        presenter = new RootPresenter(Injection.provideLastRateUpdateTime(), Injection.provideLoadCurrencyRates(),
                Injection.provideSaveSettings(), Injection.provideGetSettings());
        binding.bottomNavigation.setOnNavigationItemSelectedListener(this::onBottomNavigationItemSelected);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        navigator.setCurrentFragment(getSupportFragmentManager().findFragmentById(R.id.container));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (navigator.onOptionsItemSelected(this, item)) {
            return false;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    private boolean onBottomNavigationItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.projects:
                navigator.navigateToProjects(this);
                return true;
            case R.id.calculator:
                navigator.navigateToCalculator(this);
                return true;
            case R.id.settings:
                navigator.navigateToSettings(this);
                return true;
            default:
                return false;
        }
    }

    @Override
    public void navigateToProjectDetails(Project project) {
        navigator.navigateToProjectDetails(this, project);
    }

    @Override
    public void showCurrencyRatesUpdateError() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.error)
                .setMessage(R.string.error_loading_currencies_message)
                .setPositiveButton(R.string.try_again, (dialogInterface, i) -> presenter.start())
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void showCurrencyRatesUpdateSuccess() {
        Toast.makeText(this, "Rates updated successfully!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkLocationPermission() {
        if (!PermissionUtils.isLocationPermissionGranted(this) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case LOCATION_PERMISSION_REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission allowed!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied!", Toast.LENGTH_SHORT).show();
                }
                return;
            }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
}