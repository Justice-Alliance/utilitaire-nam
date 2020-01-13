# utilitaire-nam

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