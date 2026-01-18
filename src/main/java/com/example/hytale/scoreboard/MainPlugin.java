package com.example.hytale.scoreboard;

import com.example.hytale.scoreboard.adapter.HytaleAdapter;
import com.example.hytale.scoreboard.adapter.impl.HytaleAdapterImpl;
import com.example.hytale.scoreboard.commands.ScoreboardCommand;
import com.example.hytale.scoreboard.placeholders.DefaultPlaceholders;
import com.example.hytale.scoreboard.placeholders.PlaceholderRegistry;

import java.util.Objects;
import java.util.logging.Logger;

public class MainPlugin {
    private final HytaleAdapter adapter;
    private final ConfigService configService;
    private final PlaceholderRegistry placeholderRegistry;
    private final ScoreboardManager scoreboardManager;
    private final Logger logger;

    public MainPlugin() {
        this.adapter = new HytaleAdapterImpl("HytaleScoreboardTest");
        this.logger = Objects.requireNonNull(adapter.getLogger(), "Logger must not be null");
        this.configService = new ConfigService(adapter, logger);
        this.placeholderRegistry = new PlaceholderRegistry();
        new DefaultPlaceholders(adapter, placeholderRegistry).registerDefaults();
        this.scoreboardManager = new ScoreboardManager(adapter, configService, placeholderRegistry, logger);
    }

    public void onEnable() {
        configService.load();
        scoreboardManager.start();
        adapter.registerCommand("scoreboard", new ScoreboardCommand(scoreboardManager, configService, logger));
        logger.info("HytaleScoreboardTest enabled");
    }

    public void onDisable() {
        scoreboardManager.stop();
        logger.info("HytaleScoreboardTest disabled");
    }
}
