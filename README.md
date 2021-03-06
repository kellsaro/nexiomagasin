# Nexiomagasin

Nexiomagasin est une solution au problème suivant:

Produisez un api REST qui permet les scénarios suivants: 
* Afficher un catalogue de produits
* Afficher le détail d'un produit
* Ajouter un produit au panier
* Enlever un produit du panier
* Afficher le contenu du panier
* (Bonus) Connexion et déconnexion à un compte utilisateur

## Requeriment

* Java version 8
* Apache Maven version 3.6.2 ou ultérieure

## Installation
* Cloner ce dépôt
* Allez dans le répertoire créez et exécutez le commande: **`mvn clean install package`**
* Cette commande va:
    - télécharger les dépendances 
    - exécuter les tests 
    - compilez l'application.

## Lancer le test
* Exécuter: **`mvn test`**

## Lancer le logiciel
* Exécuter: **`java -jar target/magasin-0.0.1-SNAPSHOT.jar`**

  Sur **`http://localhost:8080`** vous aurez accès aux différents enpoints:
  
  | HTTP METHOD | URL                       | Description                            |
  | :---------- | :------------------------ | :------------------------------------- |
  |  `GET`      | `/api/v1/products`        | Afficher un catalogue de produits      |
  |  `GET`      | `/api/v1/products/{id}`   | Afficher le détail d’un produit        |
  |  `POST`     | `/api/v1/product_items`   | Ajouter un produit au panier           |
  |  `DELETE`   | `/api/v1/product_items`   | Enleve un produit du panier            |
  |  `GET`      | `/api/v1/carts/{id}`      | Affiche le contenu du panier           |
  |  `POST`     | `/api/v1/login`           | Connexion à un compte utilisateur      |
  |  `DELETE`   | `/api/v1/logout`          | Déconnexion à un compte utilisateur    |
  
À **`/h2-console`** vous avez accès à la base de données SQL in memory. 
Le credential de conection sont: utilisateur **`admin`** e mot de passe **`motdepasse`**.

Le base de donnés est initializé avec donnés du 13 produits et un utilizateur. 
Ces données sont utilisées à titre d'exemple. Vous pouvez supprimer les données du fichier **`src/main/resources/data.sql`**.

À **`http://localhost:8080/swagger-ui.html#/`** vous pouvez consulter la documentation de l'API.
