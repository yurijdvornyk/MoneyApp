package com.example.yuriidvornyk.moneyapp.presentation.addoperation;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.yuriidvornyk.moneyapp.R;
import com.example.yuriidvornyk.moneyapp.data.Injection;
import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.databinding.DialogAddOperationBinding;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseFragmentDialog;
import com.example.yuriidvornyk.moneyapp.presentation.projects.CurrencyAdapter;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-12-06.
 */

public class AddOperationDialog extends BaseFragmentDialog<AddOperationContract.Presenter> implements AddOperationContract.View {

    public static final String TAG = AddOperationDialog.class.getSimpleName();
    private static final String ADD_DIALOG_PROJECT_ARG = "add_dialog_project_argument";
    public static final int REQUEST_CODE = AddOperationDialog.class.hashCode();
    public static final String ADD_OPERATION_PROJECT_EXTRA = "add_operation_project_extra";

    private DialogAddOperationBinding binding;
    private CurrencyAdapter adapter;
    private int defaultSelection;

    public static AddOperationDialog newInstance(Project project) {
        final AddOperationDialog dialog = new AddOperationDialog();
        final Bundle args = new Bundle();
        args.putParcelable(ADD_DIALOG_PROJECT_ARG, Parcels.wrap(project));
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Project project = Parcels.unwrap(getArguments().getParcelable(ADD_DIALOG_PROJECT_ARG));
        presenter = new AddOperationPresenter(project, Injection.provideGetCurrencies(), Injection.provideAddOperation());
        adapter = CurrencyAdapter.newInstance(getContext(), new ArrayList<>());
        presenter.loadCurrencies();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        binding = DialogAddOperationBinding.inflate(LayoutInflater.from(getContext()), null, false);
        binding.spinnerCurrency.setAdapter(adapter);
        return new AlertDialog.Builder(getContext())
                .setTitle(R.string.add_project)
                .setPositiveButton(R.string.save, (dialogInterface, i) -> {
                    presenter.onNewOperationSaved(binding.editName.getText().toString(),
                            (Currency) binding.spinnerCurrency.getSelectedItem(),
                            binding.editAmount.getText().toString());
                    dismiss();
                })
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> dismiss())
                .setView(binding.getRoot())
                .create();
    }

    @Override
    public void close(int result, Project project) {
        final Intent data = new Intent();
        data.putExtra(ADD_OPERATION_PROJECT_EXTRA, Parcels.wrap(project));
        getTargetFragment().onActivityResult(getTargetRequestCode(), result, data);
    }

    @Override
    public void setCurrencies(List<Currency> currencies, String defaultCurrency) {
        adapter = CurrencyAdapter.newInstance(getContext(), currencies);
        defaultSelection = 0;
        for (int i = 0; i < currencies.size(); i++) {
            if (currencies.get(i).getCode().equals(defaultCurrency)) {
                defaultSelection = i;
                break;
            }
        }
        if (binding != null) {
            binding.spinnerCurrency.setAdapter(adapter);
            binding.spinnerCurrency.setSelection(defaultSelection);
        }
    }
}