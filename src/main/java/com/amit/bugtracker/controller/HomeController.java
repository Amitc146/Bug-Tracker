package com.amit.bugtracker.controller;

import com.amit.bugtracker.dto.ChartData;
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
        List<ChartData> ticketsPriorityData = ticketService.getPrioritiesChartData();
        List<ChartData> ticketsProjectData = ticketService.getProjectsChartData();
        List<ChartData> ticketsStatusData = ticketService.getStatusChartData();
        List<ChartData> userRolesData = roleService.getRolesCount();
        List<ChartData> userProjectsData = userService.getProjectsCount();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString;

        jsonString = objectMapper.writeValueAsString(ticketsPriorityData);
        model.addAttribute("ticketPriorityCount", jsonString);

        jsonString = objectMapper.writeValueAsString(ticketsProjectData);
        model.addAttribute("ticketProjectCount", jsonString);

        jsonString = objectMapper.writeValueAsString(ticketsStatusData);
        model.addAttribute("ticketStatusCount", jsonString);

        jsonString = objectMapper.writeValueAsString(userRolesData);
        model.addAttribute("userRolesCount", jsonString);

        jsonString = objectMapper.writeValueAsString(userProjectsData);
        model.addAttribute("userProjectsCount", jsonString);

        return "main/index";
    }

    @GetMapping("/login")
    public String showMyLoginPage() {
        return "security/login";
    }

    @GetMapping("/search")
    public String searchProject(String searchText, Model model) {
        model.addAttribute("projects", projectService.findAllByName(searchText));
        model.addAttribute("tickets", ticketService.findAllByName(searchText));

        return "main/search";
    }

}
