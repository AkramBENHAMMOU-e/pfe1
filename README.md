# Plateforme de Génération d'Emplois du Temps par Algorithme Génétique

Ce projet, réalisé dans le cadre d'une Licence en Sciences Mathématiques et Informatique à l'Université Chouaib Doukkali, est une plateforme web pour la génération automatique et optimisée des emplois du temps académiques. La solution s'appuie sur un **algorithme génétique** pour résoudre le problème complexe de la planification tout en respectant un ensemble de contraintes prédéfinies.

## 📜 Table des matières

- [Le Problème](#-le-problème)
- [La Solution](#-la-solution)
- [✨ Fonctionnalités Principales](#-fonctionnalités-principales)
- [📸 Aperçus de l'application](#-aperçus-de-lapplication)
- [🛠️ Technologies et Outils](#️-technologies-et-outils)
- [🧬 Comment fonctionne l'Algorithme Génétique ?](#-comment-fonctionne-lalgorithme-génétique-)
- [🚀 Installation et Lancement](#-installation-et-lancement)


## 🎯 Le Problème

La planification manuelle des emplois du temps au sein d'un établissement universitaire comme la Faculté des Sciences d'El Jadida est un défi majeur. Ce processus est :
- **Chronophage :** Il demande des heures, voire des jours, de travail minutieux.
- **Sujet aux erreurs :** Le risque de conflits (salles, professeurs, groupes) est très élevé.
- **Rigide :** Une fois créé, il est difficile de le modifier sans introduire de nouvelles erreurs.
- **Non optimisé :** Il ne garantit pas une utilisation optimale des ressources (salles, créneaux horaires) ni le confort des étudiants et des enseignants.

## 💡 La Solution

Ce projet propose une plateforme web qui automatise entièrement ce processus. Au cœur du système se trouve un **algorithme génétique**, une méthode d'optimisation inspirée de la sélection naturelle de Darwin.

L'algorithme génère une population de solutions (des emplois du temps potentiels) et les fait "évoluer" sur plusieurs générations. À chaque génération, les meilleures solutions sont sélectionnées, combinées (croisement) et légèrement modifiées (mutation) pour produire une nouvelle population, encore meilleure. Ce processus itératif permet de converger vers une solution optimale ou quasi-optimale qui minimise les conflits et respecte toutes les contraintes.

## ✨ Fonctionnalités Principales

### Espace Administrateur
- **Authentification sécurisée.**
- **Gestion des Entités (CRUD) :**
  - Gestion des filières (SMI, SMPC, etc.).
  - Gestion des salles (Amphis, salles de TP) avec leurs capacités.
  - Gestion des séances (cours, TD, TP) avec le nombre d'étudiants.
- **Génération d'Emplois du Temps :**
  - Lancement du processus de génération via un simple bouton.
  - Visualisation de l'état d'avancement.
- **Visualisation et Export :**
  - Affichage clair et intuitif de l'emploi du temps généré par filière.
  - Téléchargement de l'emploi du temps au format **PDF** pour une distribution facile.

### Espace Étudiant
- **Consultation simplifiée :**
  - Sélection de la filière pour afficher l'emploi du temps correspondant.
- **Téléchargement :**
  - Possibilité pour les étudiants de télécharger leur emploi du temps.

## 📸 Aperçus de l'application
[https://github.com/AkramBENHAMMOU-e/pfe1/issues/1](https://github.com/user-attachments/assets/51b30ac6-9af7-46e0-aba8-a4b3e1667ed5)

## 🛠️ Technologies et Outils

### Backend
- **Langage :** Java 17
- **Framework :** Spring Boot
- **Accès aux données :** Spring Data JPA / Hibernate
- **Base de données :** MySQL
- **API :** RESTful API
- **Bibliothèque PDF :** OpenPDF

### Frontend
- **Framework :** Angular
- **Langage :** TypeScript
- **Styling :** CSS / Bootstrap

### Outils de développement
- **IDE :** IntelliJ IDEA
- **Test d'API :** Postman
- **Gestion de projet :** Méthodologie Agile (Scrum)
- **Modélisation UML :** StarUML

## 🧬 Comment fonctionne l'Algorithme Génétique ?

L'algorithme implémenté suit les étapes classiques de l'évolution artificielle pour trouver le meilleur emploi du temps :

1.  **Population Initiale :** Création d'un ensemble d'emplois du temps complètement aléatoires. Ces solutions initiales ne respectent probablement aucune contrainte.
2.  **Évaluation (Fitness) :** Chaque emploi du temps (individu) reçoit un score basé sur le nombre de contraintes violées (conflits). Un score plus bas signifie un meilleur individu.
3.  **Sélection :** Les individus avec les meilleurs scores (le moins de conflits) sont sélectionnés pour se "reproduire".
4.  **Croisement (Crossover) :** Les emplois du temps parents échangent des parties de leurs plannings pour créer de nouveaux emplois du temps "enfants", en espérant combiner les bonnes caractéristiques de chacun.
5.  **Mutation :** Une petite modification aléatoire est introduite dans certains des nouveaux emplois du temps pour maintenir la diversité génétique et éviter de rester bloqué dans une solution sous-optimale.
6.  **Répétition :** Le processus est répété sur de nombreuses générations, améliorant la qualité de la population jusqu'à ce qu'un emploi du temps satisfaisant (avec 0 ou un minimum de conflits) soit trouvé.

## 🚀 Installation et Lancement

Suivez ces étapes pour lancer le projet sur votre machine locale.

### Prérequis
- Java JDK 17 ou supérieur
- Maven
- Node.js et npm
- MySQL Server

### 1. Backend (Serveur Spring Boot)

```bash
# 1. Clonez le dépôt
git clone https://github.com/AkramBENHAMMOU-e/pfe1.git
cd votre-repo/pfe-backend # Naviguez vers le dossier backend

# 2. Configurez la base de données
# - Créez une base de données MySQL (ex: 'timetable_db').
# - Mettez à jour les informations de connexion dans le fichier `src/main/resources/application.properties` :
#   spring.datasource.url=jdbc:mysql://localhost:3306/timetable_db
#   spring.datasource.username=votre_user
#   spring.datasource.password=votre_mot_de_passe

# 3. Lancez le serveur
mvn spring-boot:run

# Le backend sera accessible sur http://localhost:8080
```

### 2. Frontend (Application Angular)

```bash
# 1. Naviguez vers le dossier frontend (dans un autre terminal)
cd ./frontend

# 2. Installez les dépendances
npm install

# 3. Lancez le serveur de développement
ng serve

# L'application sera accessible sur http://localhost:4200
```
