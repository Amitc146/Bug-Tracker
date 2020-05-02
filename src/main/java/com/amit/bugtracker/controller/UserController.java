package com.amit.bugtracker.controller;

import com.amit.bugtracker.entity.User;
import com.amit.bugtracker.service.RoleService;
import com.amit.bugtracker.service.UserService;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("users", users);

        return "users/list-users";
    }

    @GetMapping("/new")
    public String createNewUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleService.findAll());

        return "users/user-form";
    }

    @GetMapping("/{userId}/update")
    public String updateUser(@PathVariable("userId") Long id, Model model) {
        User user = userService.findById(id);

        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAll());

        return "users/user-form";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("user") Long id) {
        userService.deleteById(id);

        return "redirect:/users";
    }

    @PostMapping("/save")
    public String saveUser(@Valid @ModelAttribute("user") User user,
                           BindingResult bindingResult, Model model) {

        // form validation
        if (bindingResult.hasErrors()) {
            model.addAttribute("roles", roleService.findAll());

            return "users/user-form";
        }

        // check the database if user already exists
        User existing = userService.findByUserName(user.getUserName());
        if (existing != null && !existing.getId().equals(user.getId())) {
            model.addAttribute("user", new User());
            model.addAttribute("roles", roleService.findAll());
            model.addAttribute("registrationError", "User name already exists.");

            return "users/user-form";
        }

        userService.save(user);

        return "redirect:/users";
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }


}
