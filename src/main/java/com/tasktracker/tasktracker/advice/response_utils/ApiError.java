package com.tasktracker.tasktracker.advice.response_utils;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class ApiError {
    private HttpStatus status;
    private String message;
    private String[] errors;
}
