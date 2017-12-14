package com.example.yuriidvornyk.moneyapp.presentation.projects;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuriidvornyk.moneyapp.R;
import com.example.yuriidvornyk.moneyapp.data.Injection;
import com.example.yuriidvornyk.moneyapp.data.model.Currency;
import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.databinding.DialogAddProjectBinding;
import com.example.yuriidvornyk.moneyapp.databinding.FragmentProjectsBinding;
import com.example.yuriidvornyk.moneyapp.presentation.addoperation.AddOperationDialog;
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseFragment;
import com.example.yuriidvornyk.moneyapp.presentation.root.RootContract;

import org.parceler.Parcels;

import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-11-22.
 */

public class ProjectsFragment extends BaseFragment<ProjectsContract.Presenter> implements ProjectsContract.View, ProjectsAdapter.OnProjectItemClickListener {

    private FragmentProjectsBinding binding;
    private ProjectsAdapter adapter;
    private AlertDialog addProjectDialog;

    public static ProjectsFragment newInstance() {
        return new ProjectsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_projects, container, false);
        presenter = new ProjectsPresenter(Injection.provideGetProjects(), Injection.provideAddProject(),
                Injection.provideGetCurrencies(), Injection.provideGetBalance());
        adapter = new ProjectsAdapter(getContext(), this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        binding.recyclerProjects.setLayoutManager(layoutManager);
        binding.recyclerProjects.setAdapter(adapter);
        binding.recyclerProjects.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        binding.fabAddProject.setOnClickListener(view -> presenter.onAddProjectClicked());
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.loadProjects();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.detach();
        if (addProjectDialog != null && addProjectDialog.isShowing()) {
            addProjectDialog.dismiss();
        }
    }

    @Override
    public void setProjects(List<Pair<Project, Double>> projects) {
        adapter.setProjects(projects);
    }

    @Override
    public void showAddProjectDialog(List<Currency> currencies) {
        final CurrencyAdapter adapter = CurrencyAdapter.newInstance(getContext(), currencies);
        adapter.setDropDownViewResource(R.layout.item_currency_spinner);
        final DialogAddProjectBinding dialogBinding = DialogAddProjectBinding.inflate(
                LayoutInflater.from(getContext()), (ViewGroup) getView().getParent(), false);
        dialogBinding.spinnerCurrency.setAdapter(adapter);
        addProjectDialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.add_project)
                .setPositiveButton(R.string.save, (dialogInterface, i) ->
                        presenter.onNewProjectSaved(dialogBinding.editName.getText().toString(),
                                (Currency) dialogBinding.spinnerCurrency.getSelectedItem()))
                .setNegativeButton(R.string.cancel, null)
                .setView(dialogBinding.getRoot())
                .show();
    }

    @Override
    public void updateProjectItem(Pair<Project, Double> item) {
        adapter.updateItem(item);
    }

    @Override
    public void onShowDetailsClicked(Project project) {
        ((RootContract.View) getActivity()).navigateToProjectDetails(project);
    }

    @Override
    public void onAddOperationClicked(Project project) {
        final AddOperationDialog dialog = AddOperationDialog.newInstance(project);
        dialog.setTargetFragment(this, AddOperationDialog.REQUEST_CODE);
        dialog.show(getAppCompatActivity().getSupportFragmentManager(), AddOperationDialog.TAG);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == AddOperationDialog.REQUEST_CODE &&
                resultCode == Activity.RESULT_OK &&
                data.hasExtra(AddOperationDialog.ADD_OPERATION_PROJECT_EXTRA)) {
            final Project project = Parcels.unwrap(data.getParcelableExtra(AddOperationDialog.ADD_OPERATION_PROJECT_EXTRA));
            presenter.onOperationAdded(project);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
