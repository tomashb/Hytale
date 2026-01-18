package com.hytale.scoreboard.data;

import com.hytale.scoreboard.types.Player;

public class TestDataProvider extends PlayerDataProvider {
  private int tick = 0;

  @Override
  public PlayerData getPlayerData(Player player) {
    tick += 1;

    return new PlayerData(
        "Testeur",
        1200 + (tick % 50),
        5 + (tick % 3),
        "Test Arena");
  }
}
