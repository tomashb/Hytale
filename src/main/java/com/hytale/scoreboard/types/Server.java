package com.hytale.scoreboard.types;

public interface Server {
  ServerEvents getEvents();

  Scheduler getScheduler();

  CommandRegistry getCommands();

  OnlineStatus getOnlineStatus();

  ScoreboardSidebar createScoreboardSidebar(Player player);
}
