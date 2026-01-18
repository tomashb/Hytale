package com.hytale.scoreboard.types;

public class Player {
  private final String id;
  private final String name;
  private final Integer ping;
  private final Integer level;
  private final Integer coins;
  private final String rank;

  public Player(String id, String name, Integer ping, Integer level, Integer coins, String rank) {
    this.id = id;
    this.name = name;
    this.ping = ping;
    this.level = level;
    this.coins = coins;
    this.rank = rank;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Integer getPing() {
    return ping;
  }

  public Integer getLevel() {
    return level;
  }

  public Integer getCoins() {
    return coins;
  }

  public String getRank() {
    return rank;
  }
}
