package com.example.yuriidvornyk.moneyapp.data.mapper;

import com.example.yuriidvornyk.moneyapp.data.local.DbProject;
import com.example.yuriidvornyk.moneyapp.data.model.Project;

/**
 * Created by yurii.dvornyk on 2017-11-28.
 */

public class ProjectMapper implements Mapper<DbProject, Project> {
    @Override
    public Project fromLocal(DbProject local) {
        final Project project = new Project();
        project.setId(local.getId());
        project.setName(local.getName());
        project.setDefaultCurrency(local.getDefaultCurrency());
        project.setCreateTime(local.getCreateTime());
        return project;
    }

    @Override
    public DbProject toLocal(Project remote) {
        final DbProject project = new DbProject();
        if (remote.getId() != null) {
            project.setId(remote.getId());
        }
        project.setName(remote.getName());
        project.setDefaultCurrency(remote.getDefaultCurrency());
        project.setCreateTime(remote.getCreateTime());
        return project;
    }
}
