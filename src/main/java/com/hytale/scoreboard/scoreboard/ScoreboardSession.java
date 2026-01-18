package com.hytale.scoreboard.scoreboard;

import com.hytale.scoreboard.types.Player;
import com.hytale.scoreboard.types.ScoreboardSidebar;
import java.util.List;

public class ScoreboardSession {
  private final Player player;
  private final ScoreboardSidebar sidebar;
  private boolean enabled;
  private boolean testMode;

  public ScoreboardSession(Player player, ScoreboardSidebar sidebar, boolean enabled) {
    this.player = player;
    this.sidebar = sidebar;
    this.enabled = enabled;
    this.testMode = false;
  }

  public Player getPlayer() {
    return player;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
    if (enabled) {
      sidebar.show();
    } else {
      sidebar.hide();
    }
  }

  public boolean isTestMode() {
    return testMode;
  }

  public void setTestMode(boolean enabled) {
    this.testMode = enabled;
  }

  public void update(String title, List<String> lines) {
    if (!enabled) {
      return;
    }

    sidebar.setTitle(title);
    sidebar.setLines(lines);
  }

  public void destroy() {
    sidebar.dispose();
  }
}
