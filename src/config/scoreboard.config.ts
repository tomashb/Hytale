export type ScoreboardConfig = {
  title: string;
  lines: string[];
  updateIntervalMs: number;
  defaultEnabled: boolean;
};

export const scoreboardConfig: ScoreboardConfig = {
  title: "[Nom du serveur] | TEST",
  lines: [
    "Joueur: {playerName}",
    "Rang: {rank}",
    "Pièces: {coins}",
    "Niveau: {level}",
    "",
    "Mini-jeu: {gameName}",
    "Joueurs en ligne: {online}/{max}",
  ],
  updateIntervalMs: 1000,
  defaultEnabled: true,
};
