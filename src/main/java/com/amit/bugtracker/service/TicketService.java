package com.amit.bugtracker.service;

import com.amit.bugtracker.dto.ChartData;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import com.amit.bugtracker.entity.User;

import java.util.List;

public interface TicketService {

    List<Ticket> findAll();

    Ticket findById(Integer id);

    List<Ticket> findAllByStatus(String status);

    List<Ticket> findAllByProjectAndStatus(Project project, String status);

    List<Ticket> findAllByUserAndStatus(User user, String status);

    void save(Ticket ticket);

    void deleteById(Integer id);

    List<ChartData> getPrioritiesChartData();

    List<ChartData> getProjectsChartData();

    List<ChartData> getStatusChartData();

    List<Ticket> findAllByName(String name);

}
