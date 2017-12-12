package com.example.yuriidvornyk.moneyapp.presentation.settings;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuriidvornyk.moneyapp.presentation.base.BaseFragment;

/**
 * Created by yurii.dvornyk on 2017-12-12.
 */

public class SettingsFragment extends BaseFragment<SettingsContract.Presenter> implements SettingsContract.View {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    public SettingsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new SettingsPresenter();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void attachPresenter(SettingsContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
