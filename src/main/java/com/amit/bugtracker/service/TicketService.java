package com.amit.bugtracker.service;

import com.amit.bugtracker.dto.ChartData;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import com.amit.bugtracker.entity.User;

import java.util.List;

public interface TicketService {

    Ticket findById(Integer id);

    List<Ticket> findAll();

    List<Ticket> findAllByTitle(String title);

    List<Ticket> findAllOpen();

    List<Ticket> findAllClosed();

    List<Ticket> findAllOpenByProject(Project project);

    List<Ticket> findAllClosedByProject(Project project);

    List<Ticket> findAllOpenByUser(User user);

    List<Ticket> findAllClosedByUser(User user);

    List<Ticket> findAllByUserAndTitle(User user, String title);

    void save(Ticket ticket);

    void deleteById(Integer id);

    List<ChartData> getPrioritiesChartData();

    List<ChartData> getProjectsChartData();

    List<ChartData> getStatusChartData();

}
