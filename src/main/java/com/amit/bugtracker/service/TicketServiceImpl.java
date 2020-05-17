package com.amit.bugtracker.service;

import com.amit.bugtracker.chart.ChartData;
import com.amit.bugtracker.dao.CommentRepository;
import com.amit.bugtracker.dao.TicketRepository;
import com.amit.bugtracker.entity.Comment;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;
    private final CommentRepository commentRepository;

    public TicketServiceImpl(TicketRepository ticketRepository, CommentRepository commentRepository) {
        this.ticketRepository = ticketRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Ticket> findAll() {
        List<Ticket> tickets = ticketRepository.findAll();
        sortByStatusAndPriority(tickets);

        return tickets;
    }

    @Override
    public Ticket findById(Integer id) {
        Optional<Ticket> result = ticketRepository.findById(id);

        Ticket ticket;
        if (result.isPresent()) {
            ticket = result.get();
        } else {
            throw new RuntimeException("Did not find ticket id - " + id);
        }

        return ticket;
    }

    @Override
    public List<Ticket> findAllByProject(Project project) {
        List<Ticket> tickets = ticketRepository.findAllByProject(project);
        sortByStatusAndPriority(tickets);

        return tickets;
    }

    @Override
    public List<Ticket> findAllByProjects(List<Project> projects) {
        List<Ticket> tickets = new ArrayList<>();
        for (Project p : projects) {
            tickets.addAll(ticketRepository.findAllByProject(p));
        }

        sortByStatusAndPriority(tickets);

        return tickets;
    }

    @Override
    public void save(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public void deleteById(Integer id) {
        ticketRepository.deleteById(id);
    }

    @Override
    public List<ChartData> getAllPriorities() {
        return ticketRepository.getAllPriorities();
    }

    @Override
    public List<ChartData> getAllProjects() {
        return ticketRepository.getAllProjects();
    }

    private void sortByStatusAndPriority(List<Ticket> tickets) {
        tickets.sort(Comparator.comparing(Ticket::getPriority).reversed());
        tickets.sort(Comparator.comparing(Ticket::getStatus));
    }

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(Integer id) {
        commentRepository.deleteById(id);
    }

    @Override
    public Comment findCommentById(Integer id) {
        Optional<Comment> result = commentRepository.findById(id);

        Comment comment;
        if (result.isPresent()) {
            comment = result.get();
        } else {
            throw new RuntimeException("Did not find comment id - " + id);
        }

        return comment;
    }
}
