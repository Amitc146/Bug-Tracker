package com.amit.bugtracker.controller;

import com.amit.bugtracker.service.RoleService;
import com.amit.bugtracker.service.TicketService;
import com.amit.bugtracker.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;

@Controller
public class HomeController {

    private final TicketService ticketService;
    private final UserService userService;
    private final RoleService roleService;

    public HomeController(TicketService ticketService, UserService userService, RoleService roleService) {
        this.ticketService = ticketService;
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/")
    public String showHome(Model model) {
        model.addAllAttributes(getCharts());
        return "index";
    }

    private HashMap<String, String> getCharts() {
        try {
            return getChartsData();
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException();
    }

    private HashMap<String, String> getChartsData() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        HashMap<String, String> chartsData = new HashMap<>();
        chartsData.put("ticketPriorityCount", objectMapper.writeValueAsString(ticketService.getPrioritiesChartData()));
        chartsData.put("ticketProjectCount", objectMapper.writeValueAsString(ticketService.getProjectsChartData()));
        chartsData.put("ticketStatusCount", objectMapper.writeValueAsString(ticketService.getStatusChartData()));
        chartsData.put("userRolesCount", objectMapper.writeValueAsString(roleService.getRolesCount()));
        chartsData.put("userProjectsCount", objectMapper.writeValueAsString(userService.getProjectsCount()));
        return chartsData;
    }

}
