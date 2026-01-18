package com.example.hytale.scoreboard.placeholders;

import com.example.hytale.scoreboard.adapter.HytaleAdapter;
import com.example.hytale.scoreboard.adapter.PlayerAdapter;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DefaultPlaceholders {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final HytaleAdapter adapter;
    private final PlaceholderRegistry registry;
    private final TestDataProvider testDataProvider = new TestDataProvider();

    public DefaultPlaceholders(HytaleAdapter adapter, PlaceholderRegistry registry) {
        this.adapter = adapter;
        this.registry = registry;
    }

    public void registerDefaults() {
        registry.register("playerName", (player, testMode) -> safe(player, PlayerAdapter::getName, "Joueur"));
        registry.register("rank", (player, testMode) -> testMode
            ? testDataProvider.randomRank()
            : safe(player, PlayerAdapter::getRank, "Inconnu"));
        registry.register("coins", (player, testMode) -> testMode
            ? String.valueOf(testDataProvider.randomCoins())
            : String.valueOf(safeInt(player, PlayerAdapter::getCoins, 0)));
        registry.register("level", (player, testMode) -> testMode
            ? String.valueOf(testDataProvider.randomLevel())
            : String.valueOf(safeInt(player, PlayerAdapter::getLevel, 1)));
        registry.register("ping", (player, testMode) -> testMode
            ? String.valueOf(testDataProvider.randomPing())
            : String.valueOf(safeInt(player, PlayerAdapter::getPing, 0)));
        registry.register("fps", (player, testMode) -> testMode
            ? String.valueOf(testDataProvider.randomFps())
            : String.valueOf(safeInt(player, PlayerAdapter::getFps, 0)));
        registry.register("worldName", (player, testMode) -> testMode
            ? testDataProvider.randomWorld()
            : safe(player, PlayerAdapter::getWorldName, "Monde"));
        registry.register("gameName", (player, testMode) -> testMode
            ? testDataProvider.randomGame()
            : safe(player, PlayerAdapter::getGameName, "Aucun"));
        registry.register("online", (player, testMode) -> String.valueOf(adapter.getOnlinePlayers().size()));
        registry.register("max", (player, testMode) -> String.valueOf(adapter.getMaxPlayers()));
        registry.register("timeFormatted", (player, testMode) -> testMode
            ? testDataProvider.formattedTime()
            : LocalTime.now().format(TIME_FORMATTER));
    }

    private String safe(PlayerAdapter player, ValueExtractor extractor, String fallback) {
        if (player == null) {
            return fallback;
        }
        String value = extractor.extract(player);
        return value == null || value.isBlank() ? fallback : value;
    }

    private int safeInt(PlayerAdapter player, IntExtractor extractor, int fallback) {
        if (player == null) {
            return fallback;
        }
        Integer value = extractor.extract(player);
        return value == null ? fallback : value;
    }

    @FunctionalInterface
    private interface ValueExtractor {
        String extract(PlayerAdapter player);
    }

    @FunctionalInterface
    private interface IntExtractor {
        Integer extract(PlayerAdapter player);
    }
}
