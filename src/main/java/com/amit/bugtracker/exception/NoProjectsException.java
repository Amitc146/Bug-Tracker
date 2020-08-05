package com.amit.bugtracker.exception;

public class NoProjectsException extends RuntimeException {

    public NoProjectsException() {
        super();
    }

    public NoProjectsException(String message) {
        super(message);
    }

}
