package com.example.yuriidvornyk.moneyapp.presentation.root;

import android.app.AlertDialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.yuriidvornyk.moneyapp.R;
import com.example.yuriidvornyk.moneyapp.data.Injection;
import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.databinding.ActivityRootBinding;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseActivity;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

public class RootActivity extends BaseActivity<RootContract.Presenter> implements RootContract.View {

    private Navigator navigator;

    private ActivityRootBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_root);
        navigator = Navigator.getInstance();
        presenter = new RootPresenter(Injection.provideLoadCurrencyRates());
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
        presenter.loadCurrencyRates();
    }

    private boolean onBottomNavigationItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.projects:
                navigator.navigateToProjects(this);
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
                .setPositiveButton(R.string.try_again, (dialogInterface, i) -> presenter.loadCurrencyRates())
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    @Override
    public void showCurrencyRatesUpdateSuccess() {
        Toast.makeText(this, "Rates updated successfully!", Toast.LENGTH_SHORT).show();
    }
}