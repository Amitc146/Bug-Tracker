package com.amit.bugtracker.service;

import com.amit.bugtracker.dao.ProjectLogRepository;
import com.amit.bugtracker.dao.TicketLogRepository;
import com.amit.bugtracker.entity.ProjectLog;
import com.amit.bugtracker.entity.TicketLog;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    private final TicketLogRepository ticketLogRepository;
    private final ProjectLogRepository projectLogRepository;

    public LogServiceImpl(TicketLogRepository ticketLogRepository, ProjectLogRepository projectLogRepository) {
        this.ticketLogRepository = ticketLogRepository;
        this.projectLogRepository = projectLogRepository;
    }

    @Override
    public void saveTicketLog(TicketLog ticketLog) {
        ticketLogRepository.save(ticketLog);
    }

    @Override
    public void deleteTicketLog(TicketLog ticketLog) {
        ticketLogRepository.delete(ticketLog);
    }

    @Override
    public void saveProjectLog(ProjectLog projectLog) {
        projectLogRepository.save(projectLog);
    }

    @Override
    public void deleteProjectLog(ProjectLog projectLog) {
        projectLogRepository.delete(projectLog);
    }
}
