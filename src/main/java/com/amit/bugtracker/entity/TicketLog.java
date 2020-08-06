package com.amit.bugtracker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "ticket_log")
@Data
@NoArgsConstructor
public class TicketLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "operation")
    private String operation;

    @Column(name = "creation_date")
    private String creationDate;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public TicketLog(String operation, String creationDate, Ticket ticket, User user) {
        this.operation = operation;
        this.creationDate = creationDate;
        this.ticket = ticket;
        this.user = user;
    }

}
