package com.hytale.scoreboard.types;

import java.util.List;

public interface ScoreboardSidebar {
  void setTitle(String title);

  void setLines(List<String> lines);

  void show();

  void hide();

  void dispose();
}
