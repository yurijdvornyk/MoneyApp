package com.example.yuriidvornyk.moneyapp.presentation.root;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.example.yuriidvornyk.moneyapp.R;
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
        presenter = new RootPresenter();
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
}