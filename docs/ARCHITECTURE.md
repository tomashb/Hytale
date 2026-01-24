# Architecture Heneria

## Objectifs
- Modularité : chaque fonctionnalité est isolée dans un module.
- Évolutivité : ajout de modules sans modifier le cœur.
- Lisibilité : classes courtes, responsabilités claires.

## Composition
- **core/** : noyau du serveur (event bus, modules, contexte).
- **modules/** : fonctionnalités métier (auth, permissions, welcome).
- **config/** : gestion de la configuration.

## Flux général
1. `HeneriaServer` charge la configuration.
2. `ModuleManager` enregistre et active les modules.
3. Les modules s’abonnent aux événements via `EventBus`.
4. Les actions (connexion, authentification, permissions) se déclenchent via les événements.

## Ajout d’un module
1. Créer une classe qui implémente `Module`.
2. S’abonner aux événements nécessaires via `EventBus`.
3. Enregistrer le module dans `ModuleManager`.

## Bonnes pratiques
- Garder des modules petits et spécialisés.
- Centraliser la configuration dans `ServerConfig`.
- Éviter les dépendances circulaires entre modules.
