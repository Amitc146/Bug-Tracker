package com.amit.bugtracker.service;

import com.amit.bugtracker.chart.ChartData;
import com.amit.bugtracker.entity.Comment;
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

    List<ChartData> getAllPriorities();

    List<ChartData> getAllProjects();

    void saveComment(Comment comment);

    void deleteCommentById(Integer id);

    void deleteComment(Comment comment);

    Comment findCommentById(Integer id);

}
