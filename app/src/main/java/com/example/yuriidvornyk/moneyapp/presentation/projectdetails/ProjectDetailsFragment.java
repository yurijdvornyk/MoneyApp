package com.example.yuriidvornyk.moneyapp.presentation.projectdetails;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuriidvornyk.moneyapp.R;
import com.example.yuriidvornyk.moneyapp.data.Injection;
import com.example.yuriidvornyk.moneyapp.data.model.Operation;
import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.databinding.FragmentProjectDetailsBinding;
import com.example.yuriidvornyk.moneyapp.presentation.addoperation.AddOperationDialog;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseFragment;
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
    private AddOperationDialog addOperationDialog;

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
                Injection.provideGetOperations(), Injection.provideGetCurrencies(), Injection.provideAddOperation());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project_details, container, false);
        adapter = new OperationsAdapter(getContext());
        binding.recyclerOperations.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);
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
    public void showAddOperationDialog(Project project) {
        addOperationDialog = AddOperationDialog.newInstance(project);
        addOperationDialog.setTargetFragment(this, AddOperationDialog.REQUEST_CODE);
        addOperationDialog.show(getAppCompatActivity().getSupportFragmentManager(), AddOperationDialog.TAG);
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

    @Override
    public void onStop() {
        super.onStop();
        if (addOperationDialog != null && addOperationDialog.isVisible()) {
            addOperationDialog.close(Activity.RESULT_CANCELED, null);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddOperationDialog.REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            presenter.start();
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
