# Hytale Scoreboard (Java/Maven)

Ce dépôt fournit un système de scoreboard générique, configurable et par joueur pour un serveur Hytale orienté mini-jeux, écrit en **Java**.

## Structure

```
src/main/java/com/hytale/scoreboard/
  commands/
    ScoreboardCommand.java
  config/
    ScoreboardConfig.java
  data/
    PlayerData.java
    PlayerDataProvider.java
    TestDataProvider.java
  scoreboard/
    PlaceholderRegistry.java
    ScoreboardManager.java
    ScoreboardSession.java
  types/
    CommandContext.java
    CommandRegistry.java
    OnlineStatus.java
    Player.java
    ScheduledTask.java
    Scheduler.java
    ScoreboardSidebar.java
    Server.java
    ServerEvents.java
  ScoreboardSystem.java
```

## Installation rapide

1. Copiez le dossier `src/main/java/com/hytale/scoreboard/` dans votre projet/mod Hytale.
2. Dans votre point d'entrée serveur, appelez `ScoreboardSystem.registerScoreboardSystem(server)`.
3. Redémarrez votre serveur.

Exemple d'appel :

```java
import com.hytale.scoreboard.ScoreboardSystem;
import com.hytale.scoreboard.scoreboard.ScoreboardManager;

ScoreboardManager manager = ScoreboardSystem.registerScoreboardSystem(server);
```

## Compiler le projet

1. Compilez avec Maven :

```bash
mvn package
```

Le JAR sera disponible dans `target/`.

## Configuration

La classe `ScoreboardConfig` vous permet de :

- Modifier le titre (`title`).
- Modifier les lignes (`lines`).
- Ajuster la fréquence d'update (`updateIntervalMs`).
- Définir si le scoreboard est activé par défaut (`defaultEnabled`).

Par défaut, `ScoreboardSystem` utilise `ScoreboardConfig.defaultConfig()`.

## Commandes

- `/scoreboard on` : active le scoreboard.
- `/scoreboard off` : désactive le scoreboard.
- `/scoreboard test` : active le mode test (données fictives).

## Ajouter des placeholders

Dans `ScoreboardSystem`, vous pouvez enregistrer un nouveau placeholder :

```java
placeholderRegistry.register("monPlaceholder", context -> context.getPlayer().getName());
```

Utilisez ensuite `{monPlaceholder}` dans le titre ou les lignes.

## Mode test

Le mode test utilise `TestDataProvider`, qui renvoie des valeurs fictives.
Vous pouvez y personnaliser les valeurs affichées.
