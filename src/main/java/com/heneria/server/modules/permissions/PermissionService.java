package com.heneria.server.modules.permissions;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class PermissionService {
    private final Map<String, Set<String>> permissionsByRole;
    private final Map<UUID, String> playerRoles = new HashMap<>();

    public PermissionService(Map<String, Set<String>> permissionsByRole) {
        this.permissionsByRole = new HashMap<>(permissionsByRole);
    }

    public void assignRole(UUID playerId, String role) {
        playerRoles.put(playerId, role);
    }

    public String getRole(UUID playerId) {
        return playerRoles.get(playerId);
    }

    public boolean hasPermission(UUID playerId, String permission) {
        String role = playerRoles.get(playerId);
        if (role == null) {
            return false;
        }
        Set<String> permissions = permissionsByRole.getOrDefault(role, new HashSet<>());
        return permissions.contains(permission);
    }

    public void registerRole(String role, Set<String> permissions) {
        permissionsByRole.put(role, new HashSet<>(permissions));
    }
}
