package com.heneria.server.modules.auth;

import com.heneria.server.core.Module;
import com.heneria.server.core.Player;
import com.heneria.server.core.PlayerAuthenticatedEvent;
import com.heneria.server.core.PlayerJoinEvent;
import com.heneria.server.core.ServerContext;

public class AuthModule implements Module {
    private ServerContext context;

    @Override
    public String name() {
        return "Authentication";
    }

    @Override
    public void onEnable(ServerContext context) {
        this.context = context;
        context.getEventBus().subscribe(PlayerJoinEvent.class, this::handleJoin);
    }

    private void handleJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        context.getEventBus().publish(new PlayerAuthenticatedEvent(player));
    }

    @Override
    public void onDisable() {
        this.context = null;
    }
}
