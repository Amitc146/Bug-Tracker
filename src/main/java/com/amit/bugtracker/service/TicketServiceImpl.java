package com.amit.bugtracker.service;

import com.amit.bugtracker.chart.ChartData;
import com.amit.bugtracker.dao.CommentRepository;
import com.amit.bugtracker.dao.TicketRepository;
import com.amit.bugtracker.entity.Comment;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import com.amit.bugtracker.entity.Ticket.TicketStatus;
import com.amit.bugtracker.entity.User;
import org.springframework.stereotype.Service;

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
    public List<Ticket> findAllByProjectAndStatus(Project project, String status) {
        List<Ticket> tickets;

        if (status.toLowerCase().equals("open")) {
            tickets = ticketRepository.findAllByProjectAndStatus(project, TicketStatus.OPEN);
        } else if (status.toLowerCase().equals("closed")) {
            tickets = ticketRepository.findAllByProjectAndStatus(project, TicketStatus.CLOSED);
        } else {
            return null;
        }

        sortByPriority(tickets);
        return tickets;
    }

    @Override
    public List<Ticket> findAllByStatus(String status) {
        List<Ticket> tickets;

        if (status.toLowerCase().equals("open")) {
            tickets = ticketRepository.findAllByStatus(TicketStatus.OPEN);
        } else if (status.toLowerCase().equals("closed")) {
            tickets = ticketRepository.findAllByStatus(TicketStatus.CLOSED);
        } else {
            return null;
        }

        sortByPriority(tickets);
        return tickets;
    }

    @Override
    public List<Ticket> findAllByUserAndStatus(User user, String status) {
        List<Ticket> tickets;

        if (status.toLowerCase().equals("open")) {
            tickets = ticketRepository.findAllByProjectUsersContainingAndStatus(user, TicketStatus.OPEN);
        } else if (status.toLowerCase().equals("closed")) {
            tickets = ticketRepository.findAllByProjectUsersContainingAndStatus(user, TicketStatus.CLOSED);
        } else {
            return null;
        }

        sortByPriority(tickets);
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

    @Override
    public void saveComment(Comment comment) {
        commentRepository.save(comment);
    }

    @Override
    public void deleteCommentById(Integer id) {
        commentRepository.deleteById(id);
    }

    @Override
    public void deleteComment(Comment comment) {
        commentRepository.delete(comment);
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

    private void sortByPriority(List<Ticket> tickets) {
        tickets.sort(Comparator.comparing(Ticket::getPriority).reversed());
    }

}
