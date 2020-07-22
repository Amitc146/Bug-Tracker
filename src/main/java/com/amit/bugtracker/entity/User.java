package com.amit.bugtracker.entity;

import com.amit.bugtracker.validation.ValidEmail;
import com.amit.bugtracker.validation.ValidUsername;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ValidUsername
    @Column(name = "username")
    private String userName;

    @NotNull(message = "Password is required")
    @Column(name = "password")
    private String password;

    @NotNull(message = "First name is required")
    @Column(name = "first_name")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Column(name = "last_name")
    private String lastName;

    @ValidEmail
    @Column(name = "email")
    private String email;

    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "users_projects",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id"))
    private List<Project> projects;

    @OneToMany(mappedBy = "submitter", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Comment> comments;

    @NotNull(message = "Role is required")
    @ManyToMany(fetch = FetchType.LAZY,
            cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;

    public User() {
    }

    public User(String userName, String password, String firstName, String lastName, String email) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public User(String userName, String password, String firstName, String lastName, String email,
                Collection<Role> roles) {
        this.userName = userName;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Collection<Role> getRoles() {
        return roles;
    }

    public void setRoles(Collection<Role> roles) {
        this.roles = roles;
    }

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

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

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public boolean equals(User user) {
        return this.id.equals(user.getId());
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

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='*********'" +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                '}';
    }


}
