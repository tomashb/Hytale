import { ScoreboardManager } from "../scoreboard/ScoreboardManager";
import { CommandContext } from "../types/hytale";

export class ScoreboardCommand {
  private readonly manager: ScoreboardManager;

  constructor(manager: ScoreboardManager) {
    this.manager = manager;
  }

  handle(context: CommandContext): void {
    const [subcommand] = context.args;

    if (!subcommand) {
      context.reply("Utilisation: /scoreboard <on|off|test>");
      return;
    }

    switch (subcommand.toLowerCase()) {
      case "on":
        this.manager.setEnabled(context.player, true);
        this.manager.setTestMode(context.player, false);
        context.reply("Scoreboard activé.");
        return;
      case "off":
        this.manager.setEnabled(context.player, false);
        this.manager.setTestMode(context.player, false);
        context.reply("Scoreboard désactivé.");
        return;
      case "test":
        this.manager.setEnabled(context.player, true);
        this.manager.setTestMode(context.player, true);
        context.reply("Scoreboard en mode test.");
        return;
      default:
        context.reply("Utilisation: /scoreboard <on|off|test>");
    }
  }
}
