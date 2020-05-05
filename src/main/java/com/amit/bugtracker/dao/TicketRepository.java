package com.amit.bugtracker.dao;

import com.amit.bugtracker.chart.ChartData;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query(nativeQuery = true, value = "SELECT priority as label, COUNT(*) as value " +
            "FROM ticket " +
            "GROUP BY priority")
    List<ChartData> getAllPriorities();

    @Query(nativeQuery = true, value = "SELECT project_id as label, COUNT(*) as value " +
            "FROM ticket " +
            "GROUP BY project_id")
    List<ChartData> getAllProjects();

    List<Ticket> findAllByProject(Project project);

}
