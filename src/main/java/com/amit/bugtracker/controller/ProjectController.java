package com.amit.bugtracker.controller;

import com.amit.bugtracker.entity.Project;
import com.amit.bugtracker.entity.Ticket;
import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.service.ProjectService;
import com.amit.bugtracker.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/projects")
public class ProjectController {

    private ProjectService projectService;
    private UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping
    public String listProjects(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);

        return "projects/list-projects";
    }

    @GetMapping("/myProjects")
    public String listUserProjects(Authentication auth, Model model) {

        // Getting a list of the user's projects
        User user = userService.findByUserName(auth.getName());
        List<Project> userProjects = projectService.findAllByUser(user);

        model.addAttribute("projects", userProjects);

        return "projects/list-projects";
    }

    @GetMapping("/{projectId}")
    public String showProject(@PathVariable int projectId, Model model) {
        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);

        return "projects/project-page";
    }

    @GetMapping("/new")
    public String addProject(Model model) {
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

        return "redirect:/projects";
    }

    @GetMapping("/delete")
    public String deleteProject(@RequestParam("project") int id) {
        projectService.deleteById(id);

        return "redirect:/projects";
    }


}
