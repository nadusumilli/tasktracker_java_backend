package com.tasktracker.tasktracker.repository;

import com.tasktracker.tasktracker.entity.Task;
import com.tasktracker.tasktracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
    List<Task> findAllByOwner(User owner);
}
