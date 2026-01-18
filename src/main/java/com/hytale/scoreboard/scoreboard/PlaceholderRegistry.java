package com.hytale.scoreboard.scoreboard;

import com.hytale.scoreboard.types.Player;
import java.util.HashMap;
import java.util.Map;

public class PlaceholderRegistry {
  public interface PlaceholderResolver {
    Object resolve(PlaceholderContext context);
  }

  public static class PlaceholderContext {
    private final Player player;
    private final int online;
    private final int max;
    private final String gameName;
    private final Map<String, Object> data;

    public PlaceholderContext(Player player, int online, int max, String gameName, Map<String, Object> data) {
      this.player = player;
      this.online = online;
      this.max = max;
      this.gameName = gameName;
      this.data = Map.copyOf(data);
    }

    public Player getPlayer() {
      return player;
    }

    public int getOnline() {
      return online;
    }

    public int getMax() {
      return max;
    }

    public String getGameName() {
      return gameName;
    }

    public Map<String, Object> getData() {
      return data;
    }
  }

  private final Map<String, PlaceholderResolver> resolvers = new HashMap<>();

  public void register(String placeholder, PlaceholderResolver resolver) {
    resolvers.put(placeholder, resolver);
  }

  public String resolveLine(String line, PlaceholderContext context) {
    String resolved = line;
    for (Map.Entry<String, PlaceholderResolver> entry : resolvers.entrySet()) {
      Object value = entry.getValue().resolve(context);
      resolved = resolved.replace("{" + entry.getKey() + "}", String.valueOf(value));
    }
    return resolved;
  }
}
