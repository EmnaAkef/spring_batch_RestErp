# REST ERP BI - ETL & Data Warehouse

## Description

Ce dépôt contient la partie ETL du module Business Intelligence intégré à REST ERP.

L’objectif principal de ce projet est d’extraire les données depuis la base opérationnelle REST ERP, de les transformer, puis de les charger dans un Data Warehouse dédié à l’analyse décisionnelle.

Cette partie permet d’alimenter les tables de dimensions et les tables de faits utilisées par les dashboards BI.

## Objectifs du module ETL

* Extraire les données opérationnelles depuis les schémas des entreprises.
* Transformer les données pour les adapter au modèle décisionnel.
* Charger les données dans le Data Warehouse.
* Gérer l’historisation des dimensions avec le principe SCD.
* Alimenter les tables de faits utilisées pour les KPIs.
* Automatiser le chargement grâce à un scheduler.

## Architecture générale

Le flux de données suit le parcours suivant :

Base opérationnelle REST ERP
→ Processus ETL Spring Batch
→ Data Warehouse PostgreSQL
→ Backend BI
→ Dashboards Angular

## Technologies utilisées

* Java
* Spring Boot
* Spring Batch
* PostgreSQL
* Spring Data JPA
* Scheduler
* SQL
* Maven

## Structure du projet

```text
src/
 └── main/
     ├── java/
     │   └── ...
     │       ├── config/
     │       ├── job/
     │       ├── reader/
     │       ├── processor/
     │       ├── writer/
     │       ├── entity/
     │       ├── repository/
     │       └── service/
     └── resources/
         ├── application.properties
         └── schema.sql
```

## Principe de fonctionnement

Le processus ETL est basé sur trois étapes principales :

### 1. Extraction

Les données sont lues depuis la base opérationnelle REST ERP.

Exemples de données extraites :

* Employés
* Départements
* Présences
* Clients
* Factures
* Commandes
* Deals commerciaux

### 2. Transformation

Les données extraites sont nettoyées et adaptées au modèle décisionnel.

Exemples de transformations :

* Association des données avec `company_key`
* Conversion des dates vers `date_key`
* Calcul de certaines valeurs intermédiaires
* Préparation des données pour les tables de faits
* Gestion des valeurs nulles
* Application du principe SCD pour certaines dimensions

### 3. Chargement

Les données transformées sont insérées ou mises à jour dans les tables du Data Warehouse.

Exemples de tables alimentées :

* `dim_company`
* `dim_user`
* `dim_department`
* `dim_customer`
* `dim_date`
* `fact_employee_hr`
* `fact_attendance_shift`
* `fact_invoice`
* `fact_bill`
* `fact_deal`
* `fact_sales_order`

## Data Warehouse

Le Data Warehouse est basé sur un modèle dimensionnel.

Il contient :

* Des tables de dimensions pour décrire les axes d’analyse.
* Des tables de faits pour stocker les mesures métier.

Ce modèle facilite le calcul des KPIs et améliore les performances des requêtes analytiques.

## Gestion multi-tenant

REST ERP est une solution multi-tenant.

Chaque entreprise possède ses propres données opérationnelles.
Lors du chargement, les données sont associées à une entreprise à travers la clé `company_key`.

Cela permet de séparer les analyses par entreprise et de garantir que chaque utilisateur visualise uniquement les données de sa société.

## Scheduler

Un scheduler permet de lancer automatiquement les jobs ETL.

Exemples de fréquence possible :

* Chargement quotidien
* Chargement hebdomadaire
* Chargement manuel pendant les tests

## Configuration

La configuration principale se trouve dans le fichier :

```text
src/main/resources/application.properties
```

Exemple de configuration :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/rest_erp_bi
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
```

## Lancement du projet

### 1. Cloner le projet

```bash
git clone <url-du-repo-etl>
cd <nom-du-repo-etl>
```

### 2. Configurer la base de données

Modifier les informations de connexion dans :

```text
application.properties
```

### 3. Lancer l’application

```bash
mvn spring-boot:run
```

## Exemple de job ETL

Un job ETL peut être lancé pour charger les données d’une entreprise donnée.

Exemple logique :

```text
Job RH
→ Lire les employés
→ Transformer les données RH
→ Charger dim_user et fact_employee_hr
```

```text
Job Finance
→ Lire les factures et les dépenses
→ Transformer les montants
→ Charger fact_invoice et fact_bill
```

```text
Job Sales
→ Lire les deals et commandes
→ Transformer les statuts
→ Charger fact_deal et fact_sales_order
```

## Résultat attendu

Après l’exécution des jobs ETL, le Data Warehouse contient des données prêtes à être utilisées par le backend BI pour calculer les KPIs et alimenter les dashboards.

## Auteur

Projet réalisé dans le cadre du Projet de Fin d’Études.

Module : Business Intelligence intégré à REST ERP.
