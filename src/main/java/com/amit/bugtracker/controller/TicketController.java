package com.amit.bugtracker.controller;

import com.amit.bugtracker.entity.*;
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

        model.addAttribute("openTickets", openTickets);
        model.addAttribute("closedTickets", closedTickets);

        return "tickets/list-tickets";
    }

    @GetMapping("/allTickets")
    public String listAllTickets(Model model) {
        List<Ticket> openTickets = ticketService.findAllByStatus("open");
        List<Ticket> closedTickets = ticketService.findAllByStatus("closed");

        model.addAttribute("openTickets", openTickets);
        model.addAttribute("closedTickets", closedTickets);

        return "tickets/list-tickets";
    }


    @GetMapping("/{ticketId}")
    public String showTicket(@PathVariable int ticketId, Authentication auth, Model model) {
        User user = userService.findByUserName(auth.getName());
        Ticket ticket = ticketService.findById(ticketId);

        if (isAllowedToView(user, ticket)) {
            // Adding a comment in case the user will try to add one
            model.addAttribute("comment", new Comment(ticket, user));
            model.addAttribute("ticket", ticket);

            return "/tickets/ticket-page";
        }

        return "error-pages/access-denied";
    }


    @GetMapping("/new")
    public String createNewTicket(@RequestParam(value = "project", required = false) Integer projectId,
                                  Authentication auth, Model model) {

        User user = userService.findByUserName(auth.getName());
        Ticket ticket = new Ticket();
        List<Project> projects;

        // If project selected
        if (projectId != null && projectId > 0) {
            Project project = projectService.findById(projectId);

            // Check if the user has permission to add a ticket to this project
            if (!project.getUsers().contains(user) && !user.isManager() && !user.isAdmin()) {
                return "error-pages/access-denied";
            }

            projects = new ArrayList<>();
            projects.add(project);
        }

        // If no project selected
        else {
            projects = projectService.findAllByUser(user);
        }

        ticket.setSubmitter(user);
        ticket.setStatus(Ticket.TicketStatus.OPEN);
        ticket.setPriority(Ticket.TicketPriority.LOW);
        ticket.setCreationDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        model.addAttribute("ticket", ticket);
        model.addAttribute("projects", projects);

        return "tickets/ticket-form";
    }

    @GetMapping("/{ticketId}/update")
    public String updateTicket(@PathVariable("ticketId") int ticketId, Authentication auth, Model model) {
        User user = userService.findByUserName(auth.getName());
        Ticket ticket = ticketService.findById(ticketId);

        // Only the submitter, admin or manager are allowed to update the ticket
        if (isAllowedToEdit(user, ticket)) {
            List<Project> projects = projectService.findAllByUser(user);

            model.addAttribute("ticket", ticket);
            model.addAttribute("projects", projects);

            return "tickets/ticket-form";
        }

        return "error-pages/access-denied";
    }

    @PostMapping("/save")
    public String saveTicket(@ModelAttribute("ticket") Ticket ticket) {
        ticketService.save(ticket);

        return "redirect:/tickets/" + ticket.getId();
    }

    @GetMapping("/delete")
    public String deleteTicket(@RequestParam("ticketId") int id, Authentication auth) {
        User user = userService.findByUserName(auth.getName());
        Ticket ticket = ticketService.findById(id);

        if (isAllowedToEdit(user, ticket)) {
            ticketService.deleteById(id);
            return "redirect:/tickets/myTickets";
        }

        return "error-pages/access-denied";
    }

    @PostMapping("/{ticketId}/saveComment")
    public String saveComment(@ModelAttribute("comment") Comment comment, @PathVariable("ticketId") int ticketId) {
        comment.setCreationDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        commentService.save(comment);

        return "redirect:/tickets/" + ticketId;
    }

    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam("commentId") int commentId, Authentication auth) {
        User user = userService.findByUserName(auth.getName());
        Comment comment = commentService.findById(commentId);

        // Only the commenter, admin or manager are allowed to delete the comment
        if (!user.equals(comment.getUser()) && !user.isAdmin() && !user.isManager()) {
            return "error-pages/access-denied";
        }

        int ticketId = comment.getTicket().getId();
        commentService.delete(comment);

        return "redirect:/tickets/" + ticketId;
    }

    private boolean isAllowedToView(User user, Ticket ticket) {
        return (ticket.getProject().getUsers().contains(user) || user.isManager() || user.isAdmin());
    }

    private boolean isAllowedToEdit(User user, Ticket ticket) {
        return (ticket.getSubmitter().equals(user) || user.isManager() || user.isAdmin());
    }
}
