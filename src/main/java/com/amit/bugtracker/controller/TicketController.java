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

    @GetMapping("/all")
    public String listTickets(Model model) {
        List<Ticket> openTickets = ticketService.findAllByStatus("open");
        List<Ticket> closedTickets = ticketService.findAllByStatus("closed");

        model.addAttribute("openTickets", openTickets);
        model.addAttribute("closedTickets", closedTickets);

        return "tickets/list-tickets";
    }

    @GetMapping
    public String listUserTickets(Authentication auth, Model model) {

        // Getting a list of the user's tickets
        User user = userService.findByUserName(auth.getName());
        List<Ticket> openTickets = ticketService.findAllByUserAndStatus(user, "open");
        List<Ticket> closedTickets = ticketService.findAllByUserAndStatus(user, "closed");

        model.addAttribute("openTickets", openTickets);
        model.addAttribute("closedTickets", closedTickets);

        return "tickets/list-tickets";
    }


    @GetMapping("/{ticketId}")
    public String showTicket(@PathVariable int ticketId, Authentication auth, Model model) {
        User user = userService.findByUserName(auth.getName());

        Ticket ticket = ticketService.findById(ticketId);

        // Checking if the user is assigned to the ticket's project
        if (!ticket.getProject().getUsers().contains(user)) {
            return "access-denied";
        }

        // Adding a comment in case the user will try to add one
        model.addAttribute("comment", new Comment(ticket, user));

        model.addAttribute("ticket", ticket);

        return "/tickets/ticket-page";
    }


    @GetMapping("/new")
    public String createNewTicket(@RequestParam(value = "project", required = false) Integer projectId,
                                  Authentication auth, Model model) {

        User user = userService.findByUserName(auth.getName());
        Ticket ticket = new Ticket();
        List<Project> projects;

        // If project selected
        if (projectId != null && projectId > 0) {
            projects = new ArrayList<>();
            projects.add(projectService.findById(projectId));
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

        // Getting the user's projects
        User user = userService.findByUserName(auth.getName());
        List<Project> projects = projectService.findAllByUser(user);

        // Getting the ticket info
        Ticket ticket = ticketService.findById(ticketId);

        // Check if the user has permission to edit this ticket
        if (!ticket.getSubmitter().equals(user)) {
            return "access-denied";
        }

        model.addAttribute("ticket", ticket);
        model.addAttribute("projects", projects);

        return "tickets/ticket-form";
    }

    @PostMapping("/save")
    public String saveTicket(@ModelAttribute("ticket") Ticket ticket) {
        ticketService.save(ticket);

        return "redirect:/tickets/" + ticket.getId();
    }

    @GetMapping("/delete")
    public String deleteTicket(@RequestParam("ticketId") int id) {
        ticketService.deleteById(id);

        return "redirect:/tickets";
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

        // Check if the user is allowed to delete this comment
        if (!user.equals(comment.getUser()) && !user.isAdmin()) {
            return "access-denied";
        }

        // Getting the ticket id for the redirection
        int ticketId = comment.getTicket().getId();

        commentService.delete(comment);

        return "redirect:/tickets/" + ticketId;
    }

}
