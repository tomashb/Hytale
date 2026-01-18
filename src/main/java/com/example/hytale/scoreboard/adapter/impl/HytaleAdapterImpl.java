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
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class HytaleAdapterImpl implements HytaleAdapter {
    private final Logger logger = Logger.getLogger("HytaleScoreboardTest");
    private final File dataFolder;
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
    private final Map<UUID, DemoPlayer> players = new ConcurrentHashMap<>();
    private final Map<String, CommandHandler> commands = new ConcurrentHashMap<>();
    private final List<PlayerListener> joinListeners = new CopyOnWriteArrayList<>();
    private final List<PlayerListener> quitListeners = new CopyOnWriteArrayList<>();
    private final int maxPlayers = 100;

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
        return Collections.unmodifiableCollection(players.values());
    }

    @Override
    public int getMaxPlayers() {
        return maxPlayers;
    }

    @Override
    public ScoreboardView createScoreboard(PlayerAdapter player) {
        return new ConsoleScoreboardView(logger, player);
    }

    @Override
    public ScheduledTask scheduleRepeating(Runnable task, long intervalMs) {
        var future = scheduler.scheduleAtFixedRate(task, intervalMs, intervalMs, TimeUnit.MILLISECONDS);
        return () -> future.cancel(false);
    }

    @Override
    public void registerCommand(String label, CommandHandler handler) {
        if (label == null || handler == null) {
            return;
        }
        commands.put(label.toLowerCase(), handler);
    }

    @Override
    public void registerPlayerJoinListener(PlayerListener listener) {
        if (listener != null) {
            joinListeners.add(listener);
        }
    }

    @Override
    public void registerPlayerQuitListener(PlayerListener listener) {
        if (listener != null) {
            quitListeners.add(listener);
        }
    }

    public PlayerAdapter addDemoPlayer(String name) {
        DemoPlayer player = new DemoPlayer(name);
        players.put(player.getUniqueId(), player);
        joinListeners.forEach(listener -> listener.onPlayerEvent(player));
        return player;
    }

    public void removeDemoPlayer(UUID playerId) {
        DemoPlayer removed = players.remove(playerId);
        if (removed != null) {
            quitListeners.forEach(listener -> listener.onPlayerEvent(removed));
        }
    }

    public boolean dispatchCommand(PlayerAdapter player, String rawCommand) {
        if (rawCommand == null || rawCommand.isBlank()) {
            return false;
        }
        String[] parts = rawCommand.trim().split("\\s+");
        String label = parts[0].toLowerCase();
        CommandHandler handler = commands.get(label);
        if (handler == null) {
            return false;
        }
        String[] args = parts.length > 1 ? java.util.Arrays.copyOfRange(parts, 1, parts.length) : new String[0];
        return handler.onCommand(player, args);
    }

    public void shutdown() {
        scheduler.shutdownNow();
    }

    private static class ConsoleScoreboardView implements ScoreboardView {
        private final Logger logger;
        private final PlayerAdapter player;
        private String title = "";
        private List<String> lines = List.of();
        private boolean visible;

        private ConsoleScoreboardView(Logger logger, PlayerAdapter player) {
            this.logger = logger;
            this.player = player;
        }

        @Override
        public void setTitle(String title) {
            this.title = title == null ? "" : title;
            if (visible) {
                logSnapshot("Mise à jour du titre");
            }
        }

        @Override
        public void setLines(java.util.List<String> lines) {
            this.lines = lines == null ? List.of() : List.copyOf(lines);
            if (visible) {
                logSnapshot("Mise à jour des lignes");
            }
        }

        @Override
        public void show() {
            visible = true;
            logSnapshot("Affichage du scoreboard");
        }

        @Override
        public void hide() {
            visible = false;
            logger.info(() -> "Scoreboard masqué pour " + player.getName());
        }

        @Override
        public void destroy() {
            logger.info(() -> "Scoreboard détruit pour " + player.getName());
        }

        private void logSnapshot(String prefix) {
            StringBuilder builder = new StringBuilder();
            builder.append(prefix).append(" pour ").append(player.getName()).append(" -> ").append(title);
            for (String line : lines) {
                builder.append(System.lineSeparator()).append(" - ").append(line);
            }
            logger.info(builder::toString);
        }
    }

    private static class DemoPlayer implements PlayerAdapter {
        private final UUID uniqueId = UUID.randomUUID();
        private final String name;

        private DemoPlayer(String name) {
            this.name = name == null || name.isBlank() ? "Joueur" : name;
        }

        @Override
        public UUID getUniqueId() {
            return uniqueId;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String getRank() {
            return "Aventurier";
        }

        @Override
        public Integer getCoins() {
            return 1200;
        }

        @Override
        public Integer getLevel() {
            return 5;
        }

        @Override
        public Integer getPing() {
            return 42;
        }

        @Override
        public Integer getFps() {
            return 120;
        }

        @Override
        public String getWorldName() {
            return "Lobby";
        }

        @Override
        public String getGameName() {
            return "Hub";
        }

        @Override
        public void sendMessage(String message) {
            logger.info(() -> "[Message " + name + "] " + message);
        }
    }
}
