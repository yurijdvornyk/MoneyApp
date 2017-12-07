package com.example.yuriidvornyk.moneyapp.presentation.projects;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yuriidvornyk.moneyapp.data.model.Project;
import com.example.yuriidvornyk.moneyapp.databinding.ItemProjectsBinding;
import com.example.yuriidvornyk.moneyapp.presentation.utils.OperationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yurii.dvornyk on 2017-11-23.
 */

class ProjectsAdapter extends RecyclerView.Adapter<ProjectsAdapter.ViewHolder> {

    private List<Pair<Project, Double>> projects;
    private Context context;
    private OnProjectItemClickListener listener;

    ProjectsAdapter(Context context, OnProjectItemClickListener listener) {
        this.context = context;
        this.listener = listener;
        projects = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(ItemProjectsBinding.inflate(LayoutInflater.from(context), parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(projects.get(position).first, projects.get(position).second);
    }

    @Override
    public int getItemCount() {
        return projects == null ? 0 : projects.size();
    }

    void setProjects(List<Pair<Project, Double>> projects) {
        this.projects.clear();
        this.projects.addAll(projects);
        notifyDataSetChanged();
    }

    void updateItem(Pair<Project, Double> item) {
        int position = -1;
        for (int i = 0; i < projects.size(); i++) {
            if (projects.get(i).first.getId().equals(item.first.getId())) {
                position = i;
            }
        }
        if (position >= 0) {
            projects.set(position, item);
            notifyItemChanged(position);
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private ItemProjectsBinding binding;

        ViewHolder(ItemProjectsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(final Project project, Double balance) {
            binding.projectName.setText(project.getName());
            binding.buttonDetails.setOnClickListener(view -> listener.onShowDetailsClicked(project));
            binding.buttonAddOperation.setOnClickListener(view -> listener.onAddOperationClicked(project));
            if (balance != null) {
                binding.textBalance.setVisibility(View.VISIBLE);
                binding.textBalance.setText(OperationUtils.formatBalance(context, balance, project.getDefaultCurrency()));
            } else {
                binding.textBalance.setVisibility(View.INVISIBLE);
            }
        }
    }

    interface OnProjectItemClickListener {

        void onShowDetailsClicked(Project project);

        void onAddOperationClicked(Project project);
    }
}
