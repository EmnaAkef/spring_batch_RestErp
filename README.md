# REST ERP BI - ETL & Data Warehouse

> Module ETL développé pour synchroniser les données opérationnelles de REST ERP vers un Data Warehouse décisionnel, afin de préparer les données nécessaires aux analyses BI, aux KPIs et aux dashboards.

![Statut](https://img.shields.io/badge/statut-termin%C3%A9-brightgreen)
![Java](https://img.shields.io/badge/Java-17-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green)
![Spring Batch](https://img.shields.io/badge/Spring%20Batch-ETL-brightgreen)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Data%20Warehouse-blue)

---

## 🎯 Contexte

* **Type de projet** : Académique - Projet de Fin d’Études
* **Durée** : 14 semaines
* **Projet global** : Intégration d’une couche Business Intelligence dans REST ERP
* **Problématique** : Les données opérationnelles de REST ERP sont stockées dans la base de données, mais elles ne sont pas suffisamment exploitées pour l’analyse décisionnelle.

Ce dépôt représente la partie ETL du projet.
Il permet d’extraire les données depuis la base opérationnelle REST ERP, de les transformer, puis de les charger dans un Data Warehouse optimisé pour l’analyse.

---

## ✨ Fonctionnalités principales

* ✅ Extraction des données depuis la base opérationnelle REST ERP
* ✅ Transformation des données selon le modèle décisionnel
* ✅ Chargement des données dans le Data Warehouse PostgreSQL
* ✅ Alimentation des tables de dimensions et des tables de faits
* ✅ Gestion de l’historisation des dimensions avec le principe SCD
* ✅ Synchronisation automatique des données à travers un scheduler
* ✅ Support du principe multi-tenant avec séparation des données par entreprise

---

## 🛠️ Stack technique

| Catégorie               | Technologies utilisées                     |
| ----------------------- | ------------------------------------------ |
| Langages                | Java, SQL                                  |
| Frameworks / Librairies | Spring Boot, Spring Batch, Spring Data JPA |
| Base de données         | PostgreSQL                                 |
| Outils / Environnement  | Maven, Git, Scheduler, IntelliJ IDEA       |

---

## ⚙️ Installation

```bash
# Cloner le repo
git clone https://github.com/tonpseudo/rest-erp-bi-etl.git
cd rest-erp-bi-etl

# Installer les dépendances
mvn clean install

# Lancer le projet
mvn spring-boot:run
```

Avant le lancement, il faut configurer la connexion à la base de données dans le fichier :

```text
src/main/resources/application.properties
```

Exemple :

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/rest_erp_bi
spring.datasource.username=postgres
spring.datasource.password=your_password

spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
```

---

## 🚀 Utilisation

Une fois le projet lancé, les jobs ETL permettent de charger les données dans le Data Warehouse.

Le processus suit trois étapes principales :

```text
Base opérationnelle REST ERP
        ↓
Extraction des données
        ↓
Transformation et nettoyage
        ↓
Chargement dans le Data Warehouse
```

Exemples de traitements réalisés :

* Chargement des entreprises dans `dim_company`
* Chargement des employés dans `dim_user`
* Chargement des départements dans `dim_department`
* Chargement des clients dans `dim_customer`
* Chargement des dates dans `dim_date`
* Chargement des présences dans `fact_attendance_shift`
* Chargement des factures dans `fact_invoice`
* Chargement des deals commerciaux dans `fact_deal`

---

## 📊 Résultats / Insights

* 🔑 **Insight clé 1** : Les données opérationnelles sont centralisées dans un Data Warehouse structuré pour l’analyse décisionnelle.
* 📈 **Indicateur clé** : Les tables de faits permettent de calculer les KPIs RH, Finance et Sales.
* 📋 **Dashboard interactif** : Les données alimentées par l’ETL sont ensuite consommées par le backend BI et affichées dans le frontend Angular.

---

## 🗂️ Architecture

```text
+-----------------------------+
| Base opérationnelle REST ERP |
+--------------+--------------+
               |
               v
+-----------------------------+
| Module ETL Spring Batch      |
| - Reader                    |
| - Processor                 |
| - Writer                    |
+--------------+--------------+
               |
               v
+-----------------------------+
| Data Warehouse PostgreSQL    |
| - Tables de dimensions       |
| - Tables de faits            |
+--------------+--------------+
               |
               v
+-----------------------------+
| Backend BI APIs              |
+--------------+--------------+
               |
               v
+-----------------------------+
| Dashboards Angular           |
+-----------------------------+
```

---

## 🔮 Améliorations futures

* [ ] Ajouter un suivi détaillé des logs ETL dans une interface d’administration
* [ ] Ajouter une relance automatique en cas d’échec d’un job
* [ ] Optimiser davantage les performances de chargement
* [ ] Ajouter des tests automatisés pour chaque job ETL
* [ ] Mettre en place un monitoring complet des traitements

---

## 👥 Auteurs

Projet réalisé par :

Emna Akef

🔗 LinkedIn : www.linkedin.com/in/emna-akef
💻 GitHub : https://github.com/EmnaAkef
📧 Email : akef.emna@gmail.com

Ghofrane Messaoud

🔗 LinkedIn : www.linkedin.com/in/ghofrane-messaoud
💻 GitHub : https://github.com/ghoffraanee
📧 Email : ghofranemessaoud05@gmail.com

