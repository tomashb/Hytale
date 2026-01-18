package com.example.hytale.scoreboard.placeholders;

import com.example.hytale.scoreboard.adapter.PlayerAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlaceholderRegistry {
    private static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\{([a-zA-Z0-9_]+)}");
    private final Map<String, PlaceholderProvider> providers = new ConcurrentHashMap<>();

    public void register(String key, PlaceholderProvider provider) {
        providers.put(key, provider);
    }

    public String renderLine(String template, PlayerAdapter player, boolean testMode) {
        if (template == null) {
            return "";
        }
        Matcher matcher = PLACEHOLDER_PATTERN.matcher(template);
        StringBuffer buffer = new StringBuffer();
        while (matcher.find()) {
            String key = matcher.group(1);
            PlaceholderProvider provider = providers.get(key);
            String value = provider == null ? "" : safeValue(provider.provide(player, testMode));
            matcher.appendReplacement(buffer, Matcher.quoteReplacement(value));
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

    private String safeValue(String value) {
        return value == null ? "" : value;
    }
}
