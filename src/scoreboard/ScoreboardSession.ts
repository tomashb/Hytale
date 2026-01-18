import { ScoreboardSidebar, Player } from "../types/hytale";

export class ScoreboardSession {
  readonly player: Player;
  private readonly sidebar: ScoreboardSidebar;
  private enabled = true;
  private testMode = false;

  constructor(player: Player, sidebar: ScoreboardSidebar, enabled: boolean) {
    this.player = player;
    this.sidebar = sidebar;
    this.enabled = enabled;
  }

  isEnabled(): boolean {
    return this.enabled;
  }

  setEnabled(enabled: boolean): void {
    this.enabled = enabled;
    if (enabled) {
      this.sidebar.show();
    } else {
      this.sidebar.hide();
    }
  }

  isTestMode(): boolean {
    return this.testMode;
  }

  setTestMode(enabled: boolean): void {
    this.testMode = enabled;
  }

  update(title: string, lines: string[]): void {
    if (!this.enabled) {
      return;
    }

    this.sidebar.setTitle(title);
    this.sidebar.setLines(lines);
  }

  destroy(): void {
    this.sidebar.dispose();
  }
}
