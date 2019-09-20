# Nexiomagasin

Nexiomagasin est una solution à un exercism avec le ènoncè:

Produisez un api REST qui permet les scénarios suivants: 
* afficher un catalogue de produits
* Afficher le détail d'un produit
* Ajouter un produit au panier
* Enlever un produit du panier
* Afficher le contenu du panier
* (Bonus) Connexxion et d'econnexion à un compte utilisateur

## Requeriment

* Java version 8
* Apache Maven version 3.6.2 ou ultérieure

## Installation
* Clon this repository
* Go to the direcotory created and execute: `mvn clean install package`
  This command will download dependencies, run the tests and compile the application.

# Run the test
* Execute: `mvn test`

## Run the application
* Execute: `java -jar target/magasin-0.0.1-SNAPSHOT.jar`

  On `http://localhost:8080` you will have acces to the different enpoints:
  
  | HTTP METHOD | URL                       | Description                             |
  | :---------- | :-----------------------: | --------------------------------------: |
  |  `GET`      | `/api/v1/products`        | Affiche un catalogue de produits        |
  |  `GET`      | `/api/v1/products/{id}`   | Afficher le détail d’un produit avec    |
  |  `POST`     | `/api/v1/product_items`   | Ajouter un produit au panier            |
  |  `DELETE`   | `/api/v1/product_items`   | Enleve un produit du panier             |
  |  `GET`      | `/api/v1/carts/{id}`      | Affiche le contenu du panier            |
  |  `POST`     | `/api/v1/login`           | Connexion à un compte utilisateur       |
  |  `DELETE`   | `/api/v1/logout`          | Déconnexion à un compte utilisateur     |
  
A `/h2-console` vous avez accès à la base de données SQL in memory. 
Le credential de conection sont: utilisateur `admin` e mot de passe `motdepasse`.