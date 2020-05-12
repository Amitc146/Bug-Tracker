package com.amit.bugtracker.controller;

import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String showProfile(Authentication authentication, Model model) {
        User user = userService.findByUserName(authentication.getName());
        model.addAttribute("user", user);

        return "profile/user-profile";
    }

}
