package com.heneria.server.core;

public class PlayerAuthenticatedEvent implements Event {
    private final Player player;

    public PlayerAuthenticatedEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }
}
