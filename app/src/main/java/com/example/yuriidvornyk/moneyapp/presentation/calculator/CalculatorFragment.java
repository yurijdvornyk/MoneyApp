package com.example.yuriidvornyk.moneyapp.presentation.calculator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuriidvornyk.moneyapp.R;
import com.example.yuriidvornyk.moneyapp.data.Injection;
import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.databinding.DialogAddProjectBinding;
import com.example.yuriidvornyk.moneyapp.databinding.FragmentCalculatorBinding;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseFragment;
import com.example.yuriidvornyk.moneyapp.presentation.projects.CurrencyAdapter;

import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-12-07.
 */

public class CalculatorFragment extends BaseFragment<CalculatorContract.Presenter> implements CalculatorContract.View {

    private FragmentCalculatorBinding binding;

    public static CalculatorFragment newInstance() {
        return new CalculatorFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CalculatorPresenter(Injection.provideGetRate(), Injection.provideGetCurrencies());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false);
        presenter.loadCurrencies();
        return binding.getRoot();
    }

    @Override
    public void showCurrencies(List<Currency> currencies) {
        binding.spinnerCurrencyFrom.setAdapter(createCurrencyAdapter(currencies));
        binding.spinnerCurrencyTo.setAdapter(createCurrencyAdapter(currencies));
    }

    @NonNull
    private CurrencyAdapter createCurrencyAdapter(List<Currency> currencies) {
        final CurrencyAdapter adapter = new CurrencyShortAdapter(getContext(), R.layout.item_currency_spinner, currencies);
        adapter.setDropDownViewResource(R.layout.item_currency_spinner);
        return adapter;
    }
}
