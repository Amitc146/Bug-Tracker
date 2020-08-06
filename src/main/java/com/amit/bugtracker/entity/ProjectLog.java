package com.amit.bugtracker.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "project_log")
@Data
@NoArgsConstructor
public class ProjectLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "operation")
    private String operation;

    @Column(name = "creation_date")
    private String creationDate;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ProjectLog(String operation, String creationDate, Project project, User user) {
        this.operation = operation;
        this.creationDate = creationDate;
        this.project = project;
        this.user = user;
    }

}
