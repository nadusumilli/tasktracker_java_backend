package com.tasktracker.tasktracker.service;

import com.tasktracker.tasktracker.advice.exception.ResourceNotFound;
import com.tasktracker.tasktracker.entity.User;
import com.tasktracker.tasktracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid username or password!"));
    }

    public User findById(String userId) {
        return userRepository.findById(userId).orElseThrow(ResourceNotFound::new);
    }
}
