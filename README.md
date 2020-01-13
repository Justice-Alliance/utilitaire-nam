Développement applicatif : Utilitaire NAM
=========================================

*   [Description du projet](#UtilitaireNAM-Descriptionduprojet)
*   [Intégration et déploiement en continue](#UtilitaireNAM-Intégrationetdéploiementencontinue)
*   [Utilisation de l'outil](#UtilitaireNAM-Utilisationdel'outil)
    *   [Service Web](#UtilitaireNAM-ServiceWeb)
        *   [Valider un numéro d'assurance maladie.](#UtilitaireNAM-Validerunnumérod'assurancemaladie.)
        *   [Générer un numéro d'assurance maladie](#UtilitaireNAM-Générerunnumérod'assurancemaladie)
        *   [Obtenir les informations sur un NAM](#UtilitaireNAM-ObtenirlesinformationssurunNAM)
    *   [API Java](#UtilitaireNAM-APIJava)
    *   [Interface utilisateur Web](#UtilitaireNAM-InterfaceutilisateurWeb)
        *   [Valider un NAM](#UtilitaireNAM-ValiderunNAM)
        *   [Générer un NAM](#UtilitaireNAM-GénérerunNAM)
        *   [Information sur un NAM](#UtilitaireNAM-InformationsurunNAM)
*   [Codes de sexes](#UtilitaireNAM-Codesdesexes)
*   [Codes des provinces](#UtilitaireNAM-Codesdesprovinces)
*   [Développement](#UtilitaireNAM-Développement)

Description du projet
=====================

Utilitaire NAM est, comme le nom l'indique, un outil permettant de manipuler les numéros d'assurance maladie.

Il a été conçu pour être utilisé par les applications en offrant un API REST ayant les fonctionnalités suivantes:

1.  Valider un numéro d'assurance maladie pour une des province Canadienne.
2.  Générer un numéro d'assurance maladie fictif pour une des provinces Canadienne.
3.  Donner les informations à propos d'un numéro d'assurance maladie pour une des province Canadienne.

L'outil inclus également un interface utilisateur Web.

Le code source de l'application est disponible sur la Forge Gouvernementale dans le dépôt: [https://gitlab.forge.gouv.qc.ca/inspq/utilitaire-nam.git](https://gitlab.forge.gouv.qc.ca/inspq/utilitaire-nam.git)

L'URL de base d'utilitaire NAM en production est [https://unam.santepublique.rtss.qc.ca.](https://unam.santepublique.rtss.qc.ca)

L'application a été déployé pour assurer un haute disponibilité du service. Les services web peuvent donc être utilisé par les applications ayant une cote de 4 en disponibilité.

Pour la confidentialité, le protocole TLS est utilisé pour chiffrer les communications avec le service et aucune donnée n'est stockée par utilitaire NAM.

Intégration et déploiement en continue
======================================

Utilitaire NAM est géré en mode dev/ops. Ce projet est utilisé comme cobail pour introduire les meilleurs pratiques dans la gestion d'un produit en DEV/OPS.

La gestion du cycle de vie d'utilitaire NAM se fait par des pipelines dans Jenkins. Les trois principaux pipelines sont les suivant:

## Pipeline utilitaire-nam-livraison

Ce Pipeline s'exécuter lorsqu'un commit est fait dans le projet.

Il fait la construction, l'exécution des tests unitaires, l'exécution des tests du domaine (Cucumber) et le déploiement en DEV3

[https://jenkins.dev.inspq.qc.ca/job/utilitaire-nam/job/utilitaire-nam-livraison/](https://jenkins.dev.inspq.qc.ca/job/utilitaire-nam/job/utilitaire-nam-livraison/)

## Pipeline utilitaire-nam-livraison-nuit

Ce pipeline s'exécute à tous les soirs.

En plus des opérations du pipeline de livraison précédent, il fais les tests de sécurités de l'image, l'analyse statique de code, l'analyse SonarQube, le déploiement dans les environnements DEV3, DEV2, les tests d'intégrations et offre la possibilité de faire un étiquetage (tag) de la version.

Si une nouvel étiquette est créé, le pipeline demande au pilote s'il doit déployer dans l'environnement DEV. Si oui l'application est déployé en DEV et les tests d'intégration sont lancés.

[https://jenkins.dev.inspq.qc.ca/job/utilitaire-nam/job/utilitaire-nam-livraison-nuit/](https://jenkins.dev.inspq.qc.ca/job/utilitaire-nam/job/utilitaire-nam-livraison-nuit/)

## Pipeline utilitaire-nam-livraison-prod

Ce pipeline s'exécute lorsqu'un nouvel étiquetage (tag) de l'application est fait.

Il déploie l'application dans les environnements PP et PROD et exécute les tests d'intégration. L'application n'est déployé, dans chacun des environnements, que si le pilote confirme à partir de l'interface utilisateur de Jenkins.

[https://jenkins.inspq.qc.ca/job/utilitaire-nam/job/utilitaire-nam-livraison-prod/](https://jenkins.inspq.qc.ca/job/utilitaire-nam/job/utilitaire-nam-livraison-prod/)

Utilisation de l'outil
======================

Utilitaire NAM peut-être utilisé de 3 manières:

*   service Web REST,
*   API JAVA en artefact Maven,
*   interface utilisateur Web.

L'utilisation du service Web est privilégié car elle minimise les dépendances entre l'application développée et le cycle d'évolution d'utilitaire NAM.

Service Web
-----------

Voici les trois cas d'utilisation de l'application:

### Valider un numéro d'assurance maladie.

Pour valider un numéro d'assurance Maladie, le service doit être utilisé avec le verbe GET, l'URI /nam/valider et les paramètres suivants sous forme de paramètre de requête (query params).

Paramètre	Description
nam			Numéro d'assurance maladie à valider
province	Province qui a émis le numéro d'assurance maladie.

La réponse retourné par le service est:

| Réponse | Description                                                 |
|---------|-------------------------------------------------------------|
| true	  | Le NAM est valide                                           |
| false	  | Le NAM n'est pas valide                                     |
| code 400|	Message d'erreur lorsque les paramètres ne sont pas valides.|

Pour valider un NAM, utiliser une requête du verbe GET ayant le format suivant:

	https://unam.santepublique.rtss.qc.ca/nam/valider?nam=NumeroAValider&province=CodeDeProvince

Voici un exemple d'appel avec l'outil curl:

	curl -X GET "https://unam.santepublique.rtss.qc.ca/nam/valider?nam=ASDA20011313&province=QC"
	true

### Générer un numéro d'assurance maladie

Pour générer un numéro d'assurance Maladie, le service doit être utilisé avec le verbe GET, l'URI /nam/generer et les paramètres suivants sous forme de paramètre de requête (query params).

| Paramètre     | Description                                             |
|---------------|---------------------------------------------------------|
| nom           | Nom de famille de l'usager pour lequel générer le NAM   |
| prenom        | Prénom de l'usager                                      |
| sexe          | Code de sexe de l'usager                                |
| datenaissance | Date de naissance de l'usager en format AAAA-MM-JJ      |
| province      | Code de province du NAM à générer                       |

Le service retourne un liste de NAM valide pour les paramètres fournis en format JSON.

Pour générer un NAM, utiliser une requête du verbe GET ayant le format suivant:

	https://unam.santepublique.rtss.qc.ca/nam/generer?nom=NomDeFamilleDeLUsager&prenom=PrenomDeLUsager&sexe=CodeDeSexe&datenaissance=DateDeNaissanceDeLUsager&province=CodeDeProvince

Voici un exemple d'appel avec l'outil curl:

	curl -X GET "https://unam.santepublique.rtss.qc.ca/nam/generer?nom=qwe&prenom=Aasd&sexe=M&datenaissance=2010-01-01&province=QC"

	["QWEA10010110","QWEA10010121","QWEA10010132","QWEA10010143","QWEA10010154","QWEA10010165","QWEA10010176","QWEA10010187","QWEA10010198"]

### Obtenir les informations sur un NAM

Pour obtenir les informations sur un numéro d'assurance Maladie, le service doit être utilisé avec le verbe GET, l'URI /nam/info et les paramètres suivants sous forme de paramètre de requête (query params).

| Paramètre | Description                                                    |
|-----------|----------------------------------------------------------------|
| nam       | Numéro d'assurance maladie pour lequel obtenir les infromations|

Le service retourne les informations suivante à propos du NAM en format JSON:

| Champ         | Description                                                      |
|---------------|------------------------------------------------------------------|
| dateNaissance | Date de naissance de l'usager                                    |
| sexe          | Sexe de l'usager. Les valeurs possibles sont MASCULIN ou FEMININ |

Pour obtenir les informations sur un NAM, utiliser une requête du verbe GET ayant le format suivant:

	https://unam.santepublique.rtss.qc.ca/nam/info?nam=NumeroAssuranceMaladiePourLequelOnVeutDesInformations

Voici un exemple de requête avec l'outil curl:

	curl -X GET "https://unam.santepublique.rtss.qc.ca/nam/info?nam=QWEA10510170"
	{
	  "dateNaissance": "2010-01-01",
	  "sexe": "FEMININ"
	}

API Java
--------

Pour intégrer l'API dans un projet Maven en Java, ajouter la dépendance suivante dans le fichier pom.xml:

        <dependency>
            <groupId>ca.qc.inspq</groupId>
            <artifactId>utilitaire-nam-api</artifactId>
            <version>${utilitaire-nam.version}</version>
            <exclusions>
                <exclusion>
                    <artifactId>javax.ws.rs-api</artifactId>
                    <groupId>javax.ws.rs</groupId>
                </exclusion>
            </exclusions>
        </dependency>

Interface utilisateur Web
-------------------------

Toutes les fonctionnalités peuvent être utilisées de manière interactive en utilisant l'interface utilisateur Web.

Son URL est [https://unam.santepublique.rtss.qc.ca/ui](https://unam.santepublique.rtss.qc.ca/ui/)

Voici les différents écrans:

### Valider un NAM

C'est l'écran d'accueil de l'application:

	https://unam.santepublique.rtss.qc.ca/ui/valider

### Générer un NAM

	https://unam.santepublique.rtss.qc.ca/ui/generer

### Information sur un NAM

	https://unam.santepublique.rtss.qc.ca/ui/info

Codes de sexes
==============

Voici les codes de sexes disponible:

| Code | Sexe     |
|------|----------|
| M    | Masculin |
| F    | Féminin  |

Codes des provinces
===================

Voici la liste des codes de provinces:

| Code | Province                  |
|------|---------------------------|
| AB   | Alberta                   |
| BC   | Colombie Britannique      |
| PE   | Île-du-Prince-Édourad     |
| MB   | Manitoba                  |
| NB   | Nouveau-Brunswick         |
| NS   | Nouvelle-Écosse           |
| NU   | Nunavut                   |
| ON   | Ontario                   |
| QC   | Québec                    |
| SK   | Saskatchewan              |
| NL   | Terre-Neuve-et-Labrador   |
| NT   | Territoires-du-Nord-Ouest |
| YK   | Yukon                     |

Développement
=============

L'utilitaire-nam est un regroupement de trois services associés aux numéros d'assurance maladie (NAM) 

Pour lancer l'utilitaire-nam sur un poste, il faut exécuter le projet utilitaire-NAM-Service comme une Spring Boot App. 
Une fois le projet lancé, l'interface de l'utilitaire-nam est accessible en utilisant l'URL http://localhost:8080/ui dans un navigateur web. 
L'interface présente chacun des services disponibles dans un onglet séparé. 

Chacun des services de l'utilitaire-nam peut être appelé soit en intégrant l'API dans un projet Java, ou par le biais de services web. Si l'intégration de l'API est choisie, il faut créer un objet 
serviceUtilitaireNAM. 
Pour appeler un des services par les services web, il faut utiliser l'URL de base (par exemple http://localhost:8080 si l'utilitaire-nam est lancé localement) et rattacher 
l'extension propre à chaque service.

Le premier service est la validation d'un NAM en fonction d'une province. Il faut fournir le NAM à valider ainsi que la province. Le service de validation retourne l'état de validité du NAM selon 
les critères de validité de la province choisie.
Pour valider un NAM avec l'API, il faut appeler la méthode validerNAM de serviceUtilitaireNAM.
Pour valider un NAM avec le service web, il faut ajouter /valider?nam=&province= à l'URL de base.

Le second service est la génération d'une liste de NAMs valides pour la province du Québec. Il faut fournir le prénom, le nom, la date de naissance et le sexe. En cas de succès, une liste de neuf NAMs 
est produite. Il est seulement possible de générer des NAMs valides pour la province du Québec.
Pour générer une liste de NAMs avec l'API, il faut appeler la méthode obtenirCombinaisonsValidesDeNAM de serviceUtilitaireNAM.
Pour générer une liste de NAMs avec le service web, il faut ajouter /generer?prenom=&nom=&datenaissance=&sexe= à l'URL de base. 

Le troisième service est l'obtention de la date de naissance et du sexe d'une personne à partir d'un NAM valide pour la province du Québec. Il faut fournir le NAM. Si le NAM est valide pour la province
du Québec, alors la date de naissance et le sexe sont retournés.
Pour obtenir les informations du NAM avec l'API, il faut appeler la méthode obtenirInformationsContenuesDansLeNam de serviceUtilitaireNAM. 
Pour obtenir des informations sur un NAM par le service web, il faut ajouter /info?nam= à l'URL de base.