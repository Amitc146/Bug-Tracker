package com.amit.bugtracker.controller;

import com.amit.bugtracker.entity.Comment;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.service.CommentService;
import com.amit.bugtracker.service.ProjectService;
import com.amit.bugtracker.service.TicketService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final ProjectService projectService;
    private final CommentService commentService;


    public TicketController(TicketService ticketService, ProjectService projectService, CommentService commentService) {
        this.ticketService = ticketService;
        this.projectService = projectService;
        this.commentService = commentService;
    }


    @GetMapping("/myTickets")
    public String listUserTickets(Model model, @ModelAttribute("currentUser") User user) {
        List<Ticket> openTickets = ticketService.findAllOpenByUser(user);
        List<Ticket> closedTickets = ticketService.findAllClosedByUser(user);

        model.addAttribute("openTickets", openTickets);
        model.addAttribute("closedTickets", closedTickets);

        return "tickets/list-tickets";
    }


    @GetMapping("/allTickets")
    public String listAllTickets(Model model) {
        List<Ticket> openTickets = ticketService.findAllOpen();
        List<Ticket> closedTickets = ticketService.findAllClosed();

        model.addAttribute("openTickets", openTickets);
        model.addAttribute("closedTickets", closedTickets);

        return "tickets/list-tickets";
    }


    @GetMapping("/{ticketId}")
    public String showTicket(Model model, @PathVariable int ticketId, @ModelAttribute("currentUser") User user) {
        Ticket ticket = ticketService.findById(ticketId);

        model.addAttribute("comment", new Comment(ticket, user));
        model.addAttribute("ticket", ticket);

        return "tickets/ticket-page";
    }


    @GetMapping("/new")
    public String createNewTicket(Model model, @ModelAttribute("currentUser") User user,
                                  @RequestParam(value = "project", required = false) Integer projectId) {

        if (isProjectSelected(projectId))
            return createNewTicketWithProjectSelected(model, user, projectId);

        Ticket ticket = Ticket.createEmptyTicket(user, getCurrentTime());
        List<Project> projects = projectService.findAllAllowedByUser(user);

        model.addAttribute("projects", projects);
        model.addAttribute("ticket", ticket);

        return "tickets/ticket-form";
    }


    private boolean isProjectSelected(Integer projectId) {
        return projectId != null && projectId > 0;
    }


    public String createNewTicketWithProjectSelected(Model model, User user, int projectId) {
        Project project = projectService.findById(projectId);
        Ticket ticket = Ticket.createEmptyTicket(user, getCurrentTime());

        model.addAttribute("projects", Collections.singletonList(project));
        model.addAttribute("ticket", ticket);

        return "tickets/ticket-form";
    }


    @GetMapping("/{ticketId}/update")
    public String updateTicket(Model model, @ModelAttribute("currentUser") User user, @PathVariable int ticketId) {
        Ticket ticket = ticketService.findById(ticketId);
        List<Project> projects = projectService.findAllAllowedByUser(user);

        model.addAttribute("ticket", ticket);
        model.addAttribute("projects", projects);

        return "tickets/ticket-form";
    }


    @PostMapping("/save")
    public String saveTicket(@ModelAttribute Ticket ticket) {
        ticketService.save(ticket);

        return "redirect:/tickets/" + ticket.getId();
    }


    @GetMapping("/{id}/delete")
    public String deleteTicket(@PathVariable int id, @RequestParam String page) {
        String nextPage = getNextPagePath(page, id);
        ticketService.deleteById(id);

        return "redirect:" + nextPage;
    }


    private String getNextPagePath(String prevPage, int ticketId) {
        if (prevPage.equals("ticketPage")) {
            Project project = ticketService.findById(ticketId).getProject();
            return "/projects/" + project.getId();
        }

        return "/tickets/" + prevPage;
    }


    @PostMapping("/saveComment")
    public String saveComment(@ModelAttribute Comment comment) {
        comment.setCreationDate(getCurrentTime());
        commentService.save(comment);

        return "redirect:/tickets/" + comment.getTicket().getId();
    }


    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam int id) {
        Comment comment = commentService.findById(id);
        commentService.delete(comment);

        return "redirect:/tickets/" + comment.getTicket().getId();
    }


    private String getCurrentTime() {
        return LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }


}
