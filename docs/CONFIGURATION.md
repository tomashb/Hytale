# Configuration

Le serveur utilise un fichier `config/server.properties` pour rester simple et facilement éditable.

## Exemple
```properties
server.name=Heneria
server.welcome=Bienvenue {player} sur Heneria !
roles.default=player
roles.admin=admin
permissions.player=chat.use,spawn.use
permissions.admin=chat.use,spawn.use,server.manage
```

## Clés supportées
- `server.name` : nom du serveur.
- `server.welcome` : message de bienvenue (placeholder `{player}`).
- `roles.default` : rôle attribué par défaut lors de la connexion.
- `roles.*` : rôles disponibles.
- `permissions.<role>` : permissions séparées par des virgules.

## Extension
Ajoutez vos propres clés pour les nouveaux modules. Chaque module doit documenter ses clés ici ou dans sa documentation dédiée.
