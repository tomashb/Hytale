package com.hytale.scoreboard.config;

import java.util.List;

public class ScoreboardConfig {
  private final String title;
  private final List<String> lines;
  private final long updateIntervalMs;
  private final boolean defaultEnabled;

  public ScoreboardConfig(String title, List<String> lines, long updateIntervalMs, boolean defaultEnabled) {
    this.title = title;
    this.lines = List.copyOf(lines);
    this.updateIntervalMs = updateIntervalMs;
    this.defaultEnabled = defaultEnabled;
  }

  public String getTitle() {
    return title;
  }

  public List<String> getLines() {
    return lines;
  }

  public long getUpdateIntervalMs() {
    return updateIntervalMs;
  }

  public boolean isDefaultEnabled() {
    return defaultEnabled;
  }

  public static ScoreboardConfig defaultConfig() {
    return new ScoreboardConfig(
        "[Nom du serveur] | TEST",
        List.of(
            "Joueur: {playerName}",
            "Rang: {rank}",
            "Pièces: {coins}",
            "Niveau: {level}",
            "",
            "Mini-jeu: {gameName}",
            "Joueurs en ligne: {online}/{max}"),
        1000L,
        true);
  }
}
