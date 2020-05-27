package com.amit.bugtracker.service;

import com.amit.bugtracker.chart.ChartData;
import com.amit.bugtracker.dao.ProjectRepository;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Project findById(Integer id) {
        Optional<Project> result = projectRepository.findById(id);

        Project project;
        if (result.isPresent()) {
            project = result.get();
        } else {
            throw new RuntimeException("Did not find project id - " + id);
        }

        return project;
    }

    @Override
    public List<Project> findAllByUser(User user) {
        return projectRepository.findAllByUsersContains(user);
    }

    @Override
    public void save(Project project) {
        projectRepository.save(project);
    }

    @Override
    public void deleteById(Integer id) {
        projectRepository.deleteById(id);
    }

    @Override
    public List<ChartData> getUserCount() {
        return projectRepository.getUserCount();
    }
}
