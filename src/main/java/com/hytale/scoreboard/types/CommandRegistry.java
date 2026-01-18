package com.hytale.scoreboard.types;

import java.util.function.Consumer;

public interface CommandRegistry {
  void register(String name, Consumer<CommandContext> handler);
}
