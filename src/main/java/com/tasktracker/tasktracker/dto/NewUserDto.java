package com.tasktracker.tasktracker.dto;

import com.tasktracker.tasktracker.entity.User;
import jakarta.validation.constraints.NotNull;

public record NewUserDto(String id, String username, String name, String email) {
    public static NewUserDto createNewUser(User user) {
        return new NewUserDto(user.getId(), user.getUsername(), user.getName(), user.getEmail());
    }
}
