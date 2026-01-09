package com.tasktracker.tasktracker.controller;

import com.tasktracker.tasktracker.advice.response_utils.ApiResponse;
import com.tasktracker.tasktracker.dto.NewTaskDto;
import com.tasktracker.tasktracker.entity.Task;
import com.tasktracker.tasktracker.entity.User;
import com.tasktracker.tasktracker.repository.TaskRepository;
import com.tasktracker.tasktracker.repository.UserRepository;
import com.tasktracker.tasktracker.service.TaskService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/tasks")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @GetMapping
    public ResponseEntity<ApiResponse<?>> getAllTasksByOwner() {
        return new ResponseEntity<>(new ApiResponse<>(taskService.getAllTasksByUser()), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getTaskDetailsById(@PathVariable String id) {
        return new ResponseEntity<>(new ApiResponse<>(taskService.getTaskDetails(id)), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ApiResponse<?>> createTask(@RequestBody @Valid NewTaskDto taskDto) {
        return ResponseEntity.ok(new ApiResponse<>(taskService.createTask(taskDto)));
    }

    @PutMapping
    public ResponseEntity<ApiResponse<?>> updateTask(@RequestBody @Valid NewTaskDto taskDto) {
        return ResponseEntity.ok(new ApiResponse<>(taskService.updateTask(taskDto)));
    }

    @DeleteMapping("/{id}")
    public HttpStatus deleteTask(@PathVariable String id) {
        taskService.deleteTask(id);
        return HttpStatus.NO_CONTENT;
    }

}
