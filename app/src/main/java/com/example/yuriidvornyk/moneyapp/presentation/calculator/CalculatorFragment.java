package com.example.yuriidvornyk.moneyapp.presentation.calculator;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.yuriidvornyk.moneyapp.R;
import com.example.yuriidvornyk.moneyapp.data.Injection;
import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.databinding.FragmentCalculatorBinding;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseFragment;
import com.example.yuriidvornyk.moneyapp.presentation.projects.CurrencyAdapter;
import com.example.yuriidvornyk.moneyapp.presentation.utils.OperationUtils;
import com.example.yuriidvornyk.moneyapp.presentation.widget.ListenableEditText;

import org.threeten.bp.LocalDateTime;
import org.threeten.bp.format.DateTimeFormatter;

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
        presenter = new CalculatorPresenter(Injection.provideGetRate(), Injection.provideGetCurrencies(),
                Injection.provideLastRateUpdateTime());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentCalculatorBinding.inflate(inflater, container, false);
        setListeners();
        presenter.loadCurrencies();
        presenter.loadLastUpdateTime();
        return binding.getRoot();
    }

    @Override
    public void showCurrencies(List<Currency> currencies) {
        binding.spinnerCurrencyFrom.setAdapter(createCurrencyAdapter(currencies));
        binding.spinnerCurrencyTo.setAdapter(createCurrencyAdapter(currencies));
    }

    @Override
    public void setResultTo(double amount) {
        binding.editTo.setText(OperationUtils.formatAmount(amount));
    }

    @Override
    public void setResultFrom(double amount) {
        binding.editFrom.setText(OperationUtils.formatAmount(amount));
    }

    @Override
    public void showLastUpdateTime(LocalDateTime time) {
        setControlsEnabled(time != null);
        binding.textLastUpdated.setText(time == null ?
                getString(R.string.calculator_not_available) :
                getString(R.string.calculator_last_update_time,
                        DateTimeFormatter.ISO_LOCAL_DATE.format(time),
                        DateTimeFormatter.ISO_LOCAL_TIME.format(time)));
    }

    private void setControlsEnabled(boolean enabled) {
        binding.editFrom.setEnabled(enabled);
        binding.editTo.setEnabled(enabled);
        binding.spinnerCurrencyFrom.setEnabled(enabled);
        binding.spinnerCurrencyTo.setEnabled(enabled);
    }

    @NonNull
    private CurrencyAdapter createCurrencyAdapter(List<Currency> currencies) {
        final CurrencyAdapter adapter = new CurrencyShortAdapter(getContext(), R.layout.item_currency_spinner, currencies);
        adapter.setDropDownViewResource(R.layout.item_currency_spinner);
        return adapter;
    }

    private void setListeners() {
        binding.editFrom.setListener(getTextListener(binding.editFrom, CalculatorContract.WhichEditText.FROM));
        binding.editTo.setListener(getTextListener(binding.editTo, CalculatorContract.WhichEditText.TO));
        binding.spinnerCurrencyFrom.setOnItemSelectedListener(
                getSpinnerListener(binding.editFrom, CalculatorContract.WhichEditText.FROM));
        binding.spinnerCurrencyTo.setOnItemSelectedListener(
                getSpinnerListener(binding.editTo, CalculatorContract.WhichEditText.TO));
    }

    @NonNull
    private AdapterView.OnItemSelectedListener getSpinnerListener(ListenableEditText editText, CalculatorContract.WhichEditText whichEditText) {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                callPresenter(editText, whichEditText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        };
    }

    @NonNull
    private ListenableEditText.TextChangeListener getTextListener(ListenableEditText editText, CalculatorContract.WhichEditText whichEditText) {
        return text -> {
            if (editText.isFocused())
                callPresenter(editText, whichEditText);
        };
    }

    private void callPresenter(ListenableEditText editText, CalculatorContract.WhichEditText whichEditText) {
        presenter.onContentChanged(
                ((Currency) binding.spinnerCurrencyFrom.getSelectedItem()).getCode(),
                ((Currency) binding.spinnerCurrencyTo.getSelectedItem()).getCode(),
                editText.getText().toString(),
                whichEditText);
    }
}