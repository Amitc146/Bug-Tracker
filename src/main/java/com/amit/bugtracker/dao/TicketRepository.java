package com.amit.bugtracker.dao;

import com.amit.bugtracker.chart.ChartData;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import com.amit.bugtracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

import static com.amit.bugtracker.entity.Ticket.*;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {

    @Query(nativeQuery = true, value = "SELECT priority as label, COUNT(*) as value " +
            "FROM ticket " +
            "WHERE ticket.status = 'OPEN'" +
            "GROUP BY priority")
    List<ChartData> getAllPriorities();

    @Query(nativeQuery = true, value = "SELECT name as label, COUNT(*) as value " +
            "FROM ticket " +
            "INNER JOIN project " +
            "ON project.id = ticket.project_id " +
            "WHERE ticket.status = 'OPEN'" +
            "GROUP BY project_id")
    List<ChartData> getAllProjects();

    List<Ticket> findAllByStatus(TicketStatus status);

    List<Ticket> findAllByProjectAndStatus(Project project, TicketStatus status);

    List<Ticket> findAllByProjectUsersContainingAndStatus(User user, TicketStatus status);

}
