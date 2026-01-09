package com.tasktracker.tasktracker.repository;

import com.tasktracker.tasktracker.entity.Session;
import com.tasktracker.tasktracker.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SessionRepository extends JpaRepository<Session, String> {
    Session findByUser(User user);
}
