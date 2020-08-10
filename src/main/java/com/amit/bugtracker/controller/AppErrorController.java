package com.amit.bugtracker.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AppErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        int statusCode = Integer.parseInt(status.toString());
        model.addAttribute("error", HttpStatus.valueOf(statusCode));
        return "error-pages/error";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "error-pages/access-denied";
    }

    @GetMapping("/no-projects-error")
    public String noProjectsError() {
        return "error-pages/no-projects-error";
    }

    @GetMapping("/demo-access-denied")
    public String demoUserAccessDenied() {
        return "error-pages/demo-user-error";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
