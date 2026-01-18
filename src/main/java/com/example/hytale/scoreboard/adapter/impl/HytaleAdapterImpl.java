package com.example.hytale.scoreboard.adapter.impl;

import com.example.hytale.scoreboard.adapter.CommandHandler;
import com.example.hytale.scoreboard.adapter.HytaleAdapter;
import com.example.hytale.scoreboard.adapter.PlayerAdapter;
import com.example.hytale.scoreboard.adapter.PlayerListener;
import com.example.hytale.scoreboard.adapter.ScheduledTask;
import com.example.hytale.scoreboard.adapter.ScoreboardView;

import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class HytaleAdapterImpl implements HytaleAdapter {
    private final Logger logger = Logger.getLogger("HytaleScoreboardTest");
    private final File dataFolder;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();

    public HytaleAdapterImpl(String pluginName) {
        this.dataFolder = new File("plugins", pluginName);
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public File getDataFolder() {
        return dataFolder;
    }

    @Override
    public Collection<PlayerAdapter> getOnlinePlayers() {
        // TODO Replace with Hytale API player list.
        return Collections.emptyList();
    }

    @Override
    public int getMaxPlayers() {
        // TODO Replace with Hytale API max player count.
        return 0;
    }

    @Override
    public ScoreboardView createScoreboard(PlayerAdapter player) {
        // TODO Replace with Hytale API scoreboard creation.
        return new NoopScoreboardView();
    }

    @Override
    public ScheduledTask scheduleRepeating(Runnable task, long intervalMs) {
        var future = scheduler.scheduleAtFixedRate(task, intervalMs, intervalMs, TimeUnit.MILLISECONDS);
        return () -> future.cancel(false);
    }

    @Override
    public void registerCommand(String label, CommandHandler handler) {
        // TODO Replace with Hytale API command registration.
    }

    @Override
    public void registerPlayerJoinListener(PlayerListener listener) {
        // TODO Replace with Hytale API join listener.
    }

    @Override
    public void registerPlayerQuitListener(PlayerListener listener) {
        // TODO Replace with Hytale API quit listener.
    }

    private static class NoopScoreboardView implements ScoreboardView {
        @Override
        public void setTitle(String title) {
        }

        @Override
        public void setLines(java.util.List<String> lines) {
        }

        @Override
        public void show() {
        }

        @Override
        public void hide() {
        }

        @Override
        public void destroy() {
        }
    }
}
