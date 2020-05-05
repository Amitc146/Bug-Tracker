package com.amit.bugtracker.service;

import com.amit.bugtracker.chart.ChartData;
import com.amit.bugtracker.dao.TicketRepository;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketServiceImpl implements TicketService {

    private TicketRepository ticketRepository;

    public TicketServiceImpl(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    @Override
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    @Override
    public Ticket findById(int id) {
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
        return project.getTickets();
    }

    @Override
    public List<Ticket> findAllByProjects(List<Project> projects) {
        List<Ticket> tickets = new ArrayList<>();
        for (Project p : projects) {
            tickets.addAll(ticketRepository.findAllByProject(p));
        }

        return tickets;
    }

    @Override
    public void save(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    @Override
    public void deleteById(int id) {
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
}
