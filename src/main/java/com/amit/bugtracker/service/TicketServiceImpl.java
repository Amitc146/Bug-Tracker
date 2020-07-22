package com.amit.bugtracker.service;

import com.amit.bugtracker.dto.ChartData;
import com.amit.bugtracker.dao.TicketRepository;
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

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
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
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public List<Ticket> findAllByTitle(String title) {
        List<Ticket> tickets = ticketRepository.findAllByTitleIsContaining(title);
        return sortByPriority(tickets);
    }

    @Override
    public List<Ticket> findAllOpen() {
        List<Ticket> tickets = ticketRepository.findAllByStatus(TicketStatus.OPEN);
        return sortByPriority(tickets);
    }

    @Override
    public List<Ticket> findAllClosed() {
        List<Ticket> tickets = ticketRepository.findAllByStatus(TicketStatus.CLOSED);
        return sortByPriority(tickets);
    }

    @Override
    public List<Ticket> findAllByProject(Project project) {
        List<Ticket> tickets = ticketRepository.findAllByProject(project);
        return sortByPriority(tickets);
    }

    @Override
    public List<Ticket> findAllOpenByProject(Project project) {
        List<Ticket> tickets = ticketRepository.findAllByProjectAndStatus(project, TicketStatus.OPEN);
        return sortByPriority(tickets);
    }

    @Override
    public List<Ticket> findAllClosedByProject(Project project) {
        List<Ticket> tickets = ticketRepository.findAllByProjectAndStatus(project, TicketStatus.CLOSED);
        return sortByPriority(tickets);
    }

    @Override
    public List<Ticket> findAllByUser(User user) {
        List<Ticket> tickets = ticketRepository.findAllByProjectUsersContaining(user);
        return sortByPriority(tickets);
    }

    @Override
    public List<Ticket> findAllOpenByUser(User user) {
        List<Ticket> tickets = ticketRepository.findAllByProjectUsersContainingAndStatus(user, TicketStatus.OPEN);
        return sortByPriority(tickets);
    }

    @Override
    public List<Ticket> findAllClosedByUser(User user) {
        List<Ticket> tickets = ticketRepository.findAllByProjectUsersContainingAndStatus(user, TicketStatus.CLOSED);
        return sortByPriority(tickets);
    }

    @Override
    public List<Ticket> findAllByUserAndTitle(User user, String title) {
        if (title == null || title.isEmpty() || title.trim().isEmpty())
            return null;

        List<Ticket> tickets = ticketRepository.findAllByProjectUsersContainingAndTitleIsContaining(user, title);
        return sortByPriority(tickets);
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
    public List<ChartData> getPrioritiesChartData() {
        return ticketRepository.getPrioritiesChartData();
    }

    @Override
    public List<ChartData> getProjectsChartData() {
        return ticketRepository.getProjectsChartData();
    }

    @Override
    public List<ChartData> getStatusChartData() {
        return ticketRepository.getStatusChartData();
    }

    private List<Ticket> sortByPriority(List<Ticket> tickets) {
        tickets.sort(Comparator.comparing(Ticket::getPriority).reversed());
        return tickets;
    }

}
