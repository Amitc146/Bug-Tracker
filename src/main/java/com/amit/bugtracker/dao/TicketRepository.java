package com.amit.bugtracker.dao;

import com.amit.bugtracker.dto.ChartData;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import com.amit.bugtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static com.amit.bugtracker.entity.Ticket.*;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    List<Ticket> findAllByStatus(TicketStatus status);

    List<Ticket> findAllByProjectAndStatus(Project project, TicketStatus status);

    List<Ticket> findAllByProjectUsersContainingAndStatus(User user, TicketStatus status);

    List<Ticket> findAllByTitleIsContaining(String text);

    List<Ticket> findAllByProjectUsersContainingAndTitleIsContaining(User user, String text);

    @Query(nativeQuery = true, value =
            "SELECT lower(priority) AS label, COUNT(*) AS value " +
            "FROM ticket t " +
            "WHERE t.status = 'OPEN'" +
            "GROUP BY priority")
    List<ChartData> getPrioritiesChartData();

    @Query(nativeQuery = true, value =
            "SELECT name AS label, COUNT(*) AS value " +
            "FROM ticket t " +
            "INNER JOIN project ON project.id = t.project_id " +
            "WHERE t.status = 'OPEN'" +
            "GROUP BY project_id")
    List<ChartData> getProjectsChartData();

    @Query(nativeQuery = true, value =
            "SELECT LOWER(status) AS label, COUNT(*) AS value " +
            "FROM ticket " +
            "GROUP BY status")
    List<ChartData> getStatusChartData();

}
