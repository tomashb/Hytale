package com.example.hytale.scoreboard.commands;

import com.example.hytale.scoreboard.ScoreboardManager;
import com.example.hytale.scoreboard.adapter.CommandHandler;
import com.example.hytale.scoreboard.adapter.PlayerAdapter;

import java.util.Locale;
import java.util.logging.Logger;

public class ScoreboardCommand implements CommandHandler {
    private final ScoreboardManager scoreboardManager;
    private final Logger logger;

    public ScoreboardCommand(ScoreboardManager scoreboardManager, Logger logger) {
        this.scoreboardManager = scoreboardManager;
        this.logger = logger;
    }

    @Override
    public boolean onCommand(PlayerAdapter player, String[] args) {
        if (player == null) {
            logger.info("Commande /scoreboard disponible uniquement en jeu");
            return true;
        }
        if (args.length == 0) {
            player.sendMessage("Utilisation: /scoreboard <on|off|test|reload>");
            return true;
        }

        String sub = args[0].toLowerCase(Locale.ROOT);
        switch (sub) {
            case "on" -> {
                scoreboardManager.enableFor(player, false);
                player.sendMessage("Scoreboard activé.");
            }
            case "off" -> {
                scoreboardManager.disableFor(player);
                player.sendMessage("Scoreboard désactivé.");
            }
            case "test" -> {
                scoreboardManager.enableFor(player, true);
                player.sendMessage("Scoreboard en mode TEST.");
            }
            case "reload" -> {
                scoreboardManager.reload();
                player.sendMessage("Configuration rechargée.");
            }
            default -> player.sendMessage("Commande inconnue: /scoreboard " + sub);
        }
        return true;
    }
}
