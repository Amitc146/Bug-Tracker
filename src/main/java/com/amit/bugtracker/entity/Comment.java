package com.amit.bugtracker.entity;

import javax.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text")
    private String text;

    @Column(name = "creation_date")
    private String creationDate;

    @ManyToOne()
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    public Comment() {
    }

    public Comment(String text, String creationDate, Ticket ticket, User user) {
        this.text = text;
        this.creationDate = creationDate;
        this.ticket = ticket;
        this.user = user;
    }

    public Comment(Ticket ticket, User user) {
        this.ticket = ticket;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
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
        return "Comment{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }
}
