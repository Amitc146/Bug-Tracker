package com.amit.bugtracker.controller;

import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.service.ProjectService;
import com.amit.bugtracker.service.TicketService;
import com.amit.bugtracker.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SearchController {

    private final ProjectService projectService;
    private final TicketService ticketService;
    private final UserService userService;


    public SearchController(ProjectService projectService, TicketService ticketService, UserService userService) {
        this.projectService = projectService;
        this.ticketService = ticketService;
        this.userService = userService;
    }


    @GetMapping("/search")
    public String search(Model model, String searchText, @ModelAttribute("currentUser") User user) {
        if (isEmptySearchText(searchText))
            return null;

        createSearchResults(model, user, searchText);

        return "search";
    }


    private boolean isEmptySearchText(String searchText) {
        return searchText.trim().isEmpty();
    }


    private void createSearchResults(Model model, User user, String searchText) {
        if (user.isManager() || user.isAdmin())
            getSearchResultsForAdminAndManager(model, searchText);
        else
            getSearchResultsForEmployee(model, searchText, user);

        model.addAttribute("users", userService.findByName(searchText));
    }


    private void getSearchResultsForAdminAndManager(Model model, String searchText) {
        model.addAttribute("projects", projectService.findAllByName(searchText));
        model.addAttribute("tickets", ticketService.findAllByTitle(searchText));
    }


    private void getSearchResultsForEmployee(Model model, String searchText, User user) {
        model.addAttribute("projects", projectService.findAllByUserAndName(user, searchText));
        model.addAttribute("tickets", ticketService.findAllByUserAndTitle(user, searchText));
    }


}
