package com.amit.bugtracker.dao;

import com.amit.bugtracker.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

}
