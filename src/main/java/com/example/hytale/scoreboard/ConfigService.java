package com.example.hytale.scoreboard;

import com.example.hytale.scoreboard.adapter.HytaleAdapter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class ConfigService {
    private final HytaleAdapter adapter;
    private final Logger logger;
    private Config currentConfig;

    public ConfigService(HytaleAdapter adapter, Logger logger) {
        this.adapter = adapter;
        this.logger = logger;
    }

    public void load() {
        File dataFolder = adapter.getDataFolder();
        if (!dataFolder.exists() && !dataFolder.mkdirs()) {
            logger.warning("Unable to create plugin data folder: " + dataFolder.getAbsolutePath());
        }

        File configFile = new File(dataFolder, "config.properties");
        if (!configFile.exists()) {
            try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
                if (input != null) {
                    Files.copy(input, configFile.toPath());
                } else {
                    logger.warning("Default config.properties not found in resources");
                }
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to copy default config.properties", ex);
            }
        }

        Properties properties = new Properties();
        if (configFile.exists()) {
            try (InputStream input = Files.newInputStream(configFile.toPath())) {
                properties.load(input);
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Failed to load config.properties", ex);
            }
        }

        String title = properties.getProperty("scoreboard.title", "MonServeur | TEST");
        long updateIntervalMs = parseLong(properties.getProperty("scoreboard.updateIntervalMs"), 1000L);
        boolean defaultEnabled = Boolean.parseBoolean(properties.getProperty("scoreboard.defaultEnabled", "true"));
        List<String> lines = parseLines(properties.getProperty("scoreboard.lines"));

        if (lines.isEmpty()) {
            lines = Collections.singletonList("Scoreboard configuré");
        }

        this.currentConfig = new Config(title, lines, Duration.ofMillis(updateIntervalMs), defaultEnabled);
        logger.info("Scoreboard config loaded with " + lines.size() + " lines");
    }

    public Config getConfig() {
        return currentConfig;
    }

    private long parseLong(String value, long fallback) {
        try {
            return value == null ? fallback : Long.parseLong(value.trim());
        } catch (NumberFormatException ex) {
            return fallback;
        }
    }

    private List<String> parseLines(String raw) {
        if (raw == null || raw.isBlank()) {
            return Collections.emptyList();
        }
        return Arrays.stream(raw.split("\\|"))
            .map(String::trim)
            .filter(line -> !line.isBlank())
            .collect(Collectors.toList());
    }

    public static class Config {
        private final String title;
        private final List<String> lines;
        private final Duration updateInterval;
        private final boolean defaultEnabled;

        public Config(String title, List<String> lines, Duration updateInterval, boolean defaultEnabled) {
            this.title = title;
            this.lines = List.copyOf(lines);
            this.updateInterval = updateInterval;
            this.defaultEnabled = defaultEnabled;
        }

        public String getTitle() {
            return title;
        }

        public List<String> getLines() {
            return lines;
        }

        public Duration getUpdateInterval() {
            return updateInterval;
        }

        public boolean isDefaultEnabled() {
            return defaultEnabled;
        }
    }
}
