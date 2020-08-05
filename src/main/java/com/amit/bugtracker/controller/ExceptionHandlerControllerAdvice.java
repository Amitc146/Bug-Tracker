package com.amit.bugtracker.controller;

import com.amit.bugtracker.exception.AccessDeniedException;
import com.amit.bugtracker.exception.DemoUserException;
import com.amit.bugtracker.exception.NoProjectsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(DemoUserException.class)
    public String DemoUserExceptionHandler(DemoUserException e) {
        return "redirect:/demo-access-denied";
    }

    @ExceptionHandler(AccessDeniedException.class)
    public String AccessDeniedExceptionHandler(AccessDeniedException e) {
        return "redirect:/access-denied";
    }

    @ExceptionHandler(NoProjectsException.class)
    public String NoProjectsExceptionHandler(NoProjectsException e) {
        return "redirect:/no-projects-error";
    }

}
