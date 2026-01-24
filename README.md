# Heneria – Base système Hytale

Ce dépôt fournit une base technique pour le serveur Heneria sur Hytale. L’objectif est de proposer une structure propre, modulaire et évolutive pour les futurs développements.

## Contenu
- Système de connexion joueur avec message de bienvenue.
- Gestion des permissions/rôles.
- Architecture modulaire (modules activables/désactivables).
- Configuration claire via un fichier `config/server.properties`.
- Documentation de base dans `docs/`.

## Démarrage rapide
1. Ajuster la configuration dans `config/server.properties`.
2. Initialiser l’application en chargeant `HeneriaServer`.
3. Enregistrer vos modules supplémentaires via `ModuleManager`.

## Compilation avec Maven
```bash
mvn clean package
```

## Documentation
- Architecture & modules : `docs/ARCHITECTURE.md`
- Configuration : `docs/CONFIGURATION.md`

## Notes
Cette base est volontairement simple et découplée afin de faciliter les évolutions et l’intégration avec l’API officielle Hytale.
