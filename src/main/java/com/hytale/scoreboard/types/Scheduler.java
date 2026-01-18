package com.hytale.scoreboard.types;

public interface Scheduler {
  ScheduledTask runRepeatingTask(Runnable handler, long intervalMs);
}
