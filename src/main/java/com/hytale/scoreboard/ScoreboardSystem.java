package com.hytale.scoreboard;

import com.hytale.scoreboard.commands.ScoreboardCommand;
import com.hytale.scoreboard.config.ScoreboardConfig;
import com.hytale.scoreboard.data.PlayerDataProvider;
import com.hytale.scoreboard.data.TestDataProvider;
import com.hytale.scoreboard.scoreboard.PlaceholderRegistry;
import com.hytale.scoreboard.scoreboard.ScoreboardManager;
import com.hytale.scoreboard.types.Server;

public final class ScoreboardSystem {
  private ScoreboardSystem() {}

  public static ScoreboardManager registerScoreboardSystem(Server server) {
    PlaceholderRegistry placeholderRegistry = new PlaceholderRegistry();

    placeholderRegistry.register("playerName", context -> context.getPlayer().getName());
    placeholderRegistry.register("rank", context -> context.getData().get("rank"));
    placeholderRegistry.register("coins", context -> context.getData().get("coins"));
    placeholderRegistry.register("level", context -> context.getData().get("level"));
    placeholderRegistry.register("gameName", PlaceholderRegistry.PlaceholderContext::getGameName);
    placeholderRegistry.register("online", PlaceholderRegistry.PlaceholderContext::getOnline);
    placeholderRegistry.register("max", PlaceholderRegistry.PlaceholderContext::getMax);
    placeholderRegistry.register(
        "ping",
        context -> context.getPlayer().getPing() != null ? context.getPlayer().getPing() : "?");

    ScoreboardManager manager = new ScoreboardManager(
        server,
        ScoreboardConfig.defaultConfig(),
        placeholderRegistry,
        new PlayerDataProvider(),
        new TestDataProvider());

    server.getCommands().register("scoreboard", context -> new ScoreboardCommand(manager).handle(context));

    manager.start();

    return manager;
  }
}
