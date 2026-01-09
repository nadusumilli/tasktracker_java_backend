package com.tasktracker.tasktracker.advice;

import com.tasktracker.tasktracker.advice.exception.ResourceNotFound;
import com.tasktracker.tasktracker.advice.response_utils.ApiError;
import com.tasktracker.tasktracker.advice.response_utils.ApiResponse;
import io.jsonwebtoken.JwtException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.security.sasl.AuthenticationException;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ApiError> handleResourceNotFoundException(ResourceNotFound exception) {
        ApiError error = ApiError.builder().message(exception.getMessage()).status(HttpStatus.NOT_FOUND).build();
        return generateErrorResponse(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleInvalidRequestException(MethodArgumentNotValidException exception) {
        String[] errors = exception.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList().toArray(new String[0]);
        ApiError error = ApiError.builder().message("Invalid Request Data has been provided").errors(errors).status(HttpStatus.BAD_REQUEST).build();
        return generateErrorResponse(error);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(AuthenticationException exception) {
        ApiError error = ApiError.builder().message(exception.getLocalizedMessage()).status(HttpStatus.UNAUTHORIZED).build();
        return generateErrorResponse(error);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(BadCredentialsException exception) {
        ApiError error = ApiError.builder().message(exception.getLocalizedMessage()).status(HttpStatus.UNAUTHORIZED).build();
        return generateErrorResponse(error);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ApiError> handleJWTException(JwtException exception) {
        ApiError error = ApiError.builder().message(exception.getLocalizedMessage()).status(HttpStatus.UNAUTHORIZED).build();
        return generateErrorResponse(error);
    }

    @ExceptionHandler
    public ResponseEntity<ApiError> handleInternalServerException(Exception exception) {
        ApiError error = ApiError.builder().message(exception.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return generateErrorResponse(error);
    }

    public ResponseEntity<ApiError> generateErrorResponse(ApiError error) {
        return new ResponseEntity<>(error, error.getStatus());
    }
}
