package com.heneria.server.modules.welcome;

import com.heneria.server.config.ServerConfig;
import com.heneria.server.core.Module;
import com.heneria.server.core.PlayerAuthenticatedEvent;
import com.heneria.server.core.ServerContext;

public class WelcomeModule implements Module {
    @Override
    public String name() {
        return "Welcome";
    }

    @Override
    public void onEnable(ServerContext context) {
        ServerConfig config = context.getConfig();
        context.getEventBus().subscribe(PlayerAuthenticatedEvent.class, event -> {
            String message = config.getWelcomeMessage().replace("{player}", event.getPlayer().getName());
            event.getPlayer().sendMessage(message);
        });
    }

    @Override
    public void onDisable() {
        // Rien à nettoyer pour ce module simple.
    }
}
