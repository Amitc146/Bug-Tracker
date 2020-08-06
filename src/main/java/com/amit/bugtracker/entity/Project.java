package com.amit.bugtracker.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ToString.Exclude
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @ToString.Exclude
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "users_projects",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;

    @ToString.Exclude
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<ProjectLog> logs;


    public List<Ticket> getOpenTickets() {
        List<Ticket> tempTickets = new ArrayList<>();
        if (tickets != null) {
            for (Ticket t : tickets) {
                if (t.isOpen())
                    tempTickets.add(t);
            }
        }
        return tempTickets;
    }

}
