package com.amit.bugtracker.controller;

import com.amit.bugtracker.exception.AccessDeniedException;
import com.amit.bugtracker.exception.DemoUserException;
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

}
