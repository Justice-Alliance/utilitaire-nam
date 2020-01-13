<div id="page">

<div id="main" class="aui-page-panel">

<div id="main-header">

<div id="breadcrumb-section">

1.  [[Développement applicatif](index.html)]{}

</div>

[ Développement applicatif : Utilitaire NAM ]{#title-text} {#title-heading .pagetitle}
==========================================================

</div>

<div id="content" class="view">

<div class="page-metadata">

Created by [ Philippe Gauthier]{.author}, last modified on 2020-01-13

</div>

<div id="main-content" class="wiki-content group">

<div class="toc-macro rbtoc1578939435828">

-   [Description du projet](#UtilitaireNAM-Descriptionduprojet)
-   [Intégration et déploiement en
    continue](#UtilitaireNAM-Intégrationetdéploiementencontinue)
-   [Utilisation de l'outil](#UtilitaireNAM-Utilisationdel'outil)
    -   [Service Web](#UtilitaireNAM-ServiceWeb)
        -   [Valider un numéro d'assurance
            maladie.](#UtilitaireNAM-Validerunnumérod'assurancemaladie.)
        -   [Générer un numéro d'assurance
            maladie](#UtilitaireNAM-Générerunnumérod'assurancemaladie)
        -   [Obtenir les informations sur un
            NAM](#UtilitaireNAM-ObtenirlesinformationssurunNAM)
    -   [API Java](#UtilitaireNAM-APIJava)
    -   [Interface utilisateur
        Web](#UtilitaireNAM-InterfaceutilisateurWeb)
        -   [Valider un NAM](#UtilitaireNAM-ValiderunNAM)
        -   [Générer un NAM](#UtilitaireNAM-GénérerunNAM)
        -   [Information sur un NAM](#UtilitaireNAM-InformationsurunNAM)
-   [Codes de sexes](#UtilitaireNAM-Codesdesexes)
-   [Codes des provinces](#UtilitaireNAM-Codesdesprovinces)

</div>

Description du projet {#UtilitaireNAM-Descriptionduprojet}
=====================

Utilitaire NAM est, comme le nom l'indique, un outil permettant de
manipuler les numéros d'assurance maladie.

Il a été conçu pour être utilisé par les applications en offrant un API
REST ayant les fonctionnalités suivantes:

1.  Valider un numéro d'assurance maladie pour une des province
    Canadienne.
2.  Générer un numéro d'assurance maladie fictif pour une des provinces
    Canadienne.
3.  Donner les informations à propos d'un numéro d'assurance maladie
    pour une des province Canadienne.

L'outil inclus également un interface utilisateur Web.

Le code source de l'application est disponible sur la Forge
Gouvernementale dans le dépôt:
<https://gitlab.forge.gouv.qc.ca/inspq/utilitaire-nam.git>

L'URL de base d'utilitaire NAM en production est
[https://unam.santepublique.rtss.qc.ca.](https://unam.santepublique.rtss.qc.ca){.external-link}

L'application a été déployé pour assurer un haute disponibilité du
service. Les services web peuvent donc être utilisé par les applications
ayant une cote de 4 en disponibilité.

Pour la confidentialité, le protocole TLS est utilisé pour chiffrer les
communications avec le service et aucune donnée n'est stockée par
utilitaire NAM.

Intégration et déploiement en continue {#UtilitaireNAM-Intégrationetdéploiementencontinue}
======================================

Utilitaire NAM est géré en mode dev/ops. Ce projet est utilisé comme
cobail pour introduire les meilleurs pratiques dans la gestion d'un
produit en DEV/OPS.

La gestion du cycle de vie d'utilitaire NAM se fait par des pipelines
dans Jenkins. Les trois principaux pipelines sont les suivant:

<div class="table-wrap">

  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  Pipeline                        Description                                                                                                                                                                                                                                                                                              URL
  ------------------------------- -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- -----------------------------------------------------------------------------------------
  utilitaire-nam-livraison        Ce Pipeline s'exécuter lorsqu'un commit est fait dans le projet.                                                                                                                                                                                                                                         <https://jenkins.dev.inspq.qc.ca/job/utilitaire-nam/job/utilitaire-nam-livraison/>
                                                                                                                                                                                                                                                                                                                                           
                                  Il fait la construction, l'exécution des tests unitaires, l'exécution des tests du domaine (Cucumber) et le déploiement en DEV3                                                                                                                                                                          

  utilitaire-nam-livraison-nuit   Ce pipeline s'exécute à tous les soirs.                                                                                                                                                                                                                                                                  <https://jenkins.dev.inspq.qc.ca/job/utilitaire-nam/job/utilitaire-nam-livraison-nuit/>
                                                                                                                                                                                                                                                                                                                                           
                                  En plus des opérations du pipeline de livraison précédent, il fais les tests de sécurités de l'image, l'analyse statique de code, l'analyse SonarQube, le déploiement dans les environnements DEV3, DEV2, les tests d'intégrations et offre la possibilité de faire un étiquetage (tag) de la version.   
                                                                                                                                                                                                                                                                                                                                           
                                  Si une nouvel étiquette est créé, le pipeline demande ua pilote s'il doit déployer dans l'environnement DEV. Si oui l'application est déployé en DEV et les tests d'intégration sont lancés.                                                                                                             

  utilitaire-nam-livraison-prod   Ce pipeline s'exécute lorsqu'un nouvel étiquetage (tag) de l'application est fait.                                                                                                                                                                                                                       <https://jenkins.inspq.qc.ca/job/utilitaire-nam/job/utilitaire-nam-livraison-prod/>
                                                                                                                                                                                                                                                                                                                                           
                                  Il déploie l'application dans les environnements PP et PROD et exécute les tests d'intégration. L'application n'est déployé, dans chacun des environnements, que si le pilote confirme à partir de l'interface utilisateur de Jenkins.                                                                   
  ----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

</div>

Utilisation de l'outil {#UtilitaireNAM-Utilisationdel'outil}
======================

Utilitaire NAM peut-être utilisé de 3 manières:

-   service Web REST,
-   API JAVA en artefact Maven,
-   interface utilisateur Web.

L'utilisation du service Web est privilégié car elle minimise les
dépendances entre l'application développée et le cycle d'évolution
d'utilitaire NAM.

Service Web {#UtilitaireNAM-ServiceWeb}
-----------

Voici les trois cas d'utilisation de l'application:

### Valider un numéro d'assurance maladie. {#UtilitaireNAM-Validerunnumérod'assurancemaladie.}

Pour valider un numéro d'assurance Maladie, le service doit être utilisé
avec le verbe GET, l'URI /nam/valider et les paramètres suivants sous
forme de paramètre de requête (query params).

<div class="table-wrap">

  Paramètre   Description
  ----------- ----------------------------------------------------
  nam         Numéro d'assurance maladie à valider
  province    Province qui a émis le numéro d'assurance maladie:

</div>

La réponse retourné par le service est:

<div class="table-wrap">

  Réponse           Description
  ----------------- --------------------------------------------------------------
  true              Le NAM est valide
  false             Le NAM n'est pas valide
  400 bad request   Message d'erreur lorsque les paramètres ne sont pas valides.

</div>

Pour valider un NAM, utiliser une requête du verbe GET ayant le format
suivant:

<div class="code panel pdl" style="border-width: 1px;">

<div class="codeContent panelContent pdl">

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
https://unam.santepublique.rtss.qc.ca/nam/valider?nam=NumeroAValider&province=CodeDeProvince
```

</div>

</div>

Voici un exemple d'appel avec l'outil curl:

<div class="code panel pdl" style="border-width: 1px;">

<div class="codeContent panelContent pdl">

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
curl -X GET "https://unam.santepublique.rtss.qc.ca/nam/valider?nam=ASDA20011313&province=QC"
true
```

</div>

</div>

### Générer un numéro d'assurance maladie {#UtilitaireNAM-Générerunnumérod'assurancemaladie}

Pour générer un numéro d'assurance Maladie, le service doit être utilisé
avec le verbe GET, l'URI /nam/generer et les paramètres suivants sous
forme de paramètre de requête (query params).

<div class="table-wrap">

  Paramètre       Description
  --------------- -------------------------------------------------------
  nom             Nom de famille de l'usager pour lequel générer le NAM
  prenom          Prénom de l'usager
  sexe            Code de sexe de l'usager
  datenaissance   Date de naissance de l'usager en format AAAA-MM-JJ
  province        Code de province du NAM à générer

</div>

Le service retourne un liste de NAM valide pour les paramètres fournis
en format JSON.

Pour générer un NAM, utiliser une requête du verbe GET ayant le format
suivant:

<div class="code panel pdl" style="border-width: 1px;">

<div class="codeContent panelContent pdl">

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
https://unam.santepublique.rtss.qc.ca/nam/generer?nom=NomDeFamilleDeLUsager&prenom=PrenomDeLUsager&sexe=CodeDeSexe&datenaissance=DateDeNaissanceDeLUsager&province=CodeDeProvince
```

</div>

</div>

Voici un exemple d'appel avec l'outil curl:

<div class="code panel pdl" style="border-width: 1px;">

<div class="codeContent panelContent pdl">

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
curl -X GET "https://unam.santepublique.rtss.qc.ca/nam/generer?nom=qwe&prenom=Aasd&sexe=M&datenaissance=2010-01-01&province=QC"
["QWEA10010110","QWEA10010121","QWEA10010132","QWEA10010143","QWEA10010154","QWEA10010165","QWEA10010176","QWEA10010187","QWEA10010198"]
```

</div>

</div>

### Obtenir les informations sur un NAM {#UtilitaireNAM-ObtenirlesinformationssurunNAM}

Pour obtenir les informations sur un numéro d'assurance Maladie, le
service doit être utilisé avec le verbe GET, l'URI /nam/info et les
paramètres suivants sous forme de paramètre de requête (query params).

<div class="table-wrap">

  Paramètre   Description
  ----------- -----------------------------------------------------------------
  nam         Numéro d'assurance maladie pour lequel obtenir les infromations

</div>

Le service retourne les informations suivante à propos du NAM en format
JSON:

<div class="table-wrap">

  Champ           Description
  --------------- -----------------------------------------------------------------
  dateNaissance   Date de naissance de l'usager
  sexe            Sexe de l'usager. Les valeurs possible sont MASCULIN ou FEMININ

</div>

Pour obtenir les informations sur un NAM, utiliser une requête du verbe
GET ayant le format suivant:

<div class="code panel pdl" style="border-width: 1px;">

<div class="codeContent panelContent pdl">

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
https://unam.santepublique.rtss.qc.ca/nam/info?nam=NumeroAssuranceMaladiePourLequelOnVeutDesInformations
```

</div>

</div>

Voici un exemple de requête avec l'outil curl:

<div class="code panel pdl" style="border-width: 1px;">

<div class="codeContent panelContent pdl">

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
curl -X GET "https://unam.santepublique.rtss.qc.ca/nam/info?nam=QWEA10510170"
{
  "dateNaissance": "2010-01-01",
  "sexe": "FEMININ"
}
```

</div>

</div>

API Java {#UtilitaireNAM-APIJava}
--------

Pour intégrer l'API dans un projet Maven en Java, ajouter la dépendance
suivante dans le fichier pom.xml:

<div class="code panel pdl" style="border-width: 1px;">

<div class="codeContent panelContent pdl">

``` {.syntaxhighlighter-pre data-syntaxhighlighter-params="brush: java; gutter: false; theme: Confluence" data-theme="Confluence"}
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
```

</div>

</div>

Interface utilisateur Web {#UtilitaireNAM-InterfaceutilisateurWeb}
-------------------------

Toutes les fonctionnalités peuvent être utilisées de manière interactive
en utilisant l'interface utilisateur Web.

Son URL est
[https://unam.santepublique.rtss.qc.ca/ui](https://unam.santepublique.rtss.qc.ca/ui/){.external-link}

Voici les différents écrans:

### Valider un NAM {#UtilitaireNAM-ValiderunNAM}

C'est l'écran d'accueil de l'application:

[![](attachments/1071481676/1071515048.png){.confluence-embedded-image}]{.confluence-embedded-file-wrapper}

### Générer un NAM {#UtilitaireNAM-GénérerunNAM}

[![](attachments/1071481676/1071482130.png){.confluence-embedded-image}]{.confluence-embedded-file-wrapper}

### Information sur un NAM {#UtilitaireNAM-InformationsurunNAM}

[![](attachments/1071481676/1071515053.png){.confluence-embedded-image}]{.confluence-embedded-file-wrapper}

Codes de sexes {#UtilitaireNAM-Codesdesexes}
==============

Voici les codes de sexes disponible:

<div class="table-wrap">

  Code   Sexe
  ------ ----------
  M      Masculin
  F      Féminin

</div>

Codes des provinces {#UtilitaireNAM-Codesdesprovinces}
===================

Voici la liste des codes de provinces:

<div class="table-wrap">

  Code   Province
  ------ ---------------------------
  AB     Alberta
  BC     Colombie Britannique
  PE     Île-du-Prince-Édourad
  MB     Manitoba
  NB     Nouveau-Brunswick
  NS     Nouvelle-Écosse
  NU     Nunavut
  ON     Ontario
  QC     Québec
  SK     Saskatchewan
  NL     Terre-Neuve-et-Labrador
  NT     Territoires-du-Nord-Ouest
  YK     Yukon

</div>

</div>

<div class="pageSection group">

<div class="pageSectionHeader">

Attachments: {#attachments .pageSectionTitle}
------------

</div>

<div class="greybox" align="left">

![](images/icons/bullet_blue.gif){width="8" height="8"}
[image2020-1-13\_12-56-30.png](attachments/1071481676/1071515048.png)
(image/png)\
![](images/icons/bullet_blue.gif){width="8" height="8"}
[image2020-1-13\_12-57-33.png](attachments/1071481676/1071482130.png)
(image/png)\
![](images/icons/bullet_blue.gif){width="8" height="8"}
[image2020-1-13\_12-58-28.png](attachments/1071481676/1071515053.png)
(image/png)\

</div>

</div>

</div>

</div>

<div id="footer" role="contentinfo">

<div class="section footer-body">

Document generated by Confluence on 2020-01-13 13:17

<div id="footer-logo">

[Atlassian](http://www.atlassian.com/)

</div>

</div>

</div>

</div>
