package com.example.yuriidvornyk.moneyapp.presentation.root;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;

import com.example.yuriidvornyk.moneyapp.R;
import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseActivity;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseFragment;
import com.example.yuriidvornyk.moneyapp.presentation.calculator.CalculatorFragment;
import com.example.yuriidvornyk.moneyapp.presentation.projectdetails.ProjectDetailsFragment;
import com.example.yuriidvornyk.moneyapp.presentation.projects.ProjectsFragment;

/**
 * Created by yurii.dvornyk on 2017-11-23.
 */

class Navigator {

    private static Navigator navigator;
    private String currentFragment;

    static Navigator getInstance() {
        if (navigator == null) {
            navigator = new Navigator();
        }
        return navigator;
    }

    <A extends BaseActivity> void navigateToProjects(A activity) {
        replaceFragment(activity, ProjectsFragment.newInstance(), true);
    }

    <A extends BaseActivity> void navigateToProjectDetails(A activity, Project project) {
        replaceFragment(activity, ProjectDetailsFragment.newInstance(project), true);
    }

    <A extends BaseActivity>  void navigateToCalculator(A activity) {
        replaceFragment(activity, CalculatorFragment.newInstance(), true);
    }

    <A extends BaseActivity, B extends BaseFragment> boolean onOptionsItemSelected(A activity, MenuItem item) {
        final B fragment = (B) activity.getSupportFragmentManager().findFragmentById(R.id.container);
        if (fragment != null) {
            fragment.onOptionsItemSelected(item);
            return true;
        } else {
            return false;
        }
    }

    <F extends Fragment> void setCurrentFragment(F fragment) {
        currentFragment = getFragmentName(fragment);
    }

    private <F extends Fragment> void replaceFragment(BaseActivity activity, F fragment, boolean addToBackStack) {
        if (shouldNavigate(fragment)) {
            final FragmentTransaction transaction = activity.getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment);
            if (addToBackStack) {
                transaction.addToBackStack(ProjectsFragment.class.getSimpleName());
            }
            transaction.commit();
            setCurrentFragment(fragment);
        }
    }

    private <F extends Fragment> boolean shouldNavigate(F fragment) {
        return !getFragmentName(fragment).equals(currentFragment);
    }

    private <F extends Fragment> String getFragmentName(F fragment) {
        return fragment == null ? null : fragment.getClass().getSimpleName();
    }
}
