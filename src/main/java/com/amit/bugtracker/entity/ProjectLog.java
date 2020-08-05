package com.amit.bugtracker.entity;

import javax.persistence.*;

@Entity
public class ProjectLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "operation")
    private String operation;

    @Column(name = "creation_date")
    private String creationDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public ProjectLog() {
    }

    public ProjectLog(String operation, String creationDate, Project project, User user) {
        this.operation = operation;
        this.creationDate = creationDate;
        this.project = project;
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

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ProjectLog{" +
                "id=" + id +
                ", operation='" + operation + '\'' +
                ", creationDate='" + creationDate + '\'' +
                ", project=" + project.getName() +
                ", user=" + user.getUserName() +
                '}';
    }
}
