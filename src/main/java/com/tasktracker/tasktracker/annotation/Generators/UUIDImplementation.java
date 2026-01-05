package com.tasktracker.tasktracker.annotation.Generators;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;

import java.util.EnumSet;

public class UUIDImplementation implements BeforeExecutionGenerator {
    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o, Object o1, EventType eventType) {
        return null;
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return null;
    }
}
