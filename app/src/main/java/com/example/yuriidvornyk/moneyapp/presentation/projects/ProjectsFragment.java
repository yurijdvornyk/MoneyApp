package com.example.yuriidvornyk.moneyapp.presentation.projects;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
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
import com.example.yuriidvornyk.moneyapp.presentation.base.BaseFragment;
import com.example.yuriidvornyk.moneyapp.presentation.root.RootContract;

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
        presenter = new ProjectsPresenter(Injection.provideGetProjects(), Injection.provideGetBalance(),
                Injection.provideAddProject(), Injection.provideGetCurrencies());
        adapter = new ProjectsAdapter(getContext(), this);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(),
                LinearLayoutManager.VERTICAL, false);
        binding.recyclerProjects.setLayoutManager(layoutManager);
        binding.recyclerProjects.setAdapter(adapter);
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
    public void setProjects(List<Project> projects) {
        adapter.setProjects(projects);
    }

    @Override
    public void showAddProjectDialog(List<Currency> currencies) {
        final CurrencyAdapter adapter = new CurrencyAdapter(getContext(), R.layout.item_currency_spinner, currencies);
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
    public void setProjectBalance(Pair<Project, Double> projectBalance) {
        adapter.setBalance(projectBalance);
    }

    @Override
    public void onItemClicked(Project project) {
        ((RootContract.View) getActivity()).navigateToProjectDetails(project);
    }
}
