#!/bin/bash

set -euo pipefail

trap 'echo "✗ Erreur lors de la suppression des données" >&2' ERR

# Script pour supprimer les données des tables de base de données

# Configuration de la base de données (surchargeable via variables d'environnement)
# IMPORTANT: "localhost" force la connexion via socket (souvent absent). Par défaut on force TCP.
DB_HOST="${DB_HOST:-127.0.0.1}"
DB_USER="${DB_USER:-root}"
DB_PASSWORD="${DB_PASSWORD:-}"
DB_NAME="${DB_NAME:-forage}"

# Exécution des commandes DELETE
MYSQL_ARGS=(--protocol=TCP -h "$DB_HOST" -u "$DB_USER" "$DB_NAME")
if [[ -n "$DB_PASSWORD" ]]; then
    MYSQL_ARGS+=("-p$DB_PASSWORD")
fi

/opt/lampp/bin/mysql "${MYSQL_ARGS[@]}" << EOF
DELETE FROM demande;
DELETE FROM statut;
DELETE FROM statutDemande;
EOF

echo "✓ Données supprimées avec succès"

