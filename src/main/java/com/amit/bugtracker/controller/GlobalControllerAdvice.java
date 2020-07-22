package com.amit.bugtracker.controller;

import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {

    private final UserService userService;

    public GlobalControllerAdvice(UserService userService) {
        this.userService = userService;
    }

    // Adding the current user to the model for every controller
    @ModelAttribute("currentUser")
    public User getCurrentUser(Authentication auth) {
        if (auth != null)
            return userService.findByUserName(auth.getName());

        return null;
    }

}
