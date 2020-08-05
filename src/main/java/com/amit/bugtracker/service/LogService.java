package com.amit.bugtracker.service;

import com.amit.bugtracker.entity.ProjectLog;
import com.amit.bugtracker.entity.TicketLog;

public interface LogService {

    void saveTicketLog(TicketLog ticketLog);

    void deleteTicketLog(TicketLog ticketLog);

    void saveProjectLog(ProjectLog projectLog);

    void deleteProjectLog(ProjectLog projectLog);

}
