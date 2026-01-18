package com.example.hytale.scoreboard;

import com.example.hytale.scoreboard.adapter.HytaleAdapter;
import com.example.hytale.scoreboard.adapter.PlayerAdapter;
import com.example.hytale.scoreboard.adapter.ScoreboardView;
import com.example.hytale.scoreboard.placeholders.PlaceholderRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

public class ScoreboardSession {
    private final HytaleAdapter adapter;
    private final PlayerAdapter player;
    private final PlaceholderRegistry placeholderRegistry;
    private final Logger logger;
    private ScoreboardView view;
    private ConfigService.Config config;
    private boolean testMode;
    private List<String> lastLines = new ArrayList<>();
    private String lastTitle = "";

    public ScoreboardSession(HytaleAdapter adapter,
                             PlayerAdapter player,
                             PlaceholderRegistry placeholderRegistry,
                             ConfigService.Config config,
                             Logger logger) {
        this.adapter = adapter;
        this.player = player;
        this.placeholderRegistry = placeholderRegistry;
        this.config = config;
        this.logger = logger;
    }

    public void applyConfig(ConfigService.Config config) {
        this.config = config;
        this.lastLines = new ArrayList<>();
        this.lastTitle = "";
    }

    public void setTestMode(boolean testMode) {
        this.testMode = testMode;
    }

    public void show() {
        if (view == null) {
            view = adapter.createScoreboard(player);
        }
        if (view != null) {
            view.show();
        }
    }

    public void update() {
        if (view == null || config == null) {
            return;
        }
        String title = placeholderRegistry.renderLine(config.getTitle(), player, testMode);
        List<String> lines = new ArrayList<>();
        for (String template : config.getLines()) {
            lines.add(placeholderRegistry.renderLine(template, player, testMode));
        }
        if (!Objects.equals(title, lastTitle)) {
            view.setTitle(title);
            lastTitle = title;
        }
        if (!lines.equals(lastLines)) {
            view.setLines(lines);
            lastLines = lines;
        }
    }

    public void dispose() {
        if (view != null) {
            try {
                view.hide();
                view.destroy();
            } catch (Exception ex) {
                logger.fine("Failed to dispose scoreboard view: " + ex.getMessage());
            }
            view = null;
        }
    }
}
