package com.example.hytale.scoreboard.adapter;

import java.io.File;
import java.util.Collection;
import java.util.logging.Logger;

public interface HytaleAdapter {
    Logger getLogger();

    File getDataFolder();

    Collection<PlayerAdapter> getOnlinePlayers();

    int getMaxPlayers();

    ScoreboardView createScoreboard(PlayerAdapter player);

    ScheduledTask scheduleRepeating(Runnable task, long intervalMs);

    void registerCommand(String label, CommandHandler handler);

    void registerPlayerJoinListener(PlayerListener listener);

    void registerPlayerQuitListener(PlayerListener listener);
}
