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

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final TicketService ticketService;
    private final UserService userService;

    public ProjectController(ProjectService projectService, TicketService ticketService, UserService userService) {
        this.projectService = projectService;
        this.ticketService = ticketService;
        this.userService = userService;
    }

    @GetMapping
    public String listUserProjects(Authentication auth, Model model) {
        User user = userService.findByUserName(auth.getName());
        List<Project> userProjects = projectService.findAllByUser(user);
        List<Project> allProjects = projectService.findAll();

        model.addAttribute("userProjects", userProjects);
        model.addAttribute("allProjects", allProjects);

        return "projects/list-projects";
    }

    @GetMapping("/{projectId}")
    public String showProject(@PathVariable int projectId, Model model) {
        Project project = projectService.findById(projectId);

        List<Ticket> openTickets = ticketService.findAllByProjectAndStatus(project, "open");
        List<Ticket> closedTickets = ticketService.findAllByProjectAndStatus(project, "closed");

        model.addAttribute("openTickets", openTickets);
        model.addAttribute("closedTickets", closedTickets);
        model.addAttribute("project", project);

        return "projects/project-page";
    }

    @GetMapping("/new")
    public String createNewProject(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("users", userService.findAll());

        return "projects/project-form";
    }

    @GetMapping("/{projectId}/update")
    public String updateProject(@PathVariable("projectId") int id, Model model) {
        Project project = projectService.findById(id);
        model.addAttribute("project", project);
        model.addAttribute("users", userService.findAll());

        return "projects/project-form";
    }

    @PostMapping("/save")
    public String saveProject(@ModelAttribute("project") Project project) {
        projectService.save(project);

        return "redirect:/projects/" + project.getId();
    }

    @GetMapping("/delete")
    public String deleteProject(@RequestParam("project") int id) {
        projectService.deleteById(id);

        return "redirect:/projects";
    }

}
