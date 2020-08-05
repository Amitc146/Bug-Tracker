package com.amit.bugtracker.dao;

import com.amit.bugtracker.entity.ProjectLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectLogRepository extends JpaRepository<ProjectLog, Integer> {
}
