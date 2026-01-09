package com.tasktracker.tasktracker.dto;

import com.tasktracker.tasktracker.entity.Task;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public record NewTaskDto(String id, @NotNull @Length(max = 30, message = "name cannot be larger than 30 characters") String name, @NotNull @Length(max = 300, message = "description cannot be greater than 300 characters") String description, Boolean isActive, String userId) {

    public NewTaskDto(String id, String name, String description, String userId) {
        this(id, name, description, false, userId);
    }

    public Task toTask() {
        return Task.builder().name(this.name).description(this.description).isActive(this.isActive).build();
    }
}
