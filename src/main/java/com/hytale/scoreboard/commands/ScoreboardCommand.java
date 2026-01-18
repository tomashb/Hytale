package com.hytale.scoreboard.commands;

import com.hytale.scoreboard.scoreboard.ScoreboardManager;
import com.hytale.scoreboard.types.CommandContext;

public class ScoreboardCommand {
  private final ScoreboardManager manager;

  public ScoreboardCommand(ScoreboardManager manager) {
    this.manager = manager;
  }

  public void handle(CommandContext context) {
    String subcommand = context.getArgs().isEmpty() ? null : context.getArgs().get(0);

    if (subcommand == null) {
      context.reply("Utilisation: /scoreboard <on|off|test>");
      return;
    }

    switch (subcommand.toLowerCase()) {
      case "on":
        manager.setEnabled(context.getPlayer(), true);
        manager.setTestMode(context.getPlayer(), false);
        context.reply("Scoreboard activé.");
        return;
      case "off":
        manager.setEnabled(context.getPlayer(), false);
        manager.setTestMode(context.getPlayer(), false);
        context.reply("Scoreboard désactivé.");
        return;
      case "test":
        manager.setEnabled(context.getPlayer(), true);
        manager.setTestMode(context.getPlayer(), true);
        context.reply("Scoreboard en mode test.");
        return;
      default:
        context.reply("Utilisation: /scoreboard <on|off|test>");
    }
  }
}
