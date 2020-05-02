package com.amit.bugtracker.controller;

import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import com.amit.bugtracker.entity.User;
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

    public TicketController(TicketService ticketService, ProjectService projectService, UserService userService) {
        this.ticketService = ticketService;
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping()
    public String listTickets(Model model) {
        List<Ticket> tickets = ticketService.findAll();
        model.addAttribute("tickets", tickets);

        return "tickets/list-tickets";
    }

    @GetMapping("/myTickets")
    public String listUserTickets(Authentication auth, Model model) {

        // Getting a list of the user's tickets
        User user = userService.findByUserName(auth.getName());
        List<Project> userProjects = projectService.findAllByUser(user);
        List<Ticket> userTickets = ticketService.findAllByProjects(userProjects);

        model.addAttribute("tickets", userTickets);

        return "tickets/list-tickets";
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

        ticket.setSubmitter(user.getUserName());
        ticket.setCreationDate(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));

        model.addAttribute("ticket", ticket);
        model.addAttribute("projects", projects);

        return "tickets/ticket-form";
    }

    @GetMapping("/{ticketId}/update")
    public String updateTicket(@PathVariable("ticketId") int ticketId, Model model) {

        // Getting the ticket and project info
        Ticket ticket = ticketService.findById(ticketId);
        List<Project> projects = new ArrayList<>();
        projects.add(ticket.getProject());

        model.addAttribute("ticket", ticket);
        model.addAttribute("projects", projects);

        return "tickets/ticket-form";
    }

    @PostMapping("/save")
    public String saveTicket(@ModelAttribute("ticket") Ticket ticket) {
        ticketService.save(ticket);

        return "redirect:/tickets";
    }

    @GetMapping("/delete")
    public String deleteTicket(@RequestParam("ticketId") int id) {
        ticketService.deleteById(id);

        return "redirect:/tickets";
    }


}
