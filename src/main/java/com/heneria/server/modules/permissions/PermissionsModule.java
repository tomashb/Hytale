package com.heneria.server.modules.permissions;

import com.heneria.server.config.ServerConfig;
import com.heneria.server.core.Module;
import com.heneria.server.core.PlayerAuthenticatedEvent;
import com.heneria.server.core.ServerContext;

public class PermissionsModule implements Module {
    private ServerContext context;

    @Override
    public String name() {
        return "Permissions";
    }

    @Override
    public void onEnable(ServerContext context) {
        this.context = context;
        ServerConfig config = context.getConfig();
        config.getPermissionsByRole().forEach(context.getPermissionService()::registerRole);

        context.getEventBus().subscribe(PlayerAuthenticatedEvent.class, event -> {
            String defaultRole = config.getDefaultRole();
            context.getPermissionService().assignRole(event.getPlayer().getId(), defaultRole);
        });
    }

    @Override
    public void onDisable() {
        this.context = null;
    }
}
