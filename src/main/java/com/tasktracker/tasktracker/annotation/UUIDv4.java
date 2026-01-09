package com.tasktracker.tasktracker.annotation;

import com.tasktracker.tasktracker.annotation.Generators.UUIDImplementation;
import jakarta.persistence.Id;
import org.hibernate.annotations.IdGeneratorType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@IdGeneratorType(UUIDImplementation.class)
public @interface UUIDv4 {
}
