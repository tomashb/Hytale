package com.heneria.server.core;

@FunctionalInterface
public interface EventListener<T extends Event> {
    void handle(T event);
}
