package com.hytale.scoreboard.data;

import com.hytale.scoreboard.types.Player;

public class PlayerDataProvider {
  public PlayerData getPlayerData(Player player) {
    String rank = player.getRank() != null ? player.getRank() : "Aventurier";
    int coins = player.getCoins() != null ? player.getCoins() : 0;
    int level = player.getLevel() != null ? player.getLevel() : 1;

    return new PlayerData(rank, coins, level, "Mini-jeu");
  }
}
