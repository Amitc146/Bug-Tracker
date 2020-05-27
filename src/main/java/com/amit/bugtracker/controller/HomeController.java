package com.amit.bugtracker.controller;

import com.amit.bugtracker.chart.ChartData;
import com.amit.bugtracker.service.ProjectService;
import com.amit.bugtracker.service.RoleService;
import com.amit.bugtracker.service.TicketService;
import com.amit.bugtracker.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {

    private final TicketService ticketService;
    private final RoleService roleService;
    private final ProjectService projectService;

    public HomeController(TicketService ticketService, RoleService roleService, ProjectService projectService) {
        this.ticketService = ticketService;
        this.roleService = roleService;
        this.projectService = projectService;
    }

    @GetMapping("/")
    public String showHome(Model model) throws JsonProcessingException {
        List<ChartData> ticketsPriorityData = ticketService.getAllPriorities();
        List<ChartData> ticketsProjectData = ticketService.getAllProjects();
        List<ChartData> userRolesData = roleService.getRolesCount();
        List<ChartData> projectUsersData = projectService.getUserCount();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString;

        jsonString = objectMapper.writeValueAsString(ticketsPriorityData);
        model.addAttribute("ticketPriorityCount", jsonString);

        jsonString = objectMapper.writeValueAsString(ticketsProjectData);
        model.addAttribute("ticketProjectCount", jsonString);

        jsonString = objectMapper.writeValueAsString(userRolesData);
        model.addAttribute("userRolesCount", jsonString);

        jsonString = objectMapper.writeValueAsString(projectUsersData);
        model.addAttribute("projectUsersCount", jsonString);

        return "main/index";
    }

    @GetMapping("/login")
    public String showMyLoginPage() {
        return "security/login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error-pages/access-denied";
    }


}










