package com.amit.bugtracker.controller;

import com.amit.bugtracker.demo.DemoUserService;
import com.amit.bugtracker.entity.Comment;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.exception.AccessDeniedException;
import com.amit.bugtracker.service.CommentService;
import com.amit.bugtracker.service.ProjectService;
import com.amit.bugtracker.service.TicketService;
import com.amit.bugtracker.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final ProjectService projectService;
    private final UserService userService;
    private final CommentService commentService;


    public TicketController(TicketService ticketService, ProjectService projectService, UserService userService, CommentService commentService) {
        this.ticketService = ticketService;
        this.projectService = projectService;
        this.userService = userService;
        this.commentService = commentService;
    }


    @GetMapping("/myTickets")
    public String listUserTickets(Authentication auth, Model model) {
        User user = userService.findByUserName(auth.getName());
        List<Ticket> openTickets = ticketService.findAllByUserAndStatus(user, "open");
        List<Ticket> closedTickets = ticketService.findAllByUserAndStatus(user, "closed");
        createTicketsList(model, openTickets, closedTickets);

        return "tickets/list-tickets";
    }


    @GetMapping("/allTickets")
    public String listAllTickets(Model model) {
        List<Ticket> openTickets = ticketService.findAllByStatus("open");
        List<Ticket> closedTickets = ticketService.findAllByStatus("closed");
        createTicketsList(model, openTickets, closedTickets);

        return "tickets/list-tickets";
    }


    @GetMapping("/{ticketId}")
    public String showTicket(@PathVariable int ticketId, Authentication auth, Model model) {
        User user = userService.findByUserName(auth.getName());
        Ticket ticket = ticketService.findById(ticketId);

        if (!isAllowedToViewAndAddTicket(user, ticket.getProject()))
            throw new AccessDeniedException();

        createTicketPage(model, ticket, user);

        return "tickets/ticket-page";
    }


    @GetMapping("/new")
    public String createNewTicket(@RequestParam(value = "project", required = false) Integer projectId,
                                  Authentication auth, Model model) {

        User user = userService.findByUserName(auth.getName());
        List<Project> projects = getProjectsList(user, projectId);
        Ticket ticket = Ticket.createEmptyTicket(user, getCurrentTime());
        createTicketForm(model, ticket, projects);

        return "tickets/ticket-form";
    }


    @GetMapping("/{ticketId}/update")
    public String updateTicket(@PathVariable("ticketId") int ticketId, Authentication auth, Model model) {
        User user = userService.findByUserName(auth.getName());
        Ticket ticket = ticketService.findById(ticketId);

        if (!isAllowedToEditAndDeleteTicket(user, ticket))
            throw new AccessDeniedException();

        List<Project> projects = getProjectsAllowedByUser(user);
        createTicketForm(model, ticket, projects);

        return "tickets/ticket-form";
    }


    @PostMapping("/save")
    public String saveTicket(@ModelAttribute("ticket") Ticket ticket, Authentication auth) {
        DemoUserService.demoCheck(userService.findByUserName(auth.getName()));
        ticketService.save(ticket);

        return "redirect:/tickets/" + ticket.getId();
    }


    @GetMapping("/delete")
    public String deleteTicket(@RequestParam("ticketId") int id, Authentication auth) {
        User user = userService.findByUserName(auth.getName());
        Ticket ticket = ticketService.findById(id);
        DemoUserService.demoCheck(user);

        if (!isAllowedToEditAndDeleteTicket(user, ticket))
            throw new AccessDeniedException();

        ticketService.deleteById(id);

        return "redirect:/tickets/myTickets";
    }


    @PostMapping("/{ticketId}/saveComment")
    public String saveComment(@ModelAttribute("comment") Comment comment,
                              @PathVariable("ticketId") int ticketId, Authentication auth) {

        DemoUserService.demoCheck(userService.findByUserName(auth.getName()));
        comment.setCreationDate(getCurrentTime());
        commentService.save(comment);

        return "redirect:/tickets/" + ticketId;
    }


    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam("commentId") int commentId, Authentication auth) {
        User user = userService.findByUserName(auth.getName());
        DemoUserService.demoCheck(user);
        Comment comment = commentService.findById(commentId);

        if (!isAllowedToDeleteComment(comment, user))
            throw new AccessDeniedException();

        int ticketId = comment.getTicket().getId();
        commentService.delete(comment);

        return "redirect:/tickets/" + ticketId;
    }


    private void createTicketsList(Model model, List<Ticket> openTickets, List<Ticket> closedTickets) {
        model.addAttribute("openTickets", openTickets);
        model.addAttribute("closedTickets", closedTickets);
    }

    private void createTicketPage(Model model, Ticket ticket, User user) {
        // Adding a comment in case the user will try to add one
        model.addAttribute("comment", new Comment(ticket, user));
        model.addAttribute("ticket", ticket);
    }


    private List<Project> getProjectsList(User user, Integer projectId) {
        if (projectId != null && projectId > 0)
            return getProjectListWithProjectSelected(user, projectId);

        return getProjectListWithoutProjectSelected(user);
    }


    private List<Project> getProjectListWithProjectSelected(User user, int projectId) {
        Project project = projectService.findById(projectId);

        if (!isAllowedToViewAndAddTicket(user, project))
            throw new AccessDeniedException();

        List<Project> projects = new ArrayList<>();
        projects.add(project);

        return projects;
    }


    private List<Project> getProjectListWithoutProjectSelected(User user) {
        return getProjectsAllowedByUser(user);
    }


    private void createTicketForm(Model model, Ticket ticket, List<Project> projects) {
        model.addAttribute("projects", projects);
        model.addAttribute("ticket", ticket);
    }


    private boolean isAllowedToViewAndAddTicket(User user, Project project) {
        return (project.containsUser(user) || user.isManager() || user.isAdmin());
    }


    private boolean isAllowedToEditAndDeleteTicket(User user, Ticket ticket) {
        return (ticket.getSubmitter().equals(user) || user.isManager() || user.isAdmin());
    }


    private boolean isAllowedToDeleteComment(Comment comment, User user) {
        return (user.equals(comment.getUser()) || user.isAdmin() || user.isManager());
    }


    private List<Project> getProjectsAllowedByUser(User user) {
        if (user.isAdmin() || user.isManager())
            return projectService.findAll();

        return projectService.findAllByUser(user);
    }


    private String getCurrentTime() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


}
