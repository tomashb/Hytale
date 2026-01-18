# Hytale Scoreboard Test Plugin

Plugin Java (Maven) qui affiche un scoreboard **sidebar** par joueur avec placeholders, mise à jour périodique et commandes d’activation.

## Récupération de `HytaleServer.jar`
1. Installez le serveur/launcher Hytale.
2. Localisez le SDK/Server JAR (ex: `HytaleServer.jar`).
3. Copiez le fichier dans `libs/HytaleServer.jar` à la racine du projet.

## Dépendance Maven (obligatoire)

### A) Méthode recommandée : installation dans le repo local
```bash
mvn install:install-file \
  -Dfile=libs/HytaleServer.jar \
  -DgroupId=com.hytale \
  -DartifactId=hytale-server \
  -Dversion=1.0 \
  -Dpackaging=jar
```
Puis utilisez la dépendance `com.hytale:hytale-server:1.0` (déjà déclarée dans le `pom.xml`).

### B) Méthode alternative : `systemPath` (moins recommandé)
Dans le `pom.xml`, remplacez la dépendance par :
```xml
<dependency>
  <groupId>com.hytale</groupId>
  <artifactId>hytale-server</artifactId>
  <version>1.0</version>
  <scope>system</scope>
  <systemPath>${project.basedir}/libs/HytaleServer.jar</systemPath>
</dependency>
```

## Compiler
```bash
mvn clean package
```

## Installer sur le serveur
1. Récupérez le JAR généré dans `target/`.
2. Copiez-le dans le dossier des plugins du serveur Hytale.
3. Assurez-vous que le `plugin descriptor` (voir `src/main/resources/plugin.json`) est reconnu par votre version du serveur.

## Configuration
Le fichier `config.properties` est copié automatiquement dans `plugins/HytaleScoreboardTest/` au premier lancement.

```properties
scoreboard.title=MonServeur | TEST
scoreboard.updateIntervalMs=1000
scoreboard.defaultEnabled=true
scoreboard.lines=Joueur: {playerName}|Rang: {rank}|Pièces: {coins}|Niveau: {level}|Ping: {ping} ms|FPS: {fps}|Monde: {worldName}|Mini-jeu: {gameName}|En ligne: {online}/{max}|Temps: {timeFormatted}
```

- **Titre** : `scoreboard.title`
- **Lignes** : `scoreboard.lines` (séparateur `|`)
- **Fréquence** : `scoreboard.updateIntervalMs`
- **Activation par défaut** : `scoreboard.defaultEnabled`

## Commandes
- `/scoreboard on` : active le scoreboard pour le joueur
- `/scoreboard off` : désactive le scoreboard pour le joueur
- `/scoreboard test` : active + force valeurs fictives
- `/scoreboard reload` : recharge la configuration

## Exemple en jeu
Une fois en jeu, le scoreboard sidebar doit afficher :
- **Titre** : `MonServeur | TEST`
- **Lignes dynamiques** : joueur, rang, pièces, niveau, ping, FPS, monde, mini-jeu, en ligne, heure.

## Remplacement de l’API Hytale
La classe `HytaleAdapterImpl` contient les TODO et un fallback no-op. Remplacez-la par une implémentation utilisant le vrai SDK Hytale (imports isolés dans `adapter/impl`).
