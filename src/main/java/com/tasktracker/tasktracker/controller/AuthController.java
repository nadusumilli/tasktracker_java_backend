package com.tasktracker.tasktracker.controller;

import com.tasktracker.tasktracker.advice.response_utils.ApiResponse;
import com.tasktracker.tasktracker.dto.LoginDto;
import com.tasktracker.tasktracker.dto.TokenDto;
import com.tasktracker.tasktracker.dto.UserSignupDto;
import com.tasktracker.tasktracker.service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<?>> signup(@RequestBody @Valid UserSignupDto userSignupDto) {
        return authService.userSignup(userSignupDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(@RequestBody @Valid LoginDto loginDto) {
        return authService.userLogin(loginDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResponse<?>> refresh(HttpServletRequest request) {
        String refreshToken = Arrays
                .stream(request
                        .getCookies())
                .filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found in the cookies"));
        return authService.refreshToken(refreshToken);
    }
}
