package com.hytale.scoreboard.scoreboard;

import com.hytale.scoreboard.config.ScoreboardConfig;
import com.hytale.scoreboard.data.PlayerData;
import com.hytale.scoreboard.data.PlayerDataProvider;
import com.hytale.scoreboard.data.TestDataProvider;
import com.hytale.scoreboard.types.OnlineStatus;
import com.hytale.scoreboard.types.Player;
import com.hytale.scoreboard.types.ScheduledTask;
import com.hytale.scoreboard.types.Server;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScoreboardManager {
  private final Server server;
  private final ScoreboardConfig config;
  private final PlaceholderRegistry placeholderRegistry;
  private final PlayerDataProvider dataProvider;
  private final TestDataProvider testDataProvider;
  private final Map<String, ScoreboardSession> sessions = new HashMap<>();
  private ScheduledTask updateTask;

  public ScoreboardManager(
      Server server,
      ScoreboardConfig config,
      PlaceholderRegistry placeholderRegistry,
      PlayerDataProvider dataProvider,
      TestDataProvider testDataProvider) {
    this.server = server;
    this.config = config;
    this.placeholderRegistry = placeholderRegistry;
    this.dataProvider = dataProvider;
    this.testDataProvider = testDataProvider;
  }

  public void start() {
    server.getEvents().onPlayerJoin(this::createSession);
    server.getEvents().onPlayerLeave(this::removeSession);

    updateTask = server.getScheduler().runRepeatingTask(this::updateAll, config.getUpdateIntervalMs());
  }

  public void stop() {
    if (updateTask != null) {
      updateTask.cancel();
      updateTask = null;
    }

    for (ScoreboardSession session : sessions.values()) {
      session.destroy();
    }
    sessions.clear();
  }

  public void setEnabled(Player player, boolean enabled) {
    ScoreboardSession session = sessions.get(player.getId());
    if (session == null) {
      return;
    }

    session.setEnabled(enabled);
  }

  public void setTestMode(Player player, boolean enabled) {
    ScoreboardSession session = sessions.get(player.getId());
    if (session == null) {
      return;
    }

    session.setTestMode(enabled);
  }

  private void createSession(Player player) {
    var sidebar = server.createScoreboardSidebar(player);
    ScoreboardSession session = new ScoreboardSession(player, sidebar, config.isDefaultEnabled());
    sessions.put(player.getId(), session);

    if (session.isEnabled()) {
      sidebar.show();
    }
  }

  private void removeSession(Player player) {
    ScoreboardSession session = sessions.get(player.getId());
    if (session == null) {
      return;
    }

    session.destroy();
    sessions.remove(player.getId());
  }

  private void updateAll() {
    OnlineStatus onlineStatus = server.getOnlineStatus();

    for (ScoreboardSession session : sessions.values()) {
      if (!session.isEnabled()) {
        continue;
      }

      PlayerDataProvider provider = session.isTestMode() ? testDataProvider : dataProvider;
      PlayerData playerData = provider.getPlayerData(session.getPlayer());

      Map<String, Object> data = new HashMap<>();
      data.put("rank", playerData.getRank());
      data.put("coins", playerData.getCoins());
      data.put("level", playerData.getLevel());

      PlaceholderRegistry.PlaceholderContext context = new PlaceholderRegistry.PlaceholderContext(
          session.getPlayer(),
          onlineStatus.getOnline(),
          onlineStatus.getMax(),
          playerData.getGameName(),
          data);

      String title = placeholderRegistry.resolveLine(config.getTitle(), context);
      List<String> lines = new ArrayList<>();
      for (String line : config.getLines()) {
        lines.add(placeholderRegistry.resolveLine(line, context));
      }

      session.update(title, dedupeLines(lines));
    }
  }

  private List<String> dedupeLines(List<String> lines) {
    Map<String, Integer> seen = new HashMap<>();
    List<String> deduped = new ArrayList<>();
    for (String line : lines) {
      int count = seen.getOrDefault(line, 0);
      seen.put(line, count + 1);
      if (count == 0) {
        deduped.add(line);
      } else {
        deduped.add(line + "\u200B" + count);
      }
    }
    return deduped;
  }
}
