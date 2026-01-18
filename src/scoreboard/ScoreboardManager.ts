import { Player, Server } from "../types/hytale";
import { ScoreboardConfig } from "../config/scoreboard.config";
import { PlaceholderRegistry } from "./PlaceholderRegistry";
import { PlayerDataProvider } from "../data/PlayerDataProvider";
import { TestDataProvider } from "../data/TestDataProvider";
import { ScoreboardSession } from "./ScoreboardSession";

export class ScoreboardManager {
  private readonly server: Server;
  private readonly config: ScoreboardConfig;
  private readonly placeholderRegistry: PlaceholderRegistry;
  private readonly dataProvider: PlayerDataProvider;
  private readonly testDataProvider: TestDataProvider;
  private readonly sessions = new Map<string, ScoreboardSession>();
  private updateTask: { cancel: () => void } | null = null;

  constructor(
    server: Server,
    config: ScoreboardConfig,
    placeholderRegistry: PlaceholderRegistry,
    dataProvider: PlayerDataProvider,
    testDataProvider: TestDataProvider,
  ) {
    this.server = server;
    this.config = config;
    this.placeholderRegistry = placeholderRegistry;
    this.dataProvider = dataProvider;
    this.testDataProvider = testDataProvider;
  }

  start(): void {
    this.server.events.onPlayerJoin((player) => this.createSession(player));
    this.server.events.onPlayerLeave((player) => this.removeSession(player));

    this.updateTask = this.server.scheduler.runRepeatingTask(
      () => this.updateAll(),
      this.config.updateIntervalMs,
    );
  }

  stop(): void {
    this.updateTask?.cancel();
    this.updateTask = null;

    for (const session of this.sessions.values()) {
      session.destroy();
    }
    this.sessions.clear();
  }

  setEnabled(player: Player, enabled: boolean): void {
    const session = this.sessions.get(player.id);
    if (!session) {
      return;
    }

    session.setEnabled(enabled);
  }

  setTestMode(player: Player, enabled: boolean): void {
    const session = this.sessions.get(player.id);
    if (!session) {
      return;
    }

    session.setTestMode(enabled);
  }

  private createSession(player: Player): void {
    const sidebar = this.server.createScoreboardSidebar(player);
    const session = new ScoreboardSession(player, sidebar, this.config.defaultEnabled);
    this.sessions.set(player.id, session);

    if (session.isEnabled()) {
      sidebar.show();
    }
  }

  private removeSession(player: Player): void {
    const session = this.sessions.get(player.id);
    if (!session) {
      return;
    }

    session.destroy();
    this.sessions.delete(player.id);
  }

  private updateAll(): void {
    const onlineStatus = this.server.getOnlineStatus();

    for (const session of this.sessions.values()) {
      if (!session.isEnabled()) {
        continue;
      }

      const dataProvider = session.isTestMode() ? this.testDataProvider : this.dataProvider;
      const playerData = dataProvider.getPlayerData(session.player);

      const context = {
        player: session.player,
        online: onlineStatus.online,
        max: onlineStatus.max,
        gameName: playerData.gameName,
        data: {
          rank: playerData.rank,
          coins: playerData.coins,
          level: playerData.level,
        },
      };

      const title = this.placeholderRegistry.resolveLine(this.config.title, context);
      const lines = this.config.lines.map((line) =>
        this.placeholderRegistry.resolveLine(line, context),
      );

      session.update(title, this.dedupeLines(lines));
    }
  }

  private dedupeLines(lines: string[]): string[] {
    const seen = new Map<string, number>();
    return lines.map((line) => {
      const count = seen.get(line) ?? 0;
      seen.set(line, count + 1);
      if (count === 0) {
        return line;
      }
      return `${line}\u200B${count}`;
    });
  }
}
