package com.amit.bugtracker.dao;

import com.amit.bugtracker.chart.ChartData;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findAllByUsersContains(User user);

    @Query(nativeQuery = true, value = "SELECT name as label, COUNT(*) as value " +
            "FROM users_projects " +
            "INNER JOIN project ON project_id = project.id " +
            "GROUP BY project_id")
    List<ChartData> getUserCount();

}