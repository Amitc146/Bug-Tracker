package com.amit.bugtracker.controller;

import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.exception.AccessDeniedException;
import com.amit.bugtracker.exception.DemoUserException;
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

    @GetMapping("/myProjects")
    public String listUserProjects(Authentication auth, Model model) {
        User user = userService.findByUserName(auth.getName());
        List<Project> userProjects = projectService.findAllByUser(user);

        model.addAttribute("projects", userProjects);

        return "projects/list-projects";
    }

    @GetMapping("/allProjects")
    public String listAllProjects(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);

        return "projects/list-projects";
    }

    @GetMapping("/{projectId}")
    public String showProject(@PathVariable int projectId, Authentication auth, Model model) {
        User user = userService.findByUserName(auth.getName());
        Project project = projectService.findById(projectId);

        if (!isAllowedToView(user, project)) {
            throw new AccessDeniedException(String.format("User '%s' is not allowed to view project id='%d'", user.getUserName(), project.getId()));
        }

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
    public String saveProject(@ModelAttribute("project") Project project, Authentication auth) {

        // Blocking demo users from changing stuff
        demoUserCheck(userService.findByUserName(auth.getName()));

        projectService.save(project);

        return "redirect:/projects/" + project.getId();
    }

    @GetMapping("/delete")
    public String deleteProject(@RequestParam("project") int id, Authentication auth) {

        // Blocking demo users from changing stuff
        demoUserCheck(userService.findByUserName(auth.getName()));

        projectService.deleteById(id);

        return "redirect:/projects/allProjects";
    }

    private boolean isAllowedToView(User user, Project project) {
        return (project.getUsers().contains(user) || user.isManager() || user.isAdmin());
    }

    private void demoUserCheck(User user) {
        if (user.getFirstName().equals("Demo")) {
            throw new DemoUserException("Access denied - Demo user");
        }
    }

}
