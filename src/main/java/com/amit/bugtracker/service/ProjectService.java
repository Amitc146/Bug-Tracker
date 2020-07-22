package com.amit.bugtracker.service;

import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.User;

import java.util.List;

public interface ProjectService {

    Project findById(Integer id);

    List<Project> findAll();

    List<Project> findAllByUser(User user);

    List<Project> findAllAllowedByUser(User user);

    List<Project> findAllByName(String name);

    List<Project> findAllByUserAndName(User user, String name);

    void save(Project project);

    void deleteById(Integer id);

}
