package com.amit.bugtracker.service;

import com.amit.bugtracker.entity.TicketLog;

public interface LogService {

    void save(TicketLog ticketLog);

    void delete(TicketLog ticketLog);

}
