package com.amit.bugtracker.entity;

import com.amit.bugtracker.validation.ValidEmail;
import com.amit.bugtracker.validation.ValidName;
import com.amit.bugtracker.validation.ValidUsername;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ValidUsername
    @Column(name = "username")
    private String userName;

    @ToString.Exclude
    @NotNull(message = "Password is required")
    @Column(name = "password")
    private String password;

    @ValidName
    @Column(name = "first_name")
    private String firstName;

    @ValidName
    @Column(name = "last_name")
    private String lastName;

    @ValidEmail
    @Column(name = "email")
    private String email;

    @ToString.Exclude
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "users_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;

    @ToString.Exclude
    @OneToMany(mappedBy = "submitter", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @NotNull(message = "Role is required")
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;


    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public boolean isAdmin() {
        for (Role r : roles) {
            if (r.getName().equals("ROLE_ADMIN"))
                return true;
        }
        return false;
    }

    public boolean isManager() {
        for (Role r : roles) {
            if (r.getName().equals("ROLE_MANAGER"))
                return true;
        }
        return false;
    }

    public boolean isAllowedToView(Project project) {
        return project.getUsers().contains(this) || isManager() || isAdmin();
    }

    public boolean isAllowedToEditAndDelete(Ticket ticket) {
        return ticket.getSubmitter().equals(this) || isManager() || isAdmin();
    }

    public boolean isAllowedToDeleteComment(Comment comment) {
        return comment.getUser().equals(this) || isManager() || isAdmin();
    }

}
