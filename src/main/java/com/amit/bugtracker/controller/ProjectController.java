package com.amit.bugtracker.controller;

import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.service.ProjectService;
import com.amit.bugtracker.service.TicketService;
import com.amit.bugtracker.service.UserService;
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


    @GetMapping("/myProjects")
    public String listUserProjects(Model model, @ModelAttribute("currentUser") User user) {
        List<Project> projects = user.getProjects();
        model.addAttribute("projects", projects);

        return "projects/list-projects";
    }


    @GetMapping("/allProjects")
    public String listAllProjects(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);

        return "projects/list-projects";
    }


    @GetMapping("/{projectId}")
    public String showProject(Model model, @PathVariable int projectId) {
        Project project = projectService.findById(projectId);

        List<Ticket> openTickets = ticketService.findAllOpenByProject(project);
        List<Ticket> closedTickets = ticketService.findAllClosedByProject(project);
        model.addAttribute("openTickets", openTickets);
        model.addAttribute("closedTickets", closedTickets);
        model.addAttribute("project", project);

        return "projects/project-page";
    }


    @GetMapping("/new")
    public String createNewProject(Model model) {
        Project project = new Project();
        model.addAttribute("project", project);
        model.addAttribute("users", userService.findAll());

        return "projects/project-form";
    }


    @GetMapping("/{projectId}/update")
    public String updateProject(Model model, @PathVariable int projectId) {
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);
        model.addAttribute("users", userService.findAll());

        return "projects/project-form";
    }


    @PostMapping("/save")
    public String saveProject(@ModelAttribute Project project) {
        projectService.save(project);

        return "redirect:/projects/" + project.getId();
    }


    @GetMapping("/{id}/delete")
    public String deleteProject(@PathVariable int id, @RequestParam String page) {
        projectService.deleteById(id);

        return "redirect:" + getNextPagePath(page);
    }


    private String getNextPagePath(String prevPage) {
        return "/projects/" + (prevPage.equals("allProjects") ? prevPage : "myProjects");
    }

}
