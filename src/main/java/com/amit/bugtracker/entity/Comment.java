package com.amit.bugtracker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "text")
    private String text;

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

    public Comment(Ticket ticket, User user) {
        this.ticket = ticket;
        this.user = user;
    }

}
