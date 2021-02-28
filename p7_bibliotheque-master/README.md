# Étape de déploiement du projet de bibliothèque municipale

## Prémière étape:
* Tout d'abord, il vous faut un Jre installer sur votre machine que vous pouvez télécharger ici :[Installez un JRE](https://www.java.com/fr/download/)


## Deuxième étape:
* Dans cette étape, nous allons d'abord récupérer le jar du serveur sur mon  github:[Telecharger le jar](https://github.com/soro1987/projet-p7/releases/download/v1.0.0/p7_server_1.0.0.jar)
 <br>
* Puis on exécuter le serveur sur le port 8080 en tapant cette commande dans le cmd:
<pre>
    <code>
         $ java -jar p7_server_1.0.0.jar 
    </code>
</pre>



## Troisième étape:
* Même étape, cette fois-ci pour le jar du client ici:[Telecharger le jar](https://github.com/soro1987/batch/releases/download/1.0.0/batch.jar).
* Puis on exécuter le client sur le port 8081 en tapant cette commande dans le cmd:
<pre>
    <code>
         $ java -jar batch.jar
    </code>
</pre>
hello

## Quatrième étape:
* Et enfin, le jar du batch ici:[Telecharger le jar](https://github.com/soro1987/Client-p7/releases/download/1.0.0/biblio-client.jar).
* Puis on exécuter le client sur le port 8083 en tapant cette commande dans le cmd:
<pre>
    <code>
         $ java -jar biblio-client.jar
    </code>
</pre>

## Derniere étape:
* Allez sur le navigateur puis pointer sur les URL suivant.


-http://localhost:8080/ pour le serveur

-http://localhost:8081/accueil pour le client

-http://localhost:8083/ pour le batch


## Les technologies utilisées:
* Java  1.8
* Spring boot 2.3.6
* Hibernate
* MYSQL 5
* Thymeleaf
* Bootstrap 4
* Eclipse IDE



