package com.heneria.server.core;

import com.heneria.server.config.ServerConfig;
import com.heneria.server.modules.permissions.PermissionService;

public class ServerContext {
    private final ServerConfig config;
    private final EventBus eventBus;
    private final PermissionService permissionService;

    public ServerContext(ServerConfig config, EventBus eventBus, PermissionService permissionService) {
        this.config = config;
        this.eventBus = eventBus;
        this.permissionService = permissionService;
    }

    public ServerConfig getConfig() {
        return config;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public PermissionService getPermissionService() {
        return permissionService;
    }
}
