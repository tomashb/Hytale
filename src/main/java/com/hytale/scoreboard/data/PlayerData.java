package com.hytale.scoreboard.data;

public class PlayerData {
  private final String rank;
  private final int coins;
  private final int level;
  private final String gameName;

  public PlayerData(String rank, int coins, int level, String gameName) {
    this.rank = rank;
    this.coins = coins;
    this.level = level;
    this.gameName = gameName;
  }

  public String getRank() {
    return rank;
  }

  public int getCoins() {
    return coins;
  }

  public int getLevel() {
    return level;
  }

  public String getGameName() {
    return gameName;
  }
}
