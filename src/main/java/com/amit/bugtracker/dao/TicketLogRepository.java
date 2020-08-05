package com.amit.bugtracker.dao;

import com.amit.bugtracker.entity.TicketLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketLogRepository extends JpaRepository<TicketLog, Integer> {
}
