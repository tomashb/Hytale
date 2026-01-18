package com.example.hytale.scoreboard.placeholders;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

public class TestDataProvider {
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");

    public String randomRank() {
        String[] ranks = {"Aventurier", "Héros", "Champion", "Légende"};
        return ranks[ThreadLocalRandom.current().nextInt(ranks.length)];
    }

    public int randomCoins() {
        return 1000 + ThreadLocalRandom.current().nextInt(9000);
    }

    public int randomLevel() {
        return 1 + ThreadLocalRandom.current().nextInt(60);
    }

    public int randomPing() {
        return 30 + ThreadLocalRandom.current().nextInt(120);
    }

    public int randomFps() {
        return 60 + ThreadLocalRandom.current().nextInt(90);
    }

    public String randomWorld() {
        String[] worlds = {"Lobby", "Hub", "Donjon", "Citadelle"};
        return worlds[ThreadLocalRandom.current().nextInt(worlds.length)];
    }

    public String randomGame() {
        String[] games = {"SkyWars", "Rush", "Arena", "Adventure"};
        return games[ThreadLocalRandom.current().nextInt(games.length)];
    }

    public String formattedTime() {
        return LocalTime.now().format(TIME_FORMATTER);
    }
}
