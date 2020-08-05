package com.amit.bugtracker.entity;

import javax.persistence.*;

@Entity
public class TicketLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "operation")
    private String operation;

    @Column(name = "creation_date")
    private String creationDate;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public TicketLog() {
    }

    public TicketLog(String operation, String creationDate, Ticket ticket, User user) {
        this.operation = operation;
        this.creationDate = creationDate;
        this.ticket = ticket;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOperation() {
        return operation;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "TicketLog{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", ticket=" + ticket.getTitle() +
                ", user=" + user.getUserName() +
                '}';
    }
}
