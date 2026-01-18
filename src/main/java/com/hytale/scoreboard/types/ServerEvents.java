package com.hytale.scoreboard.types;

import java.util.function.Consumer;

public interface ServerEvents {
  void onPlayerJoin(Consumer<Player> handler);

  void onPlayerLeave(Consumer<Player> handler);
}
