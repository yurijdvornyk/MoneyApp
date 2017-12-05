package com.example.yuriidvornyk.moneyapp.presentation.projectdetails;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuriidvornyk.moneyapp.R;
import com.example.yuriidvornyk.moneyapp.data.Injection;
import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.data.model.Operation;
import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.databinding.DialogAddOperationBinding;
import com.example.yuriidvornyk.moneyapp.databinding.FragmentProjectDetailsBinding;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseFragment;
import com.example.yuriidvornyk.moneyapp.presentation.projects.CurrencyAdapter;
import com.example.yuriidvornyk.moneyapp.presentation.utils.OperationUtils;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-11-24.
 */
public class ProjectDetailsFragment extends BaseFragment<ProjectDetailsContract.Presenter>
        implements ProjectDetailsContract.View {

    private static final String ARG_PROJECT_DETAILS_PROJECT = "arg_project_details_project";

    private FragmentProjectDetailsBinding binding;
    private OperationsAdapter adapter;

    public static ProjectDetailsFragment newInstance(Project project) {
        final ProjectDetailsFragment fragment = new ProjectDetailsFragment();
        final Bundle arguments = new Bundle();
        arguments.putParcelable(ARG_PROJECT_DETAILS_PROJECT, Parcels.wrap(project));
        fragment.setArguments(arguments);
        return fragment;
    }

    public ProjectDetailsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Project project = Parcels.unwrap(getArguments().getParcelable(ARG_PROJECT_DETAILS_PROJECT));
        presenter = new ProjectDetailsPresenter(project, Injection.provideGetBalance(),
                Injection.provideGetOperations(), Injection.provideGetCurrencies(), Injection.provideAddOperation(),
                Injection.provideGetRate());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details, container, false);
        adapter = new OperationsAdapter(getContext());
        binding.recyclerOperations.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.recyclerOperations.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(
                binding.recyclerOperations.getContext(), layoutManager.getOrientation());
        binding.recyclerOperations.addItemDecoration(dividerItemDecoration);
        getAppCompatActivity().setSupportActionBar(binding.toolbar);
        getAppCompatActivity().getSupportActionBar().setHomeButtonEnabled(true);
        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24px);
        binding.toolbar.setTitle(R.string.project_details);
        binding.fabAddOperation.setOnClickListener(view -> presenter.onAddOperationClicked());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void showProjectData(Project project) {
        binding.textProjectTitle.setText(project.getName());
    }

    @Override
    public void showMoneyBalance(double balance, String currency) {
        binding.textBalance.setText(OperationUtils.formatBalance(getContext(), balance, currency));
    }

    @Override
    public void showOperations(List<Operation> operations) {
        adapter.setOperations(operations);
    }

    @Override
    public void showAddOperationDialog(List<Currency> currencies, String defaultCurrency) {
        final CurrencyAdapter adapter = new CurrencyAdapter(getContext(), R.layout.item_currency_spinner, currencies);
        adapter.setDropDownViewResource(R.layout.item_currency_spinner);

        final DialogAddOperationBinding addOperationDialogBinding =
                DialogAddOperationBinding.inflate(LayoutInflater.from(getContext()), (ViewGroup) getView().getParent(), false);
        addOperationDialogBinding.spinnerCurrency.setAdapter(adapter);
        for (int i = 0; i < currencies.size(); i++) {
            if (currencies.get(i).getCode().equals(defaultCurrency)) {
                addOperationDialogBinding.spinnerCurrency.setSelection(i);
                break;
            }
        }
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.add_project)
                .setPositiveButton(R.string.save, (dialogInterface, i) ->
                        presenter.onNewOperationSaved(addOperationDialogBinding.editName.getText().toString(),
                                (Currency) addOperationDialogBinding.spinnerCurrency.getSelectedItem(),
                                addOperationDialogBinding.editAmount.getText().toString()))
                .setNegativeButton(R.string.cancel, null)
                .setView(addOperationDialogBinding.getRoot())
                .show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
