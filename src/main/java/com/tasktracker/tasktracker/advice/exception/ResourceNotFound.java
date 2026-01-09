package com.tasktracker.tasktracker.advice.exception;

public class ResourceNotFound extends RuntimeException {
    public ResourceNotFound(String message) {
        super(message);
    }
    public ResourceNotFound() {
        super("Resource Not Found");
    }
}
