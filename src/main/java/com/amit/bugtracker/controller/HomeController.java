package com.amit.bugtracker.controller;

import com.amit.bugtracker.chart.ChartData;
import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.service.TicketService;
import com.amit.bugtracker.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

@Controller
public class HomeController {

    private final TicketService ticketService;

    public HomeController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @GetMapping("/")
    public String showHome(Model model) throws JsonProcessingException {
        List<ChartData> ticketsPriorityData = ticketService.getAllPriorities();
        List<ChartData> ticketsProjectData = ticketService.getAllProjects();

        ObjectMapper objectMapper = new ObjectMapper();

        String jsonString = objectMapper.writeValueAsString(ticketsPriorityData);
        model.addAttribute("ticketPriorityCount", jsonString);

        jsonString = objectMapper.writeValueAsString(ticketsProjectData);
        model.addAttribute("ticketProjectCount", jsonString);

        return "index";
    }

    @GetMapping("/login")
    public String showMyLoginPage() {
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }


}










