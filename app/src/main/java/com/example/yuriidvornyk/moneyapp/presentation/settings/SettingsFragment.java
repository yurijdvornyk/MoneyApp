package com.example.yuriidvornyk.moneyapp.presentation.settings;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuriidvornyk.moneyapp.R;
import com.example.yuriidvornyk.moneyapp.data.Injection;
import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.data.model.MoneyAppSettings;
import com.example.yuriidvornyk.moneyapp.databinding.FragmentSettingsBinding;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseFragment;
import com.example.yuriidvornyk.moneyapp.presentation.projects.CurrencyAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-12-12.
 */

public class SettingsFragment extends BaseFragment<SettingsContract.Presenter> implements SettingsContract.View {

    private FragmentSettingsBinding binding;
    private CurrencyAdapter adapter;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    public SettingsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        presenter = new SettingsPresenter(Injection.provideGetSettings(), Injection.provideSaveSettings(),
                Injection.provideGetCurrencies());
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_settings, container, false);
        adapter = CurrencyAdapter.newInstance(getContext(), new ArrayList<>());
        binding.spinnerDefaultCurrency.setAdapter(adapter);
        binding.buttonSave.setOnClickListener(v -> onSaveButtonClicked());
        presenter.start();
        return binding.getRoot();
    }

    @Override
    public void setCurrencies(List<Currency> currencies) {
        adapter.setItems(currencies);
    }

    @Override
    public void setSettings(MoneyAppSettings settings) {
        int defaultSelection = 0;
        for (int i = 0; i < adapter.getItems().size(); i++) {
            if (adapter.getItems().get(i).getCode().equals(settings.getDefaultCurrency())) {
                defaultSelection = i;
                break;
            }
        }
        binding.spinnerDefaultCurrency.setSelection(defaultSelection);
        binding.switchAutoSetDefaultCurrency.setChecked(settings.isAutoSetCurrency());
    }

    @Override
    public void onSettingsSaved() {
        binding.progressBar.setVisibility(View.GONE);
    }

    private void onSaveButtonClicked() {
        binding.progressBar.setVisibility(View.VISIBLE);
        presenter.save(
                ((Currency) binding.spinnerDefaultCurrency.getSelectedItem()).getCode(),
                binding.switchAutoSetDefaultCurrency.isChecked());
    }
}