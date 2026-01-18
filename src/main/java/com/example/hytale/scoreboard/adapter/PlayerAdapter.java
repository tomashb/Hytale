package com.example.hytale.scoreboard.adapter;

import java.util.UUID;

public interface PlayerAdapter {
    UUID getUniqueId();

    String getName();

    String getRank();

    Integer getCoins();

    Integer getLevel();

    Integer getPing();

    Integer getFps();

    String getWorldName();

    String getGameName();

    void sendMessage(String message);
}
