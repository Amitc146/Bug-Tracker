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
        createUserForm(model, new User());
        return "users/user-form";
    }

    @GetMapping("/{userId}/update")
    public String updateUser(Model model, @PathVariable int userId) {
        User user = userService.findById(userId);
        createUserForm(model, user);
        return "users/user-form";
    }

    @GetMapping("/{id}/delete")
    public String deleteUser(@PathVariable int id) {
        userService.deleteById(id);
        return "redirect:/users";
    }

    @PostMapping("/save")
    public String saveUser(Model model, @Valid @ModelAttribute User user, BindingResult bindingResult) {
        if (isInvalidUserForm(bindingResult))
            return invalidUserForm(model);

        if (isUserAlreadyExists(user))
            return userAlreadyExists(model);

        userService.save(user);
        return "redirect:/users";
    }

    private boolean isInvalidUserForm(BindingResult bindingResult) {
        return bindingResult.hasErrors();
    }

    private String invalidUserForm(Model model) {
        model.addAttribute("roles", roleService.findAll());
        return "users/user-form";
    }

    private boolean isUserAlreadyExists(User user) {
        User existingUser = userService.findByUserName(user.getUserName());
        return (existingUser != null && !existingUser.getId().equals(user.getId()));
    }

    private String userAlreadyExists(Model model) {
        createUserForm(model, new User());
        model.addAttribute("registrationError", "User name already exists.");
        return "users/user-form";
    }

    private void createUserForm(Model model, User user) {
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.findAll());
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        // Removing white spaces from strings
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

}
