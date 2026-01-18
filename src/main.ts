import { scoreboardConfig } from "./config/scoreboard.config";
import { ScoreboardManager } from "./scoreboard/ScoreboardManager";
import { PlaceholderRegistry } from "./scoreboard/PlaceholderRegistry";
import { PlayerDataProvider } from "./data/PlayerDataProvider";
import { TestDataProvider } from "./data/TestDataProvider";
import { ScoreboardCommand } from "./commands/ScoreboardCommand";
import { Server } from "./types/hytale";

export function registerScoreboardSystem(server: Server): ScoreboardManager {
  const placeholderRegistry = new PlaceholderRegistry();

  placeholderRegistry.register("playerName", ({ player }) => player.name);
  placeholderRegistry.register("rank", ({ data }) => data.rank);
  placeholderRegistry.register("coins", ({ data }) => data.coins);
  placeholderRegistry.register("level", ({ data }) => data.level);
  placeholderRegistry.register("gameName", ({ gameName }) => gameName);
  placeholderRegistry.register("online", ({ online }) => online);
  placeholderRegistry.register("max", ({ max }) => max);
  placeholderRegistry.register("ping", ({ player }) => player.ping ?? "?");

  const manager = new ScoreboardManager(
    server,
    scoreboardConfig,
    placeholderRegistry,
    new PlayerDataProvider(),
    new TestDataProvider(),
  );

  server.commands.register("scoreboard", (context) => {
    new ScoreboardCommand(manager).handle(context);
  });

  manager.start();

  return manager;
}
