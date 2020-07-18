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

import java.util.HashMap;
import java.util.Map;

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


    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }


    @GetMapping("/")
    public String showHome(Model model) {
        try {
            createCharts(model);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "index";
    }


    private void createCharts(Model model) throws JsonProcessingException {
        for (Map.Entry<String, String> chart : getChartsData().entrySet())
            model.addAttribute(chart.getKey(), chart.getValue());
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


    @GetMapping("/search")
    public String search(Authentication auth, Model model, String searchText) {
        if (isEmptySearchText(searchText))
            return null;

        User user = userService.findByUserName(auth.getName());
        createSearchResults(model, user, searchText);

        return "search";
    }


    private void createSearchResults(Model model, User user, String searchText) {
        if (user.isManager() || user.isAdmin())
            getSearchResultsForAdminAndManager(model, searchText);
        else
            getSearchResultsForEmployee(model, user, searchText);

        model.addAttribute("users", userService.findByName(searchText));
    }


    private void getSearchResultsForAdminAndManager(Model model, String searchText) {
        model.addAttribute("projects", projectService.findAllByName(searchText));
        model.addAttribute("tickets", ticketService.findAllByName(searchText));
    }


    private void getSearchResultsForEmployee(Model model, User user, String searchText) {
        model.addAttribute("projects", projectService.findAllByUserAndName(user, searchText));
        model.addAttribute("tickets", ticketService.findAllByUserAndName(user, searchText));
    }


    private boolean isEmptySearchText(String searchText) {
        return searchText.trim().isEmpty();
    }


}
