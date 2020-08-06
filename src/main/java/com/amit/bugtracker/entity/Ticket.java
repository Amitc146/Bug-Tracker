package com.amit.bugtracker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "creation_date")
    private String creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority")
    private TicketPriority priority;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private TicketStatus status;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "user_id")
    private User submitter;

    @ToString.Exclude
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "project_id")
    private Project project;

    @ToString.Exclude
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ToString.Exclude
    @OneToMany(mappedBy = "ticket", cascade = CascadeType.ALL)
    private List<TicketLog> logs;


    public Ticket(User submitter, TicketStatus status, TicketPriority priority, String creationDate) {
        this.submitter = submitter;
        this.status = status;
        this.priority = priority;
        this.creationDate = creationDate;
    }

    public static Ticket createEmptyTicket(User user, String date) {
        return new Ticket(user, TicketStatus.OPEN, TicketPriority.LOW, date);
    }

    public boolean isOpen() {
        return this.status == TicketStatus.OPEN;
    }

    public enum TicketStatus {
        OPEN("Open"),
        CLOSED("Closed");

        private final String displayValue;

        TicketStatus(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

    public enum TicketPriority {
        LOW("Low"),
        MEDIUM("Medium"),
        HIGH("High");

        private final String displayValue;

        TicketPriority(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }
    }

}

