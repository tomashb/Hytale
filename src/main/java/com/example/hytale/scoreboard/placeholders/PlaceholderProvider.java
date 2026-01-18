package com.example.hytale.scoreboard.placeholders;

import com.example.hytale.scoreboard.adapter.PlayerAdapter;

@FunctionalInterface
public interface PlaceholderProvider {
    String provide(PlayerAdapter player, boolean testMode);
}
