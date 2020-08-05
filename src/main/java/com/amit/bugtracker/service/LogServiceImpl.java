package com.amit.bugtracker.service;

import com.amit.bugtracker.dao.TicketLogRepository;
import com.amit.bugtracker.entity.TicketLog;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    private final TicketLogRepository ticketLogRepository;

    public LogServiceImpl(TicketLogRepository ticketLogRepository) {
        this.ticketLogRepository = ticketLogRepository;
    }

    @Override
    public void save(TicketLog ticketLog) {
        ticketLogRepository.save(ticketLog);
    }

    @Override
    public void delete(TicketLog ticketLog) {
        ticketLogRepository.delete(ticketLog);
    }
}
