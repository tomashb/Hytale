package com.hytale.scoreboard.types;

import java.util.List;

public interface CommandContext {
  Player getPlayer();

  List<String> getArgs();

  void reply(String message);
}
