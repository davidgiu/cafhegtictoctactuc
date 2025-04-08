# Rapport
# CAFHEG Project

Projet développé dans le cadre du cours sur les tests unitaires, d'intégration et le développement orienté service.

## Exercice 1 – Analyse & refactoring

- Analyse de l’architecture et du fonctionnement du projet
- Test du endpoint `/droits/quel-parent` via Swagger ou Postman
- Couverture à 100% de `AllocationService#getParentDroitAllocation`
- Refactoring : remplacement de Map<String, Object> par une classe dédiée
- Implémentation de la logique officielle suisse des allocations (site eak.admin.ch)

## Exercice 2 – Suppression & modification d’un allocataire

-  Suppression autorisée uniquement sans versements
-  Modification autorisée uniquement si nom ou prénom change (NoAVS non modifiable)
-  Exposition via API REST (Spring Boot)

##  Exercice 4 – Gestion des logs

-  Remplacement de tous les `System.out.println` et `printStackTrace` par des `Logger`
-  Configuration avancée de Logback :
    - `err.log` pour les erreurs
    - `cafheg_{date}.log` pour les accès info des services
    - Console pour les logs debug

##  Exercice 5 – Tests d’intégration

-  Réorganisation du projet avec `integration-test/java` & `resources`
-  Création d’un test `MyTestsIT` toujours passant
-  Fichier XML `allocataire_dataset.xml` pour préparer les données
-  Tests d’intégration réalisés pour :
    - Suppression d’un allocataire
    - Modification d’un allocataire

---

## 🛠️ Technologies utilisées

- Java 11
- Spring Boot
- JUnit 5
- DBUnit
- AssertJ
- Logback
- Swagger

## 👩‍💻 Démarrage

1. Importer le projet dans IntelliJ
2. Lancer la classe `Application.java`
3. Utiliser Postman ou Swagger pour tester les endpoints REST

