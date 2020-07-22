package com.amit.bugtracker.exception;

public class DemoUserException extends RuntimeException {

    public DemoUserException() {
        super("Demo users are not allowed to modify data.");
    }

}
