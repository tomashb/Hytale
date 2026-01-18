import { Player } from "../types/hytale";

export type PlayerData = {
  rank: string;
  coins: number;
  level: number;
  gameName: string;
};

export class PlayerDataProvider {
  getPlayerData(player: Player): PlayerData {
    return {
      rank: player.rank ?? "Aventurier",
      coins: player.coins ?? 0,
      level: player.level ?? 1,
      gameName: "Mini-jeu",
    };
  }
}
