package com.hytale.scoreboard.types;

public class OnlineStatus {
  private final int online;
  private final int max;

  public OnlineStatus(int online, int max) {
    this.online = online;
    this.max = max;
  }

  public int getOnline() {
    return online;
  }

  public int getMax() {
    return max;
  }
}
