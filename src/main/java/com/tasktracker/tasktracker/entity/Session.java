package com.tasktracker.tasktracker.entity;

import com.tasktracker.tasktracker.annotation.UUIDv4;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
public class Session {
    @Id
    @UUIDv4
    private String id;

    @OneToOne(mappedBy = "session")
    private User user;

    @Column(nullable = false)
    private String token;
}
