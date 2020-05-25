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

    @GetMapping
    public String listUserTickets(Authentication auth, Model model) {
        User user = userService.findByUserName(auth.getName());
        List<Ticket> openTickets = ticketService.findAllByStatus("open");
        List<Ticket> closedTickets = ticketService.findAllByStatus("closed");
        List<Ticket> openUserTickets = ticketService.findAllByUserAndStatus(user, "open");

        model.addAttribute("openTickets", openTickets);
        model.addAttribute("closedTickets", closedTickets);
        model.addAttribute("openUserTickets", openUserTickets);

        return "tickets/list-tickets";
    }


    @GetMapping("/{ticketId}")
    public String showTicket(@PathVariable int ticketId, Authentication auth, Model model) {
        User user = userService.findByUserName(auth.getName());
        Ticket ticket = ticketService.findById(ticketId);

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
        User user = userService.findByUserName(auth.getName());
        Ticket ticket = ticketService.findById(ticketId);

        // Only the submitter, admin or manager are allowed to update the ticket
        if (!ticket.getSubmitter().equals(user) && !user.isAdmin() && !user.isManager()) {
            return "error-pages/access-denied";
        }

        List<Project> projects = projectService.findAllByUser(user);

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
    public String deleteTicket(@RequestParam("ticketId") int id, Authentication auth) {
        User user = userService.findByUserName(auth.getName());
        Ticket ticket = ticketService.findById(id);

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

        // Only the commenter, admin or manager are allowed to delete the comment
        if (!user.equals(comment.getUser()) && !user.isAdmin() && !user.isManager()) {
            return "error-pages/access-denied";
        }

        int ticketId = comment.getTicket().getId();
        commentService.delete(comment);

        return "redirect:/tickets/" + ticketId;
    }

}
