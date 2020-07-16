package com.amit.bugtracker.controller;

import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.service.ProjectService;
import com.amit.bugtracker.service.RoleService;
import com.amit.bugtracker.service.TicketService;
import com.amit.bugtracker.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ProjectService projectService;
    private final TicketService ticketService;
    private final UserService userService;
    private final RoleService roleService;

    public HomeController(ProjectService projectService, TicketService ticketService, RoleService roleService, UserService userService) {
        this.projectService = projectService;
        this.ticketService = ticketService;
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String showHome(Model model) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString;

        jsonString = objectMapper.writeValueAsString(ticketService.getPrioritiesChartData());
        model.addAttribute("ticketPriorityCount", jsonString);

        jsonString = objectMapper.writeValueAsString(ticketService.getProjectsChartData());
        model.addAttribute("ticketProjectCount", jsonString);

        jsonString = objectMapper.writeValueAsString(ticketService.getStatusChartData());
        model.addAttribute("ticketStatusCount", jsonString);

        jsonString = objectMapper.writeValueAsString(roleService.getRolesCount());
        model.addAttribute("userRolesCount", jsonString);

        jsonString = objectMapper.writeValueAsString(userService.getProjectsCount());
        model.addAttribute("userProjectsCount", jsonString);

        return "index";
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/search")
    public String searchProject(Authentication auth, Model model, String searchText) {

        // For empty string return null
        if (searchText.trim().isEmpty()) {
            return null;
        }

        User user = userService.findByUserName(auth.getName());

        if (user.isManager() || user.isAdmin()) {
            // If the user is 'Admin' or 'Manager', add any ticket or project found
            model.addAttribute("projects", projectService.findAllByName(searchText));
            model.addAttribute("tickets", ticketService.findAllByName(searchText));
        } else {
            // If the user is 'Employee', add only projects and tickets he's assigned to
            model.addAttribute("projects", projectService.findAllByUserAndName(user, searchText));
            model.addAttribute("tickets", ticketService.findAllByUserAndName(user, searchText));
        }

        model.addAttribute("users", userService.findByName(searchText));

        return "search";
    }

}
