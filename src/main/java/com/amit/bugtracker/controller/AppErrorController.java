package com.amit.bugtracker.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class AppErrorController implements ErrorController {

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-pages/error-404";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                return "error-pages/error-403";
            }
        }
        return "error-pages/error-500";
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
