#!/bin/bash

echo "🔥 Nettoyage du projet..."
mvn clean

echo "📦 Compilation + génération WAR..."
mvn package

echo "🚀 Déploiement sur Tomcat..."

# Chemin à adapter selon ton PC
TOMCAT_HOME="/home/miora/Applications/tomcat"

cp target/*.war $TOMCAT_HOME/webapps/

echo "▶ Démarrage Tomcat..."
$TOMCAT_HOME/bin/startup.sh

echo "Projet lancé : http://localhost:8080/"