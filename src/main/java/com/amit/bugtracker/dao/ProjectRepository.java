package com.amit.bugtracker.dao;

import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findAllByUsersContains(User user);

    List<Project> findAllByNameIsContaining(String name);

}