package com.tasktracker.tasktracker.dto;

import com.tasktracker.tasktracker.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record UserSignupDto(

        String id,

        String name,

        @NotNull(message = "Username field cannot be empty")
        String username,

        @NotNull(message = "Email field cannot be empty")
        @Email(message = "Please enter a valid email address")
        String email,

        @NotNull(message = "Password field cannot be empty")
        @Length(min = 8, message = "Password must be atleast 8 characters")
        String password
) {

    public User toUser() {
        User user = new User();
        user.setName(this.name);
        user.setEmail(this.email);
        user.setPassword(this.password);
        user.setUsername(this.username);

        return user;
    }
}
