package com.amit.bugtracker.entity;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "description")
    private String description;

    @Column(name = "priority")
    private String priority;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE,
            CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "project_id")
    private Project project;

    @JoinColumn(name = "submitter")
    private String submitter;

    @Column(name = "creation_date")
    private String creationDate;

    public Ticket() {
    }


    public Ticket(int id, String description, String priority, Project project, String submitter, String creationDate) {
        this.id = id;
        this.description = description;
        this.priority = priority;
        this.project = project;
        this.submitter = submitter;
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getSubmitter() {
        return submitter;
    }

    public void setSubmitter(String submitter) {
        this.submitter = submitter;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", priority='" + priority + '\'' +
                ", project=" + project +
                ", submitter='" + submitter + '\'' +
                ", creationDate='" + creationDate + '\'' +
                '}';
    }


}

