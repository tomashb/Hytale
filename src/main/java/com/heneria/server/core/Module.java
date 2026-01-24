package com.heneria.server.core;

public interface Module {
    String name();

    void onEnable(ServerContext context);

    void onDisable();
}
