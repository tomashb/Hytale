package com.heneria.server.config;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

public class ServerConfig {
    private final String serverName;
    private final String welcomeMessage;
    private final String defaultRole;
    private final Map<String, Set<String>> permissionsByRole;

    private ServerConfig(String serverName,
                         String welcomeMessage,
                         String defaultRole,
                         Map<String, Set<String>> permissionsByRole) {
        this.serverName = serverName;
        this.welcomeMessage = welcomeMessage;
        this.defaultRole = defaultRole;
        this.permissionsByRole = permissionsByRole;
    }

    public static ServerConfig load(Path path) throws IOException {
        Properties properties = new Properties();
        try (InputStream inputStream = Files.newInputStream(path)) {
            properties.load(inputStream);
        }

        String serverName = properties.getProperty("server.name", "Heneria");
        String welcomeMessage = properties.getProperty("server.welcome", "Bienvenue {player} !");
        String defaultRole = properties.getProperty("roles.default", "player");

        Map<String, Set<String>> permissionsByRole = new HashMap<>();
        for (String key : properties.stringPropertyNames()) {
            if (!key.startsWith("permissions.")) {
                continue;
            }
            String role = key.substring("permissions.".length());
            String value = properties.getProperty(key, "");
            Set<String> permissions = parseCsv(value);
            permissionsByRole.put(role, permissions);
        }

        return new ServerConfig(serverName, welcomeMessage, defaultRole, permissionsByRole);
    }

    private static Set<String> parseCsv(String value) {
        if (value == null || value.isBlank()) {
            return Collections.emptySet();
        }
        return Set.of(value.split(","))
            .stream()
            .map(String::trim)
            .filter(item -> !item.isEmpty())
            .collect(Collectors.toSet());
    }

    public String getServerName() {
        return serverName;
    }

    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    public String getDefaultRole() {
        return defaultRole;
    }

    public Map<String, Set<String>> getPermissionsByRole() {
        return Collections.unmodifiableMap(permissionsByRole);
    }
}
