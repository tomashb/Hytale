import { Player } from "../types/hytale";
import { PlayerData } from "./PlayerDataProvider";

export class TestDataProvider {
  private tick = 0;

  getPlayerData(player: Player): PlayerData {
    this.tick += 1;

    return {
      rank: "Testeur",
      coins: 1200 + (this.tick % 50),
      level: 5 + (this.tick % 3),
      gameName: "Test Arena",
    };
  }
}
