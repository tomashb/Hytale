package com.heneria.server;

import com.heneria.server.config.ServerConfig;
import com.heneria.server.core.EventBus;
import com.heneria.server.core.ModuleManager;
import com.heneria.server.core.Player;
import com.heneria.server.core.PlayerJoinEvent;
import com.heneria.server.core.ServerContext;
import com.heneria.server.modules.auth.AuthModule;
import com.heneria.server.modules.permissions.PermissionService;
import com.heneria.server.modules.permissions.PermissionsModule;
import com.heneria.server.modules.welcome.WelcomeModule;
import java.io.IOException;
import java.nio.file.Path;
import java.util.UUID;

public class HeneriaServer {
    private final ServerConfig config;
    private final EventBus eventBus;
    private final PermissionService permissionService;
    private final ModuleManager moduleManager;

    public HeneriaServer(ServerConfig config) {
        this.config = config;
        this.eventBus = new EventBus();
        this.permissionService = new PermissionService(config.getPermissionsByRole());
        this.moduleManager = new ModuleManager();
    }

    public void start() {
        ServerContext context = new ServerContext(config, eventBus, permissionService);
        moduleManager.register(new AuthModule());
        moduleManager.register(new PermissionsModule());
        moduleManager.register(new WelcomeModule());
        moduleManager.enableAll(context);
    }

    public void stop() {
        moduleManager.disableAll();
    }

    public void simulatePlayerJoin(String playerName) {
        Player player = new Player(UUID.randomUUID(), playerName);
        eventBus.publish(new PlayerJoinEvent(player));
    }

    public static void main(String[] args) throws IOException {
        Path configPath = Path.of("config/server.properties");
        ServerConfig config = ServerConfig.load(configPath);
        HeneriaServer server = new HeneriaServer(config);
        server.start();
        server.simulatePlayerJoin("Alyx");
    }
}
