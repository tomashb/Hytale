package com.example.hytale.scoreboard;

import com.example.hytale.scoreboard.adapter.HytaleAdapter;
import com.example.hytale.scoreboard.adapter.PlayerAdapter;
import com.example.hytale.scoreboard.adapter.ScheduledTask;
import com.example.hytale.scoreboard.placeholders.PlaceholderRegistry;

import java.time.Duration;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

public class ScoreboardManager {
    private final HytaleAdapter adapter;
    private final ConfigService configService;
    private final PlaceholderRegistry placeholderRegistry;
    private final Logger logger;
    private final Map<UUID, ScoreboardSession> sessions = new ConcurrentHashMap<>();
    private ScheduledTask updateTask;

    public ScoreboardManager(HytaleAdapter adapter, ConfigService configService, PlaceholderRegistry placeholderRegistry, Logger logger) {
        this.adapter = adapter;
        this.configService = configService;
        this.placeholderRegistry = placeholderRegistry;
        this.logger = logger;
    }

    public void start() {
        adapter.registerPlayerJoinListener(this::handleJoin);
        adapter.registerPlayerQuitListener(this::handleQuit);

        ConfigService.Config config = configService.getConfig();
        Duration interval = config == null ? Duration.ofSeconds(1) : config.getUpdateInterval();
        long intervalMs = Math.max(250L, interval.toMillis());
        this.updateTask = adapter.scheduleRepeating(this::updateAll, intervalMs);

        for (PlayerAdapter player : adapter.getOnlinePlayers()) {
            if (config != null && config.isDefaultEnabled()) {
                enableFor(player, false);
            }
        }
    }

    public void stop() {
        if (updateTask != null) {
            updateTask.cancel();
        }
        sessions.values().forEach(ScoreboardSession::dispose);
        sessions.clear();
    }

    public void reload() {
        configService.load();
        ConfigService.Config config = configService.getConfig();
        sessions.values().forEach(session -> {
            session.applyConfig(config);
            session.update();
        });
        rescheduleUpdates(config);
    }

    public void enableFor(PlayerAdapter player, boolean testMode) {
        if (player == null) {
            return;
        }
        sessions.compute(player.getUniqueId(), (uuid, existing) -> {
            if (existing == null) {
                ScoreboardSession session = new ScoreboardSession(adapter, player, placeholderRegistry, configService.getConfig(), logger);
                session.setTestMode(testMode);
                session.show();
                session.update();
                return session;
            }
            existing.setTestMode(testMode);
            existing.show();
            existing.update();
            return existing;
        });
    }

    public void disableFor(PlayerAdapter player) {
        if (player == null) {
            return;
        }
        ScoreboardSession session = sessions.remove(player.getUniqueId());
        if (session != null) {
            session.dispose();
        }
    }

    public void setTestMode(PlayerAdapter player, boolean enabled) {
        ScoreboardSession session = sessions.get(player.getUniqueId());
        if (session != null) {
            session.setTestMode(enabled);
        }
    }

    private void updateAll() {
        for (ScoreboardSession session : sessions.values()) {
            session.update();
        }
    }

    private void rescheduleUpdates(ConfigService.Config config) {
        if (updateTask != null) {
            updateTask.cancel();
        }
        Duration interval = config == null ? Duration.ofSeconds(1) : config.getUpdateInterval();
        long intervalMs = Math.max(250L, interval.toMillis());
        this.updateTask = adapter.scheduleRepeating(this::updateAll, intervalMs);
    }

    private void handleJoin(PlayerAdapter player) {
        ConfigService.Config config = configService.getConfig();
        if (config != null && config.isDefaultEnabled()) {
            enableFor(player, false);
        }
    }

    private void handleQuit(PlayerAdapter player) {
        disableFor(player);
    }
}
