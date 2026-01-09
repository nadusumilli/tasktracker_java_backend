package com.tasktracker.tasktracker.service;

import com.tasktracker.tasktracker.advice.exception.ResourceNotFound;
import com.tasktracker.tasktracker.dto.NewTaskDto;
import com.tasktracker.tasktracker.entity.Task;
import com.tasktracker.tasktracker.entity.User;
import com.tasktracker.tasktracker.repository.TaskRepository;
import com.tasktracker.tasktracker.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Security;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public List<Task> getAllTasksByUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return taskRepository.findAllByOwner(user);
    }

    public Task getTaskDetails(String id) {
        return taskRepository.findById(id).orElseThrow(ResourceNotFound::new);
    }

    @Transactional
    public Task createTask(NewTaskDto taskDto) {
        Task task = taskDto.toTask();
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("\n\n\nUser information: " + user);
        task.setOwner(user);
        taskRepository.save(task);
        return task;
    }

    public Task updateTask(NewTaskDto taskDto) {
        Task task = taskRepository.findById(taskDto.id()).orElseThrow(ResourceNotFound::new);
        task.setName(taskDto.name());
        task.setDescription(taskDto.description());
        task.setIsActive(taskDto.isActive());
        task.setOwner((User) userRepository.findById(taskDto.userId()).orElseThrow(ResourceNotFound::new));
        taskRepository.save(task);
        return task;
    }

    public void deleteTask(String id) {
        taskRepository.deleteById(id);
    }

}
