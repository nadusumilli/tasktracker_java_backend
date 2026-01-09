package com.tasktracker.tasktracker.service;

import com.tasktracker.tasktracker.advice.response_utils.ApiResponse;
import com.tasktracker.tasktracker.dto.LoginDto;
import com.tasktracker.tasktracker.dto.TokenDto;
import com.tasktracker.tasktracker.dto.UserSignupDto;
import com.tasktracker.tasktracker.entity.Session;
import com.tasktracker.tasktracker.entity.User;
import com.tasktracker.tasktracker.repository.SessionRepository;
import com.tasktracker.tasktracker.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final JWTService jWTService;
    private final AuthenticationManager authenticationManager;
    private final SessionRepository sessionRepository;
    private final UserService userService;

    public ResponseEntity<ApiResponse<?>> userSignup(UserSignupDto userSignupDto) {
        User newUser = userSignupDto.toUser();
        newUser.setPassword(passwordEncoder.encode(userSignupDto.password()));
        return ResponseEntity.ok(new ApiResponse<>(userRepository.save(newUser)));
    }

    public ResponseEntity<ApiResponse<?>> userLogin(LoginDto loginDto) throws BadCredentialsException {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.username(), loginDto.password())
        );

        User user = (User) auth.getPrincipal();

        if(user == null) {
            throw new BadCredentialsException("Invalid username or password");
        }

        String accessToken = jWTService.generateAccessTokenFromUser(user);
        String refreshToken = jWTService.generateRefreshTokenFromUser(user);

        Session sessionToRemove = sessionRepository.findByUser(user);
        if (sessionToRemove != null) sessionRepository.deleteById(sessionToRemove.getId());

        Session session = new Session();
        session.setUser(user);
        session.setToken(accessToken);
        sessionRepository.save(session);

        return ResponseEntity.ok(new ApiResponse<>(new TokenDto(user.getId(), accessToken, refreshToken)));
    }

    public ResponseEntity<ApiResponse<?>> refreshToken(String refreshToken) {
        String userId = jWTService.getUserIdFromToken(refreshToken);
        User user = userService.findById(userId);

        String accessToken = jWTService.generateAccessTokenFromUser(user);
        return ResponseEntity.ok(new ApiResponse<>(new TokenDto(user.getId(), accessToken, refreshToken)));
    }
}
