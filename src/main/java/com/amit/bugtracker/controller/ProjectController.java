package com.amit.bugtracker.controller;

import com.amit.bugtracker.demo.DemoUserService;
import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.exception.AccessDeniedException;
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
        createProjectsList(model, user.getProjects());

        return "projects/list-projects";
    }


    @GetMapping("/allProjects")
    public String listAllProjects(Model model) {
        createProjectsList(model, projectService.findAll());

        return "projects/list-projects";
    }


    @GetMapping("/{projectId}")
    public String showProject(@PathVariable int projectId, Authentication auth, Model model) {
        User user = userService.findByUserName(auth.getName());
        Project project = projectService.findById(projectId);

        if (!isAllowedToViewProject(user, project))
            throw new AccessDeniedException();

        createProjectPage(model, project);

        return "projects/project-page";
    }


    @GetMapping("/new")
    public String createNewProject(Model model) {
        createProjectForm(model, new Project());

        return "projects/project-form";
    }


    @GetMapping("/{projectId}/update")
    public String updateProject(@PathVariable("projectId") int id, Model model) {
        Project project = projectService.findById(id);
        createProjectForm(model, project);

        return "projects/project-form";
    }


    @PostMapping("/save")
    public String saveProject(@ModelAttribute("project") Project project, Authentication auth) {
        DemoUserService.demoCheck(userService.findByUserName(auth.getName()));
        projectService.save(project);

        return "redirect:/projects/" + project.getId();
    }


    @GetMapping("/delete")
    public String deleteProject(@RequestParam("project") int id, Authentication auth) {
        DemoUserService.demoCheck(userService.findByUserName(auth.getName()));
        projectService.deleteById(id);

        return "redirect:/projects/allProjects";
    }


    private void createProjectsList(Model model, List<Project> projects) {
        model.addAttribute("projects", projects);
    }


    private void createProjectPage(Model model, Project project) {
        List<Ticket> openTickets = ticketService.findAllByProjectAndStatus(project, "open");
        List<Ticket> closedTickets = ticketService.findAllByProjectAndStatus(project, "closed");

        model.addAttribute("openTickets", openTickets);
        model.addAttribute("closedTickets", closedTickets);
        model.addAttribute("project", project);
    }


    private void createProjectForm(Model model, Project project) {
        model.addAttribute("project", project);
        model.addAttribute("users", userService.findAll());
    }


    private boolean isAllowedToViewProject(User user, Project project) {
        return (project.getUsers().contains(user) || user.isManager() || user.isAdmin());
    }


}
