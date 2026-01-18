# Hytale Scoreboard (Test)

Ce dépôt fournit un système de scoreboard générique, configurable et par joueur pour un serveur Hytale orienté mini-jeux.

## Structure

```
src/
  commands/
    ScoreboardCommand.ts
  config/
    scoreboard.config.ts
  data/
    PlayerDataProvider.ts
    TestDataProvider.ts
  scoreboard/
    PlaceholderRegistry.ts
    ScoreboardManager.ts
    ScoreboardSession.ts
  types/
    hytale.ts
  main.ts
```

## Installation rapide

1. Copiez le dossier `src/` dans votre projet/mod Hytale.
2. Dans votre point d'entrée serveur, appelez `registerScoreboardSystem(server)`.
3. Redémarrez votre serveur.

Exemple d'appel :

```ts
import { registerScoreboardSystem } from "./src/main";

registerScoreboardSystem(server);
```

## Compiler le projet

1. Installez les dépendances :

```bash
npm install
```

2. Compilez le TypeScript :

```bash
npm run build
```

Les fichiers JavaScript compilés seront disponibles dans `dist/`.

## Configuration

Le fichier `src/config/scoreboard.config.ts` vous permet de :

- Modifier le titre (`title`).
- Modifier les lignes (`lines`).
- Ajuster la fréquence d'update (`updateIntervalMs`).
- Définir si le scoreboard est activé par défaut (`defaultEnabled`).

## Commandes

- `/scoreboard on` : active le scoreboard.
- `/scoreboard off` : désactive le scoreboard.
- `/scoreboard test` : active le mode test (données fictives).

## Ajouter des placeholders

Dans `src/main.ts`, vous pouvez enregistrer un nouveau placeholder :

```ts
placeholderRegistry.register("monPlaceholder", ({ player }) => player.name);
```

Utilisez ensuite `{monPlaceholder}` dans le titre ou les lignes.

## Mode test

Le mode test utilise `src/data/TestDataProvider.ts`, qui renvoie des valeurs fictives.
Vous pouvez y personnaliser les valeurs affichées.
