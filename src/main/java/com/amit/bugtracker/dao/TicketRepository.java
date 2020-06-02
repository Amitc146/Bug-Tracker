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

    @Query(nativeQuery = true, value = "SELECT lower(priority) as label, COUNT(*) as value " +
            "FROM ticket " +
            "WHERE ticket.status = 'OPEN'" +
            "GROUP BY priority")
    List<ChartData> getPrioritiesChartData();

    @Query(nativeQuery = true, value = "SELECT name as label, COUNT(*) as value " +
            "FROM ticket " +
            "INNER JOIN project " +
            "ON project.id = ticket.project_id " +
            "WHERE ticket.status = 'OPEN'" +
            "GROUP BY project_id")
    List<ChartData> getProjectsChartData();

    @Query(nativeQuery = true, value = "SELECT lower(status) as label, count(*) as value " +
            "FROM ticket " +
            "GROUP BY status")
    List<ChartData> getStatusChartData();

    List<Ticket> findAllByTitleIsContaining(String name);

}
