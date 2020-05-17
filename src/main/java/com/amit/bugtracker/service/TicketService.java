package com.amit.bugtracker.service;

import com.amit.bugtracker.chart.ChartData;
import com.amit.bugtracker.entity.Comment;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;

import java.util.List;

public interface TicketService {

    List<Ticket> findAll();

    Ticket findById(Integer id);

    List<Ticket> findAllByProject(Project project);

    List<Ticket> findAllByProjects(List<Project> projects);

    void save(Ticket ticket);

    void deleteById(Integer id);

    List<ChartData> getAllPriorities();

    List<ChartData> getAllProjects();

    void saveComment(Comment comment);

    void deleteCommentById(Integer id);

    Comment findCommentById(Integer id);

}
